# 💻 Desarrollo de Aplicaciones Web Avanzado (DAWA) - Proyectos y Laboratorios

**Estudiante:** Israel Angel Huaman Huaman  
**Institución:** Tecsup  
**Docente:** Ricardo Coello Palomino  
**Repositorio Oficial:** [https://github.com/israelhuamanh/daw-projects](https://github.com/israelhuamanh/daw-projects)

---

## 📁 Semanas y Proyectos

| Semana | Descripción / Tema | Enlace al Proyecto | Documentación / Word |
|---|---|---|---|
| **Semana 4** | Implementación de Persistencia con Spring Boot y Hibernate (`@OneToMany`, `@OneToOne`, `@ManyToMany`) | [Ver Proyecto Semana 4](./semana-4/) | [Informe Word](./semana-4/GLAB-S04-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx) \| [README](./semana-4/README.md) |
| **Semana 5** | Validación y Manejo de Errores en APIs con Spring Boot (`@Valid`, DTOs, `@RestControllerAdvice`) | [Ver Proyecto Semana 5](./semana-5/) | [Informe Word](./semana-5/GLAB-S05-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx) \| [README](./semana-5/README.md) |
| **Semana 9** | Implementación de Seguridad JWT y Autenticación en APIs REST con Spring Boot y Spring Security 6 | [Ver Proyecto Semana 9](./semana-9/) | [Informe Word](./semana-9/GLAB-S09-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx) \| [README](./semana-9/README.md) |
| **Semana 10** | Configuración de Perfiles y Gestión de Ambientes en Spring Boot (`dev`, `qa`, `test`, `prod`) | [Ver Proyecto Semana 10](./semana-10/) | [Informe Word](./semana-10/GLAB-S10-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx) \| [README](./semana-10/README.md) |
| **Semana 11** | Arquitectura de Microservicios con Spring Boot y YAML (Tienda y Clínica Salud Integral) | [Ver Proyecto Semana 11](./semana-11/) | [Informe Word](./semana-11/GLAB-S11-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx) \| [README](./semana-11/README.md) |
| **Semana 12** | Comunicación entre Microservicios con OpenFeign y Eureka Server (`categoria`, `producto`, `cliente`, `pedido`) | [Ver Proyecto Semana 12](./semana-12/) | [Informe Word](./semana-12/GLAB-S12-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx) \| [README](./semana-12/README.md) |

---

### 🚀 Resumen de Proyectos

#### 📌 Semana 4: Persistencia y Relaciones JPA
- API REST con Spring Boot 3 y Java 17.
- Modelado relacional completo:
  - **1:N:** Categoría y Producto.
  - **1:1:** Usuario y Perfil con cascada completa (`CascadeType.ALL`).
  - **N:M:** Estudiante y Curso con tabla intermedia `estudiante_curso` y validación de inscripción no duplicada.
- Colección completa de Postman: [`semana-4/postman_collection.json`](./semana-4/postman_collection.json).
- Documento oficial Word (.docx): [`semana-4/GLAB-S04-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx`](./semana-4/GLAB-S04-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx).

#### 📌 Semana 5: Validación y Manejo de Errores
- API REST con Spring Boot 3, Java 17 y Hibernate Validator.
- Patrón DTO (`ProductoDTO`) y validaciones declarativas con Bean Validation (`@NotBlank`, `@Positive`, `@Min`).
- Manejo centralizado de excepciones con `@RestControllerAdvice` y respuestas JSON uniformes (`400 Bad Request`).
- **Actividad implementada:** Campo `categoria` y endpoint de búsqueda flexible por nombre (`GET /api/productos/buscar?nombre=...`).
- Respuestas exhaustivas al cuestionario conceptual (DTO vs Entidad, `@Valid`, `@RestControllerAdvice`, `@Autowired`, diferencias entre `save`, `findById`, `findAll`).
- Colección completa de Postman: [`semana-5/postman_collection.json`](./semana-5/postman_collection.json).
- Documento oficial Word (.docx): [`semana-5/GLAB-S05-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx`](./semana-5/GLAB-S05-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx).

#### 📌 Semana 9: Seguridad JWT y Autenticación por Roles
- API REST con Spring Boot 3, Spring Security 6 y JJWT (`io.jsonwebtoken 0.11.5`).
- Autenticación Stateless con Bearer Token firmado por HMAC-SHA256 y encriptación de claves con BCrypt.
- Filtro `JwtAuthFilter` y configuración de seguridad basada en roles (RBAC) con `SecurityFilterChain`.
- **Resolución de los 6 Casos de Análisis:**
  - Caso 1: Ruta pública (`/api/test/all`).
  - Caso 2: Acceso sin token (`401 Unauthorized`).
  - Caso 3: Token alterado o inválido (`401 Unauthorized`).
  - Caso 4: Acceso exitoso con token válido de usuario (`200 OK`).
  - Caso 5: Acceso a ruta ADMIN con rol USER (`403 Forbidden`).
  - Caso 6: Acceso a ruta ADMIN con rol ADMIN (`200 OK`).
- Colección completa de Postman: [`semana-9/postman_collection.json`](./semana-9/postman_collection.json).
- Documento oficial Word (.docx): [`semana-9/GLAB-S09-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx`](./semana-9/GLAB-S09-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx).

#### 📌 Semana 10: Configuración de Perfiles y Ambientes (TechSolutions SAC)
- API REST con Spring Boot 3, Spring Data JPA y soporte para perfiles desacoplados.
- Arquitectura de 4 ambientes estandarizados:
  - **dev:** Puerto 8080, Base de datos `empleados_dev`.
  - **qa:** Puerto 8082, Base de datos `empleados_qa`.
  - **test:** Puerto 8081, Base de datos `empleados_test`.
  - **prod:** Puerto 8083, Base de datos `empleados_prod`.
- Endpoints de diagnóstico y operación:
  - `GET /configuracion`: JSON con `{ambiente, perfil, puerto, baseDatos}`.
  - `GET /config`: Diagnóstico rápido de variables de entorno y perfiles activos.
  - CRUD completo `/api/empleados` (`POST`, `GET`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`).
- Colección completa de Postman: [`semana-10/postman_collection.json`](./semana-10/postman_collection.json).
- Documento oficial Word (.docx): [`semana-10/GLAB-S10-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx`](./semana-10/GLAB-S10-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx).

#### 📌 Semana 11: Creación de Microservicios con Spring Boot y YAML
- Arquitectura distribuida con patrón **Database per Service** y configuración en **YAML** con perfiles desacoplados (`dev` y `prod`).
- **Módulo Tienda / E-Commerce (4 Microservicios):**
  - `categoria-service` (Puerto 8082 dev / 9092 prod, BD `bd_categoria_dev`).
  - `cliente-service` (Puerto 8081 dev / 9081 prod, BD `bd_cliente_dev`).
  - `producto-service` (Puerto 8083 dev / 9083 prod, BD `bd_producto_dev`).
  - `pedido-service` (Puerto 8084 dev / 9084 prod, BD `bd_pedido_dev`, relación `@OneToMany` en cascada).
- **Módulo Clínica Salud Integral - Actividad (3 Microservicios):**
  - `paciente-service` (Puerto 8085 dev / 9085 prod, BD `bd_paciente_dev`).
  - `medico-service` (Puerto 8086 dev / 9086 prod, BD `bd_medico_dev`, gestión de especialidades, médicos y horarios).
  - `cita-service` (Puerto 8087 dev / 9087 prod, BD `bd_cita_dev`, máquina de estados: `PROGRAMADA`, `CONFIRMADA`, `ATENDIDA`, `CANCELADA`, y registro de atenciones).
- Colección completa de Postman: [`semana-11/postman_collection.json`](./semana-11/postman_collection.json).
- Documento oficial Word (.docx): [`semana-11/GLAB-S11-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx`](./semana-11/GLAB-S11-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx).

#### 📌 Semana 12: Comunicación entre Microservicios con OpenFeign y Eureka Server
- Ecosistema distribuido Spring Cloud desplegado sobre la base **`microservicios`**:
  - `eureka-server` (Puerto 8761): Service Registry y Dashboard visual.
  - `categoria-service` (Puerto 8082): Catálogo de categorías con registro en Eureka.
  - `producto-service` (Puerto 8083): Cliente Feign `CategoriaClient` para enriquecer productos con información de categoría en tiempo de ejecución.
  - `cliente-service` (Puerto 8081 - Actividad): Gestión de clientes con validación estricta de DNI de 8 dígitos y registro en Eureka.
  - `pedido-service` (Puerto 8084 - Actividad): Integración con `ClienteClient` y `ProductoClient` vía OpenFeign, sin duplicación de datos en base de datos (`bd_pedido_dev`), agregación de payloads y anulación lógica de pedidos.
- Colección completa de Postman: [`semana-12/postman_collection.json`](./semana-12/postman_collection.json).
- Documento oficial Word (.docx): [`semana-12/GLAB-S12-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx`](./semana-12/GLAB-S12-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx).
