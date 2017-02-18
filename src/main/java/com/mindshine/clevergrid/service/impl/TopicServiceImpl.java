package com.mindshine.clevergrid.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mindshine.clevergrid.domain.Topic;
import com.mindshine.clevergrid.repository.TopicRepository;
import com.mindshine.clevergrid.service.TopicService;
import com.mindshine.clevergrid.service.dto.TopicDTO;
import com.mindshine.clevergrid.service.mapper.TopicMapper;

/**
 * Service Implementation for managing Topic.
 */
@Service
public class TopicServiceImpl implements TopicService{

    private final Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);
    
    @Inject
    private TopicRepository topicRepository;

    @Inject
    private TopicMapper topicMapper;

    /**
     * Save a topic.
     *
     * @param topicDTO the entity to save
     * @return the persisted entity
     */
    public TopicDTO save(TopicDTO topicDTO) {
        log.debug("Request to save Topic : {}", topicDTO);
        Topic topic = topicMapper.topicDTOToTopic(topicDTO);
        topic = topicRepository.save(topic);
        TopicDTO result = topicMapper.topicToTopicDTO(topic);
        return result;
    }

    /**
     *  Get all the topics.
     *  
     *  @return the list of entities
     */
    public List<TopicDTO> findAll() {
        log.debug("Request to get all Topics");
        List<TopicDTO> result = topicRepository.findAll().stream()
            .map(topicMapper::topicToTopicDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one topic by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public TopicDTO findOne(String id) {
        log.debug("Request to get Topic : {}", id);
        Topic topic = topicRepository.findOne(id);
        TopicDTO topicDTO = topicMapper.topicToTopicDTO(topic);
        return topicDTO;
    }

    /**
     *  Delete the  topic by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Topic : {}", id);
        topicRepository.delete(id);
    }
}
