package com.SoupOfYingyme.login;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.SoupOfYingyme.QuestionData;

@Entity
public class Account {
	@Id
	private String username;
	
	private String password;
	
	private String role = "USER";
	
	@ManyToMany
	private Set<QuestionData> good_question;
	
	public Set<QuestionData> getGood_question() {
		return good_question;
	}
	public void setGood_question(Set<QuestionData> good_question) {
		this.good_question = good_question;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
