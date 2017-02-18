package com.mindshine.clevergrid.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mindshine.clevergrid.domain.Tag;
import com.mindshine.clevergrid.repository.TagRepository;
import com.mindshine.clevergrid.service.TagService;
import com.mindshine.clevergrid.service.dto.TagDTO;
import com.mindshine.clevergrid.service.mapper.TagMapper;

/**
 * Service Implementation for managing Tag.
 */
@Service
public class TagServiceImpl implements TagService{

    private final Logger log = LoggerFactory.getLogger(TagServiceImpl.class);
    
    @Inject
    private TagRepository tagRepository;

    @Inject
    private TagMapper tagMapper;

    /**
     * Save a tag.
     *
     * @param tagDTO the entity to save
     * @return the persisted entity
     */
    public TagDTO save(TagDTO tagDTO) {
        log.debug("Request to save Tag : {}", tagDTO);
        Tag tag = tagMapper.tagDTOToTag(tagDTO);
        tag = tagRepository.save(tag);
        TagDTO result = tagMapper.tagToTagDTO(tag);
        return result;
    }

    /**
     *  Get all the tags.
     *  
     *  @return the list of entities
     */
    public List<TagDTO> findAll() {
        log.debug("Request to get all Tags");
        List<TagDTO> result = tagRepository.findAll().stream()
            .map(tagMapper::tagToTagDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one tag by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public TagDTO findOne(String id) {
        log.debug("Request to get Tag : {}", id);
        Tag tag = tagRepository.findOne(id);
        TagDTO tagDTO = tagMapper.tagToTagDTO(tag);
        return tagDTO;
    }

    /**
     *  Delete the  tag by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Tag : {}", id);
        tagRepository.delete(id);
    }
}
