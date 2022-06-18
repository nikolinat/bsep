package com.bsep.admin.app.repository;

import com.bsep.admin.app.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, Integer> {
}
