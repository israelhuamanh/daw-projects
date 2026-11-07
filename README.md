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
