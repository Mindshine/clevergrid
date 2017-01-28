package com.mindshine.clevergrid.repo;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mindshine.clevergrid.model.Topic;

@RepositoryRestResource
public interface TopicRepository extends CrudRepository<Topic, ObjectId> {

	Iterable<Topic> findByTitleLike(@Param("q") String q);
}
