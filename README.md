# ⚽️ Sports Pulse
Backend con arquitectura de microservicios enfocada en fútbol.

# ⚙️ Tecnologías utilizadas

## Stack principal
- Spring Boot 3.5.13
- Java 17
- Spring Cloud 2025
- Spring Data JPA
- Spring Security
- Spring Cache

## Testing
- JUnit
- Mockito
- Wiremock (Algunos microservicios)
- Postman (testing automatizado, documentado más abajo)

# 🚀 Primeros Pasos
Esta guía te mostrará cómo configurar y ejecutar la aplicación usando Docker Compose, y cómo ejecutar las pruebas automatizadas.

# Prerrequisitos
Asegúrate de tener instalados los siguientes componentes en tu sistema:

- Docker
- Docker Compose
- Node.js y npm (para ejecutar las pruebas)


# 🐳 Ejecutando con Docker Compose
Para levantar todos los servicios de la aplicación, debes seguir estos pasos:

## 1. Configurar Variables de Entorno
Primero, copia el archivo de ejemplo de variables de entorno para crear tu archivo local `.env`:

```bash
cp .env.example .env
```

Abre el archivo .env recién creado y completa todas las variables de entorno necesarias con tus credenciales y configuraciones específicas.

## 2. Iniciar Servicios
Navega a la raíz del proyecto y ejecuta:

```bash
docker compose up -d
```
Este comando construirá e iniciará todos los servicios definidos en docker-compose.yml en modo detached (segundo plano).

# 🧪 Ejecutando Pruebas Automatizadas
Para ejecutar el suite completo de pruebas automatizadas, necesitarás tener instalado Newman. Si no lo tienes, instálalo globalmente con npm:

```bash
npm install -g newman
```
Una vez instalado, ejecuta el siguiente comando desde la raíz del proyecto:

```bash
node scripts/run-tests.js ./postman/tests ./postman/dev.json --stop-on-failure
```

# 📖 Documentación API (Swagger)
La documentación principal de la API se encuentra accesible en http://localhost:8080/swagger-ui.html.


>⚠️ AVISO IMPORTANTE: Aunque esta URL muestra una documentación general, no se recomienda ejecutar pruebas de Swagger desde este punto. Para probar la documentación de un microservicio específico, debes acceder directamente al swagger-ui proporcionado por ese servicio en particular.