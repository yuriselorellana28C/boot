# springboot-api-demo-progra3

Proyecto desarrollado en **Spring Boot 3** con **Java 17** como parte del taller de Programación III.

## Descripción

Este proyecto consiste en una API REST que permite:

- verificar el estado general de la aplicación
- generar saludos por medio de peticiones GET y POST
- validar datos de entrada
- manejar errores de validación y reglas de negocio
- documentar la API con Swagger / OpenAPI
- simular un préstamo mediante un endpoint final

## Tecnologías utilizadas

- Java 17
- Spring Boot 3
- Maven
- Spring Web
- Spring Validation
- Springdoc OpenAPI / Swagger
- JUnit / MockMvc

## Estructura general del proyecto

```text
src/main/java/com/ejemplo/demo
 ├── api
 │   ├── controller
 │   │   ├── SaludoController.java
 │   │   └── PrestamoController.java
 │   ├── dto
 │   │   ├── SaludoRequest.java
 │   │   ├── SaludoResponse.java
 │   │   ├── ErrorResponse.java
 │   │   ├── PrestamoRequest.java
 │   │   └── PrestamoResponse.java
 │   └── exception
 │       └── GlobalExceptionHandler.java
 ├── domain
 │   └── service
 │       ├── SaludoService.java
 │       └── PrestamoService.java
 └── SpringbootApiDemoApplication.java