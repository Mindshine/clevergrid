package com.mindshine.clevergrid.service.impl;

import com.mindshine.clevergrid.service.TopicItemService;
import com.mindshine.clevergrid.domain.TopicItem;
import com.mindshine.clevergrid.repository.TopicItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing TopicItem.
 */
@Service
public class TopicItemServiceImpl implements TopicItemService {

    private final Logger log = LoggerFactory.getLogger(TopicItemServiceImpl.class);

    private final TopicItemRepository topicItemRepository;

    public TopicItemServiceImpl(TopicItemRepository topicItemRepository) {
        this.topicItemRepository = topicItemRepository;
    }

    /**
     * Save a topicItem.
     *
     * @param topicItem the entity to save
     * @return the persisted entity
     */
    @Override
    public TopicItem save(TopicItem topicItem) {
        log.debug("Request to save TopicItem : {}", topicItem);
        return topicItemRepository.save(topicItem);
    }

    /**
     * Get all the topicItems.
     *
     * @return the list of entities
     */
    @Override
    public List<TopicItem> findAll() {
        log.debug("Request to get all TopicItems");
        return topicItemRepository.findAll();
    }

    /**
     * Get one topicItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public TopicItem findOne(String id) {
        log.debug("Request to get TopicItem : {}", id);
        return topicItemRepository.findOne(id);
    }

    /**
     * Delete the topicItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete TopicItem : {}", id);
        topicItemRepository.delete(id);
    }
}
