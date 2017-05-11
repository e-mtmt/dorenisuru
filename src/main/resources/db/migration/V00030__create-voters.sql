create table voters(
  id         serial      primary key,
  name       varchar(20) not null,
  topic_id   integer     not null references topics(id),
  created_at timestamp   not null,
  updated_at timestamp   not null
);
