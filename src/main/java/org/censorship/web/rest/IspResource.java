package org.censorship.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.censorship.domain.Isp;
import org.censorship.service.IspService;
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
 * REST controller for managing Isp.
 */
@RestController
@RequestMapping("/api")
public class IspResource {

    private final Logger log = LoggerFactory.getLogger(IspResource.class);

    private static final String ENTITY_NAME = "isp";

    private final IspService ispService;

    public IspResource(IspService ispService) {
        this.ispService = ispService;
    }

    /**
     * POST  /isps : Create a new isp.
     *
     * @param isp the isp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new isp, or with status 400 (Bad Request) if the isp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/isps")
    @Timed
    public ResponseEntity<Isp> createIsp(@RequestBody Isp isp) throws URISyntaxException {
        log.debug("REST request to save Isp : {}", isp);
        if (isp.getId() != null) {
            throw new BadRequestAlertException("A new isp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Isp result = ispService.save(isp);
        return ResponseEntity.created(new URI("/api/isps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /isps : Updates an existing isp.
     *
     * @param isp the isp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated isp,
     * or with status 400 (Bad Request) if the isp is not valid,
     * or with status 500 (Internal Server Error) if the isp couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/isps")
    @Timed
    public ResponseEntity<Isp> updateIsp(@RequestBody Isp isp) throws URISyntaxException {
        log.debug("REST request to update Isp : {}", isp);
        if (isp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Isp result = ispService.save(isp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, isp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /isps : get all the isps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of isps in body
     */
    @GetMapping("/isps")
    @Timed
    public ResponseEntity<List<Isp>> getAllIsps(Pageable pageable) {
        log.debug("REST request to get a page of Isps");
        Page<Isp> page = ispService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/isps");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /isps/:id : get the "id" isp.
     *
     * @param id the id of the isp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the isp, or with status 404 (Not Found)
     */
    @GetMapping("/isps/{id}")
    @Timed
    public ResponseEntity<Isp> getIsp(@PathVariable String id) {
        log.debug("REST request to get Isp : {}", id);
        Optional<Isp> isp = ispService.findOne(id);
        return ResponseUtil.wrapOrNotFound(isp);
    }

    /**
     * DELETE  /isps/:id : delete the "id" isp.
     *
     * @param id the id of the isp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/isps/{id}")
    @Timed
    public ResponseEntity<Void> deleteIsp(@PathVariable String id) {
        log.debug("REST request to delete Isp : {}", id);
        ispService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
