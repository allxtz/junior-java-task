package com.example.juniorjavatask.services;

import com.example.juniorjavatask.ORM.BlogEntity;
import com.example.juniorjavatask.ORM.BlogEntityRepository;
import com.example.juniorjavatask.ORM.UserEntity;
import com.example.juniorjavatask.ORM.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private BlogEntityRepository blog;
    @Autowired
    private UserEntityRepository user;

    @Override
    @Transactional
    public List<BlogEntity> getBlog() {
        return blog.getAll();
    }

    @Override
    public UserEntity findUserEntitiesByUsername(String username) {
        return user.findUserEntitiesByUsername(username);
    }

    public void newEntry(BlogEntity blogEntity) {
        blog.save(blogEntity);
    }

    public void saveUser(UserEntity userEntity) {
        user.save(userEntity);
    }

    public void deleteEntry(int id) {
        blog.deleteById(id);
    }
}
