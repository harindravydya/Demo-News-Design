CREATE TABLE article (
 id BIGINT(20) NOT NULL AUTO_INCREMENT,
 header varchar2(100) NOT NULL,
 short_description  varchar2(255) NOT NULL,
 text LONGTEXT,
 publish_date DATE,
 PRIMARY KEY(id)
);

CREATE TABLE article_keywords (
article_id BIGINT(20) NOT NULL,
keywords varchar2(100)
);

CREATE TABLE article_authors (
article_id BIGINT(20) NOT NULL,
authors VARCHAR(100) NOT NULL
);