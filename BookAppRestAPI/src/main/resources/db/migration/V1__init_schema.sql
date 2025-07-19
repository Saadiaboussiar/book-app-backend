CREATE TABLE  book(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    category VARCHAR(255),
    publisher VARCHAR(255),
    publishyear INTEGER,
    pages INTEGER

);

CREATE TABLE  author(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    age INTEGER,
    country VARCHAR(255),
    booksnumber INTEGER
);

CREATE TABLE  app_role(
    id SERIAL PRIMARY KEY,
    role_name VARCHAR(255) UNIQUE
);

CREATE TABLE  app_user(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE app_user_approles(
    app_user_id BIGINT NOT NULL,
    app_role_id BIGINT NOT NULL,
    PRIMARY KEY (app_user_id,app_role_id),
    FOREIGN KEY (app_user_id) REFERENCES app_user(id),
    FOREIGN KEY (app_role_id) REFERENCES app_role(id)

);