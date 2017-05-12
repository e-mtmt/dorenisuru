package jp.eunika.dorenisuru.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.eunika.dorenisuru.domain.entity.Voter;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Integer> {}
