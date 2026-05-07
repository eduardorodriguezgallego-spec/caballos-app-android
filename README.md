# 📱 Caballos para disfrutar - Aplicación Android Kotlin

Aplicación móvil Android desarrollada en Kotlin para gestionar las reservas online del centro ecuestre **Caballos para disfrutar**.

La aplicación consume una API REST desarrollada en Laravel y permite a los usuarios realizar todas las operaciones relacionadas con las reservas de paseos de hípica.

---

# 📌 Objetivo del proyecto

El objetivo de la aplicación es permitir a los alumnos del centro ecuestre:

- Registrarse
- Iniciar sesión
- Consultar caballos
- Crear reservas
- Modificar reservas
- Eliminar reservas
- Realizar pagos
- Consultar reservas futuras

Todo ello desde un dispositivo móvil Android.

---

# 🛠️ Tecnologías utilizadas

- Kotlin
- Android Studio
- MVVM
- Retrofit
- LiveData
- RecyclerView
- JSON
- API REST
- Laravel Backend
- ViewModel
- Coroutines

---

# 🧱 Arquitectura MVVM

La aplicación se ha desarrollado utilizando el patrón arquitectónico MVVM:

```txt
UI (Activities)
↓
ViewModel
↓
Repository / Retrofit
↓
API REST Laravel

Esto permite:

Separación de responsabilidades
Código más mantenible
Mejor organización
Escalabilidad
Gestión correcta del ciclo de vida
📂 Estructura del proyecto
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
🔐 Sistema de autenticación

La autenticación se realiza mediante tokens Bearer generados por Laravel Sanctum.

El usuario:

Introduce email y contraseña
El backend devuelve un token
El token se almacena en la app
Retrofit lo envía en cada petición protegida

Ejemplo:

Authorization: Bearer TOKEN
🌐 Comunicación con la API REST

La aplicación utiliza Retrofit para consumir el backend Laravel.

Ejemplo de endpoint:

@POST("reservas")
suspend fun crearReserva(
    @Header("Authorization") token: String,
    @Body body: CrearReservaRequest
): Response<ReservaResponse>
📅 Gestión de reservas

La aplicación permite:

Ver reservas futuras
Crear reservas
Modificar reservas
Eliminar reservas
Pagar reservas

Cada reserva muestra:

Fecha
Hora
Caballo
Estado
Estado del pago
🐴 Gestión de caballos

Los caballos se obtienen desde la API REST.

Cada caballo contiene:

Nombre
Raza
Foto
Estado de salud
Observaciones
💳 Sistema de pagos

La app permite registrar pagos asociados a reservas.

Se envían:

reserva_id
plataforma
cantidad
comision
referencia_pago

La plataforma utilizada en la simulación es Stripe.

📧 Confirmaciones

Cuando se crea una reserva:

Laravel envía email automático
Laravel envía WhatsApp mediante CallMeBot

La aplicación Android recibe la confirmación desde la API.

🎨 Interfaz de usuario

La interfaz se ha diseñado programáticamente en Kotlin utilizando:

LinearLayout
TextView
EditText
Button
RecyclerView

Características:

Diseño limpio
Colores cálidos
Formularios sencillos
Navegación intuitiva
Botones de volver
Estados visuales
📆 Formato de fechas

La aplicación utiliza:

Pantalla:
dd-MM-yyyy

Ejemplo:

20-05-2026

La API utiliza:

yyyy-MM-dd

Ejemplo:

2026-05-20

La conversión se realiza mediante:

FechaUtils.kt
🔄 RecyclerView

Las reservas se muestran mediante RecyclerView.

El adaptador:

ReservasAdapter.kt

permite:

Mostrar reservas
Editar
Eliminar
Pagar
Mostrar estados visuales
📌 Estados visuales

Estados implementados:

Reserva
🟢 Confirmada
🟡 Pendiente
🔴 Cancelada
Pago
🟢 Pagado
🟡 Pendiente
📡 Endpoints consumidos
Autenticación
POST /api/login
POST /api/registro
Reservas
GET /api/reservas
POST /api/reservas
PUT /api/reservas/{id}
DELETE /api/reservas/{id}
Caballos
GET /api/caballos
Pagos
POST /api/pagos
⚙️ Configuración del proyecto
1. Clonar repositorio
git clone URL_REPOSITORIO_ANDROID
2. Abrir en Android Studio
File → Open

Seleccionar la carpeta del proyecto.

3. Configurar URL API

Archivo:

RetrofitClient.kt

Ejemplo:

private const val BASE_URL =
    "http://10.0.2.2:8000/api/"
Emulador Android

Usar:

10.0.2.2
Dispositivo físico

Usar IP local del ordenador:

http://192.168.X.X:8000/api/
▶️ Ejecutar aplicación

Desde Android Studio:

Run → Run app
🔒 Seguridad

La aplicación utiliza:

Tokens Bearer
Validación de formularios
Manejo de errores
Protección de rutas
Control de sesiones
❌ Manejo de errores

La aplicación muestra mensajes claros para:

Fecha inválida
Hora incorrecta
Caballo enfermo
Turno completo
Error de conexión
Error de autenticación

Ejemplo:

Solo se puede reservar sábados y domingos
📱 Compatibilidad

Compatible con:

Android Emulator
Dispositivos Android físicos
✅ Requisitos cumplidos
Login y registro
Consumo API REST Laravel
MVVM
Retrofit
CRUD reservas
Gestión de pagos
Gestión de caballos
RecyclerView
LiveData
Validaciones
Estados visuales
Formato correcto de fechas
Integración backend Laravel
🚀 Mejoras futuras
Jetpack Compose
Room Database
Notificaciones push
Calendario visual
Integración Stripe real
Dark Mode
Material Design 3
Subida de imágenes
Recuperación de contraseña
Tests automáticos
👨‍💻 Autor

Eduardo Rodríguez Gallego

Proyecto académico Android + Laravel para la gestión de reservas online de un centro ecuestre.