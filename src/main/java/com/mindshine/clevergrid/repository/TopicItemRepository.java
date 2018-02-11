package com.mindshine.clevergrid.repository;

import com.mindshine.clevergrid.domain.TopicItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the TopicItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopicItemRepository extends MongoRepository<TopicItem, String> {

}
