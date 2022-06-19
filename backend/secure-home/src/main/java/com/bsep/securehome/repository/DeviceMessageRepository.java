package com.bsep.securehome.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bsep.securehome.model.DeviceMessage;

public interface DeviceMessageRepository extends MongoRepository<DeviceMessage, UUID>{
    
}
