-- =========================================================
-- ROLES TABLE
-- =========================================================
CREATE TABLE roles
(
    id          BIGSERIAL PRIMARY KEY,

    role_name   VARCHAR(100) NOT NULL UNIQUE,

    description TEXT,

    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by  VARCHAR(100) NOT NULL,

    updated_at  TIMESTAMP,
    updated_by  VARCHAR(100)
);