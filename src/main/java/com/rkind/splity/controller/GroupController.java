package com.rkind.splity.controller;

import com.rkind.splity.dto.*;
import com.rkind.splity.entity.Group;
import com.rkind.splity.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.IOException;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    
    @GetMapping("/has-group/{userId}")
    public ResponseEntity<Boolean> hasGroup(@PathVariable Long userId) {
        return ResponseEntity.ok(groupService.hasGroup(userId));
    }

    
    @PostMapping("/create")
    public ResponseEntity<Group> create(@RequestBody CreateGroupRequest request) {

        Group group = groupService.createGroup(
                request.getUserId(),
                request.getName(),
                request.getCode(),
                request.getDpUri()
        );

        return ResponseEntity.ok(group);
    }

    @GetMapping("/{groupId}/messages")
    public ResponseEntity<List<GroupMessageResponseDto>> messages(
            @PathVariable Long groupId,
            @RequestParam Long userId) {

        return ResponseEntity.ok(groupService.getGroupMessages(groupId, userId));
    }

    @PostMapping("/messages")
    public ResponseEntity<GroupMessageResponseDto> sendMessage(@RequestBody SendGroupMessageRequest request) {
        return ResponseEntity.ok(groupService.sendMessage(request));
    }

    @PostMapping("/upload-document")
    public ResponseEntity<Map<String, Object>> uploadDocument(
            @RequestParam("file") MultipartFile file) throws IOException {

        Path uploadDir = Paths.get("uploads/documents");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String originalName = file.getOriginalFilename();

        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        String newFileName = UUID.randomUUID() + extension;

        Path destination = uploadDir.resolve(newFileName);

        Files.copy(
                file.getInputStream(),
                destination,
                StandardCopyOption.REPLACE_EXISTING
        );

        Map<String, Object> result = new HashMap<>();

        result.put(
                "url",
                "http://10.16.251.94:8080/uploads/documents/" + newFileName
        );

        result.put(
                "fileName",
                originalName
        );

        result.put(
                "fileSize",
                file.getSize()
        );

        result.put(
                "mimeType",
                file.getContentType()
        );

        return ResponseEntity.ok(result);
    }
    @PostMapping("/upload-audio")
    public ResponseEntity<Map<String, Object>> uploadAudio(
            @RequestParam("file") MultipartFile file) throws IOException {

        Path uploadDir = Paths.get("uploads/audio");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String originalName = file.getOriginalFilename();

        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        String newFileName = UUID.randomUUID() + extension;

        Path destination = uploadDir.resolve(newFileName);

        Files.copy(
                file.getInputStream(),
                destination,
                StandardCopyOption.REPLACE_EXISTING
        );

        Map<String, Object> result = new HashMap<>();

        result.put(
                "url",
                "http://10.16.251.94:8080/uploads/audio/" + newFileName
        );

        result.put("fileName", originalName);
        result.put("fileSize", file.getSize());
        result.put("mimeType", file.getContentType());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/messages/delete")
    public ResponseEntity<?> deleteMessage(@RequestParam Long messageId,
                                           @RequestParam Long groupId,
                                           @RequestParam Long userId) {
        groupService.deleteMessage(messageId, groupId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/messages/react")
    public ResponseEntity<GroupMessageResponseDto> reactToMessage(@RequestBody ReactToMessageRequest request) {
        return ResponseEntity.ok(groupService.reactToMessage(request));
    }


    
    @PostMapping("/join-request")
    public ResponseEntity<?> joinRequest(@RequestBody JoinGroupRequest request) {

        groupService.sendJoinRequest(
                request.getUserId(),
                request.getCode()
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{groupId}/join-requests")
    public ResponseEntity<List<JoinRequestResponseDto>> pending(
            @PathVariable Long groupId) {

        return ResponseEntity.ok(
                groupService.pendingRequests(groupId)
        );
    }

    @GetMapping("/user-join-requests/{userId}")
    public ResponseEntity<List<JoinRequestResponseDto>> userJoinRequests(@PathVariable Long userId) {
        return ResponseEntity.ok(groupService.getUserJoinRequests(userId));
    }

    
    @PostMapping("/handle-join-request")
    public ResponseEntity<?> handle(@RequestBody HandleJoinRequestDto dto) {

        groupService.handleJoinRequest(
                dto.getRequestId(),
                dto.isApprove()
        );

        return ResponseEntity.ok().build();
    }

    
    @GetMapping("/my/{userId}")
    public ResponseEntity<Group> myGroup(@PathVariable Long userId) {

        try {
            return ResponseEntity.ok(groupService.getMyGroup(userId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-code/{code}")
    public ResponseEntity<Group> byCode(@PathVariable String code) {
        try {
            return ResponseEntity.ok(groupService.getGroupByCode(code));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/exit/{userId}")
    public ResponseEntity<?> exitGroup(@PathVariable("userId") long userId) {

        groupService.exitGroup(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/poll")
    public ResponseEntity<?> createPoll(
            @RequestBody CreatePollRequest request) {

        groupService.createPoll(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/poll/vote")
    public ResponseEntity<PollResponseDto> votePoll(
            @RequestBody VotePollRequest request) {

        return ResponseEntity.ok(groupService.votePoll(request));
    }

    @PostMapping("/messages/edit")
    public ResponseEntity<GroupMessageResponseDto> editMessage(
            @RequestBody UpdateMessageRequest request) {

        return ResponseEntity.ok(
                groupService.editMessage(request)
        );
    }

    @PostMapping("/upload-video")
    public ResponseEntity<Map<String, Object>> uploadVideo(
            @RequestParam("file") MultipartFile file) throws IOException {

        Path uploadDir = Paths.get("uploads/videos");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String originalName = file.getOriginalFilename();

        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        String newFileName = UUID.randomUUID() + extension;

        Path destination = uploadDir.resolve(newFileName);

        Files.copy(
                file.getInputStream(),
                destination,
                StandardCopyOption.REPLACE_EXISTING
        );

        Map<String, Object> result = new HashMap<>();

        result.put(
                "url",
                "http://10.16.251.94:8080/uploads/videos/" + newFileName
        );

        result.put("fileName", originalName);
        result.put("fileSize", file.getSize());
        result.put("mimeType", file.getContentType());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<GroupMemberResponseDto>> getMembers(
            @PathVariable Long groupId) {

        return ResponseEntity.ok(
                groupService.getGroupMembers(groupId)
        );
    }


    @PostMapping("/update-description")
    public ResponseEntity<?> updateDescription(
            @RequestBody UpdateGroupDescriptionRequest request) {

        groupService.updateGroupDescription(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{groupId}/inquiry")
    public ResponseEntity<List<GroupMessageResponseDto>> getInquiry(
            @PathVariable Long groupId,
            @RequestParam String type) {

        return ResponseEntity.ok(
                groupService.getInquiry(groupId, type)
        );
    }

    @PostMapping("/clear-chat")
    public ResponseEntity<?> clearChat(
            @RequestBody ClearChatRequest request) {

        groupService.clearChat(
                request.getGroupId(),
                request.getUserId()
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-fcm-token")
    public ResponseEntity<?> updateFcmToken(
            @RequestBody UpdateFcmTokenRequest request) {

        try {

            System.out.println("USER = " + request.getUserId());
            System.out.println("TOKEN = " + request.getToken());

            groupService.updateFcmToken(
                    request.getUserId(),
                    request.getToken()
            );

            return ResponseEntity.ok("SUCCESS");

        } catch (Exception e) {

            e.printStackTrace();   // <-- sabse important

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

}
