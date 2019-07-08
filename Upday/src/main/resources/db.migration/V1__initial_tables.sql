CREATE TABLE article(
 id BIGINT(20) NOT NULL AUTO_INCREMENT,
 header varchar(100) NOT NULL,
 short_description  varchar(255) NOT NULL,
 text LONGTEXT,
 publish_date TIMESTAMP,
 PRIMARY KEY(id)
);

CREATE TABLE article_keywords(
article_id BIGINT(20) NOT NULL,
keywords varchar(100)
);

CREATE TABLE ARTICLE_AUTHORS(
article_id BIGINT(20) NOT NULL,
author VARCHAR(100) NOT NULL
);