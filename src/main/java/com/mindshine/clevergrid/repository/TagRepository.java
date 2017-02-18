package com.mindshine.clevergrid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mindshine.clevergrid.domain.Tag;

/**
 * Spring Data MongoDB repository for the Tag entity.
 */
@SuppressWarnings("unused")
public interface TagRepository extends MongoRepository<Tag,String> {

}
