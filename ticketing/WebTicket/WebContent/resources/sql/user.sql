CREATE TABLE IF NOT EXISTS user (
	username VARCHAR(20) NOT NULL,
	password VARCHAR(20) NOT NULL,
	PRIMARY KEY (username)
) default CHARSET=utf8;