-- ====================================================================
-- DAWA - SEMANA 11: ARQUITECTURA DE MICROSERVICIOS CON SPRING BOOT
-- Estudiante: Israel Angel Huaman Huaman
-- Docente: Ricardo Coello Palomino
-- ====================================================================

-- ====================================================================
-- PARTE 1: BASES DE DATOS PARA MICROSERVICIOS DE TIENDA / E-COMMERCE
-- ====================================================================

-- 1. BASE DE DATOS: bd_categoria (y bd_categoria_dev)
CREATE DATABASE IF NOT EXISTS bd_categoria;
CREATE DATABASE IF NOT EXISTS bd_categoria_dev;
USE bd_categoria_dev;

CREATE TABLE IF NOT EXISTS categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    estado VARCHAR(20) NOT NULL
);

INSERT INTO categoria (nombre, estado) VALUES 
('Laptops', 'ACTIVO'),
('Accesorios', 'ACTIVO'),
('Impresoras', 'ACTIVO');

-- 2. BASE DE DATOS: bd_cliente (y bd_cliente_dev)
CREATE DATABASE IF NOT EXISTS bd_cliente;
CREATE DATABASE IF NOT EXISTS bd_cliente_dev;
USE bd_cliente_dev;

CREATE TABLE IF NOT EXISTS cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    nro_dni VARCHAR(8) NOT NULL,
    estado VARCHAR(20) DEFAULT 'ACTIVO'
);

INSERT INTO cliente (nombre, apellidos, direccion, telefono, nro_dni, estado) VALUES
('Juan', 'Pérez Gómez', 'Av. Javier Prado 123, Lima', '987654321', '12345678', 'ACTIVO'),
('María', 'Ramírez Torres', 'Calle Los Olivos 456', '912345678', '87654321', 'ACTIVO');

-- 3. BASE DE DATOS: bd_producto (y bd_producto_dev)
CREATE DATABASE IF NOT EXISTS bd_producto;
CREATE DATABASE IF NOT EXISTS bd_producto_dev;
USE bd_producto_dev;

CREATE TABLE IF NOT EXISTS producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL,
    categoria_id BIGINT NOT NULL
);

INSERT INTO producto (nombre, precio, stock, categoria_id) VALUES
('Laptop Lenovo ThinkPad', 3500.00, 15, 1),
('Mouse Logitech M185', 89.90, 50, 2),
('Teclado Mecánico Redragon', 199.99, 25, 2);

-- 4. BASE DE DATOS: bd_pedido (y bd_pedido_dev)
CREATE DATABASE IF NOT EXISTS bd_pedido;
CREATE DATABASE IF NOT EXISTS bd_pedido_dev;
USE bd_pedido_dev;

CREATE TABLE IF NOT EXISTS pedido (
    id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    id_cliente BIGINT NOT NULL,
    total DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS detalle_pedido (
    id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pedido BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio DOUBLE NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE
);

-- ====================================================================
-- PARTE 2: BASES DE DATOS PARA ACTIVIDAD CLÍNICA "SALUD INTEGRAL"
-- ====================================================================

-- 5. BASE DE DATOS: bd_paciente (y bd_paciente_dev)
CREATE DATABASE IF NOT EXISTS bd_paciente;
CREATE DATABASE IF NOT EXISTS bd_paciente_dev;
USE bd_paciente_dev;

CREATE TABLE IF NOT EXISTS paciente (
    id_paciente INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(8) UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE,
    sexo CHAR(1),
    telefono VARCHAR(15),
    direccion VARCHAR(200),
    correo VARCHAR(100),
    estado CHAR(1) DEFAULT 'A'
);

INSERT INTO paciente (dni, nombres, apellidos, fecha_nacimiento, sexo, telefono, direccion, correo, estado) VALUES
('47852196', 'Carlos Alberto', 'Mendoza Quispe', '1990-05-14', 'M', '987112233', 'Av. Arequipa 1250, Lince', 'carlos.mendoza@email.com', 'A'),
('70854123', 'Lucía Fernanda', 'García Paredes', '1995-11-20', 'F', '991223344', 'Jr. Las Flores 340, San Isidro', 'lucia.garcia@email.com', 'A');

-- 6. BASE DE DATOS: bd_medico (y bd_medico_dev)
CREATE DATABASE IF NOT EXISTS bd_medico;
CREATE DATABASE IF NOT EXISTS bd_medico_dev;
USE bd_medico_dev;

CREATE TABLE IF NOT EXISTS especialidad (
    id_especialidad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200),
    estado CHAR(1) DEFAULT 'A'
);

CREATE TABLE IF NOT EXISTS medico (
    id_medico INT AUTO_INCREMENT PRIMARY KEY,
    cmp VARCHAR(20) UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    correo VARCHAR(100),
    id_especialidad INT NOT NULL,
    estado CHAR(1) DEFAULT 'A',
    FOREIGN KEY (id_especialidad) REFERENCES especialidad(id_especialidad)
);

CREATE TABLE IF NOT EXISTS horario_medico (
    id_horario INT AUTO_INCREMENT PRIMARY KEY,
    id_medico INT NOT NULL,
    dia_semana VARCHAR(15),
    hora_inicio TIME,
    hora_fin TIME,
    FOREIGN KEY (id_medico) REFERENCES medico(id_medico) ON DELETE CASCADE
);

INSERT INTO especialidad (nombre, descripcion, estado) VALUES
('Cardiología', 'Enfermedades cardiovasculares del corazón', 'A'),
('Pediatría', 'Atención integral a recién nacidos, niños y adolescentes', 'A');

INSERT INTO medico (cmp, nombres, apellidos, telefono, correo, id_especialidad, estado) VALUES
('CMP-58471', 'Alejandro', 'Vargas Salazar', '998877665', 'alejandro.vargas@saludintegral.pe', 1, 'A'),
('CMP-63214', 'Rosa Elena', 'Navarro Ríos', '994433221', 'rosa.navarro@saludintegral.pe', 2, 'A');

INSERT INTO horario_medico (id_medico, dia_semana, hora_inicio, hora_fin) VALUES
(1, 'LUNES', '08:00:00', '14:00:00'),
(1, 'MIERCOLES', '08:00:00', '14:00:00'),
(2, 'MARTES', '14:00:00', '20:00:00');

-- 7. BASE DE DATOS: bd_cita (y bd_cita_dev)
CREATE DATABASE IF NOT EXISTS bd_cita;
CREATE DATABASE IF NOT EXISTS bd_cita_dev;
USE bd_cita_dev;

CREATE TABLE IF NOT EXISTS cita (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    id_paciente INT NOT NULL,
    id_medico INT NOT NULL,
    motivo VARCHAR(300),
    estado VARCHAR(20) DEFAULT 'PROGRAMADA'
);

CREATE TABLE IF NOT EXISTS atencion (
    id_atencion INT AUTO_INCREMENT PRIMARY KEY,
    id_cita INT UNIQUE,
    diagnostico TEXT,
    tratamiento TEXT,
    observaciones TEXT,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cita) REFERENCES cita(id_cita) ON DELETE CASCADE
);

INSERT INTO cita (fecha, hora, id_paciente, id_medico, motivo, estado) VALUES
('2026-06-15', '09:00:00', 1, 1, 'Control preventivo cardiológico y taquicardia leve', 'PROGRAMADA');
