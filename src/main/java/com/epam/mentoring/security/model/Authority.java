package com.epam.mentoring.security.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "authorities")
public class Authority {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String authority;

	@ManyToOne
	@JoinColumn(name = "username", nullable = false)
	private User user;

	public Authority() {
	}

	public Authority(String authority, User user) {
		this.authority = authority;
		this.user = user;
	}

	public String getAuthority() {
		return authority;
	}
}
