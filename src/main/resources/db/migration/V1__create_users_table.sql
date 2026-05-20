-- =========================================================
-- USERS TABLE
-- =========================================================
CREATE TABLE users
(
    id                         BIGSERIAL PRIMARY KEY,

    employee_id                VARCHAR(50)  NOT NULL UNIQUE,

    username                   VARCHAR(100) NOT NULL UNIQUE,

    email                      VARCHAR(150) NOT NULL UNIQUE,

    password_hash              TEXT         NOT NULL,

    is_active                  BOOLEAN      NOT NULL DEFAULT TRUE,

    is_account_non_locked      BOOLEAN      NOT NULL DEFAULT TRUE,

    is_account_non_expired     BOOLEAN      NOT NULL DEFAULT TRUE,

    is_credentials_non_expired BOOLEAN      NOT NULL DEFAULT TRUE,

    last_login_at              TIMESTAMP,

    created_at                 TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by                 VARCHAR(100) NOT NULL,

    updated_at                 TIMESTAMP,
    updated_by                 VARCHAR(100)
);