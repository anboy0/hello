package com.example.hello.mapper;

import com.example.hello.pojo.entity.mycollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<mycollection,String> {
    mycollection findByName(String name);
}
