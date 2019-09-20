package com.example.hello.service.impl;

import com.example.hello.mapper.UserRepository;
import com.example.hello.pojo.entity.mycollection;
import com.example.hello.service.UserMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserMongoServiceImpl implements UserMongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    @Override
    public mycollection getUserbyName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public void savemyCollection(mycollection mycollection) {
        mongoTemplate.save(mycollection);
    }
}
