package com.mindshine.clevergrid.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindshine.clevergrid.domain.Quiz;
import com.mindshine.clevergrid.service.QuizService;
import com.mindshine.clevergrid.web.rest.errors.BadRequestAlertException;
import com.mindshine.clevergrid.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Quiz.
 */
@RestController
@RequestMapping("/api")
public class QuizResource {

    private final Logger log = LoggerFactory.getLogger(QuizResource.class);

    private static final String ENTITY_NAME = "quiz";

    private final QuizService quizService;

    public QuizResource(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * POST  /quizzes : Create a new quiz.
     *
     * @param quiz the quiz to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quiz, or with status 400 (Bad Request) if the quiz has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quizzes")
    @Timed
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) throws URISyntaxException {
        log.debug("REST request to save Quiz : {}", quiz);
        if (quiz.getId() != null) {
            throw new BadRequestAlertException("A new quiz cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Quiz result = quizService.save(quiz);
        return ResponseEntity.created(new URI("/api/quizzes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quizzes : Updates an existing quiz.
     *
     * @param quiz the quiz to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quiz,
     * or with status 400 (Bad Request) if the quiz is not valid,
     * or with status 500 (Internal Server Error) if the quiz couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quizzes")
    @Timed
    public ResponseEntity<Quiz> updateQuiz(@RequestBody Quiz quiz) throws URISyntaxException {
        log.debug("REST request to update Quiz : {}", quiz);
        if (quiz.getId() == null) {
            return createQuiz(quiz);
        }
        Quiz result = quizService.save(quiz);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quiz.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quizzes : get all the quizzes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of quizzes in body
     */
    @GetMapping("/quizzes")
    @Timed
    public List<Quiz> getAllQuizzes() {
        log.debug("REST request to get all Quizzes");
        return quizService.findAll();
        }

    /**
     * GET  /quizzes/:id : get the "id" quiz.
     *
     * @param id the id of the quiz to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quiz, or with status 404 (Not Found)
     */
    @GetMapping("/quizzes/{id}")
    @Timed
    public ResponseEntity<Quiz> getQuiz(@PathVariable String id) {
        log.debug("REST request to get Quiz : {}", id);
        Quiz quiz = quizService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quiz));
    }

    /**
     * DELETE  /quizzes/:id : delete the "id" quiz.
     *
     * @param id the id of the quiz to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/quizzes/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuiz(@PathVariable String id) {
        log.debug("REST request to delete Quiz : {}", id);
        quizService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
