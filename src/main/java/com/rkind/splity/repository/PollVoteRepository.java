package com.rkind.splity.repository;

import com.rkind.splity.entity.PollOption;
import com.rkind.splity.entity.PollVote;
import com.rkind.splity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PollVoteRepository extends JpaRepository<PollVote, Long> {

    List<PollVote> findByOption(PollOption option);

    Optional<PollVote> findByOptionAndUser(
            PollOption option,
            User user
    );
}