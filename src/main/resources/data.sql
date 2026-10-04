-- 1. Inserción de Clientes Titulares (es_titular = 1, titular_asociado_id = NULL, estado = 'ACTIVO')
INSERT IGNORE INTO clientes
(id, fecha_creacion, nombre, cuil, mail, telefono, direccion, es_titular, titular_asociado_id, estado, token_activacion, fecha_expiracion_token)
VALUES
    (UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), '2026-01-10 09:00:00',
     'Ana Perez', '27-30111222-3', 'ana.perez@example.com', '388-4551001', 'Av. Belgrano 125, San Salvador de Jujuy', 1, NULL, 'ACTIVO', NULL, NULL),
    (UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), '2026-01-11 10:15:00',
     'Bruno Gomez', '20-28444555-6', 'bruno.gomez@example.com', '388-4551002', 'Alvear 450, San Salvador de Jujuy', 1, NULL, 'ACTIVO', NULL, NULL),
    (UUID_TO_BIN('33333333-3333-3333-3333-333333333333'), '2026-01-12 11:30:00',
     'Carla Lopez', '27-32666777-8', 'carla.lopez@example.com', '388-4551003', 'Belgrano 980, San Salvador de Jujuy', 1, NULL, 'ACTIVO', NULL, NULL),
    (UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), '2026-01-13 12:45:00',
     'Diego Sosa', '20-31222333-5', 'diego.sosa@example.com', '388-4551004', 'Patricias Argentinas 210, Palpala', 1, NULL, 'ACTIVO', NULL, NULL);

-- 2. Inserción de Clientes Adherentes (es_titular = 0, vinculados a titulares, estado = 'ACTIVO')
INSERT IGNORE INTO clientes
(id, fecha_creacion, nombre, cuil, mail, telefono, direccion, es_titular, titular_asociado_id, estado, token_activacion, fecha_expiracion_token)
VALUES
    -- Adherente (hijo) vinculado a Ana Perez
    (UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), '2026-01-14 09:00:00',
     'Lucas Perez', '20-50111222-9', 'lucas.perez@example.com', '388-4551005', 'Av. Belgrano 125, San Salvador de Jujuy', 0, UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), 'ACTIVO', NULL, NULL),
    -- Adherente (cónyuge) vinculado a Bruno Gomez
    (UUID_TO_BIN('66666666-6666-6666-6666-666666666666'), '2026-01-14 10:15:00',
     'Marta Gomez', '27-28444555-2', 'marta.gomez@example.com', '388-4551006', 'Alvear 450, San Salvador de Jujuy', 0, UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), 'ACTIVO', NULL, NULL);
-- 3. Cuentas Financieras
INSERT IGNORE INTO cuentas_financieras
(id, fecha_creacion, cbu, alias, saldo, estado, titular_principal_id, tipo_cuenta,
 tasa_interes_anual, cupo_limite, margen_descubierto_autorizado,
 costo_de_comision_de_mantenimiento)
VALUES
    (UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2026-01-15 09:00:00',
     '2850590940090418132456', 'ANA.AHORRO', 185000.50, 'ACTIVA',
     UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), 'CAJA_AHORRO',
     32.50, 3, 0, 0),
    (UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'), '2026-01-15 09:30:00',
     '2850590940090418132463', 'BRUNO.CUENTA', 42000.00, 'ACTIVA',
     UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), 'CUENTA_CORRIENTE',
     0, 0, 75000.00, 1500.00),
    (UUID_TO_BIN('cccccccc-cccc-cccc-cccc-cccccccccccc'), '2026-01-16 10:00:00',
     '2850590940090418132470', 'CARLA.AHORRO', 98500.75, 'ACTIVA',
     UUID_TO_BIN('33333333-3333-3333-3333-333333333333'), 'CAJA_AHORRO',
     30.00, 2, 0, 0),
    (UUID_TO_BIN('dddddddd-dddd-dddd-dddd-dddddddddddd'), '2026-01-16 10:30:00',
     '2850590940090418132487', 'DIEGO.CORRIENTE', -12500.00, 'SUSPENDIDA',
     UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), 'CUENTA_CORRIENTE',
     0, 0, 25000.00, 1200.00),
    (UUID_TO_BIN('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'), '2026-01-17 11:00:00',
     '2850590940090418132494', 'ANA.COMUN', 25000.00, 'ACTIVA',
     UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), 'CAJA_AHORRO',
     28.00, 1, 0, 0);

-- 4. Cotitulares
INSERT IGNORE INTO cliente_cotitular (cliente_principal_id, cotitular_id)
VALUES
    (UUID_TO_BIN('11111111-1111-1111-1111-111111111111'),
     UUID_TO_BIN('22222222-2222-2222-2222-222222222222')),
    (UUID_TO_BIN('33333333-3333-3333-3333-333333333333'),
     UUID_TO_BIN('44444444-4444-4444-4444-444444444444'));

-- 5. Transacciones
INSERT IGNORE INTO transacciones
(id, fecha_creacion, fecha_hora, monto, tipo, estado, cuenta_id)
VALUES
    (UUID_TO_BIN('f1111111-1111-1111-1111-111111111111'), '2026-02-01 09:00:00',
     '2026-02-01 08:55:00', 200000.00, 'DEPOSITO', 'COMPLETADA',
     UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa')),
    (UUID_TO_BIN('f2222222-2222-2222-2222-222222222222'), '2026-02-03 14:20:00',
     '2026-02-03 14:15:00', 15000.00, 'EXTRACCION', 'COMPLETADA',
     UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa')),
    (UUID_TO_BIN('f3333333-3333-3333-3333-333333333333'), '2026-02-05 16:10:00',
     '2026-02-05 16:10:00', 5000.00, 'TRANSFERENCIA_ENVIADA', 'COMPLETADA',
     UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa')),
    (UUID_TO_BIN('f4444444-4444-4444-4444-444444444444'), '2026-02-05 16:10:00',
     '2026-02-05 16:10:00', 5000.00, 'TRANSFERENCIA_RECIBIDA', 'COMPLETADA',
     UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb')),
    (UUID_TO_BIN('f5555555-5555-5555-5555-555555555555'), '2026-02-08 10:00:00',
     '2026-02-08 10:00:00', 12000.00, 'DEPOSITO', 'PENDIENTE',
     UUID_TO_BIN('cccccccc-cccc-cccc-cccc-cccccccccccc')),
    (UUID_TO_BIN('f6666666-6666-6666-6666-666666666666'), '2026-02-10 12:30:00',
     '2026-02-10 12:30:00', 8000.00, 'EXTRACCION', 'RECHAZADA',
     UUID_TO_BIN('dddddddd-dddd-dddd-dddd-dddddddddddd'));