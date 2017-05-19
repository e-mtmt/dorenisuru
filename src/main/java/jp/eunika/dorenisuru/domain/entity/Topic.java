package jp.eunika.dorenisuru.domain.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
	@Column(nullable = false, updatable = false)
	private Integer id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String contents;

	@Column(nullable = false, updatable = false)
	private String hash;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "topic", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy("createdAt asc")
	private List<Choice> choices;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "topic", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy("createdAt asc")
	private List<Voter> voters;

	public static Topic of(String title, String contents) {
		Topic topic = new Topic();
		topic.title = title;
		topic.contents = contents;
		return topic;
	}

	@PrePersist
	public void preCreate() {
		this.hash = UUID.randomUUID().toString();
		this.createdAt = this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public Choice getChoice(Integer choiceId) {
		return choices.stream().filter(choice -> choice.getId() == choiceId).findAny().orElseThrow(
				EntityNotFoundException::new);
	}
}
