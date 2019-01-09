package org.censorship.web.rest;

import org.censorship.InternetCensorshipApp;

import org.censorship.domain.Isp;
import org.censorship.repository.IspRepository;
import org.censorship.service.IspService;
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
 * Test class for the IspResource REST controller.
 *
 * @see IspResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InternetCensorshipApp.class)
public class IspResourceIntTest {

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LONG_NAME = "BBBBBBBBBB";

    @Autowired
    private IspRepository ispRepository;

    @Autowired
    private IspService ispService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restIspMockMvc;

    private Isp isp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IspResource ispResource = new IspResource(ispService);
        this.restIspMockMvc = MockMvcBuilders.standaloneSetup(ispResource)
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
    public static Isp createEntity() {
        Isp isp = new Isp()
            .shortName(DEFAULT_SHORT_NAME)
            .longName(DEFAULT_LONG_NAME);
        return isp;
    }

    @Before
    public void initTest() {
        ispRepository.deleteAll();
        isp = createEntity();
    }

    @Test
    public void createIsp() throws Exception {
        int databaseSizeBeforeCreate = ispRepository.findAll().size();

        // Create the Isp
        restIspMockMvc.perform(post("/api/isps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isp)))
            .andExpect(status().isCreated());

        // Validate the Isp in the database
        List<Isp> ispList = ispRepository.findAll();
        assertThat(ispList).hasSize(databaseSizeBeforeCreate + 1);
        Isp testIsp = ispList.get(ispList.size() - 1);
        assertThat(testIsp.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testIsp.getLongName()).isEqualTo(DEFAULT_LONG_NAME);
    }

    @Test
    public void createIspWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ispRepository.findAll().size();

        // Create the Isp with an existing ID
        isp.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restIspMockMvc.perform(post("/api/isps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isp)))
            .andExpect(status().isBadRequest());

        // Validate the Isp in the database
        List<Isp> ispList = ispRepository.findAll();
        assertThat(ispList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllIsps() throws Exception {
        // Initialize the database
        ispRepository.save(isp);

        // Get all the ispList
        restIspMockMvc.perform(get("/api/isps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isp.getId())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].longName").value(hasItem(DEFAULT_LONG_NAME.toString())));
    }
    
    @Test
    public void getIsp() throws Exception {
        // Initialize the database
        ispRepository.save(isp);

        // Get the isp
        restIspMockMvc.perform(get("/api/isps/{id}", isp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(isp.getId()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.longName").value(DEFAULT_LONG_NAME.toString()));
    }

    @Test
    public void getNonExistingIsp() throws Exception {
        // Get the isp
        restIspMockMvc.perform(get("/api/isps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateIsp() throws Exception {
        // Initialize the database
        ispService.save(isp);

        int databaseSizeBeforeUpdate = ispRepository.findAll().size();

        // Update the isp
        Isp updatedIsp = ispRepository.findById(isp.getId()).get();
        updatedIsp
            .shortName(UPDATED_SHORT_NAME)
            .longName(UPDATED_LONG_NAME);

        restIspMockMvc.perform(put("/api/isps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIsp)))
            .andExpect(status().isOk());

        // Validate the Isp in the database
        List<Isp> ispList = ispRepository.findAll();
        assertThat(ispList).hasSize(databaseSizeBeforeUpdate);
        Isp testIsp = ispList.get(ispList.size() - 1);
        assertThat(testIsp.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testIsp.getLongName()).isEqualTo(UPDATED_LONG_NAME);
    }

    @Test
    public void updateNonExistingIsp() throws Exception {
        int databaseSizeBeforeUpdate = ispRepository.findAll().size();

        // Create the Isp

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIspMockMvc.perform(put("/api/isps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isp)))
            .andExpect(status().isBadRequest());

        // Validate the Isp in the database
        List<Isp> ispList = ispRepository.findAll();
        assertThat(ispList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteIsp() throws Exception {
        // Initialize the database
        ispService.save(isp);

        int databaseSizeBeforeDelete = ispRepository.findAll().size();

        // Get the isp
        restIspMockMvc.perform(delete("/api/isps/{id}", isp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Isp> ispList = ispRepository.findAll();
        assertThat(ispList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Isp.class);
        Isp isp1 = new Isp();
        isp1.setId("id1");
        Isp isp2 = new Isp();
        isp2.setId(isp1.getId());
        assertThat(isp1).isEqualTo(isp2);
        isp2.setId("id2");
        assertThat(isp1).isNotEqualTo(isp2);
        isp1.setId(null);
        assertThat(isp1).isNotEqualTo(isp2);
    }
}
