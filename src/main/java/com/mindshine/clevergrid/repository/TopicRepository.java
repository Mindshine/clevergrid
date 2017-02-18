package com.mindshine.clevergrid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mindshine.clevergrid.domain.Topic;

/**
 * Spring Data MongoDB repository for the Topic entity.
 */
@SuppressWarnings("unused")
public interface TopicRepository extends MongoRepository<Topic,String> {

}
