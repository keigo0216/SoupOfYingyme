package com.SoupOfYingyme;

public class JsonQuestionData {
	private Long id;
	
	private String answer;
	
	private String question;

	private long good;
	
	
	
	public JsonQuestionData() {}

	public JsonQuestionData(Long id, String question, String answer, long good) {
		super();
		this.id = id;
		this.answer = answer;
		this.question = question;
		this.good = good;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public long getGood() {
		return good;
	}

	public void setGood(long good) {
		this.good = good;
	}
	
	
}
