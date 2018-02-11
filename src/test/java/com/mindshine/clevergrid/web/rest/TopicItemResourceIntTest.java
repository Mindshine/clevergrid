package com.mindshine.clevergrid.web.rest;

import com.mindshine.clevergrid.ClevergridApp;

import com.mindshine.clevergrid.domain.TopicItem;
import com.mindshine.clevergrid.repository.TopicItemRepository;
import com.mindshine.clevergrid.service.TopicItemService;
import com.mindshine.clevergrid.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.mindshine.clevergrid.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TopicItemResource REST controller.
 *
 * @see TopicItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClevergridApp.class)
public class TopicItemResourceIntTest {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    @Autowired
    private TopicItemRepository topicItemRepository;

    @Autowired
    private TopicItemService topicItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restTopicItemMockMvc;

    private TopicItem topicItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TopicItemResource topicItemResource = new TopicItemResource(topicItemService);
        this.restTopicItemMockMvc = MockMvcBuilders.standaloneSetup(topicItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicItem createEntity() {
        TopicItem topicItem = new TopicItem()
            .question(DEFAULT_QUESTION)
            .answer(DEFAULT_ANSWER);
        return topicItem;
    }

    @Before
    public void initTest() {
        topicItemRepository.deleteAll();
        topicItem = createEntity();
    }

    @Test
    public void createTopicItem() throws Exception {
        int databaseSizeBeforeCreate = topicItemRepository.findAll().size();

        // Create the TopicItem
        restTopicItemMockMvc.perform(post("/api/topic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topicItem)))
            .andExpect(status().isCreated());

        // Validate the TopicItem in the database
        List<TopicItem> topicItemList = topicItemRepository.findAll();
        assertThat(topicItemList).hasSize(databaseSizeBeforeCreate + 1);
        TopicItem testTopicItem = topicItemList.get(topicItemList.size() - 1);
        assertThat(testTopicItem.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testTopicItem.getAnswer()).isEqualTo(DEFAULT_ANSWER);
    }

    @Test
    public void createTopicItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicItemRepository.findAll().size();

        // Create the TopicItem with an existing ID
        topicItem.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicItemMockMvc.perform(post("/api/topic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topicItem)))
            .andExpect(status().isBadRequest());

        // Validate the TopicItem in the database
        List<TopicItem> topicItemList = topicItemRepository.findAll();
        assertThat(topicItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllTopicItems() throws Exception {
        // Initialize the database
        topicItemRepository.save(topicItem);

        // Get all the topicItemList
        restTopicItemMockMvc.perform(get("/api/topic-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicItem.getId())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())));
    }

    @Test
    public void getTopicItem() throws Exception {
        // Initialize the database
        topicItemRepository.save(topicItem);

        // Get the topicItem
        restTopicItemMockMvc.perform(get("/api/topic-items/{id}", topicItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(topicItem.getId()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER.toString()));
    }

    @Test
    public void getNonExistingTopicItem() throws Exception {
        // Get the topicItem
        restTopicItemMockMvc.perform(get("/api/topic-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTopicItem() throws Exception {
        // Initialize the database
        topicItemService.save(topicItem);

        int databaseSizeBeforeUpdate = topicItemRepository.findAll().size();

        // Update the topicItem
        TopicItem updatedTopicItem = topicItemRepository.findOne(topicItem.getId());
        updatedTopicItem
            .question(UPDATED_QUESTION)
            .answer(UPDATED_ANSWER);

        restTopicItemMockMvc.perform(put("/api/topic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTopicItem)))
            .andExpect(status().isOk());

        // Validate the TopicItem in the database
        List<TopicItem> topicItemList = topicItemRepository.findAll();
        assertThat(topicItemList).hasSize(databaseSizeBeforeUpdate);
        TopicItem testTopicItem = topicItemList.get(topicItemList.size() - 1);
        assertThat(testTopicItem.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testTopicItem.getAnswer()).isEqualTo(UPDATED_ANSWER);
    }

    @Test
    public void updateNonExistingTopicItem() throws Exception {
        int databaseSizeBeforeUpdate = topicItemRepository.findAll().size();

        // Create the TopicItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTopicItemMockMvc.perform(put("/api/topic-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topicItem)))
            .andExpect(status().isCreated());

        // Validate the TopicItem in the database
        List<TopicItem> topicItemList = topicItemRepository.findAll();
        assertThat(topicItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTopicItem() throws Exception {
        // Initialize the database
        topicItemService.save(topicItem);

        int databaseSizeBeforeDelete = topicItemRepository.findAll().size();

        // Get the topicItem
        restTopicItemMockMvc.perform(delete("/api/topic-items/{id}", topicItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TopicItem> topicItemList = topicItemRepository.findAll();
        assertThat(topicItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicItem.class);
        TopicItem topicItem1 = new TopicItem();
        topicItem1.setId("id1");
        TopicItem topicItem2 = new TopicItem();
        topicItem2.setId(topicItem1.getId());
        assertThat(topicItem1).isEqualTo(topicItem2);
        topicItem2.setId("id2");
        assertThat(topicItem1).isNotEqualTo(topicItem2);
        topicItem1.setId(null);
        assertThat(topicItem1).isNotEqualTo(topicItem2);
    }
}
