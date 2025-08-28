# 📄 Invoices API – Spring Boot

API REST para gestión de *Invoices* con soporte de subida de archivos y acceso público a los mismos.

---

## 🚀 Tecnologías
- Java 17
- Spring Boot 3.5.5
- PostgreSQL
- Lombok
- Docker & Docker Compose

---

## 🗺 Diagrama del flujo de la API

```mermaid
flowchart TD
    A[POST /invoice] --> B[Controller: recibe Multipart + DTO]
    B --> C[Service: guarda archivo en ./invoices]
    C --> D[Genera fileUrl]
    D --> E[Mapea DTO a entidad Invoice]
    E --> F[Persiste en base de datos]
    F --> G[Devuelve InvoiceResponseDTO con fileUrl]

    H[GET /invoice] --> I[Controller: llama a Service]
    I --> J[Obtiene lista de entidades]
    J --> K[Mapea a InvoiceResponseDTO]
    K --> L[Devuelve JSON]

    M[GET /invoices/#123;fileName#125;] --> N[ResourceHandler]
    N --> O[Devuelve archivo físico desde ./invoices]

```
## 🧪 Ejecutar la API y los tests

Este proyecto usa `docker-compose` con contenedores separados para:

- `springboot-app`: la aplicación Spring Boot compilada.
- `postgres-db`: base de datos PostgreSQL.

### ✅ Requisitos
- Docker

---

### 🪟 Windows (por defecto)

Docker Desktop ya configura automáticamente el socket de Docker en Windows.
Simplemente ejecuta:

```powershell
docker compose up --build
```

### 🐧 Linux / 🍎 MacOS

```powershell
DOCKER_SOCKET=/var/run/docker.sock docker compose up --build
```