--liquibase formatted sql
--changeset Alekssandher:202503021416
--comment: boards stable create

CREATE TABLE BOARDS(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL

) ENGINE=InnoDB;
--rollback DROP TABLE BOARDS