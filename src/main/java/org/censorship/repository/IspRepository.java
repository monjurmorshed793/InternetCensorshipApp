package org.censorship.repository;

import org.censorship.domain.Isp;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Isp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IspRepository extends MongoRepository<Isp, String> {

}
