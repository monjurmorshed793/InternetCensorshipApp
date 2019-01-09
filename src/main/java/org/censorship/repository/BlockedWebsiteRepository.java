package org.censorship.repository;

import org.censorship.domain.BlockedWebsite;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the BlockedWebsite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlockedWebsiteRepository extends MongoRepository<BlockedWebsite, String> {

}
