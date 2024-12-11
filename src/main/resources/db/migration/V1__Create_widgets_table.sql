CREATE TABLE widgets
(
    name        VARCHAR(100)     NOT NULL UNIQUE,
    description VARCHAR(1000)    NOT NULL,
    price       DOUBLE PRECISION NOT NULL CHECK (price >= 1.00 AND price <= 20000.00),
    PRIMARY KEY (name)
);
