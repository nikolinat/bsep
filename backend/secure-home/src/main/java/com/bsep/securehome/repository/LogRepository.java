package com.bsep.securehome.repository;

import com.bsep.securehome.model.Log;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, UUID> {
}
