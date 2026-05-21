-- =========================================================
-- Update USERS TABLE
-- =========================================================
-- update the employee_id column type from VARCHAR(50) to BIGINT
ALTER TABLE users
ALTER COLUMN employee_id TYPE BIGINT
USING employee_id::BIGINT;
