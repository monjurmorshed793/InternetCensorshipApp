package org.censorship.web.rest;

import org.censorship.InternetCensorshipApp;

import org.censorship.domain.BlockedWebsite;
import org.censorship.repository.BlockedWebsiteRepository;
import org.censorship.service.BlockedWebsiteService;
import org.censorship.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import java.util.List;


import static org.censorship.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BlockedWebsiteResource REST controller.
 *
 * @see BlockedWebsiteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InternetCensorshipApp.class)
public class BlockedWebsiteResourceIntTest {

    private static final String DEFAULT_SITE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SITE_NAME = "BBBBBBBBBB";

    @Autowired
    private BlockedWebsiteRepository blockedWebsiteRepository;

    @Autowired
    private BlockedWebsiteService blockedWebsiteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restBlockedWebsiteMockMvc;

    private BlockedWebsite blockedWebsite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlockedWebsiteResource blockedWebsiteResource = new BlockedWebsiteResource(blockedWebsiteService);
        this.restBlockedWebsiteMockMvc = MockMvcBuilders.standaloneSetup(blockedWebsiteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlockedWebsite createEntity() {
        BlockedWebsite blockedWebsite = new BlockedWebsite()
            .siteName(DEFAULT_SITE_NAME);
        return blockedWebsite;
    }

    @Before
    public void initTest() {
        blockedWebsiteRepository.deleteAll();
        blockedWebsite = createEntity();
    }

    @Test
    public void createBlockedWebsite() throws Exception {
        int databaseSizeBeforeCreate = blockedWebsiteRepository.findAll().size();

        // Create the BlockedWebsite
        restBlockedWebsiteMockMvc.perform(post("/api/blocked-websites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockedWebsite)))
            .andExpect(status().isCreated());

        // Validate the BlockedWebsite in the database
        List<BlockedWebsite> blockedWebsiteList = blockedWebsiteRepository.findAll();
        assertThat(blockedWebsiteList).hasSize(databaseSizeBeforeCreate + 1);
        BlockedWebsite testBlockedWebsite = blockedWebsiteList.get(blockedWebsiteList.size() - 1);
        assertThat(testBlockedWebsite.getSiteName()).isEqualTo(DEFAULT_SITE_NAME);
    }

    @Test
    public void createBlockedWebsiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blockedWebsiteRepository.findAll().size();

        // Create the BlockedWebsite with an existing ID
        blockedWebsite.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlockedWebsiteMockMvc.perform(post("/api/blocked-websites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockedWebsite)))
            .andExpect(status().isBadRequest());

        // Validate the BlockedWebsite in the database
        List<BlockedWebsite> blockedWebsiteList = blockedWebsiteRepository.findAll();
        assertThat(blockedWebsiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllBlockedWebsites() throws Exception {
        // Initialize the database
        blockedWebsiteRepository.save(blockedWebsite);

        // Get all the blockedWebsiteList
        restBlockedWebsiteMockMvc.perform(get("/api/blocked-websites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blockedWebsite.getId())))
            .andExpect(jsonPath("$.[*].siteName").value(hasItem(DEFAULT_SITE_NAME.toString())));
    }
    
    @Test
    public void getBlockedWebsite() throws Exception {
        // Initialize the database
        blockedWebsiteRepository.save(blockedWebsite);

        // Get the blockedWebsite
        restBlockedWebsiteMockMvc.perform(get("/api/blocked-websites/{id}", blockedWebsite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blockedWebsite.getId()))
            .andExpect(jsonPath("$.siteName").value(DEFAULT_SITE_NAME.toString()));
    }

    @Test
    public void getNonExistingBlockedWebsite() throws Exception {
        // Get the blockedWebsite
        restBlockedWebsiteMockMvc.perform(get("/api/blocked-websites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBlockedWebsite() throws Exception {
        // Initialize the database
        blockedWebsiteService.save(blockedWebsite);

        int databaseSizeBeforeUpdate = blockedWebsiteRepository.findAll().size();

        // Update the blockedWebsite
        BlockedWebsite updatedBlockedWebsite = blockedWebsiteRepository.findById(blockedWebsite.getId()).get();
        updatedBlockedWebsite
            .siteName(UPDATED_SITE_NAME);

        restBlockedWebsiteMockMvc.perform(put("/api/blocked-websites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBlockedWebsite)))
            .andExpect(status().isOk());

        // Validate the BlockedWebsite in the database
        List<BlockedWebsite> blockedWebsiteList = blockedWebsiteRepository.findAll();
        assertThat(blockedWebsiteList).hasSize(databaseSizeBeforeUpdate);
        BlockedWebsite testBlockedWebsite = blockedWebsiteList.get(blockedWebsiteList.size() - 1);
        assertThat(testBlockedWebsite.getSiteName()).isEqualTo(UPDATED_SITE_NAME);
    }

    @Test
    public void updateNonExistingBlockedWebsite() throws Exception {
        int databaseSizeBeforeUpdate = blockedWebsiteRepository.findAll().size();

        // Create the BlockedWebsite

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlockedWebsiteMockMvc.perform(put("/api/blocked-websites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockedWebsite)))
            .andExpect(status().isBadRequest());

        // Validate the BlockedWebsite in the database
        List<BlockedWebsite> blockedWebsiteList = blockedWebsiteRepository.findAll();
        assertThat(blockedWebsiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBlockedWebsite() throws Exception {
        // Initialize the database
        blockedWebsiteService.save(blockedWebsite);

        int databaseSizeBeforeDelete = blockedWebsiteRepository.findAll().size();

        // Get the blockedWebsite
        restBlockedWebsiteMockMvc.perform(delete("/api/blocked-websites/{id}", blockedWebsite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BlockedWebsite> blockedWebsiteList = blockedWebsiteRepository.findAll();
        assertThat(blockedWebsiteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlockedWebsite.class);
        BlockedWebsite blockedWebsite1 = new BlockedWebsite();
        blockedWebsite1.setId("id1");
        BlockedWebsite blockedWebsite2 = new BlockedWebsite();
        blockedWebsite2.setId(blockedWebsite1.getId());
        assertThat(blockedWebsite1).isEqualTo(blockedWebsite2);
        blockedWebsite2.setId("id2");
        assertThat(blockedWebsite1).isNotEqualTo(blockedWebsite2);
        blockedWebsite1.setId(null);
        assertThat(blockedWebsite1).isNotEqualTo(blockedWebsite2);
    }
}
