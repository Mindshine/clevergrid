package com.mindshine.clevergrid.service;

import com.mindshine.clevergrid.domain.Topic;
import java.util.List;

/**
 * Service Interface for managing Topic.
 */
public interface TopicService {

    /**
     * Save a topic.
     *
     * @param topic the entity to save
     * @return the persisted entity
     */
    Topic save(Topic topic);

    /**
     * Get all the topics.
     *
     * @return the list of entities
     */
    List<Topic> findAll();

    /**
     * Get the "id" topic.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Topic findOne(String id);

    /**
     * Delete the "id" topic.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
