-- Seed roles with fixed UUIDs (roles.id has no default, so we must provide one)
INSERT INTO roles (id, name) VALUES
  ('11111111-1111-1111-1111-111111111111', 'PATIENT'),
  ('22222222-2222-2222-2222-222222222222', 'DOCTOR'),
  ('33333333-3333-3333-3333-333333333333', 'ADMIN')
ON CONFLICT (name) DO NOTHING;