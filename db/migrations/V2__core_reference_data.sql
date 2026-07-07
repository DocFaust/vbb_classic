INSERT INTO ROLES (NAME, DESCRIPTION)
VALUES
  ('READER', 'Leser'),
  ('USER', 'Benutzer'),
  ('ADMIN', 'Administratoren')
ON DUPLICATE KEY UPDATE DESCRIPTION = VALUES(DESCRIPTION);

INSERT INTO CONFIG (CONFIGKEY, CONFIGVALUE)
VALUES
  ('subject', 'Registrierung bei der Volleyballbuchung'),
  ('sender.address', 'werner@docfaust.de'),
  ('domain', 'http://localhost:8080/vbb')
ON DUPLICATE KEY UPDATE CONFIGVALUE = VALUES(CONFIGVALUE);
