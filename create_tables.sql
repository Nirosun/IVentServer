DROP DATABASE IF EXISTS ivent;
CREATE DATABASE ivent;

USE ivent;

CREATE TABLE users (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE categories (
	id BIGINT NOT NULL AUTO_INCREMENT,
	user_id BIGINT NOT NULL,
	name VARCHAR(200) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE events (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(200) NOT NULL,
	event_time DATETIME NOT NULL,
	location TEXT NOT NULL,
	description TEXT NOT NULL,
	image_link TEXT,
	PRIMARY KEY (id)
);

CREATE TABLE category_event_binding (
	category_id BIGINT NOT NULL,
	event_id BIGINT NOT NULL,
	PRIMARY KEY (category_id, event_id),
	FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE,
	FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);

CREATE TABLE chats (
	id BIGINT NOT NULL AUTO_INCREMENT,
	user_id BIGINT NOT NULL,
	event_id BIGINT NOT NULL,
	chat_text TEXT NOT NULL,
	ts TIMESTAMP NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
	FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);

CREATE TABLE posts (
	id BIGINT NOT NULL AUTO_INCREMENT,
	user_id BIGINT NOT NULL,
	event_id BIGINT NOT NULL,
	post_text TEXT NOT NULL,
	ts TIMESTAMP NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
	FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);
