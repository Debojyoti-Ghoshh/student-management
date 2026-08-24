package com.example.student_management.service;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.student_management.model.DatabaseSequence;

@Service
public class SequenceGeneratorService {

    private final MongoOperations mongoOperations;

    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String sequenceName) {

        DatabaseSequence counter = mongoOperations.findAndModify(
                Query.query(
                        Criteria.where("_id").is(sequenceName)
                ),
                new Update().inc("sequence", 1),
                FindAndModifyOptions.options()
                        .returnNew(true)
                        .upsert(true),
                DatabaseSequence.class
        );

        return counter.getSequence();
    }
}