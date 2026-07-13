package com.rkind.splity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rkind.splity.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByCode(String code);

}