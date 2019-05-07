INSERT INTO vbb.ROLES (NAME, DESCRIPTION) VALUES
  ('READER','Leser'),
  ('USER','Benutzer'),
  ('ADMIN','Administratoren');

INSERT INTO vbb.CONFIG (CONFIGKEY, CONFIGVALUE) VALUES 
('subject', 'Registrierung bei der Volleyballbuchung'),
('sender.address', 'DocFaust75@gmail.com'),
('domain', 'http://localhost:8080/vbb');
