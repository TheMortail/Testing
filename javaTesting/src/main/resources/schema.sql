CREATE TABLE if not exists user_record(
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    age int,
    address VARCHAR(50)
);