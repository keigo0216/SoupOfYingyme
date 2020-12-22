package com.SoupOfYingyme.login;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.SoupOfYingyme.QuestionData;

@Entity
public class Account {
	@Id
	@Column(length = 20, nullable = false)
	@NotBlank(message="空白は不可です")
	@Size(max = 20, message="文字数は20以下です")
	private String username;
	
	@Column(nullable = false)
	@NotBlank(message="空白は不可です")
	private String password;
	
	private String role = "USER";
	
	@ManyToMany(mappedBy="good_account")
	private Set<QuestionData> good_question;
	
	public void addGood_question(QuestionData question) {
		this.good_question.add(question);
		question.getGood_account().add(this);
	}
	
	public void deleteGood_question(QuestionData question) {
		this.good_question.remove(question);
		question.getGood_account().remove(this);
	}
	
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
