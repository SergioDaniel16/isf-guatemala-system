-- ===============================================
-- ESQUEMA COMPLETO ISF GUATEMALA
-- ===============================================

-- Eliminar tablas si existen (en orden correcto para evitar problemas de FK)
DROP TABLE IF EXISTS registro_horas CASCADE;
DROP TABLE IF EXISTS control_kilometraje CASCADE;
DROP TABLE IF EXISTS empleados CASCADE;
DROP TABLE IF EXISTS proyectos CASCADE;
DROP TABLE IF EXISTS tareas CASCADE;
DROP TABLE IF EXISTS vehiculos CASCADE;
DROP TABLE IF EXISTS puestos CASCADE;
DROP TABLE IF EXISTS oficinas CASCADE;

-- ===============================================
-- TABLA OFICINAS
-- ===============================================
CREATE TABLE oficinas (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    activo BOOLEAN NOT NULL DEFAULT true
);

-- ===============================================
-- TABLA PUESTOS
-- ===============================================
CREATE TABLE puestos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    detalle VARCHAR(500),
    salario_por_hora DECIMAL(10,2) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT true
);

-- ===============================================
-- TABLA EMPLEADOS
-- ===============================================
CREATE TABLE empleados (
    id BIGSERIAL PRIMARY KEY,
    primer_nombre VARCHAR(50) NOT NULL,
    segundo_nombre VARCHAR(50),
    primer_apellido VARCHAR(50) NOT NULL,
    segundo_apellido VARCHAR(50),
    contacto VARCHAR(8) NOT NULL UNIQUE,
    activo BOOLEAN NOT NULL DEFAULT true,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    puesto_id BIGINT NOT NULL REFERENCES puestos(id)
);

-- ===============================================
-- TABLA PROYECTOS
-- ===============================================
CREATE TABLE proyectos (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(200) NOT NULL,
    departamento VARCHAR(100) NOT NULL,
    municipio VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('DIRECTO', 'CAPITULO', 'EXTERNO')),
    cambio_dolar DECIMAL(10,4) NOT NULL,
    tarifa_base_vehiculo DECIMAL(10,2) NOT NULL,
    tarifa_km_extra DECIMAL(10,2) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT true,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    oficina_id BIGINT NOT NULL REFERENCES oficinas(id)
);

-- ===============================================
-- TABLA TAREAS
-- ===============================================
CREATE TABLE tareas (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    definicion VARCHAR(500),
    es_cobrable BOOLEAN NOT NULL DEFAULT true,
    activo BOOLEAN NOT NULL DEFAULT true,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===============================================
-- TABLA VEHICULOS
-- ===============================================
CREATE TABLE vehiculos (
    id BIGSERIAL PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    anio INTEGER NOT NULL,
    color VARCHAR(30) NOT NULL,
    placa VARCHAR(10) NOT NULL UNIQUE,
    tipo_vehiculo VARCHAR(50) NOT NULL,
    odometro_actual DECIMAL(12,2) NOT NULL,
    tipo_combustible VARCHAR(20) NOT NULL,
    transmision VARCHAR(20) NOT NULL,
    unidad_medida VARCHAR(15) NOT NULL CHECK (unidad_medida IN ('KILOMETROS', 'MILLAS')),
    estado VARCHAR(15) NOT NULL DEFAULT 'ACTIVO' CHECK (estado IN ('ACTIVO', 'INACTIVO', 'MANTENIMIENTO')),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===============================================
-- TABLA REGISTRO_HORAS
-- ===============================================
CREATE TABLE registro_horas (
    id BIGSERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    horas INTEGER NOT NULL CHECK (horas >= 1 AND horas <= 24),
    minutos INTEGER NOT NULL CHECK (minutos >= 0 AND minutos <= 59),
    total_horas_decimal DECIMAL(10,4) NOT NULL,
    costo_total DECIMAL(10,2) NOT NULL,
    es_cobrable BOOLEAN NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    oficina_id BIGINT NOT NULL REFERENCES oficinas(id),
    proyecto_id BIGINT NOT NULL REFERENCES proyectos(id),
    tarea_id BIGINT NOT NULL REFERENCES tareas(id),
    empleado_id BIGINT NOT NULL REFERENCES empleados(id)
);

-- ===============================================
-- TABLA CONTROL_KILOMETRAJE
-- ===============================================
CREATE TABLE control_kilometraje (
    id_viaje BIGSERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    destino VARCHAR(255),
    millaje_inicial DECIMAL(12,2) NOT NULL,
    millaje_egreso DECIMAL(12,2),
    millas_recorrido DECIMAL(12,2),
    kilometros_recorrido DECIMAL(12,2),
    km_extra DECIMAL(10,2),
    costo_base DECIMAL(10,2),
    costo_km_extra DECIMAL(10,2),
    costo_total_dolares DECIMAL(10,2),
    costo_total_quetzales DECIMAL(10,2),
    estado VARCHAR(15) NOT NULL DEFAULT 'ACTIVO' CHECK (estado IN ('ACTIVO', 'CERRADO', 'CANCELADO')),
    foto_inicial_url VARCHAR(500),
    foto_final_url VARCHAR(500),
    fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_cierre TIMESTAMP,
    vehiculo_id BIGINT NOT NULL REFERENCES vehiculos(id),
    oficina_id BIGINT NOT NULL REFERENCES oficinas(id),
    proyecto_id BIGINT NOT NULL REFERENCES proyectos(id),
    responsable_id BIGINT NOT NULL REFERENCES empleados(id)
);

-- ===============================================
-- ÍNDICES PARA MEJORAR PERFORMANCE
-- ===============================================
CREATE INDEX idx_empleados_puesto ON empleados(puesto_id);
CREATE INDEX idx_proyectos_oficina ON proyectos(oficina_id);
CREATE INDEX idx_registro_horas_fecha ON registro_horas(fecha);
CREATE INDEX idx_registro_horas_proyecto ON registro_horas(proyecto_id);
CREATE INDEX idx_registro_horas_empleado ON registro_horas(empleado_id);
CREATE INDEX idx_control_km_vehiculo ON control_kilometraje(vehiculo_id);
CREATE INDEX idx_control_km_fecha ON control_kilometraje(fecha);
CREATE INDEX idx_control_km_estado ON control_kilometraje(estado);

-- ===============================================
-- DATOS INICIALES
-- ===============================================

-- Oficinas
INSERT INTO oficinas (nombre, descripcion) VALUES 
    ('Nacional', 'Oficina Nacional - Guatemala'),
    ('Xela', 'Oficina Regional Quetzaltenango'),
    ('Joyabaj', 'Oficina Regional Joyabaj'),
    ('Sololá', 'Oficina Regional Sololá');

-- Puestos
INSERT INTO puestos (nombre, detalle, salario_por_hora) VALUES
    ('Coordinador de Proyecto', 'Responsable de la coordinación general del proyecto', 75.00),
    ('Ingeniero de Campo', 'Ingeniero responsable de las actividades técnicas en campo', 65.00),
    ('Supervisor de Obra', 'Supervisor de actividades de construcción', 50.00),
    ('Asistente Técnico', 'Apoyo en actividades técnicas y administrativas', 35.00),
    ('Administrador', 'Responsable de actividades administrativas', 45.00);

-- Empleados
INSERT INTO empleados (primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, contacto, puesto_id) VALUES
    ('Carlos', 'Antonio', 'Méndez', 'López', '12345678', 1),
    ('María', 'Elena', 'García', 'Rodríguez', '87654321', 2),
    ('José', 'Luis', 'Morales', NULL, '11223344', 3),
    ('Ana', NULL, 'Pérez', 'Vásquez', '44332211', 4);

-- Proyectos
INSERT INTO proyectos (codigo, nombre, departamento, municipio, tipo, cambio_dolar, tarifa_base_vehiculo, tarifa_km_extra, oficina_id) VALUES
    ('ISF-2024-001', 'Sistema de Agua Potable San Juan', 'Quetzaltenango', 'San Juan Ostuncalco', 'DIRECTO', 7.85, 150.00, 3.50, 2),
    ('ISF-2024-002', 'Puente Peatonal Comunidad El Progreso', 'Quiché', 'Joyabaj', 'CAPITULO', 7.80, 200.00, 4.00, 3),
    ('ISF-2024-003', 'Escuela Rural Sololá', 'Sololá', 'Sololá', 'EXTERNO', 7.90, 175.00, 3.75, 4);

-- Tareas
INSERT INTO tareas (nombre, definicion, es_cobrable) VALUES
    ('Supervisión de Obra', 'Supervisión general de actividades de construcción', true),
    ('Planificación', 'Actividades de planificación y diseño', true),
    ('Reuniones', 'Reuniones con comunidad y stakeholders', true),
    ('Capacitación', 'Capacitación a personal local', true),
    ('Tiempo de Transporte', 'Tiempo invertido en transporte', false),
    ('Tiempo de Almuerzo', 'Tiempo de descanso para almuerzo', false);

-- Vehículos de ejemplo
INSERT INTO vehiculos (marca, modelo, anio, color, placa, tipo_vehiculo, odometro_actual, tipo_combustible, transmision, unidad_medida) VALUES
    ('Toyota', 'Hilux', 2020, 'Blanco', 'P123ABC', 'Pickup', 45000.00, 'Diesel', 'Manual', 'KILOMETROS'),
    ('Nissan', 'Frontier', 2019, 'Gris', 'P456DEF', 'Pickup', 38000.00, 'Gasolina', 'Automática', 'KILOMETROS');

COMMIT;
