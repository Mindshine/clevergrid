package com.mindshine.clevergrid.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.mindshine.clevergrid.service.TopicService;
import com.mindshine.clevergrid.service.dto.TopicDTO;
import com.mindshine.clevergrid.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Topic.
 */
@RestController
@RequestMapping("/api")
public class TopicResource {

    private final Logger log = LoggerFactory.getLogger(TopicResource.class);
        
    @Inject
    private TopicService topicService;

    /**
     * POST  /topics : Create a new topic.
     *
     * @param topicDTO the topicDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new topicDTO, or with status 400 (Bad Request) if the topic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/topics")
    @Timed
    public ResponseEntity<TopicDTO> createTopic(@RequestBody TopicDTO topicDTO) throws URISyntaxException {
        log.debug("REST request to save Topic : {}", topicDTO);
        if (topicDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("topic", "idexists", "A new topic cannot already have an ID")).body(null);
        }
        TopicDTO result = topicService.save(topicDTO);
        return ResponseEntity.created(new URI("/api/topics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("topic", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /topics : Updates an existing topic.
     *
     * @param topicDTO the topicDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated topicDTO,
     * or with status 400 (Bad Request) if the topicDTO is not valid,
     * or with status 500 (Internal Server Error) if the topicDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/topics")
    @Timed
    public ResponseEntity<TopicDTO> updateTopic(@RequestBody TopicDTO topicDTO) throws URISyntaxException {
        log.debug("REST request to update Topic : {}", topicDTO);
        if (topicDTO.getId() == null) {
            return createTopic(topicDTO);
        }
        TopicDTO result = topicService.save(topicDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("topic", topicDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /topics : get all the topics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of topics in body
     */
    @GetMapping("/topics")
    @Timed
    public List<TopicDTO> getAllTopics() {
        log.debug("REST request to get all Topics");
        return topicService.findAll();
    }

    /**
     * GET  /topics/:id : get the "id" topic.
     *
     * @param id the id of the topicDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the topicDTO, or with status 404 (Not Found)
     */
    @GetMapping("/topics/{id}")
    @Timed
    public ResponseEntity<TopicDTO> getTopic(@PathVariable String id) {
        log.debug("REST request to get Topic : {}", id);
        TopicDTO topicDTO = topicService.findOne(id);
        return Optional.ofNullable(topicDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /topics/:id : delete the "id" topic.
     *
     * @param id the id of the topicDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/topics/{id}")
    @Timed
    public ResponseEntity<Void> deleteTopic(@PathVariable String id) {
        log.debug("REST request to delete Topic : {}", id);
        topicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("topic", id.toString())).build();
    }

}
