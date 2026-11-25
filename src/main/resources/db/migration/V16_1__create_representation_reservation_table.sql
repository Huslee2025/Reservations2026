
CREATE TABLE representation_reservation (
    id INT NOT NULL AUTO_INCREMENT,
    representation_id INT NOT NULL,
    reservation_id INT NOT NULL,
    price_id INT NOT NULL,
    quantity TINYINT NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_representation_reservation_representation
        FOREIGN KEY (representation_id)
        REFERENCES representations(id),

    CONSTRAINT fk_representation_reservation_reservation
        FOREIGN KEY (reservation_id)
        REFERENCES reservations(id),

    CONSTRAINT fk_representation_reservation_price
        FOREIGN KEY (price_id)
        REFERENCES prices(id),

    CONSTRAINT chk_representation_reservation_quantity
        CHECK (quantity >= 1)
);

