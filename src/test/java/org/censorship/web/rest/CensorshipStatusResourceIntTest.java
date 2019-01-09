package org.censorship.web.rest;

import org.censorship.InternetCensorshipApp;

import org.censorship.domain.CensorshipStatus;
import org.censorship.repository.CensorshipStatusRepository;
import org.censorship.service.CensorshipStatusService;
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

import org.censorship.domain.enumeration.CensorshipType;
import org.censorship.domain.enumeration.CensorshipType;
/**
 * Test class for the CensorshipStatusResource REST controller.
 *
 * @see CensorshipStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InternetCensorshipApp.class)
public class CensorshipStatusResourceIntTest {

    private static final String DEFAULT_ISP_ID = "AAAAAAAAAA";
    private static final String UPDATED_ISP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ISP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ISP_NAME = "BBBBBBBBBB";

    private static final CensorshipType DEFAULT_OONI_STATUS = CensorshipType.DNS;
    private static final CensorshipType UPDATED_OONI_STATUS = CensorshipType.TCP_IP;

    private static final CensorshipType DEFAULT_SYSTEM_STATUS = CensorshipType.DNS;
    private static final CensorshipType UPDATED_SYSTEM_STATUS = CensorshipType.TCP_IP;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CensorshipStatusRepository censorshipStatusRepository;

    @Autowired
    private CensorshipStatusService censorshipStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCensorshipStatusMockMvc;

    private CensorshipStatus censorshipStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CensorshipStatusResource censorshipStatusResource = new CensorshipStatusResource(censorshipStatusService);
        this.restCensorshipStatusMockMvc = MockMvcBuilders.standaloneSetup(censorshipStatusResource)
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
    public static CensorshipStatus createEntity() {
        CensorshipStatus censorshipStatus = new CensorshipStatus()
            .ispId(DEFAULT_ISP_ID)
            .ispName(DEFAULT_ISP_NAME)
            .ooniStatus(DEFAULT_OONI_STATUS)
            .systemStatus(DEFAULT_SYSTEM_STATUS)
            .description(DEFAULT_DESCRIPTION);
        return censorshipStatus;
    }

    @Before
    public void initTest() {
        censorshipStatusRepository.deleteAll();
        censorshipStatus = createEntity();
    }

    @Test
    public void createCensorshipStatus() throws Exception {
        int databaseSizeBeforeCreate = censorshipStatusRepository.findAll().size();

        // Create the CensorshipStatus
        restCensorshipStatusMockMvc.perform(post("/api/censorship-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(censorshipStatus)))
            .andExpect(status().isCreated());

        // Validate the CensorshipStatus in the database
        List<CensorshipStatus> censorshipStatusList = censorshipStatusRepository.findAll();
        assertThat(censorshipStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CensorshipStatus testCensorshipStatus = censorshipStatusList.get(censorshipStatusList.size() - 1);
        assertThat(testCensorshipStatus.getIspId()).isEqualTo(DEFAULT_ISP_ID);
        assertThat(testCensorshipStatus.getIspName()).isEqualTo(DEFAULT_ISP_NAME);
        assertThat(testCensorshipStatus.getOoniStatus()).isEqualTo(DEFAULT_OONI_STATUS);
        assertThat(testCensorshipStatus.getSystemStatus()).isEqualTo(DEFAULT_SYSTEM_STATUS);
        assertThat(testCensorshipStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createCensorshipStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = censorshipStatusRepository.findAll().size();

        // Create the CensorshipStatus with an existing ID
        censorshipStatus.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCensorshipStatusMockMvc.perform(post("/api/censorship-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(censorshipStatus)))
            .andExpect(status().isBadRequest());

        // Validate the CensorshipStatus in the database
        List<CensorshipStatus> censorshipStatusList = censorshipStatusRepository.findAll();
        assertThat(censorshipStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCensorshipStatuses() throws Exception {
        // Initialize the database
        censorshipStatusRepository.save(censorshipStatus);

        // Get all the censorshipStatusList
        restCensorshipStatusMockMvc.perform(get("/api/censorship-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(censorshipStatus.getId())))
            .andExpect(jsonPath("$.[*].ispId").value(hasItem(DEFAULT_ISP_ID.toString())))
            .andExpect(jsonPath("$.[*].ispName").value(hasItem(DEFAULT_ISP_NAME.toString())))
            .andExpect(jsonPath("$.[*].ooniStatus").value(hasItem(DEFAULT_OONI_STATUS.toString())))
            .andExpect(jsonPath("$.[*].systemStatus").value(hasItem(DEFAULT_SYSTEM_STATUS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    public void getCensorshipStatus() throws Exception {
        // Initialize the database
        censorshipStatusRepository.save(censorshipStatus);

        // Get the censorshipStatus
        restCensorshipStatusMockMvc.perform(get("/api/censorship-statuses/{id}", censorshipStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(censorshipStatus.getId()))
            .andExpect(jsonPath("$.ispId").value(DEFAULT_ISP_ID.toString()))
            .andExpect(jsonPath("$.ispName").value(DEFAULT_ISP_NAME.toString()))
            .andExpect(jsonPath("$.ooniStatus").value(DEFAULT_OONI_STATUS.toString()))
            .andExpect(jsonPath("$.systemStatus").value(DEFAULT_SYSTEM_STATUS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingCensorshipStatus() throws Exception {
        // Get the censorshipStatus
        restCensorshipStatusMockMvc.perform(get("/api/censorship-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCensorshipStatus() throws Exception {
        // Initialize the database
        censorshipStatusService.save(censorshipStatus);

        int databaseSizeBeforeUpdate = censorshipStatusRepository.findAll().size();

        // Update the censorshipStatus
        CensorshipStatus updatedCensorshipStatus = censorshipStatusRepository.findById(censorshipStatus.getId()).get();
        updatedCensorshipStatus
            .ispId(UPDATED_ISP_ID)
            .ispName(UPDATED_ISP_NAME)
            .ooniStatus(UPDATED_OONI_STATUS)
            .systemStatus(UPDATED_SYSTEM_STATUS)
            .description(UPDATED_DESCRIPTION);

        restCensorshipStatusMockMvc.perform(put("/api/censorship-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCensorshipStatus)))
            .andExpect(status().isOk());

        // Validate the CensorshipStatus in the database
        List<CensorshipStatus> censorshipStatusList = censorshipStatusRepository.findAll();
        assertThat(censorshipStatusList).hasSize(databaseSizeBeforeUpdate);
        CensorshipStatus testCensorshipStatus = censorshipStatusList.get(censorshipStatusList.size() - 1);
        assertThat(testCensorshipStatus.getIspId()).isEqualTo(UPDATED_ISP_ID);
        assertThat(testCensorshipStatus.getIspName()).isEqualTo(UPDATED_ISP_NAME);
        assertThat(testCensorshipStatus.getOoniStatus()).isEqualTo(UPDATED_OONI_STATUS);
        assertThat(testCensorshipStatus.getSystemStatus()).isEqualTo(UPDATED_SYSTEM_STATUS);
        assertThat(testCensorshipStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingCensorshipStatus() throws Exception {
        int databaseSizeBeforeUpdate = censorshipStatusRepository.findAll().size();

        // Create the CensorshipStatus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCensorshipStatusMockMvc.perform(put("/api/censorship-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(censorshipStatus)))
            .andExpect(status().isBadRequest());

        // Validate the CensorshipStatus in the database
        List<CensorshipStatus> censorshipStatusList = censorshipStatusRepository.findAll();
        assertThat(censorshipStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCensorshipStatus() throws Exception {
        // Initialize the database
        censorshipStatusService.save(censorshipStatus);

        int databaseSizeBeforeDelete = censorshipStatusRepository.findAll().size();

        // Get the censorshipStatus
        restCensorshipStatusMockMvc.perform(delete("/api/censorship-statuses/{id}", censorshipStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CensorshipStatus> censorshipStatusList = censorshipStatusRepository.findAll();
        assertThat(censorshipStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CensorshipStatus.class);
        CensorshipStatus censorshipStatus1 = new CensorshipStatus();
        censorshipStatus1.setId("id1");
        CensorshipStatus censorshipStatus2 = new CensorshipStatus();
        censorshipStatus2.setId(censorshipStatus1.getId());
        assertThat(censorshipStatus1).isEqualTo(censorshipStatus2);
        censorshipStatus2.setId("id2");
        assertThat(censorshipStatus1).isNotEqualTo(censorshipStatus2);
        censorshipStatus1.setId(null);
        assertThat(censorshipStatus1).isNotEqualTo(censorshipStatus2);
    }
}
