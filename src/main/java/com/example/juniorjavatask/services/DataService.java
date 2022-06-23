package com.example.juniorjavatask.services;

import com.example.juniorjavatask.ORM.BlogEntity;
import com.example.juniorjavatask.ORM.UserEntity;

import java.util.List;

public interface DataService {

    List<BlogEntity> getBlog();
    UserEntity findUserEntitiesByUsername(String username);
    void newEntry(BlogEntity blogEntity);
    void saveUser(UserEntity userEntity);
    public  void deleteEntry(int id);

}
