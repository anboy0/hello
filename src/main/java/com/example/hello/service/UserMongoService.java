package com.example.hello.service;

import com.example.hello.pojo.entity.mycollection;

public interface UserMongoService {
    mycollection getUserbyName(String name);

    void savemyCollection(mycollection mycollection);
}
