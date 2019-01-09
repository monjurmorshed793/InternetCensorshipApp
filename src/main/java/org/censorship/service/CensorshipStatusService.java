package org.censorship.service;

import org.censorship.domain.CensorshipStatus;
import org.censorship.repository.CensorshipStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing CensorshipStatus.
 */
@Service
public class CensorshipStatusService {

    private final Logger log = LoggerFactory.getLogger(CensorshipStatusService.class);

    private final CensorshipStatusRepository censorshipStatusRepository;

    public CensorshipStatusService(CensorshipStatusRepository censorshipStatusRepository) {
        this.censorshipStatusRepository = censorshipStatusRepository;
    }

    /**
     * Save a censorshipStatus.
     *
     * @param censorshipStatus the entity to save
     * @return the persisted entity
     */
    public CensorshipStatus save(CensorshipStatus censorshipStatus) {
        log.debug("Request to save CensorshipStatus : {}", censorshipStatus);
        return censorshipStatusRepository.save(censorshipStatus);
    }

    /**
     * Get all the censorshipStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<CensorshipStatus> findAll(Pageable pageable) {
        log.debug("Request to get all CensorshipStatuses");
        return censorshipStatusRepository.findAll(pageable);
    }


    /**
     * Get one censorshipStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<CensorshipStatus> findOne(String id) {
        log.debug("Request to get CensorshipStatus : {}", id);
        return censorshipStatusRepository.findById(id);
    }

    /**
     * Delete the censorshipStatus by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete CensorshipStatus : {}", id);
        censorshipStatusRepository.deleteById(id);
    }
}
