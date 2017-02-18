package com.mindshine.clevergrid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mindshine.clevergrid.domain.Category;

/**
 * Spring Data MongoDB repository for the Category entity.
 */
@SuppressWarnings("unused")
public interface CategoryRepository extends MongoRepository<Category,String> {

}
