# demo.spring.boot

API REST para gestión de productos e inventario, con autenticación y autorización basada en roles

## Stack
 - Lenguaje: Java
 - Framework: Spring Boot
 - Seguridad: JWT
 - Testing: Mockito y JUnit
 - Infraestructura: Docker

## Features
 - CRUD de productos y categorías
 - Autenticación con JWT
 - Roles ADMIN/USER
 - Validaciones de datos
 - Manejo de errores
 - Documentación con Swagger
 - Tests unitarios
 - Containerizado con Docker
 - Deployeado en Render con Postgres

## Decisiones técnicas destacadas
 - DTOs para no exponer entidades
 - Idempotencia en el seeder para producción
 - Separacion de perfiles de configuracion (dev/prod) para poder disponer de H2 a la hora de querer desarrollar y de Postgres en producción

## Como correrlo localmente
 - Clonacion: git clone https://github.com/TomiiMN/demo.spring.boot.git
 - Variables de entorno necesarias:
  * JWT_SECRET=<secreto-generado>
  * JWT_EXPIRATION=3600000
 - Comandos para levantar la app con:
  * Docker:
      docker build -t demo-spring-boot .
      docker run -p 8080:8080 --env-file .env demo-spring-boot
  * Maven: ./mvnw spring-boot:run

## Deploy
API disponible en: https://product-api-p3ji.onrender.com

Swagger UI: https://product-api-p3ji.onrender.com/swagger-ui.html

Credenciales de prueba:
- Admin: admin@demo.com / admin1234
- User: user@demo.com / user1234

Nota: el servicio está en el free tier de Render, puede tardar ~1 min en responder tras un período de inactividad.

## Endpoints principales

| Método | Endpoint                                     | Descripción                                    | Auth requerida |
|--------|----------------------------------------------|------------------------------------------------|----------------|
| POST   | /auth/register                               | Registro de usuario                            | No             |
| POST   | /auth/login                                  | Login, devuelve JWT                            | No             |
| GET    | /products                                    | Listar productos                               | Sí             |
| GET    | /products/{id}                               | Listar un producto                             | Sí             |
| GET    | /products/price/{price}                      | Listar productos menores al precio indicado    | Sí             |
| GET    | /products/category/{categoryName}            | Listar productos por nombre de categoría       | Sí             |
| GET    | /categories                                  | Listar categorías                              | Sí             |
| GET    | /categories/{id}                             | Listar una categoría                           | Sí             |
| GET    | /categories/{id}/products                    | Listar los productos de una categoría          | Sí             |
| POST   | /products                                    | Crear producto                                 | Sí (ADMIN)     |
| POST   | /categories                                  | Crear una categoría                            | Sí (ADMIN)     |
| PUT    | /products/{id}                               | Actualizar producto                            | Sí (ADMIN)     |
| PUT    | /products/{productId}/category/{categoryId}  | Asignar o cambiar la categoria de un producto  | Sí (ADMIN)     |
| PUT    | /categories/{id}                             | Actualizar una categoría existente             | Sí (ADMIN)     |
| DELETE | /products/{id}                               | Eliminar producto                              | Sí (ADMIN)     |
| DELETE | /categories/{id}                             | Eliminar categoría                             | Sí (ADMIN)     |
