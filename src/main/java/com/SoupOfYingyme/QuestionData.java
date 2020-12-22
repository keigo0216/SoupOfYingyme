package com.SoupOfYingyme;

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

import com.SoupOfYingyme.login.Account;
import com.sun.istack.NotNull;

@Entity
public class QuestionData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@NotNull
	private long id;
	
	@Column(nullable = false)
	@NotBlank(message="空白は不可です。")
	@Size(max=300, message="文字数は300以下です。")
	private String question;
	
	@Column(nullable = false)
	@NotBlank(message="空白は不可です。")
	@Size(max = 300, message="文字数は300以下です。")
	private String answer;
	
	@Column(nullable = false)
	private long good = 0;
	
	@ManyToMany
	private Set<Account> good_account;
	
	public void addGood_account(Account account) {
		this.good_account.add(account);
		account.getGood_question().add(this);
	}
	
	public void deleteGood_account(Account account) {
		this.good_account.remove(account);
		account.getGood_question().remove(this);
	}

	public long getId() {
		return id;
	}

	public Set<Account> getGood_account() {
		return good_account;
	}

	public void setGood_account(Set<Account> good_account) {
		this.good_account = good_account;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public long getGood() {
		return good;
	}

	public void setGood(long good) {
		this.good = good;
	}

	
}
