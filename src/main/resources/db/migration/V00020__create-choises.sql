create table choices(
  id         serial      primary key,
  text       varchar(30) not null,
  topic_id   integer     not null references topics(id),
  created_at timestamp   not null,
  updated_at timestamp   not null
);
