create table voter_choices(
  id         serial      primary key,
  feeling    varchar(10) not null default 'Unknown',
  voter_id   integer     not null references voters(id),
  choice_id  integer     not null references choices(id),
  created_at timestamp   not null,
  updated_at timestamp   not null
);

create unique index idx_voter_choices_on_voter_id_and_choice_id on voter_choices(voter_id, choice_id);
