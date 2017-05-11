package jp.eunika.dorenisuru.domain.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "topics")
@ToString(exclude = { "choices", "voters" })
public class Topic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String contents;

	@Column(nullable = false)
	private String hash;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "topic")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Choice> choices;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "topic")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Voter> voters;

	public static Topic of(String title, String contents) {
		Topic topic = new Topic();
		topic.title = title;
		topic.contents = contents;
		topic.hash = UUID.randomUUID().toString();
		topic.createdAt = LocalDateTime.now();
		topic.updatedAt = LocalDateTime.now();
		return topic;
	}
}
