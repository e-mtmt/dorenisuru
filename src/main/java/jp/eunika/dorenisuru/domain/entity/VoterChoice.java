package jp.eunika.dorenisuru.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voter_choices")
public class VoterChoice {
	public enum Feeling {
		OK, NG, Unknown
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Feeling feeling;

	@Column(nullable = false)
	private String comment;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "voter_id", nullable = false)
	private Voter voter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "choice_id", nullable = false)
	private Choice choice;

	public static VoterChoice of(Feeling feeling, String comment, Voter voter, Choice choice) {
		VoterChoice voterChoice = new VoterChoice();
		voterChoice.feeling = feeling;
		voterChoice.comment = comment;
		voterChoice.createdAt = LocalDateTime.now();
		voterChoice.updatedAt = LocalDateTime.now();
		voterChoice.voter = voter;
		voterChoice.choice = choice;
		return voterChoice;
	}
}