-- Seed a default admin account for fresh installs.
-- Username: admin
-- Password: admin (MD5+Base64 hash expected by legacy WildFly Database login module)
INSERT INTO USERS (USERID, USERNAME, EMAIL, PASSWORD, STATE, REGID, GROUP_ID)
SELECT
  'admin',
  'Default Administrator',
  'admin@localhost',
  'ISMvKXpXpadDiUoOSoAfww==',
  'PROOFED',
  NULL,
  r.ID
FROM ROLES r
WHERE r.NAME = 'ADMIN'
  AND NOT EXISTS (
    SELECT 1
    FROM USERS u
    JOIN ROLES rr ON rr.ID = u.GROUP_ID
    WHERE rr.NAME = 'ADMIN'
  )
  AND NOT EXISTS (
    SELECT 1
    FROM USERS u2
    WHERE u2.USERID = 'admin'
  );
