create database desperfectos;

use desperfectos;

-- AULAS
INSERT INTO aula (codigo, descripcion) VALUES
('A-101', 'Aula teoría planta 1'),
('A-102', 'Aula teoría planta 1'),
('LAB-1', 'Laboratorio informática 1'),
('LAB-2', 'Laboratorio informática 2');

-- ESTADOS
INSERT INTO estados (nombre, orden) VALUES
('PENDIENTE', 1),
('EN_CURSO', 2),
('RESUELTA', 3),
('CERRADA', 4);

-- USUARIOS
INSERT INTO usuarios (username, nombre, email, activo) VALUES
('admin', 'Administrador', 'admin@centro.com', true),
('user', 'Usuario Básico', 'user@centro.com', true);
