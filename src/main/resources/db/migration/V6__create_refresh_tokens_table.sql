-- =========================================================
-- REFRESH TOKENS TABLE
-- =========================================================
CREATE TABLE refresh_tokens
(
    id         BIGSERIAL PRIMARY KEY,

    user_id    BIGINT    NOT NULL,

    token      TEXT      NOT NULL UNIQUE,

    expires_at TIMESTAMP NOT NULL,

    revoked    BOOLEAN   NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100)

--     CONSTRAINT fk_refresh_tokens_user
--         FOREIGN KEY (user_id)
--             REFERENCES auth_db.users (id)
--             ON DELETE CASCADE
);