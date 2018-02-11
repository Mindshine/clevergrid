package com.mindshine.clevergrid.service.impl;

import com.mindshine.clevergrid.service.QuizService;
import com.mindshine.clevergrid.domain.Quiz;
import com.mindshine.clevergrid.repository.QuizRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Quiz.
 */
@Service
public class QuizServiceImpl implements QuizService {

    private final Logger log = LoggerFactory.getLogger(QuizServiceImpl.class);

    private final QuizRepository quizRepository;

    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    /**
     * Save a quiz.
     *
     * @param quiz the entity to save
     * @return the persisted entity
     */
    @Override
    public Quiz save(Quiz quiz) {
        log.debug("Request to save Quiz : {}", quiz);
        return quizRepository.save(quiz);
    }

    /**
     * Get all the quizzes.
     *
     * @return the list of entities
     */
    @Override
    public List<Quiz> findAll() {
        log.debug("Request to get all Quizzes");
        return quizRepository.findAll();
    }

    /**
     * Get one quiz by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Quiz findOne(String id) {
        log.debug("Request to get Quiz : {}", id);
        return quizRepository.findOne(id);
    }

    /**
     * Delete the quiz by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Quiz : {}", id);
        quizRepository.delete(id);
    }
}
