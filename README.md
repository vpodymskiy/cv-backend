# cv-backend

API REST para el formulario de contacto de mi web personal ([cv-ruddy-three.vercel.app](https://cv-ruddy-three.vercel.app)): recibe el mensaje del visitante, lo valida y lo envía por correo.

En producción en **Google Cloud Run**.

## Tecnologías

Java 25 · Spring Boot 4.1 · Spring Web MVC · Spring Mail · Bean Validation · Lombok · JUnit 5 · MockMvc · Gradle · Docker · Google Cloud Run · Secret Manager

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
- `400 Bad Request`: datos no válidos (errores por campo) o JSON mal formado.
- `500 Internal Server Error`: error al enviar el correo.

## Estructura

```
src/main/java/com/vlad/contact/contact_backend
├── controller/ContactController.java      # POST /contact, CORS limitado a la web
├── dto/ContactRequest.java                # datos del formulario con validación
├── dto/ApiResponse.java                   # formato de las respuestas JSON
├── exception/GlobalExceptionHandler.java  # 400 / 500 con mensajes claros
└── service/EmailService.java              # envío por SMTP con timeouts
```

## Configuración

Variables de entorno:

| Variable | Descripción |
|---|---|
| `MAIL_USERNAME` | cuenta de Gmail que envía el correo |
| `MAIL_PASSWORD` | contraseña de aplicación de Gmail (en producción, desde Secret Manager) |
| `PORT` | puerto HTTP (por defecto 8080; Cloud Run lo asigna automáticamente) |

## Ejecutar en local

```bash
export MAIL_USERNAME=tu@gmail.com
export MAIL_PASSWORD=contraseña-de-aplicación
./gradlew bootRun
```

## Despliegue

```bash
gcloud run deploy cv-backend --source .
```

Cloud Run construye la imagen con el `Dockerfile` y escala a cero cuando no hay tráfico.

> Antes estaba en Render; al bloquear su plan gratuito los puertos SMTP, el servicio se migró a Cloud Run.
