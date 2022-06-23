package com.example.juniorjavatask.ORM;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findUserEntitiesByUsername(String username);

}