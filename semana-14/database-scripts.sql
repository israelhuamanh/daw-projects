-- ====================================================================
-- DAWA - SEMANA 14: CONFIG SERVER Y PATRONES DE RESILIENCIA (RESILIENCE4J)
-- Estudiante: Israel Angel Huaman Huaman
-- Docente: Ricardo Coello Palomino
-- Institución: Tecsup
-- ====================================================================

CREATE DATABASE IF NOT EXISTS bd_categoria_dev;
CREATE DATABASE IF NOT EXISTS bd_categoria_prod;
USE bd_categoria_dev;

CREATE TABLE IF NOT EXISTS categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    estado VARCHAR(20) NOT NULL
);

INSERT INTO categoria (nombre, estado) VALUES
('Tecnología', 'ACTIVO'),
('Accesorios', 'ACTIVO');

CREATE DATABASE IF NOT EXISTS bd_producto_dev;
CREATE DATABASE IF NOT EXISTS bd_producto_prod;
USE bd_producto_dev;

CREATE TABLE IF NOT EXISTS producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL,
    categoria_id BIGINT NOT NULL
);

INSERT INTO producto (nombre, precio, stock, categoria_id) VALUES
('Laptop', 2500.00, 10, 1),
('Mouse Inalámbrico', 89.90, 35, 2);

CREATE DATABASE IF NOT EXISTS bd_cliente_dev;
CREATE DATABASE IF NOT EXISTS bd_cliente_prod;
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
('Carlos', 'Ramirez', 'Av. La Marina 2500, San Miguel', '987654321', '12345678', 'ACTIVO');

CREATE DATABASE IF NOT EXISTS bd_pedido_dev;
CREATE DATABASE IF NOT EXISTS bd_pedido_prod;
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
(1, 1, 2, '2026-11-18', 5000.00, 'PENDIENTE');
