package com.rkind.splity.service;

import com.rkind.splity.dto.*;
import com.rkind.splity.entity.*;
import com.rkind.splity.repository.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.rkind.splity.repository.PollRepository;
import com.rkind.splity.repository.PollOptionRepository;
import com.rkind.splity.repository.PollVoteRepository;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupJoinRequestRepository joinRequestRepository;
    private final GroupMessageRepository groupMessageRepository;
    private final UserRepository userRepository;
    private final PollRepository pollRepository;
    private final PollOptionRepository pollOptionRepository;
    private final PollVoteRepository pollVoteRepository;
    private final GroupChatClearRepository groupChatClearRepository;
    private final OnlineUserService onlineUserService;
    private final FirebaseNotificationService firebaseNotificationService;

    private final SimpMessagingTemplate messagingTemplate;

    public GroupService(GroupRepository groupRepository,
                        GroupMemberRepository groupMemberRepository,
                        GroupJoinRequestRepository joinRequestRepository,
                        GroupMessageRepository groupMessageRepository,
                        UserRepository userRepository, PollRepository pollRepository,
                        PollOptionRepository pollOptionRepository,
                        PollVoteRepository pollVoteRepository, SimpMessagingTemplate messagingTemplate,
                        GroupChatClearRepository groupChatClearRepository, OnlineUserService onlineUserService,
                        FirebaseNotificationService firebaseNotificationService) {

        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.joinRequestRepository = joinRequestRepository;
        this.groupMessageRepository = groupMessageRepository;
        this.userRepository = userRepository;
        this.pollRepository = pollRepository;
        this.pollOptionRepository = pollOptionRepository;
        this.pollVoteRepository = pollVoteRepository;
        this.messagingTemplate = messagingTemplate;
        this.groupChatClearRepository = groupChatClearRepository;
        this.onlineUserService = onlineUserService;
        this.firebaseNotificationService = firebaseNotificationService;
    }


    public Group createGroup(Long userId,
                          String name,
                          String code,
                          String dpUrl) {

        // One user -> one group rule
        if (groupMemberRepository.existsByUserId(userId)) {
            throw new RuntimeException("User already belongs to a group");
        }

        Group group = new Group();
        group.setName(name);
        group.setCode(code);
        group.setDpUrl(dpUrl);
        group.setCreatedBy(userId);
        group.setCreatedAt(LocalDateTime.now());

        System.out.println("NAME = " + group.getName());
        System.out.println("CODE = " + group.getCode());
        System.out.println("DP = " + (group.getDpUrl() == null ? "null" : group.getDpUrl().length()));
        System.out.println("DESCRIPTION = " + (group.getDescription() == null ? "null" : group.getDescription().length()));

        groupRepository.save(group);

        GroupMember member = new GroupMember();
        member.setGroupId(group.getId());
        member.setUserId(userId);
        member.setJoinedAt(LocalDateTime.now());

        groupMemberRepository.save(member);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String userName = user.getDisplayName();

        if (userName == null || userName.trim().isEmpty()) {
            userName = user.getPhoneNumber();
        }

        addSystemMessage(
                group.getId(),
                userId,
                name + " created the group"
        );

        return group;

    }


    public void sendJoinRequest(Long userId, String code) {

        // One user -> one group rule
        if (groupMemberRepository.existsByUserId(userId)) {
            throw new RuntimeException("User already belongs to a group");
        }

        Group group = groupRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid group code"));

        // Sirf PENDING request check karo
        if (joinRequestRepository
                .findByGroupIdAndUserIdAndStatus(
                        group.getId(),
                        userId,
                        "PENDING")
                .isPresent()) {

            throw new RuntimeException("Request already sent");
        }

        GroupJoinRequest req = new GroupJoinRequest();
        req.setGroupId(group.getId());
        req.setUserId(userId);
        req.setStatus("PENDING");
        req.setRequestedAt(LocalDateTime.now());

        joinRequestRepository.save(req);
    }

   
    public List<JoinRequestResponseDto> pendingRequests(Long groupId) {

        return joinRequestRepository
                .findByGroupIdAndStatus(groupId, "PENDING")
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<JoinRequestResponseDto> getUserJoinRequests(Long userId) {
        return joinRequestRepository.findByUserIdOrderByRequestedAtDesc(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public void handleJoinRequest(Long requestId, boolean approve) {

        GroupJoinRequest req = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!"PENDING".equals(req.getStatus())) {
            throw new RuntimeException("Already processed");
        }

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String name = user.getDisplayName();

        if (name == null || name.trim().isEmpty()) {
            name = user.getPhoneNumber();
        }

        if (approve) {

            if (groupMemberRepository.existsByUserId(req.getUserId())) {

                req.setStatus("REJECTED");
                joinRequestRepository.save(req);

                List<GroupJoinRequest> pending =
                        joinRequestRepository.findByUserIdAndStatus(
                                req.getUserId(),
                                "PENDING"
                        );

                for (GroupJoinRequest r : pending) {
                    r.setStatus("REJECTED");
                }

                joinRequestRepository.saveAll(pending);

                addSystemMessage(
                        req.getGroupId(),
                        req.getUserId(),
                        name + "'s join request was rejected"
                );

                return;
            }

            GroupMember member = new GroupMember();
            member.setGroupId(req.getGroupId());
            member.setUserId(req.getUserId());
            member.setJoinedAt(LocalDateTime.now());

            groupMemberRepository.save(member);

            req.setStatus("APPROVED");
            joinRequestRepository.save(req);

            List<GroupJoinRequest> pending =
                    joinRequestRepository.findByUserIdAndStatus(
                            req.getUserId(),
                            "PENDING"
                    );

            for (GroupJoinRequest r : pending) {

                if (!r.getId().equals(req.getId())) {
                    r.setStatus("REJECTED");
                }

            }

            joinRequestRepository.saveAll(pending);

            addSystemMessage(
                    req.getGroupId(),
                    req.getUserId(),
                    name + " joined the group"
            );

        } else {

            req.setStatus("REJECTED");
            joinRequestRepository.save(req);

            addSystemMessage(
                    req.getGroupId(),
                    req.getUserId(),
                    name + "'s join request was rejected"
            );
        }
    }

   
    public Group getMyGroup(Long userId) {

        List<GroupMember> members =
                groupMemberRepository.findAllByUserId(userId);

        if (members.isEmpty()) {
            throw new RuntimeException("User not in any group");
        }

        GroupMember member = members.get(0);

        return groupRepository.findById(member.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    
    public boolean hasGroup(Long userId) {
        return groupMemberRepository.existsByUserId(userId);
    }

    public Group getGroupByCode(String code) {
        return groupRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    public void exitGroup(Long userId) {

    List<GroupMember> members =
            groupMemberRepository.findAllByUserId(userId);

    if (members.isEmpty()) {
        throw new RuntimeException("User not in any group");
    }

        GroupMember member = members.get(0);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String name = user.getDisplayName();

        if (name == null || name.trim().isEmpty()) {
            name = user.getPhoneNumber();
        }

        addSystemMessage(
                member.getGroupId(),
                userId,
                name + " left the group"
        );

        groupMemberRepository.delete(member);

        joinRequestRepository.deleteByGroupIdAndUserId(
                member.getGroupId(),
                userId
        );
}

    public List<GroupMessageResponseDto> getGroupMessages(Long groupId, Long userId) {

        validateMembership(groupId, userId);

        GroupMember member =
                groupMemberRepository
                        .findByUserIdAndGroupId(userId, groupId)
                        .orElseThrow(() ->
                                new RuntimeException("Member not found"));

        LocalDateTime fromTime = member.getJoinedAt();

        GroupChatClear clear =
                groupChatClearRepository
                        .findByGroupIdAndUserId(groupId, userId)
                        .orElse(null);

        if (clear != null && clear.getClearedAt().isAfter(fromTime)) {
            fromTime = clear.getClearedAt();
        }

        List<GroupMessage> messages =
                groupMessageRepository
                        .findByGroupIdAndCreatedAtGreaterThanEqualOrderByCreatedAtAsc(
                                groupId,
                                fromTime
                        );

        return messages.stream()
                .map(message -> toMessageDto(message, userId))
                .collect(Collectors.toList());
    }

    public GroupMessageResponseDto sendMessage(SendGroupMessageRequest request) {

        validateMembership(request.getGroupId(), request.getSenderId());

        if (request.getMessageText() == null || request.getMessageText().isEmpty()) {
            throw new RuntimeException("Message cannot be empty");
        }

        if (request.getReplyToMessageId() != null) {

            groupMessageRepository
                    .findByIdAndGroupId(
                            request.getReplyToMessageId(),
                            request.getGroupId())
                    .orElseThrow(() ->
                            new RuntimeException("Reply message not found"));
        }

        GroupMessage message = new GroupMessage();

        message.setGroupId(request.getGroupId());
        message.setSenderId(request.getSenderId());

        message.setMessageText(request.getMessageText());

        message.setMessageType(
                normalizeMessageType(request.getMessageType())
        );

        // Document / Image metadata
        message.setFileName(request.getFileName());
        message.setFileSize(request.getFileSize());
        message.setFileMimeType(request.getFileMimeType());

        message.setReplyToMessageId(request.getReplyToMessageId());

        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        groupMessageRepository.save(message);

        GroupMessageResponseDto dto =
                toMessageDto(message, -1L);

        User sender = userRepository.findById(request.getSenderId())
                .orElse(null);

        String senderName;

        if (sender == null) {
            senderName = "Splity";
        } else if (sender.getDisplayName() != null
                && !sender.getDisplayName().trim().isEmpty()) {
            senderName = sender.getDisplayName();
        } else {
            senderName = sender.getPhoneNumber();
        }

        sendGroupMessageNotification(
                request.getGroupId(),
                request.getSenderId(),
                senderName,
                request.getMessageText()
        );

        messagingTemplate.convertAndSend(
                "/topic/group/" + request.getGroupId(),
                dto
        );

        return dto;
    }

    public void deleteMessage(Long messageId, Long groupId, Long userId) {
        validateMembership(groupId, userId);

        GroupMessage message = groupMessageRepository.findByIdAndGroupId(messageId, groupId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!message.getSenderId().equals(userId) && !group.getCreatedBy().equals(userId)) {
            throw new RuntimeException("You cannot delete this message");
        }

        groupMessageRepository.delete(message);
    }

    public GroupMessageResponseDto reactToMessage(ReactToMessageRequest request) {
        validateMembership(request.getGroupId(), request.getUserId());

        GroupMessage message = groupMessageRepository.findByIdAndGroupId(request.getMessageId(), request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setReaction(request.getReaction());
        message.setUpdatedAt(LocalDateTime.now());
        groupMessageRepository.save(message);

        return toMessageDto(message, request.getUserId());
    }
    @Transactional
    public GroupMessageResponseDto editMessage(UpdateMessageRequest request) {

        GroupMessage message = groupMessageRepository
                .findById(request.getMessageId())
                .orElseThrow(() ->
                        new RuntimeException("Message not found"));

        if (!message.getSenderId().equals(request.getUserId())) {
            throw new RuntimeException("You can edit only your own message");
        }

        if (!"TEXT".equalsIgnoreCase(message.getMessageType())) {
            throw new RuntimeException("Only text messages can be edited");
        }

        message.setMessageText(request.getMessage());

        message.setUpdatedAt(LocalDateTime.now());

        groupMessageRepository.save(message);

        return toMessageDto(message, request.getUserId());
    }

    private JoinRequestResponseDto toDto(GroupJoinRequest request) {
        JoinRequestResponseDto dto = new JoinRequestResponseDto();
        dto.setId(request.getId());
        dto.setGroupId(request.getGroupId());
        dto.setUserId(request.getUserId());
        dto.setStatus(request.getStatus());

        User user = userRepository.findById(request.getUserId()).orElse(null);
        dto.setUserPhoneNumber(user != null ? user.getPhoneNumber() : null);
        dto.setUserName("User " + request.getUserId());

        if (request.getRequestedAt() != null) {
            dto.setRequestedAt(request.getRequestedAt()
                    .format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")));
        }

        return dto;
    }

    private GroupMessageResponseDto toMessageDto(GroupMessage message, Long currentUserId) {
        GroupMessageResponseDto dto = new GroupMessageResponseDto();
        dto.setId(message.getId());
        dto.setGroupId(message.getGroupId());
        dto.setSenderId(message.getSenderId());
        dto.setMessageText(message.getMessageText());
        dto.setMessageType(message.getMessageType());
        dto.setFileName(message.getFileName());
        dto.setFileSize(message.getFileSize());
        dto.setFileMimeType(message.getFileMimeType());
        dto.setReplyToMessageId(message.getReplyToMessageId());
        dto.setReaction(message.getReaction());
        boolean mine =
                currentUserId != null &&
                        currentUserId > 0 &&
                        message.getSenderId().equals(currentUserId);

        dto.setMine(mine);

        User sender =
                userRepository.findById(message.getSenderId()).orElse(null);

        if (mine) {

            dto.setSenderName("You");

        } else {

            dto.setSenderName(
                    sender != null &&
                            sender.getPhoneNumber() != null
                            ? sender.getPhoneNumber()
                            : "User " + message.getSenderId()
            );

        }

        dto.setSenderPhoneNumber(sender != null ? sender.getPhoneNumber() : null);
        dto.setCreatedAt(message.getCreatedAt() == null
                ? null
                : message.getCreatedAt().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")));

        if ("POLL".equals(message.getMessageType()) && message.getPoll() != null) {

            Poll poll = message.getPoll();

            PollResponseDto pollDto = new PollResponseDto();

            pollDto.setPollId(poll.getId());
            pollDto.setQuestion(poll.getQuestion());
            pollDto.setMultipleAnswer(poll.isMultipleAnswer());

            List<PollOptionDto> optionDtos = new ArrayList<>();

            List<PollOption> options =
                    pollOptionRepository.findByPollId(poll.getId());

            for (PollOption option : options) {

                PollOptionDto optionDto = new PollOptionDto();

                optionDto.setId(option.getId());
                optionDto.setOptionText(option.getOptionText());

                int voteCount = pollVoteRepository.findByOption(option).size();
                optionDto.setVoteCount(voteCount);

                boolean voted =
                        pollVoteRepository.findByOptionAndUser(option, sender).isPresent();

                optionDto.setVoted(voted);

                optionDtos.add(optionDto);
            }

            pollDto.setOptions(optionDtos);

            dto.setPoll(pollDto);
        }

        if (message.getReplyToMessageId() != null) {
            groupMessageRepository.findById(message.getReplyToMessageId()).ifPresent(reply -> {
                dto.setReplyToMessageText(reply.getMessageText());
            });
        }

        return dto;
    }

    private String normalizeMessageType(String messageType) {
        if (messageType == null || messageType.trim().isEmpty()) {
            return "TEXT";
        }
        return messageType.trim().toUpperCase();
    }

    private void validateMembership(Long groupId, Long userId) {
        if (!groupMemberRepository.existsByUserIdAndGroupId(userId, groupId)) {
            throw new RuntimeException("User is not a member of this group");
        }
    }

    public void createPoll(CreatePollRequest request) {

        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getQuestion() == null || request.getQuestion().trim().isEmpty()) {
            throw new RuntimeException("Question is required");
        }

        if (request.getOptions() == null || request.getOptions().size() < 2) {
            throw new RuntimeException("Minimum 2 options required");
        }

        Poll poll = new Poll();

        poll.setGroup(group);
        poll.setCreatedBy(user);
        poll.setQuestion(request.getQuestion());
        poll.setMultipleAnswer(request.isMultipleAnswer());

        poll = pollRepository.save(poll);

        for (String option : request.getOptions()) {

            if (option == null || option.trim().isEmpty()) {
                continue;
            }

            PollOption pollOption = new PollOption();

            pollOption.setPoll(poll);
            pollOption.setOptionText(option.trim());

            pollOptionRepository.save(pollOption);
        }

        GroupMessage message = new GroupMessage();

        message.setGroupId(group.getId());

        message.setSenderId(user.getId());

        message.setMessageType("POLL");

        message.setMessageText(request.getQuestion());

        message.setPoll(poll);

        message.setCreatedAt(LocalDateTime.now());

        message.setUpdatedAt(LocalDateTime.now());

        groupMessageRepository.save(message);

    }

    @Transactional
    public PollResponseDto votePoll(VotePollRequest request) {

        Poll poll = pollRepository.findById(request.getPollId())
                .orElseThrow(() -> new RuntimeException("Poll not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Long> optionIds = request.getOptionIds();

        if (optionIds == null || optionIds.isEmpty()) {
            throw new RuntimeException("No option selected");
        }

        if (!poll.isMultipleAnswer() && optionIds.size() > 1) {
            throw new RuntimeException("Only one option allowed");
        }

        for (Long optionId : optionIds) {

            PollOption option = pollOptionRepository.findById(optionId)
                    .orElseThrow(() -> new RuntimeException("Option not found"));

            if (!option.getPoll().getId().equals(poll.getId())) {
                throw new RuntimeException("Invalid option");
            }

            if (pollVoteRepository.findByOptionAndUser(option, user).isPresent()) {
                continue;
            }

            PollVote vote = new PollVote();
            vote.setOption(option);
            vote.setUser(user);

            pollVoteRepository.save(vote);
        }

        PollResponseDto dto = new PollResponseDto();

        dto.setPollId(poll.getId());
        dto.setQuestion(poll.getQuestion());
        dto.setMultipleAnswer(poll.isMultipleAnswer());

        List<PollOptionDto> optionDtos = new ArrayList<>();

        for (PollOption option : pollOptionRepository.findByPollId(poll.getId())) {

            PollOptionDto optionDto = new PollOptionDto();

            optionDto.setId(option.getId());
            optionDto.setOptionText(option.getOptionText());

            int voteCount = pollVoteRepository.findByOption(option).size();
            optionDto.setVoteCount(voteCount);

            boolean voted =
                    pollVoteRepository.findByOptionAndUser(option, user).isPresent();

            optionDto.setVoted(voted);

            optionDtos.add(optionDto);
        }

        dto.setOptions(optionDtos);

        dto.setCreatedBy(
                poll.getCreatedBy() != null
                        ? poll.getCreatedBy().getPhoneNumber()
                        : ""
        );

        dto.setCreatedAt(
                poll.getCreatedAt() != null
                        ? poll.getCreatedAt().format(
                        DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"))
                        : ""
        );

        return dto;
    }


    public List<GroupMemberResponseDto> getGroupMembers(Long groupId) {

        List<GroupMember> members =
                groupMemberRepository.findAllByGroupId(groupId);

        List<Long> userIds = members.stream()
                .map(GroupMember::getUserId)
                .toList();

        List<User> users =
                userRepository.findAllByIdIn(userIds);

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        List<GroupMemberResponseDto> result = new ArrayList<>();

        for (User user : users) {

            GroupMemberResponseDto dto = new GroupMemberResponseDto();

            dto.setUserId(user.getId());

            dto.setPhoneNumber(user.getPhoneNumber());

            dto.setName(
                    user.getDisplayName() == null
                            || user.getDisplayName().trim().isEmpty()
                            ? user.getPhoneNumber()
                            : user.getDisplayName().trim()
            );

            dto.setProfilePicture(user.getProfilePicture());

            boolean isAdmin = user.getId().equals(group.getCreatedBy());

            dto.setAdmin(isAdmin);
            dto.setRole(isAdmin ? "Admin" : "Member");

            // Abhi sab offline rahenge
            // Baad me websocket se live update karenge.
            dto.setOnline(
                    onlineUserService.isOnline(user.getId())
            );

            result.add(dto);
        }

        // Alphabetical Sort (A → Z)
        result.sort((a, b) ->
                a.getName().compareToIgnoreCase(b.getName())
        );

        return result;
    }

    @Transactional
    public void updateGroupDescription(UpdateGroupDescriptionRequest request) {

        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!group.getCreatedBy().equals(request.getUserId())) {
            throw new RuntimeException("Only group admin can update description");
        }

        String description = request.getDescription();

        if (description != null) {
            description = description.trim();

            if (description.length() > 500) {
                throw new RuntimeException("Description cannot exceed 500 characters");
            }
        }

        group.setDescription(description);

        groupRepository.save(group);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String name = user.getDisplayName();

        if (name == null || name.trim().isEmpty()) {
            name = user.getPhoneNumber();
        }

        addSystemMessage(
                group.getId(),
                request.getUserId(),
                name + " updated the group description"
        );
    }

    private void addSystemMessage(
            Long groupId,
            Long senderId,
            String text
    ) {

        GroupMessage message = new GroupMessage();

        message.setGroupId(groupId);
        message.setSenderId(senderId);

        message.setMessageType("SYSTEM");
        message.setMessageText(text);

        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        groupMessageRepository.save(message);

        GroupMessageResponseDto dto =
                toMessageDto(message, -1L);

        messagingTemplate.convertAndSend(
                "/topic/group/" + groupId,
                dto
        );
    }

    public List<GroupMessageResponseDto> getInquiry(
            Long groupId,
            String type
    ) {

        List<GroupMessage> messages;

        switch (type.toUpperCase()) {

            case "MEDIA":

                messages = groupMessageRepository
                        .findByGroupIdAndMessageTypeInOrderByCreatedAtDesc(
                                groupId,
                                List.of("IMAGE", "VIDEO")
                        );
                break;

            case "DOCUMENTS":

                messages = groupMessageRepository
                        .findByGroupIdAndMessageTypeOrderByCreatedAtDesc(
                                groupId,
                                "DOCUMENT"
                        );
                break;

            case "AUDIO":

                messages = groupMessageRepository
                        .findByGroupIdAndMessageTypeOrderByCreatedAtDesc(
                                groupId,
                                "AUDIO"
                        );
                break;

            case "LOCATION":

                messages = groupMessageRepository
                        .findByGroupIdAndMessageTypeOrderByCreatedAtDesc(
                                groupId,
                                "LOCATION"
                        );
                break;

            case "NOTIFICATIONS":

                messages = groupMessageRepository
                        .findByGroupIdAndMessageTypeOrderByCreatedAtDesc(
                                groupId,
                                "SYSTEM"
                        );
                break;

            case "LINKS":

                messages = groupMessageRepository
                        .findByGroupIdAndMessageTypeOrderByCreatedAtDesc(
                                groupId,
                                "TEXT"
                        )
                        .stream()
                        .filter(m -> {

                            String text = m.getMessageText();

                            if (text == null) {
                                return false;
                            }

                            text = text.toLowerCase();

                            return text.contains("http://")
                                    || text.contains("https://")
                                    || text.contains("www.");
                        })
                        .toList();

                break;

            default:
                messages = new ArrayList<>();
        }

        return messages.stream()
                .map(m -> toMessageDto(m, -1L))
                .collect(Collectors.toList());
    }

    public void clearChat(Long groupId, Long userId) {

        GroupChatClear clear =
                groupChatClearRepository
                        .findByGroupIdAndUserId(groupId, userId)
                        .orElse(new GroupChatClear());

        clear.setGroupId(groupId);
        clear.setUserId(userId);
        clear.setClearedAt(LocalDateTime.now());

        groupChatClearRepository.save(clear);
    }


    public void updateFcmToken(Long userId, String token) {

        System.out.println("===== UPDATE FCM TOKEN =====");
        System.out.println("USER ID = " + userId);
        System.out.println("TOKEN = " + token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("USER FOUND = " + user.getId());

        user.setFcmToken(token);

        System.out.println("Saving...");

        userRepository.save(user);

        System.out.println("Saved Successfully");
    }

    private void sendGroupMessageNotification(
            Long groupId,
            Long senderId,
            String senderName,
            String message
    ) {

        List<GroupMember> members =
                groupMemberRepository.findAllByGroupId(groupId);

        List<Long> userIds = new ArrayList<>();

        for (GroupMember member : members) {

            if (!member.getUserId().equals(senderId)) {
                userIds.add(member.getUserId());
            }

        }

        List<User> users = userRepository.findAllByIdIn(userIds);


        for (User user : users) {

            System.out.println("========================");
            System.out.println("Sending to User : " + user.getId());
            System.out.println("Token : " + user.getFcmToken());
            System.out.println("========================");

            firebaseNotificationService.sendNotification(
                    user.getFcmToken(),
                    groupId,
                    senderId,
                    senderName,
                    message
            );

        }
    }
}
