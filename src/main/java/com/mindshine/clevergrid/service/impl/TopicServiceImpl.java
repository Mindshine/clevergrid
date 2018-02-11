package com.mindshine.clevergrid.service.impl;

import com.mindshine.clevergrid.service.TopicService;
import com.mindshine.clevergrid.domain.Topic;
import com.mindshine.clevergrid.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Topic.
 */
@Service
public class TopicServiceImpl implements TopicService {

    private final Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);

    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Save a topic.
     *
     * @param topic the entity to save
     * @return the persisted entity
     */
    @Override
    public Topic save(Topic topic) {
        log.debug("Request to save Topic : {}", topic);
        return topicRepository.save(topic);
    }

    /**
     * Get all the topics.
     *
     * @return the list of entities
     */
    @Override
    public List<Topic> findAll() {
        log.debug("Request to get all Topics");
        return topicRepository.findAll();
    }

    /**
     * Get one topic by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Topic findOne(String id) {
        log.debug("Request to get Topic : {}", id);
        return topicRepository.findOne(id);
    }

    /**
     * Delete the topic by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Topic : {}", id);
        topicRepository.delete(id);
    }
}
