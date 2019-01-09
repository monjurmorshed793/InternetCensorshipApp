package org.censorship.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.censorship.domain.BlockedWebsite;
import org.censorship.service.BlockedWebsiteService;
import org.censorship.web.rest.errors.BadRequestAlertException;
import org.censorship.web.rest.util.HeaderUtil;
import org.censorship.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BlockedWebsite.
 */
@RestController
@RequestMapping("/api")
public class BlockedWebsiteResource {

    private final Logger log = LoggerFactory.getLogger(BlockedWebsiteResource.class);

    private static final String ENTITY_NAME = "blockedWebsite";

    private final BlockedWebsiteService blockedWebsiteService;

    public BlockedWebsiteResource(BlockedWebsiteService blockedWebsiteService) {
        this.blockedWebsiteService = blockedWebsiteService;
    }

    /**
     * POST  /blocked-websites : Create a new blockedWebsite.
     *
     * @param blockedWebsite the blockedWebsite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new blockedWebsite, or with status 400 (Bad Request) if the blockedWebsite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/blocked-websites")
    @Timed
    public ResponseEntity<BlockedWebsite> createBlockedWebsite(@RequestBody BlockedWebsite blockedWebsite) throws URISyntaxException {
        log.debug("REST request to save BlockedWebsite : {}", blockedWebsite);
        if (blockedWebsite.getId() != null) {
            throw new BadRequestAlertException("A new blockedWebsite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlockedWebsite result = blockedWebsiteService.save(blockedWebsite);
        return ResponseEntity.created(new URI("/api/blocked-websites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blocked-websites : Updates an existing blockedWebsite.
     *
     * @param blockedWebsite the blockedWebsite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated blockedWebsite,
     * or with status 400 (Bad Request) if the blockedWebsite is not valid,
     * or with status 500 (Internal Server Error) if the blockedWebsite couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/blocked-websites")
    @Timed
    public ResponseEntity<BlockedWebsite> updateBlockedWebsite(@RequestBody BlockedWebsite blockedWebsite) throws URISyntaxException {
        log.debug("REST request to update BlockedWebsite : {}", blockedWebsite);
        if (blockedWebsite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BlockedWebsite result = blockedWebsiteService.save(blockedWebsite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, blockedWebsite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /blocked-websites : get all the blockedWebsites.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of blockedWebsites in body
     */
    @GetMapping("/blocked-websites")
    @Timed
    public ResponseEntity<List<BlockedWebsite>> getAllBlockedWebsites(Pageable pageable) {
        log.debug("REST request to get a page of BlockedWebsites");
        Page<BlockedWebsite> page = blockedWebsiteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blocked-websites");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /blocked-websites/:id : get the "id" blockedWebsite.
     *
     * @param id the id of the blockedWebsite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the blockedWebsite, or with status 404 (Not Found)
     */
    @GetMapping("/blocked-websites/{id}")
    @Timed
    public ResponseEntity<BlockedWebsite> getBlockedWebsite(@PathVariable String id) {
        log.debug("REST request to get BlockedWebsite : {}", id);
        Optional<BlockedWebsite> blockedWebsite = blockedWebsiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blockedWebsite);
    }

    /**
     * DELETE  /blocked-websites/:id : delete the "id" blockedWebsite.
     *
     * @param id the id of the blockedWebsite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/blocked-websites/{id}")
    @Timed
    public ResponseEntity<Void> deleteBlockedWebsite(@PathVariable String id) {
        log.debug("REST request to delete BlockedWebsite : {}", id);
        blockedWebsiteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
