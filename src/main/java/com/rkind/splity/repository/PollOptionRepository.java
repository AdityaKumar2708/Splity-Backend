package com.rkind.splity.repository;

import com.rkind.splity.entity.Poll;
import com.rkind.splity.entity.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PollOptionRepository extends JpaRepository<PollOption, Long> {

    List<PollOption> findByPollId(Long pollId);

    Optional<PollOption> findByPollAndId(Poll poll, Long id);

}