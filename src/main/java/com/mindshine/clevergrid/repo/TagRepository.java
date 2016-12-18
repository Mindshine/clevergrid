package com.mindshine.clevergrid.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mindshine.clevergrid.model.Tag;

public interface TagRepository extends CrudRepository<Tag, Integer> {
	
	Iterable<Tag> findByTagName(@Param("q")String q);
	
}
