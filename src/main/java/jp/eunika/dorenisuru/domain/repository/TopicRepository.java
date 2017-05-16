package jp.eunika.dorenisuru.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.eunika.dorenisuru.domain.entity.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
	@Query("SELECT t FROM Topic t WHERE t.hash = :hash")
//	@Query(
//			value = "SELECT * FROM topics t LEFT OUTER JOIN choices c ON t.id = c.topic_id "
//					+ "LEFT OUTER JOIN voters v ON t.id = v.topic_id " + "LEFT OUTER JOIN voter_choices vc ON c.id = vc.choice_id "
//					+ "WHERE t.hash = :hash",
//			nativeQuery = true)
	Topic findByHash(@Param("hash") String hash);

//	@Modifying
//	@Query("delete from Topic t where t.hash = :hash")
//	void deleteByHash(@Param("hash") String hash);
}
