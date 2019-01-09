package org.censorship.repository;

import org.censorship.domain.CensorshipStatus;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the CensorshipStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CensorshipStatusRepository extends MongoRepository<CensorshipStatus, String> {

}
