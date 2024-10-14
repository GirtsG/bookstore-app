CREATE TABLE IF NOT EXISTS books
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    name
    VARCHAR
(
    255
) NOT NULL UNIQUE,
    author VARCHAR
(
    255
),
    price DECIMAL
(
    10,
    2
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );
