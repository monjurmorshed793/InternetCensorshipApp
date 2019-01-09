package org.censorship.service;

import org.censorship.domain.Isp;
import org.censorship.repository.IspRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Isp.
 */
@Service
public class IspService {

    private final Logger log = LoggerFactory.getLogger(IspService.class);

    private final IspRepository ispRepository;

    public IspService(IspRepository ispRepository) {
        this.ispRepository = ispRepository;
    }

    /**
     * Save a isp.
     *
     * @param isp the entity to save
     * @return the persisted entity
     */
    public Isp save(Isp isp) {
        log.debug("Request to save Isp : {}", isp);
        return ispRepository.save(isp);
    }

    /**
     * Get all the isps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<Isp> findAll(Pageable pageable) {
        log.debug("Request to get all Isps");
        return ispRepository.findAll(pageable);
    }


    /**
     * Get one isp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Isp> findOne(String id) {
        log.debug("Request to get Isp : {}", id);
        return ispRepository.findById(id);
    }

    /**
     * Delete the isp by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Isp : {}", id);
        ispRepository.deleteById(id);
    }
}
