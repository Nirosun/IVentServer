DROP DATABASE ivent;
CREATE DATABASE ivent;

USE ivent;

CREATE TABLE users (
	id BIGINT NOT NULL AUTO_INCREMENT,
	fb_id BIGINT NOT NULL,
	name VARCHAR(100) NOT NULL,
	PRIMARY KEY (user_id)
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
	location VARCHAR(500) NOT NULL,
	description VARCHAR(5000) NOT NULL,
	image_link VARCHAR(500),
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
	chat_text VARCHAR(1000) NOT NULL,
	ts TIMESTAMP NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
	FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);
