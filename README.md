# 📄 Invoices API – Spring Boot

API REST para gestión de *Invoices* con soporte de subida de archivos y acceso público a los mismos.

---

## 🚀 Tecnologías
- Java 17
- Spring Boot 3.x (Web MVC, Data JPA)
- PostgreSQL
- Lombok
- Swagger / OpenAPI
- Docker & Docker Compose

---

## 📂 Estructura básica del proyecto

- **src/**
    - **main/**
        - **java/**
            - **com/**
                - **example/**
                    - **invoices/**
                        - **controller/** → Controladores REST
                        - **service/** → Lógica de negocio
                        - **dto/** → DTOs de request/response
                        - **mapper/** → Conversión DTO ↔ Entidad
                        - **entity/** → Entidades JPA
                        - **config/** → Configuración (ej. recursos estáticos)
        - **resources/**
            - `application.properties` → Configuración por defecto
    - **test/**
        - **java/** → Tests unitarios

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
