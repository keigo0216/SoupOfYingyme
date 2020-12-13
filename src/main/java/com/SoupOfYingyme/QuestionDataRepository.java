package com.SoupOfYingyme;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDataRepository extends JpaRepository<QuestionData, Long> {
	public Optional<QuestionData> findById(Long id);
}
