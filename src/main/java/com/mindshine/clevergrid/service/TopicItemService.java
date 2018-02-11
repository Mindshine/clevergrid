package com.mindshine.clevergrid.service;

import com.mindshine.clevergrid.domain.TopicItem;
import java.util.List;

/**
 * Service Interface for managing TopicItem.
 */
public interface TopicItemService {

    /**
     * Save a topicItem.
     *
     * @param topicItem the entity to save
     * @return the persisted entity
     */
    TopicItem save(TopicItem topicItem);

    /**
     * Get all the topicItems.
     *
     * @return the list of entities
     */
    List<TopicItem> findAll();

    /**
     * Get the "id" topicItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TopicItem findOne(String id);

    /**
     * Delete the "id" topicItem.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
