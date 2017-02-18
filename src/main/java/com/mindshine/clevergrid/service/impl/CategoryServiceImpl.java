package com.mindshine.clevergrid.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mindshine.clevergrid.domain.Category;
import com.mindshine.clevergrid.repository.CategoryRepository;
import com.mindshine.clevergrid.service.CategoryService;
import com.mindshine.clevergrid.service.dto.CategoryDTO;
import com.mindshine.clevergrid.service.mapper.CategoryMapper;

/**
 * Service Implementation for managing Category.
 */
@Service
public class CategoryServiceImpl implements CategoryService{

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    
    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private CategoryMapper categoryMapper;

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save
     * @return the persisted entity
     */
    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        category = categoryRepository.save(category);
        CategoryDTO result = categoryMapper.categoryToCategoryDTO(category);
        return result;
    }

    /**
     *  Get all the categories.
     *  
     *  @return the list of entities
     */
    public List<CategoryDTO> findAll() {
        log.debug("Request to get all Categories");
        List<CategoryDTO> result = categoryRepository.findAll().stream()
            .map(categoryMapper::categoryToCategoryDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one category by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public CategoryDTO findOne(String id) {
        log.debug("Request to get Category : {}", id);
        Category category = categoryRepository.findOne(id);
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
        return categoryDTO;
    }

    /**
     *  Delete the  category by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.delete(id);
    }
}
