-- =========================================================
-- REFRESH TOKENS TABLE
-- =========================================================
ALTER TABLE refresh_tokens
    ADD COLUMN updated_at TIMESTAMP,
    ADD COLUMN updated_by VARCHAR(100);