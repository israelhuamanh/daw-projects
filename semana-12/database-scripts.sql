-- ====================================================================
-- DAWA - SEMANA 12: COMUNICACIÓN ENTRE MICROSERVICIOS CON OPENFEIGN Y EUREKA
-- Estudiante: Israel Angel Huaman Huaman
-- Docente: Ricardo Coello Palomino
-- Institución: Tecsup
-- ====================================================================

-- 1. BASE DE DATOS: bd_categoria_dev
CREATE DATABASE IF NOT EXISTS bd_categoria;
CREATE DATABASE IF NOT EXISTS bd_categoria_dev;
USE bd_categoria_dev;

CREATE TABLE IF NOT EXISTS categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    estado VARCHAR(20) NOT NULL
);

INSERT INTO categoria (nombre, estado) VALUES
('Tecnología', 'ACTIVO'),
('Accesorios', 'ACTIVO'),
('Electrodomésticos', 'ACTIVO');

-- 2. BASE DE DATOS: bd_producto_dev
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
('Laptop Lenovo ThinkPad', 2500.00, 10, 1),
('Mouse Ergonómico Inalámbrico', 85.50, 40, 2),
('Teclado Gamer Mecánico', 180.00, 20, 2);

-- 3. BASE DE DATOS: bd_cliente_dev (ACTIVIDAD)
CREATE DATABASE IF NOT EXISTS bd_cliente;
CREATE DATABASE IF NOT EXISTS bd_cliente_dev;
USE bd_cliente_dev;

CREATE TABLE IF NOT EXISTS cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(20),
    nro_dni VARCHAR(8) NOT NULL UNIQUE,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO'
);

INSERT INTO cliente (nombre, apellidos, direccion, telefono, nro_dni, estado) VALUES
('Carlos', 'Ramirez', 'Av. La Marina 2500, San Miguel', '987654321', '12345678', 'ACTIVO'),
('Ana', 'Fernandez Rios', 'Calle Los Sauces 432, Surco', '912345678', '87654321', 'ACTIVO');

-- 4. BASE DE DATOS: bd_pedido_dev (ACTIVIDAD)
CREATE DATABASE IF NOT EXISTS bd_pedido;
CREATE DATABASE IF NOT EXISTS bd_pedido_dev;
USE bd_pedido_dev;

CREATE TABLE IF NOT EXISTS pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    fecha DATE NOT NULL,
    total DOUBLE NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE'
);

INSERT INTO pedido (cliente_id, producto_id, cantidad, fecha, total, estado) VALUES
(1, 1, 2, '2026-06-12', 5000.00, 'PENDIENTE');
