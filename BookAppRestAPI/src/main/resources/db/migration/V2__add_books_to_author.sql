ALTER TABLE book ADD COLUMN author_id BIGINT;
ALTER TABLE book
    ADD CONSTRAINT fk_author
    FOREIGN KEY (author_id) REFERENCES author(id);