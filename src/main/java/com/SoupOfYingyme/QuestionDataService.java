package com.SoupOfYingyme;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionDataService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	QuestionDataRepository repository;
	
	@SuppressWarnings("unchecked")
	public List<QuestionData> getAllIdDesk() {
		List<QuestionData> list = entityManager.createQuery("from QuestionData").getResultList();
		Collections.reverse(list);
		return list;
	}
	
	public List<QuestionData> getAllGoodDesk() {
		List<QuestionData> list = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuestionData> query = builder.createQuery(QuestionData.class);
		Root<QuestionData> root = query.from(QuestionData.class);
		query.select(root).orderBy(builder.desc(root.get("good")));
		list = (List<QuestionData>)entityManager.createQuery(query).getResultList();
		return list;
	}
	
	public void addGood(Long id) {
		Optional<QuestionData> questiondata = repository.findById(id);
		long goodpoint = questiondata.get().getGood();
		goodpoint++;
		questiondata.get().setGood(goodpoint);
		repository.saveAndFlush(questiondata.get());
	}
	
	
}


















