package com.crossover.trial.journals.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
public class Lock {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String key;

	@Column(nullable = false)
	private Date setDate;
	
    @PrePersist
    void onPersist() {
        this.setDate = new Date();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getSetDate() {
		return setDate;
	}

	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}
}
