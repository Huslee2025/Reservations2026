CREATE TABLE prices (
    id INT NOT NULL AUTO_INCREMENT,
    type VARCHAR(30) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    start_date DATE,
    end_date DATE,

    PRIMARY KEY (id)
);

