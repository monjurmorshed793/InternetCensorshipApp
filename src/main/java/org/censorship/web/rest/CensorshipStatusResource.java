package org.censorship.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.censorship.domain.CensorshipStatus;
import org.censorship.service.CensorshipStatusService;
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
 * REST controller for managing CensorshipStatus.
 */
@RestController
@RequestMapping("/api")
public class CensorshipStatusResource {

    private final Logger log = LoggerFactory.getLogger(CensorshipStatusResource.class);

    private static final String ENTITY_NAME = "censorshipStatus";

    private final CensorshipStatusService censorshipStatusService;

    public CensorshipStatusResource(CensorshipStatusService censorshipStatusService) {
        this.censorshipStatusService = censorshipStatusService;
    }

    /**
     * POST  /censorship-statuses : Create a new censorshipStatus.
     *
     * @param censorshipStatus the censorshipStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new censorshipStatus, or with status 400 (Bad Request) if the censorshipStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/censorship-statuses")
    @Timed
    public ResponseEntity<CensorshipStatus> createCensorshipStatus(@RequestBody CensorshipStatus censorshipStatus) throws URISyntaxException {
        log.debug("REST request to save CensorshipStatus : {}", censorshipStatus);
        if (censorshipStatus.getId() != null) {
            throw new BadRequestAlertException("A new censorshipStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CensorshipStatus result = censorshipStatusService.save(censorshipStatus);
        return ResponseEntity.created(new URI("/api/censorship-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /censorship-statuses : Updates an existing censorshipStatus.
     *
     * @param censorshipStatus the censorshipStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated censorshipStatus,
     * or with status 400 (Bad Request) if the censorshipStatus is not valid,
     * or with status 500 (Internal Server Error) if the censorshipStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/censorship-statuses")
    @Timed
    public ResponseEntity<CensorshipStatus> updateCensorshipStatus(@RequestBody CensorshipStatus censorshipStatus) throws URISyntaxException {
        log.debug("REST request to update CensorshipStatus : {}", censorshipStatus);
        if (censorshipStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CensorshipStatus result = censorshipStatusService.save(censorshipStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, censorshipStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /censorship-statuses : get all the censorshipStatuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of censorshipStatuses in body
     */
    @GetMapping("/censorship-statuses")
    @Timed
    public ResponseEntity<List<CensorshipStatus>> getAllCensorshipStatuses(Pageable pageable) {
        log.debug("REST request to get a page of CensorshipStatuses");
        Page<CensorshipStatus> page = censorshipStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/censorship-statuses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /censorship-statuses/:id : get the "id" censorshipStatus.
     *
     * @param id the id of the censorshipStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the censorshipStatus, or with status 404 (Not Found)
     */
    @GetMapping("/censorship-statuses/{id}")
    @Timed
    public ResponseEntity<CensorshipStatus> getCensorshipStatus(@PathVariable String id) {
        log.debug("REST request to get CensorshipStatus : {}", id);
        Optional<CensorshipStatus> censorshipStatus = censorshipStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(censorshipStatus);
    }

    /**
     * DELETE  /censorship-statuses/:id : delete the "id" censorshipStatus.
     *
     * @param id the id of the censorshipStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/censorship-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCensorshipStatus(@PathVariable String id) {
        log.debug("REST request to delete CensorshipStatus : {}", id);
        censorshipStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
