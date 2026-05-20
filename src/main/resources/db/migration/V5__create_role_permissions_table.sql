-- =========================================================
-- ROLE PERMISSIONS TABLE
-- MANY-TO-MANY, it means One role can have:
-- ADMIN -> READ, WRITE, DELETE
-- HR -> READ, WRITE
-- Employee -> READ ONLY etc.
-- =========================================================
CREATE TABLE role_permissions
(
    id            BIGSERIAL PRIMARY KEY,

    role_id       BIGINT       NOT NULL,

    permission_id BIGINT       NOT NULL,

    assigned_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    assigned_by   VARCHAR(100) NOT NULL
--     PRIMARY KEY (role_id, permission_id),
--
--     CONSTRAINT fk_role_permissions_role
--         FOREIGN KEY (role_id)
--             REFERENCES auth_db.roles(id)
--             ON DELETE CASCADE,
--
--     CONSTRAINT fk_role_permissions_permission
--         FOREIGN KEY (permission_id)
--             REFERENCES auth_db.permissions(id)
--             ON DELETE CASCADE
);