CREATE TABLE users (
                       id   INTEGER      NOT NULL AUTO_INCREMENT,
                       username VARCHAR(128) NOT NULL,
                       password VARCHAR(128) NOT NULL,
                       PRIMARY KEY (id),
                       UNIQUE (username)
);

CREATE TABLE tasks (
                       id   INTEGER      NOT NULL AUTO_INCREMENT,
                       owner VARCHAR(128) NOT NULL,
                       title VARCHAR(128) NOT NULL,
                       content TEXT,
                       PRIMARY KEY (id),
                       FOREIGN KEY (owner) references users(username)
);