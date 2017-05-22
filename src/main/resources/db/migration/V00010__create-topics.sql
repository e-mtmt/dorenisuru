create table topics(
  id               serial        primary key,
  title            varchar(50)   not null,
  contents         varchar(1000) not null,
  hash             varchar(50)   not null,
  last_accessed_at timestamp,
  created_at       timestamp     not null,
  updated_at       timestamp     not null
);

create unique index idx_topics_on_hash on topics(hash);
create index idx_topics_on_last_accessed_at on topics(last_accessed_at);
