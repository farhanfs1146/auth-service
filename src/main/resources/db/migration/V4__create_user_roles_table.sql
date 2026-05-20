-- =========================================================
-- USER ROLES TABLE
-- MANY-TO-MANY, it means One user can have:
-- ADMIN
-- HR
-- simultaneously.
-- =========================================================
CREATE TABLE user_roles
(
    id          BIGSERIAL PRIMARY KEY,

    user_id     BIGINT       NOT NULL,

    role_id     BIGINT       NOT NULL,

    assigned_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    assigned_by VARCHAR(100) NOT NULL

--     PRIMARY KEY (user_id, role_id),

--     CONSTRAINT fk_user_roles_user
--         FOREIGN KEY (user_id)
--             REFERENCES auth_db.users (id)
--             ON DELETE CASCADE,
--
--     CONSTRAINT fk_user_roles_role
--         FOREIGN KEY (role_id)
--             REFERENCES auth_db.roles (id)
--             ON DELETE CASCADE
);