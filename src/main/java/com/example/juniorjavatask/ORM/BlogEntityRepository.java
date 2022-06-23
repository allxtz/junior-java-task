package com.example.juniorjavatask.ORM;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogEntityRepository extends JpaRepository<BlogEntity, Integer> {

    @Modifying
    @Query("select b from BlogEntity b order by b.id asc ")
    List<BlogEntity> getAll();

    void deleteById(int id);
}