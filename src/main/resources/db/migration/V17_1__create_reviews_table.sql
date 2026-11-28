CREATE TABLE reviews (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    show_id INT NOT NULL,
    review TEXT,
    stars TINYINT UNSIGNED NOT NULL,
    validated BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_reviews_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_reviews_show
        FOREIGN KEY (show_id)
        REFERENCES shows(id),

    CONSTRAINT chk_reviews_stars
        CHECK (stars BETWEEN 1 AND 5)
);
