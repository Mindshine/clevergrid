package com.mindshine.clevergrid.repository;

import com.mindshine.clevergrid.domain.Quiz;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Quiz entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuizRepository extends MongoRepository<Quiz, String> {

}
