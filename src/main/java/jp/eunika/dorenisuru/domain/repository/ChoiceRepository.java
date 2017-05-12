package jp.eunika.dorenisuru.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.eunika.dorenisuru.domain.entity.Choice;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Integer> {}
