create table voter_choices(
  id         serial      primary key,
  feeling    varchar(10) not null default 'Unknown',
  comment    varchar(50) not null,
  voter_id   integer     not null references voters(id),
  choice_id  integer     not null references choices(id),
  created_at timestamp   not null,
  updated_at timestamp   not null
);
