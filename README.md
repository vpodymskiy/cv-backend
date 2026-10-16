# cv-backend

API REST para el formulario de contacto de mi web personal: recibe el mensaje del visitante y lo envía por correo electrónico.

## Tecnologías

Java 24 · Spring Boot 3.5 · Spring Web · Spring Mail · Bean Validation · Lombok · Gradle · Docker

## Endpoint

`POST /contact`

```json
{
  "name": "Ana García",
  "email": "ana@example.com",
  "phone": "+34 600 000 000",
  "message": "Hola, me gustaría hablar contigo."
}
```

- `200 OK`: el correo se ha enviado.
- `500 Internal Server Error`: error al enviar el correo.

## Estructura

```
src/main/java/com/vlad/contact/contact_backend
├── ContactBackendApplication.java
├── controller/ContactController.java   # recibe la petición POST /contact
└── service/EmailService.java           # envía el correo con Spring Mail
```

## Cómo ejecutar

Antes de arrancar, rellena `spring.mail.username` y `spring.mail.password` en `src/main/resources/application.properties` (por defecto se usa SMTP de Gmail, `smtp.gmail.com:587`, con una contraseña de aplicación).

```bash
./gradlew bootRun
```

Con Docker:

```bash
docker build -t cv-backend .
docker run -p 8080:8080 cv-backend
```
