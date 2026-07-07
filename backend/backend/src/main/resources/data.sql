-- Insertar usuario admin (contraseña: admin123 encriptada)
INSERT INTO users (id, username, password, email, role, enabled, created_at)
VALUES (
    gen_random_uuid(),
    'admin',
    '$2a$10$NkXGk9VJtUJtUJtUJtUJtOxGzLzLzLzLzLzLzLzLzLzLzLzLzLzL',
    'admin@mechanicmotoz.com',
    'ADMIN',
    true,
    NOW()
) ON CONFLICT (username) DO NOTHING;

-- Insertar usuario mecánico
INSERT INTO users (id, username, password, email, role, enabled, created_at)
VALUES (
    gen_random_uuid(),
    'mecanico',
    '$2a$10$NkXGk9VJtUJtUJtUJtUJtOxGzLzLzLzLzLzLzLzLzLzLzLzLzLzL',
    'mecanico@mechanicmotoz.com',
    'MECHANIC',
    true,
    NOW()
) ON CONFLICT (username) DO NOTHING;