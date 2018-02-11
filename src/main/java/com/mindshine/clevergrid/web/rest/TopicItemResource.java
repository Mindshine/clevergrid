package com.mindshine.clevergrid.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindshine.clevergrid.domain.TopicItem;
import com.mindshine.clevergrid.service.TopicItemService;
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
 * REST controller for managing TopicItem.
 */
@RestController
@RequestMapping("/api")
public class TopicItemResource {

    private final Logger log = LoggerFactory.getLogger(TopicItemResource.class);

    private static final String ENTITY_NAME = "topicItem";

    private final TopicItemService topicItemService;

    public TopicItemResource(TopicItemService topicItemService) {
        this.topicItemService = topicItemService;
    }

    /**
     * POST  /topic-items : Create a new topicItem.
     *
     * @param topicItem the topicItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new topicItem, or with status 400 (Bad Request) if the topicItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/topic-items")
    @Timed
    public ResponseEntity<TopicItem> createTopicItem(@RequestBody TopicItem topicItem) throws URISyntaxException {
        log.debug("REST request to save TopicItem : {}", topicItem);
        if (topicItem.getId() != null) {
            throw new BadRequestAlertException("A new topicItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicItem result = topicItemService.save(topicItem);
        return ResponseEntity.created(new URI("/api/topic-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /topic-items : Updates an existing topicItem.
     *
     * @param topicItem the topicItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated topicItem,
     * or with status 400 (Bad Request) if the topicItem is not valid,
     * or with status 500 (Internal Server Error) if the topicItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/topic-items")
    @Timed
    public ResponseEntity<TopicItem> updateTopicItem(@RequestBody TopicItem topicItem) throws URISyntaxException {
        log.debug("REST request to update TopicItem : {}", topicItem);
        if (topicItem.getId() == null) {
            return createTopicItem(topicItem);
        }
        TopicItem result = topicItemService.save(topicItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, topicItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /topic-items : get all the topicItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of topicItems in body
     */
    @GetMapping("/topic-items")
    @Timed
    public List<TopicItem> getAllTopicItems() {
        log.debug("REST request to get all TopicItems");
        return topicItemService.findAll();
        }

    /**
     * GET  /topic-items/:id : get the "id" topicItem.
     *
     * @param id the id of the topicItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the topicItem, or with status 404 (Not Found)
     */
    @GetMapping("/topic-items/{id}")
    @Timed
    public ResponseEntity<TopicItem> getTopicItem(@PathVariable String id) {
        log.debug("REST request to get TopicItem : {}", id);
        TopicItem topicItem = topicItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(topicItem));
    }

    /**
     * DELETE  /topic-items/:id : delete the "id" topicItem.
     *
     * @param id the id of the topicItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/topic-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteTopicItem(@PathVariable String id) {
        log.debug("REST request to delete TopicItem : {}", id);
        topicItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
