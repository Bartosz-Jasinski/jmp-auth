package com.epam.mentoring.security.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.mentoring.security.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	List<User> findUserByEnabledIsFalse();
}
