package com.mindshine.clevergrid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mindshine.clevergrid.domain.Authority;

/**
 * Spring Data MongoDB repository for the Authority entity.
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {
}
