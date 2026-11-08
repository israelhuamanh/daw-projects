# 🚪 Service Discovery (Eureka) y API Gateway (Spring Cloud Gateway) - Semana 13

**Estudiante:** Israel Angel Huaman Huaman  
**Institución:** Tecsup  
**Docente:** Ricardo Coello Palomino  
**Curso:** Desarrollo de Aplicaciones Web Avanzado (DAWA)  
**Repositorio Oficial:** [https://github.com/israelhuamanh/daw-projects](https://github.com/israelhuamanh/daw-projects)  
**Documento Oficial Word:** [`GLAB-S13-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx`](./GLAB-S13-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx)

---

## 📋 Descripción del Laboratorio

En este laboratorio se implementa un **API Gateway centralizado** con **Spring Cloud Gateway**, **Spring Cloud LoadBalancer** y **Netflix Eureka Server** como Service Registry:
- **Punto Único de Entrada (Reverse Proxy):** Todos los clientes externos (navegador, aplicaciones móviles, Postman) consumen las APIs únicamente a través del puerto `8080` de `gateway-service`.
- **Ruteo Dinámico y Balanceo del Lado del Cliente (`lb://`):** El Gateway resuelve dinámicamente las instancias disponibles registradas en Eureka Server (`http://localhost:8761`) y balancea el tráfico entre ellas.
- **Aislamiento Perimetral:** Los puertos internos de los microservicios backend (`8081`, `8082`, `8083`, `8084`) permanecen protegidos y desacoplados de la exposición pública directa.

El proyecto está estructurado dentro de la carpeta `microservicios/` conteniendo:
1. **`gateway-service`** (Puerto `8080`): API Gateway con ruteo hacia los 4 microservicios backend.
2. **`eureka-server`** (Puerto `8761`): Servidor de Registro y Descubrimiento de Servicios.
3. **`categoria-service`** (Puerto `8082`): Catálogo de categorías (Procedimiento).
4. **`producto-service`** (Puerto `8083`): Catálogo de productos con integración Feign a categoría (Procedimiento).
5. **`cliente-service`** (Puerto `8081`): Gestión de clientes con validación de DNI de 8 dígitos (Actividad).
6. **`pedido-service`** (Puerto `8084`): Gestión de pedidos con integración OpenFeign a cliente y producto, y anulación lógica (Actividad).

---

## 🏛️ Diagrama de Arquitectura de API Gateway y Service Discovery

```mermaid
flowchart TD
    subgraph Clientes["🌐 Clientes Externos (Norte - Sur)"]
        CLIENT["Frontend Web / App Móvil / Postman"]
    end

    subgraph Perimetro["🚪 Perímetro y Enrutamiento (Puerto 8080)"]
        GATEWAY["gateway-service\n(Spring Cloud Gateway + LoadBalancer)\nhttp://localhost:8080"]
    end

    subgraph ServiceRegistry["☁️ Service Discovery (Puerto 8761)"]
        EUREKA["eureka-server\n(Netflix Eureka Server)\nhttp://localhost:8761"]
    end

    subgraph RedInterna["⚙️ Microservicios de Dominio (Red Privada Backend)"]
        CAT["categoria-service\n(Puerto 8082)\nPath: /api/categorias/**"]
        PROD["producto-service\n(Puerto 8083)\nPath: /api/productos/**"]
        CLI["cliente-service\n(Puerto 8081)\nPath: /api/clientes/**"]
        PED["pedido-service\n(Puerto 8084)\nPath: /api/pedidos/**"]
    end

    subgraph Persistencia["🗄️ Databases per Service (MySQL)"]
        DBCAT[("bd_categoria_dev")]
        DBPROD[("bd_producto_dev")]
        DBCLI[("bd_cliente_dev")]
        DBPED[("bd_pedido_dev")]
    end

    %% Flujo de clientes a Gateway
    CLIENT ==>|Todas las peticiones a http://localhost:8080| GATEWAY

    %% Registro y consulta a Eureka
    GATEWAY -.->|Consulta instancias activas| EUREKA
    CAT -.->|Heartbeat / Registro| EUREKA
    PROD -.->|Heartbeat / Registro| EUREKA
    CLI -.->|Heartbeat / Registro| EUREKA
    PED -.->|Heartbeat / Registro| EUREKA

    %% Ruteo inverso balanceado desde Gateway
    GATEWAY -->|lb://categoria-service| CAT
    GATEWAY -->|lb://producto-service| PROD
    GATEWAY -->|lb://cliente-service (Actividad)| CLI
    GATEWAY -->|lb://pedido-service (Actividad)| PED

    %% Comunicación interna Este - Oeste (Feign)
    PROD -.->|OpenFeign CategoriaClient| CAT
    PED -.->|OpenFeign ClienteClient| CLI
    PED -.->|OpenFeign ProductoClient| PROD

    %% Persistencia
    CAT --> DBCAT
    PROD --> DBPROD
    CLI --> DBCLI
    PED --> DBPED
```

---

## ⚙️ Matriz de Ruteo del API Gateway (`application.yaml`)

| Ruta ID | Predicado de Ruta (`Path`) | URI de Destino en Gateway | Microservicio Destino | Puerto Backend |
|---|---|---|---|:---:|
| **`categoria-service`** | `/api/categorias/**` | `lb://categoria-service` | Categoria Service | `8082` |
| **`producto-service`** | `/api/productos/**` | `lb://producto-service` | Producto Service | `8083` |
| **`cliente-service`** | `/api/clientes/**` | `lb://cliente-service` | Cliente Service (Actividad) | `8081` |
| **`pedido-service`** | `/api/pedidos/**` | `lb://pedido-service` | Pedido Service (Actividad) | `8084` |

---

## 🚀 Endpoints Disponibles a través del Gateway (`http://localhost:8080`)

### 1. Actuator & Diagnóstico del Gateway
- `GET http://localhost:8080/actuator/gateway/routes`: Listado JSON de rutas dinámicas registradas en el Gateway.
- `GET http://localhost:8080/actuator/health`: Estado de salud general del Gateway.

### 2. Categoria Service vía Gateway
- `GET http://localhost:8080/api/categorias`: Listar todas las categorías.
- `POST http://localhost:8080/api/categorias`: Registrar nueva categoría.
- `GET http://localhost:8080/api/categorias/{id}`: Obtener categoría por ID.

### 3. Producto Service vía Gateway
- `GET http://localhost:8080/api/productos`: Listar productos con categoría completada vía OpenFeign.
- `POST http://localhost:8080/api/productos`: Crear producto con validación.
- `GET http://localhost:8080/api/productos/{id}`: Obtener producto por ID.

### 4. Cliente Service vía Gateway (Actividad)
- `GET http://localhost:8080/api/clientes`: Listar clientes registrados.
- `POST http://localhost:8080/api/clientes`: Registrar cliente con validación de DNI de 8 dígitos.
- `GET http://localhost:8080/api/clientes/{id}`: Consultar cliente por ID.

### 5. Pedido Service vía Gateway (Actividad)
- `POST http://localhost:8080/api/pedidos`: Registrar pedido (valida cliente y producto vía Feign, calcula total y asigna estado `PENDIENTE`).
- `GET http://localhost:8080/api/pedidos/{id}`: Consultar pedido por ID con enriquecimiento automático de cliente y producto.
- `GET http://localhost:8080/api/pedidos`: Listar todos los pedidos.
- `PUT http://localhost:8080/api/pedidos/{id}/estado?nuevoEstado=ATENDIDO`: Actualizar estado de pedido.
- `PUT http://localhost:8080/api/pedidos/{id}/anular`: Anulación lógica del pedido según requerimiento.

---

## 🗃️ Base de Datos y Scripts DDL

El script DDL consolidado para la creación de las bases de datos y registros iniciales se encuentra en:
[`database-scripts.sql`](./database-scripts.sql)

Para ejecutarlo en MySQL:
```bash
mysql -u root -p < database-scripts.sql
```

---

## 📮 Colección de Postman

La colección de pruebas con todas las peticiones apuntando al API Gateway en el puerto `8080` se encuentra en:
[`postman_collection.json`](./postman_collection.json)

---

## 📸 Evidencias Gráficas

Las 15 capturas de pantalla de la ejecución y el código se encuentran en [`docs/screenshots/`](./docs/screenshots/):
- `01_eureka_server_dashboard.png`: Panel de Eureka Server con las 5 instancias registradas (`UP`).
- `02_gateway_actuator_routes.png`: Endpoint Actuator mostrando las 4 rutas registradas con `lb://`.
- `03_gateway_categoria_post.png`: POST `/api/categorias` vía Gateway (201 Created).
- `04_gateway_categoria_get_all.png`: GET `/api/categorias` vía Gateway (200 OK).
- `05_gateway_producto_post.png`: POST `/api/productos` vía Gateway (201 Created).
- `06_gateway_producto_get_feign.png`: GET `/api/productos` vía Gateway con categoría resuelta por Feign (200 OK).
- `07_gateway_cliente_post.png`: POST `/api/clientes` vía Gateway (201 Created - Actividad).
- `08_gateway_cliente_get_id.png`: GET `/api/clientes/1` vía Gateway (200 OK - Actividad).
- `09_gateway_pedido_post.png`: POST `/api/pedidos` vía Gateway (201 Created - Actividad).
- `10_gateway_pedido_get_feign_id.png`: GET `/api/pedidos/1` vía Gateway con agregación Feign de Cliente y Producto (200 OK - Actividad).
- `11_gateway_pedido_estado_atendido.png`: PUT `/api/pedidos/1/estado?nuevoEstado=ATENDIDO` vía Gateway (200 OK - Actividad).
- `12_gateway_pedido_anular.png`: PUT `/api/pedidos/1/anular` vía Gateway (200 OK - Actividad).
- `13_code_gateway_application_yaml.png`: Código del archivo `application.yaml` de `gateway-service`.
- `14_code_gateway_main_class.png`: Código de `GatewayServiceApplication.java` con `@EnableDiscoveryClient`.
- `15_code_gateway_routes_architecture.png`: Arquitectura de ruteo dinámico y balanceo en Spring Cloud Gateway.

---

## 📝 Documento Oficial Word

El informe formal completado con la carátula, datos del estudiante, respuestas, capturas, 5 observaciones técnicas y 5 conclusiones profesionales se encuentra en:
- [`GLAB-S13-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx`](./GLAB-S13-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx)
- [`docs/GLAB-S13-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx`](./docs/GLAB-S13-ISRAEL-ANGEL-HUAMAN-HUAMAN.docx)
