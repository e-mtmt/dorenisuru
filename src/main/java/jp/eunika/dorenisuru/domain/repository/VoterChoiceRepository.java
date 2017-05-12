package jp.eunika.dorenisuru.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.eunika.dorenisuru.domain.entity.VoterChoice;

@Repository
public interface VoterChoiceRepository extends JpaRepository<VoterChoice, Integer> {}
