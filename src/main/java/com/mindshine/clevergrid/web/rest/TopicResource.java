package com.mindshine.clevergrid.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindshine.clevergrid.domain.Topic;
import com.mindshine.clevergrid.service.TopicService;
import com.mindshine.clevergrid.web.rest.errors.BadRequestAlertException;
import com.mindshine.clevergrid.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Topic.
 */
@RestController
@RequestMapping("/api")
public class TopicResource {

    private final Logger log = LoggerFactory.getLogger(TopicResource.class);

    private static final String ENTITY_NAME = "topic";

    private final TopicService topicService;

    public TopicResource(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * POST  /topics : Create a new topic.
     *
     * @param topic the topic to create
     * @return the ResponseEntity with status 201 (Created) and with body the new topic, or with status 400 (Bad Request) if the topic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/topics")
    @Timed
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) throws URISyntaxException {
        log.debug("REST request to save Topic : {}", topic);
        if (topic.getId() != null) {
            throw new BadRequestAlertException("A new topic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Topic result = topicService.save(topic);
        return ResponseEntity.created(new URI("/api/topics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /topics : Updates an existing topic.
     *
     * @param topic the topic to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated topic,
     * or with status 400 (Bad Request) if the topic is not valid,
     * or with status 500 (Internal Server Error) if the topic couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/topics")
    @Timed
    public ResponseEntity<Topic> updateTopic(@RequestBody Topic topic) throws URISyntaxException {
        log.debug("REST request to update Topic : {}", topic);
        if (topic.getId() == null) {
            return createTopic(topic);
        }
        Topic result = topicService.save(topic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, topic.getId().toString()))
            .body(result);
    }

    /**
     * GET  /topics : get all the topics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of topics in body
     */
    @GetMapping("/topics")
    @Timed
    public List<Topic> getAllTopics() {
        log.debug("REST request to get all Topics");
        return topicService.findAll();
        }

    /**
     * GET  /topics/:id : get the "id" topic.
     *
     * @param id the id of the topic to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the topic, or with status 404 (Not Found)
     */
    @GetMapping("/topics/{id}")
    @Timed
    public ResponseEntity<Topic> getTopic(@PathVariable String id) {
        log.debug("REST request to get Topic : {}", id);
        Topic topic = topicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(topic));
    }

    /**
     * DELETE  /topics/:id : delete the "id" topic.
     *
     * @param id the id of the topic to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/topics/{id}")
    @Timed
    public ResponseEntity<Void> deleteTopic(@PathVariable String id) {
        log.debug("REST request to delete Topic : {}", id);
        topicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
