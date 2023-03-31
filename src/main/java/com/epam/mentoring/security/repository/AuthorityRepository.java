package com.epam.mentoring.security.repository;

import org.springframework.data.repository.CrudRepository;

import com.epam.mentoring.security.model.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
}
