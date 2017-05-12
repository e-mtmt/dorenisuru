package jp.eunika.dorenisuru.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.eunika.dorenisuru.domain.entity.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
	@Query("select t from Topic t where t.hash = :hash")
	Topic findByHash(@Param("hash") String hash);

	@Modifying
	@Query("delete from Topic t where t.hash = :hash")
	void deleteByHash(@Param("hash") String hash);
}
