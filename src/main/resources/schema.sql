  DROP TABLE IF EXISTS articles;

CREATE TABLE IF NOT EXISTS articles (
  id serial PRIMARY KEY,
  title varchar(30) NOT NULL,
  date timestamptz NOT NULL,
  content varchar(1000) NOT NULL
);


