CREATE TABLE reservations (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    booking_date DATETIME NOT NULL,
    status VARCHAR(60) NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_reservations_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

