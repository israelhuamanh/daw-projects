-- ====================================================================
-- DAWA - SEMANA 13: SERVICE DISCOVERY (EUREKA) Y API GATEWAY (SPRING CLOUD)
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
('Audio y Video', 'ACTIVO');

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
('Laptop HP Pavilion Gaming', 3200.00, 12, 1),
('Mouse Gamer Óptico RGB', 95.00, 45, 2),
('Headset Inalámbrico Pro', 249.90, 18, 3);

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
('Lucía', 'Fernandez Silva', 'Jr. Dos de Mayo 780, Miraflores', '912345678', '87654321', 'ACTIVO');

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
(1, 1, 2, '2026-06-18', 6400.00, 'PENDIENTE');
