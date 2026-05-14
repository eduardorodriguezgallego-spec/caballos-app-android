# 📱 Caballos para disfrutar - Aplicación Android Kotlin

Aplicación móvil Android desarrollada en Kotlin para gestionar las reservas online del centro ecuestre **Caballos para disfrutar**.

La aplicación consume una API REST desarrollada en Laravel y permite a los usuarios realizar todas las operaciones relacionadas con las reservas de paseos de hípica.

---

# 🌍 Producción

* API pública HTTPS
* Dominio: https://ecodubi.com
* VPS Ubuntu
* Laravel + MariaDB
* APK descargable

APK disponible en:

https://ecodubi.com/downloads/caballos-app.apk

---

# 📌 Objetivo del proyecto

El objetivo de la aplicación es permitir a los alumnos del centro ecuestre:

* Registrarse
* Iniciar sesión
* Consultar caballos
* Crear reservas
* Modificar reservas
* Eliminar reservas
* Realizar pagos
* Consultar reservas futuras
* Cerrar sesión de forma segura

Todo ello desde un dispositivo móvil Android.

---

# ✨ Características principales

* Login y registro
* Gestión de reservas
* Gestión de pagos
* Consumo API REST Laravel
* Validaciones en tiempo real
* Persistencia de sesión
* Logout seguro
* Integración HTTPS
* Arquitectura MVVM

---

# 🛠️ Tecnologías utilizadas

## Android

* Kotlin
* Android Studio
* MVVM
* LiveData
* RecyclerView
* Coroutines

## Comunicación API

* Retrofit
* JSON
* Bearer Token

## Backend conectado

* Laravel API REST
* Laravel Sanctum
* HTTPS

## Infraestructura

* VPS Ubuntu
* Nginx
* MariaDB

---

# 🧱 Arquitectura MVVM

La aplicación se ha desarrollado utilizando el patrón arquitectónico MVVM:

```txt id="mqh9tp"
UI (Activities)
↓
ViewModel
↓
Repository / Retrofit
↓
API REST Laravel
```

Esto permite:

* Separación de responsabilidades
* Código más mantenible
* Mejor organización
* Escalabilidad
* Gestión correcta del ciclo de vida

---

# 📂 Estructura del proyecto

```txt id="40hfnh"
app/
├── models/
│   ├── Reserva.kt
│   ├── Caballo.kt
│   ├── Pago.kt
│   ├── LoginRequest.kt
│   ├── LoginResponse.kt
│   └── CrearReservaRequest.kt
│
├── network/
│   ├── ApiService.kt
│   └── RetrofitClient.kt
│
├── viewmodel/
│   └── ReservasViewModel.kt
│
├── utils/
│   └── FechaUtils.kt
│
├── MainActivity.kt
├── LoginActivity.kt
├── RegistroActivity.kt
├── ReservasActivity.kt
├── CrearReservaActivity.kt
├── EditarReservaActivity.kt
└── PagarReservaActivity.kt
```

---

# 🔐 Sistema de autenticación

La autenticación se realiza mediante tokens Bearer generados por Laravel Sanctum.

El usuario:

* Introduce email y contraseña
* El backend devuelve un token
* El token se almacena en la app
* Retrofit lo envía en cada petición protegida

Ejemplo:

```txt id="ekm6vw"
Authorization: Bearer TOKEN
```

---

# 🌐 Comunicación con la API REST

La aplicación utiliza Retrofit para consumir el backend Laravel.

Ejemplo de endpoint:

```kotlin id="e3krqr"
@POST("reservas")
suspend fun crearReserva(
    @Header("Authorization") token: String,
    @Body body: CrearReservaRequest
): Response<ReservaResponse>
```

---

# 📱 Aplicación desplegada

La aplicación Android consume una API REST pública desplegada en:

https://ecodubi.com/api/

La APK puede descargarse desde:

https://ecodubi.com/downloads/caballos-app.apk

---

# 📅 Gestión de reservas

La aplicación permite:

* Ver reservas futuras
* Crear reservas
* Modificar reservas
* Eliminar reservas
* Pagar reservas

Cada reserva muestra:

* Fecha
* Hora
* Caballo
* Estado
* Estado del pago

---

# 🐴 Gestión de caballos

Los caballos se obtienen desde la API REST.

Cada caballo contiene:

* Nombre
* Raza
* Foto
* Estado de salud
* Observaciones

---

# 💳 Sistema de pagos

La app permite registrar pagos asociados a reservas.

Se envían:

* reserva_id
* plataforma
* cantidad
* comision
* referencia_pago

La plataforma utilizada en la simulación es Stripe.

---

# 📧 Confirmaciones

Cuando se crea una reserva:

* Laravel envía email automático
* Laravel envía WhatsApp mediante CallMeBot

La aplicación Android recibe la confirmación desde la API.

---

# 🎨 Interfaz de usuario

La interfaz se ha diseñado programáticamente en Kotlin utilizando:

* LinearLayout
* TextView
* EditText
* Button
* RecyclerView

Características:

* Diseño limpio
* Colores cálidos
* Formularios sencillos
* Navegación intuitiva
* Botones de volver
* Estados visuales

---

# 📆 Formato de fechas

La aplicación utiliza:

## Pantalla

```txt id="s1g7kk"
dd-MM-yyyy
```

Ejemplo:

```txt id="n5fj9y"
20-05-2026
```

## API

```txt id="hxtjhg"
yyyy-MM-dd
```

Ejemplo:

```txt id="8o1x9x"
2026-05-20
```

La conversión se realiza mediante:

```txt id="ydcg1v"
FechaUtils.kt
```

---

# 🔄 RecyclerView

Las reservas se muestran mediante RecyclerView.

El adaptador:

```txt id="0jshmx"
ReservasAdapter.kt
```

permite:

* Mostrar reservas
* Editar
* Eliminar
* Pagar
* Mostrar estados visuales

---

# 📌 Estados visuales

## Reserva

* 🟢 Confirmada
* 🟡 Pendiente
* 🔴 Cancelada

## Pago

* 🟢 Pagado
* 🟡 Pendiente

---

# 📡 Endpoints consumidos

## Autenticación

| Método | Endpoint      |
| ------ | ------------- |
| POST   | /api/login    |
| POST   | /api/registro |

## Reservas

| Método | Endpoint           |
| ------ | ------------------ |
| GET    | /api/reservas      |
| POST   | /api/reservas      |
| PUT    | /api/reservas/{id} |
| DELETE | /api/reservas/{id} |

## Caballos

| Método | Endpoint      |
| ------ | ------------- |
| GET    | /api/caballos |

## Pagos

| Método | Endpoint   |
| ------ | ---------- |
| POST   | /api/pagos |

---

# ⚙️ Configuración del proyecto

## 1. Clonar repositorio

```bash id="x4z66e"
git clone URL_REPOSITORIO_ANDROID
```

## 2. Abrir en Android Studio

```txt id="5gwxma"
File → Open
```

Seleccionar la carpeta del proyecto.

---

# 🌐 Configuración API

Archivo:

```txt id="w7bq6m"
RetrofitClient.kt
```

Configuración utilizada:

```kotlin id="4p7mw7"
private const val BASE_URL =
    "https://ecodubi.com/api/"
```

---

# ▶️ Ejecutar aplicación

Desde Android Studio:

```txt id="3htqj0"
Run → Run app
```

---

# 🔒 Seguridad

La aplicación utiliza:

* HTTPS
* Laravel Sanctum
* Tokens Bearer
* Persistencia de sesión
* Logout seguro
* Validación de formularios
* Control de acceso
* Manejo de errores

---

# ❌ Manejo de errores

La aplicación muestra mensajes claros para:

* Fecha inválida
* Hora incorrecta
* Caballo enfermo
* Turno completo
* Error de conexión
* Error de autenticación

Ejemplo:

```txt id="rbfrv3"
Solo se puede reservar sábados y domingos
```

---

# 📱 Compatibilidad

Compatible con:

* Android Emulator
* Dispositivos Android físicos
* Android Studio

---

# ✅ Requisitos cumplidos

* Login y registro
* Consumo API REST Laravel
* MVVM
* Retrofit
* CRUD reservas
* Gestión de pagos
* Gestión de caballos
* RecyclerView
* LiveData
* Validaciones
* Estados visuales
* Formato correcto de fechas
* Integración backend Laravel
* Logout seguro
* Persistencia de sesión
* HTTPS
* VPS en producción

---

# 🚀 Mejoras futuras

* Jetpack Compose
* Room Database
* Notificaciones push
* Calendario visual
* Integración Stripe real
* Dark Mode
* Material Design 3
* Subida de imágenes
* Recuperación de contraseña
* Tests automáticos

---

# 👨‍💻 Autor

Eduardo Rodríguez Gallego

Proyecto académico Android + Laravel para la gestión de reservas online de un centro ecuestre.
