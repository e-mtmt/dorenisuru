package jp.eunika.dorenisuru.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.eunika.dorenisuru.domain.entity.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
	@Query("SELECT t FROM Topic t WHERE t.hash = :hash")
	Topic findByHash(@Param("hash") String hash);
}
