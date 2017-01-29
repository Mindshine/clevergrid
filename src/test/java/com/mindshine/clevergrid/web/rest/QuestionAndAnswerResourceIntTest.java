package com.mindshine.clevergrid.web.rest;

import com.mindshine.clevergrid.ClevergridApp;

import com.mindshine.clevergrid.domain.QuestionAndAnswer;
import com.mindshine.clevergrid.repository.QuestionAndAnswerRepository;
import com.mindshine.clevergrid.service.QuestionAndAnswerService;
import com.mindshine.clevergrid.service.dto.QuestionAndAnswerDTO;
import com.mindshine.clevergrid.service.mapper.QuestionAndAnswerMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QuestionAndAnswerResource REST controller.
 *
 * @see QuestionAndAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClevergridApp.class)
public class QuestionAndAnswerResourceIntTest {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    @Inject
    private QuestionAndAnswerRepository questionAndAnswerRepository;

    @Inject
    private QuestionAndAnswerMapper questionAndAnswerMapper;

    @Inject
    private QuestionAndAnswerService questionAndAnswerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restQuestionAndAnswerMockMvc;

    private QuestionAndAnswer questionAndAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuestionAndAnswerResource questionAndAnswerResource = new QuestionAndAnswerResource();
        ReflectionTestUtils.setField(questionAndAnswerResource, "questionAndAnswerService", questionAndAnswerService);
        this.restQuestionAndAnswerMockMvc = MockMvcBuilders.standaloneSetup(questionAndAnswerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionAndAnswer createEntity() {
        QuestionAndAnswer questionAndAnswer = new QuestionAndAnswer()
                .question(DEFAULT_QUESTION)
                .answer(DEFAULT_ANSWER);
        return questionAndAnswer;
    }

    @Before
    public void initTest() {
        questionAndAnswerRepository.deleteAll();
        questionAndAnswer = createEntity();
    }

    @Test
    public void createQuestionAndAnswer() throws Exception {
        int databaseSizeBeforeCreate = questionAndAnswerRepository.findAll().size();

        // Create the QuestionAndAnswer
        QuestionAndAnswerDTO questionAndAnswerDTO = questionAndAnswerMapper.questionAndAnswerToQuestionAndAnswerDTO(questionAndAnswer);

        restQuestionAndAnswerMockMvc.perform(post("/api/question-and-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAndAnswerDTO)))
            .andExpect(status().isCreated());

        // Validate the QuestionAndAnswer in the database
        List<QuestionAndAnswer> questionAndAnswerList = questionAndAnswerRepository.findAll();
        assertThat(questionAndAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionAndAnswer testQuestionAndAnswer = questionAndAnswerList.get(questionAndAnswerList.size() - 1);
        assertThat(testQuestionAndAnswer.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testQuestionAndAnswer.getAnswer()).isEqualTo(DEFAULT_ANSWER);
    }

    @Test
    public void createQuestionAndAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionAndAnswerRepository.findAll().size();

        // Create the QuestionAndAnswer with an existing ID
        QuestionAndAnswer existingQuestionAndAnswer = new QuestionAndAnswer();
        existingQuestionAndAnswer.setId("existing_id");
        QuestionAndAnswerDTO existingQuestionAndAnswerDTO = questionAndAnswerMapper.questionAndAnswerToQuestionAndAnswerDTO(existingQuestionAndAnswer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionAndAnswerMockMvc.perform(post("/api/question-and-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingQuestionAndAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<QuestionAndAnswer> questionAndAnswerList = questionAndAnswerRepository.findAll();
        assertThat(questionAndAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllQuestionAndAnswers() throws Exception {
        // Initialize the database
        questionAndAnswerRepository.save(questionAndAnswer);

        // Get all the questionAndAnswerList
        restQuestionAndAnswerMockMvc.perform(get("/api/question-and-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionAndAnswer.getId())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())));
    }

    @Test
    public void getQuestionAndAnswer() throws Exception {
        // Initialize the database
        questionAndAnswerRepository.save(questionAndAnswer);

        // Get the questionAndAnswer
        restQuestionAndAnswerMockMvc.perform(get("/api/question-and-answers/{id}", questionAndAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionAndAnswer.getId()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER.toString()));
    }

    @Test
    public void getNonExistingQuestionAndAnswer() throws Exception {
        // Get the questionAndAnswer
        restQuestionAndAnswerMockMvc.perform(get("/api/question-and-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateQuestionAndAnswer() throws Exception {
        // Initialize the database
        questionAndAnswerRepository.save(questionAndAnswer);
        int databaseSizeBeforeUpdate = questionAndAnswerRepository.findAll().size();

        // Update the questionAndAnswer
        QuestionAndAnswer updatedQuestionAndAnswer = questionAndAnswerRepository.findOne(questionAndAnswer.getId());
        updatedQuestionAndAnswer
                .question(UPDATED_QUESTION)
                .answer(UPDATED_ANSWER);
        QuestionAndAnswerDTO questionAndAnswerDTO = questionAndAnswerMapper.questionAndAnswerToQuestionAndAnswerDTO(updatedQuestionAndAnswer);

        restQuestionAndAnswerMockMvc.perform(put("/api/question-and-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAndAnswerDTO)))
            .andExpect(status().isOk());

        // Validate the QuestionAndAnswer in the database
        List<QuestionAndAnswer> questionAndAnswerList = questionAndAnswerRepository.findAll();
        assertThat(questionAndAnswerList).hasSize(databaseSizeBeforeUpdate);
        QuestionAndAnswer testQuestionAndAnswer = questionAndAnswerList.get(questionAndAnswerList.size() - 1);
        assertThat(testQuestionAndAnswer.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQuestionAndAnswer.getAnswer()).isEqualTo(UPDATED_ANSWER);
    }

    @Test
    public void updateNonExistingQuestionAndAnswer() throws Exception {
        int databaseSizeBeforeUpdate = questionAndAnswerRepository.findAll().size();

        // Create the QuestionAndAnswer
        QuestionAndAnswerDTO questionAndAnswerDTO = questionAndAnswerMapper.questionAndAnswerToQuestionAndAnswerDTO(questionAndAnswer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuestionAndAnswerMockMvc.perform(put("/api/question-and-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAndAnswerDTO)))
            .andExpect(status().isCreated());

        // Validate the QuestionAndAnswer in the database
        List<QuestionAndAnswer> questionAndAnswerList = questionAndAnswerRepository.findAll();
        assertThat(questionAndAnswerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteQuestionAndAnswer() throws Exception {
        // Initialize the database
        questionAndAnswerRepository.save(questionAndAnswer);
        int databaseSizeBeforeDelete = questionAndAnswerRepository.findAll().size();

        // Get the questionAndAnswer
        restQuestionAndAnswerMockMvc.perform(delete("/api/question-and-answers/{id}", questionAndAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuestionAndAnswer> questionAndAnswerList = questionAndAnswerRepository.findAll();
        assertThat(questionAndAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
