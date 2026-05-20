-- =========================================================
-- PERMISSIONS TABLE
-- =========================================================
CREATE TABLE permissions
(
    id              BIGSERIAL PRIMARY KEY,

    permission_name VARCHAR(150) NOT NULL UNIQUE,

    description     TEXT,

    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by      VARCHAR(100) NOT NULL,

    updated_at      TIMESTAMP,
    updated_by      VARCHAR(100)
);