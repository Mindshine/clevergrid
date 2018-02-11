package com.mindshine.clevergrid.service;

import com.mindshine.clevergrid.domain.Quiz;
import java.util.List;

/**
 * Service Interface for managing Quiz.
 */
public interface QuizService {

    /**
     * Save a quiz.
     *
     * @param quiz the entity to save
     * @return the persisted entity
     */
    Quiz save(Quiz quiz);

    /**
     * Get all the quizzes.
     *
     * @return the list of entities
     */
    List<Quiz> findAll();

    /**
     * Get the "id" quiz.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Quiz findOne(String id);

    /**
     * Delete the "id" quiz.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
