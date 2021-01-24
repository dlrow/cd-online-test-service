package com.cd.onlinetest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cd.onlinetest.mongoDomain.Test;

public interface TestRepository extends MongoRepository<Test, String> {
  
}
