package com.mindshine.clevergrid.service;

import com.mindshine.clevergrid.service.dto.TagDTO;
import java.util.List;

/**
 * Service Interface for managing Tag.
 */
public interface TagService {

    /**
     * Save a tag.
     *
     * @param tagDTO the entity to save
     * @return the persisted entity
     */
    TagDTO save(TagDTO tagDTO);

    /**
     *  Get all the tags.
     *  
     *  @return the list of entities
     */
    List<TagDTO> findAll();

    /**
     *  Get the "id" tag.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TagDTO findOne(String id);

    /**
     *  Delete the "id" tag.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
