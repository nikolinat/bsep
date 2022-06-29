package com.bsep.securehome.repository;

import com.bsep.securehome.model.DevicesLog;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
public interface DevicesLogRepository extends MongoRepository<DevicesLog, UUID> {
    
}
