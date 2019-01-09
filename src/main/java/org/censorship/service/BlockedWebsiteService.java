package org.censorship.service;

import org.censorship.domain.BlockedWebsite;
import org.censorship.repository.BlockedWebsiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing BlockedWebsite.
 */
@Service
public class BlockedWebsiteService {

    private final Logger log = LoggerFactory.getLogger(BlockedWebsiteService.class);

    private final BlockedWebsiteRepository blockedWebsiteRepository;

    public BlockedWebsiteService(BlockedWebsiteRepository blockedWebsiteRepository) {
        this.blockedWebsiteRepository = blockedWebsiteRepository;
    }

    /**
     * Save a blockedWebsite.
     *
     * @param blockedWebsite the entity to save
     * @return the persisted entity
     */
    public BlockedWebsite save(BlockedWebsite blockedWebsite) {
        log.debug("Request to save BlockedWebsite : {}", blockedWebsite);
        return blockedWebsiteRepository.save(blockedWebsite);
    }

    /**
     * Get all the blockedWebsites.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<BlockedWebsite> findAll(Pageable pageable) {
        log.debug("Request to get all BlockedWebsites");
        return blockedWebsiteRepository.findAll(pageable);
    }


    /**
     * Get one blockedWebsite by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<BlockedWebsite> findOne(String id) {
        log.debug("Request to get BlockedWebsite : {}", id);
        return blockedWebsiteRepository.findById(id);
    }

    /**
     * Delete the blockedWebsite by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete BlockedWebsite : {}", id);
        blockedWebsiteRepository.deleteById(id);
    }
}
