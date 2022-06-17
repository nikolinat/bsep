package com.bsep.securehome.repository;

import com.bsep.securehome.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, Integer> {
}
