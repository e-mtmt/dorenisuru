ALTER TABLE choices
	DROP CONSTRAINT choices_topic_id_fkey,
	ADD CONSTRAINT  choices_topic_id_fkey FOREIGN KEY (topic_id) REFERENCES topics(id) ON DELETE CASCADE;

ALTER TABLE voters
	DROP CONSTRAINT voters_topic_id_fkey,
	ADD CONSTRAINT  voters_topic_id_fkey FOREIGN KEY (topic_id) REFERENCES topics(id) ON DELETE CASCADE;

ALTER TABLE voter_choices
	DROP CONSTRAINT voter_choices_choice_id_fkey,
	DROP CONSTRAINT voter_choices_voter_id_fkey,
	ADD CONSTRAINT  voter_choices_choice_id_fkey FOREIGN KEY (choice_id) REFERENCES choices(id) ON DELETE CASCADE,
	ADD CONSTRAINT  voter_choices_voter_id_fkey  FOREIGN KEY (voter_id)  REFERENCES voters(id)  ON DELETE CASCADE;
