package com.mindshine.clevergrid.service.impl;

import com.mindshine.clevergrid.service.TagService;
import com.mindshine.clevergrid.domain.Tag;
import com.mindshine.clevergrid.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Tag.
 */
@Service
public class TagServiceImpl implements TagService {

    private final Logger log = LoggerFactory.getLogger(TagServiceImpl.class);

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     * Save a tag.
     *
     * @param tag the entity to save
     * @return the persisted entity
     */
    @Override
    public Tag save(Tag tag) {
        log.debug("Request to save Tag : {}", tag);
        return tagRepository.save(tag);
    }

    /**
     * Get all the tags.
     *
     * @return the list of entities
     */
    @Override
    public List<Tag> findAll() {
        log.debug("Request to get all Tags");
        return tagRepository.findAll();
    }

    /**
     * Get one tag by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Tag findOne(String id) {
        log.debug("Request to get Tag : {}", id);
        return tagRepository.findOne(id);
    }

    /**
     * Delete the tag by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Tag : {}", id);
        tagRepository.delete(id);
    }
}
