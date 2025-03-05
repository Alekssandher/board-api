--liquibase formatted sql
--changeset Alekssandher:202503021416
--comment: boards stable create

CREATE TABLE BLOCKS(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    block_reason VARCHAR(255) NOT NULL,
    blocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    unblock_reason VARCHAR(255) NOT NULL,
    unblock_at TIMESTAMP NULL,
    card_id BIGINT NOT NULL,
    CONSTRAINT cards__blocks_fk FOREIGN KEY (card_id) REFERENCES CARDS(id) ON DELETE CASCADE

) ENGINE=InnoDB;
--rollback DROP TABLE BLOCKS