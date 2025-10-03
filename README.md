# Sistema ISF Guatemala

Sistema de gestión integral para Ingenieros Sin Fronteras Guatemala, desarrollado para controlar proyectos, empleados, horas trabajadas y uso vehicular.

---

## 📋 Índice

- [Estado Actual del Proyecto](#estado-actual-del-proyecto)
- [Arquitectura Técnica](#arquitectura-técnica)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Configuración de Base de Datos](#configuración-de-base-de-datos)
- [Instalación y Ejecución](#instalación-y-ejecución)
- [Endpoints API](#endpoints-api)
- [Próximos Pasos](#próximos-pasos)
- [Información del Servidor](#información-del-servidor)

---

## 🎯 Estado Actual del Proyecto

### ✅ COMPLETADO

#### Backend Spring Boot (100%)
- ✅ 9 Entidades (Models) con JPA
- ✅ 9 Repositories con queries personalizadas
- ✅ 9 Services con lógica de negocio completa
- ✅ 9 Controllers con endpoints REST
- ✅ DTOs Request/Response para todas las entidades
- ✅ Mappers para conversión Entity ↔ DTO
- ✅ Manejo global de excepciones (@RestControllerAdvice)
- ✅ Validaciones con Bean Validation
- ✅ Configuración CORS global
- ✅ Spring Security básico (modo desarrollo)
- ✅ PostgreSQL 16 configurado y funcionando
- ✅ Datos de prueba insertados

#### Infraestructura
- ✅ Servidor AWS EC2 (Ubuntu 24.04 LTS)
- ✅ Java 17 LTS
- ✅ Maven 3.8+
- ✅ PostgreSQL 16
- ✅ Node.js 18 LTS
- ✅ Nginx instalado
- ✅ UFW Firewall configurado

### ⏳ PENDIENTE

- ❌ Frontend React
- ❌ Configuración Nginx + SSL
- ❌ Configuración dominio app.isf-guatemala.org
- ❌ Integración S3 + Lambda para OCR
- ❌ Sistema de autenticación completo
- ❌ Generación de reportes PDF

---

## 🏗️ Arquitectura Técnica

### Backend Stack
Spring Boot 2.7.18
├── Java 17 LTS (OpenJDK)
├── Maven 3.8+
├── Spring Web
├── Spring Data JPA
├── Spring Security
├── PostgreSQL Driver
├── Lombok
└── Validation API

### Base de Datos
PostgreSQL 16


## 📁 Estructura del Proyecto
isf-guatemala/
├── backend/
│   └── src/main/java/org/isf/guatemala/
│       ├── config/
│       │   ├── SecurityConfig.java          # Configuración Spring Security
│       │   └── CorsConfig.java              # Configuración CORS global
│       ├── controller/                      # 9 Controllers REST
│       │   ├── OficinaController.java
│       │   ├── ProyectoController.java
│       │   ├── EmpleadoController.java
│       │   ├── PuestoController.java
│       │   ├── TareaController.java
│       │   ├── VehiculoController.java
│       │   ├── RegistroHorasController.java
│       │   └── ControlKilometrajeController.java
│       ├── dto/
│       │   ├── request/                     # DTOs para recibir datos
│       │   │   ├── OficinaRequestDTO.java
│       │   │   ├── ProyectoRequestDTO.java
│       │   │   ├── EmpleadoRequestDTO.java
│       │   │   ├── PuestoRequestDTO.java
│       │   │   ├── TareaRequestDTO.java
│       │   │   ├── VehiculoRequestDTO.java
│       │   │   ├── RegistroHorasRequestDTO.java
│       │   │   └── ControlKilometrajeRequestDTO.java
│       │   └── response/                    # DTOs para enviar datos
│       │       ├── OficinaResponseDTO.java
│       │       ├── ProyectoResponseDTO.java
│       │       ├── EmpleadoResponseDTO.java
│       │       ├── PuestoResponseDTO.java
│       │       ├── TareaResponseDTO.java
│       │       ├── VehiculoResponseDTO.java
│       │       ├── RegistroHorasResponseDTO.java
│       │       ├── ControlKilometrajeResponseDTO.java
│       │       └── ErrorResponseDTO.java
│       ├── exception/                       # Manejo de errores
│       │   ├── GlobalExceptionHandler.java
│       │   ├── ResourceNotFoundException.java
│       │   ├── DuplicateResourceException.java
│       │   ├── InvalidDataException.java
│       │   └── BusinessRuleException.java
│       ├── mapper/
│       │   └── EntityMapper.java            # Conversión Entity ↔ DTO
│       ├── model/                           # 9 Entidades JPA
│       │   ├── Oficina.java
│       │   ├── Proyecto.java
│       │   ├── Empleado.java
│       │   ├── Puesto.java
│       │   ├── Tarea.java
│       │   ├── Vehiculo.java
│       │   ├── RegistroHoras.java
│       │   ├── ControlKilometraje.java
│       │   └── Usuario.java
│       ├── repository/                      # 9 Repositories JPA
│       │   ├── OficinaRepository.java
│       │   ├── ProyectoRepository.java
│       │   ├── EmpleadoRepository.java
│       │   ├── PuestoRepository.java
│       │   ├── TareaRepository.java
│       │   ├── VehiculoRepository.java
│       │   ├── RegistroHorasRepository.java
│       │   ├── ControlKilometrajeRepository.java
│       │   └── UsuarioRepository.java
│       ├── service/                         # 9 Services con lógica
│       │   ├── OficinaService.java
│       │   ├── ProyectoService.java
│       │   ├── EmpleadoService.java
│       │   ├── PuestoService.java
│       │   ├── TareaService.java
│       │   ├── VehiculoService.java
│       │   ├── RegistroHorasService.java
│       │   └── ControlKilometrajeService.java
│       └── IsfGuatemalaApplication.java
└── frontend/                                # Por crear

## 🗄️ Configuración de Base de Datos

### Tablas Creadas (9)

| Tabla | Descripción | Relaciones |
|-------|-------------|------------|
| `oficinas` | Regiones (Nacional, Xela, Joyabaj, Sololá) | → proyectos |
| `puestos` | Puestos de trabajo con salario/hora | → empleados |
| `empleados` | Colaboradores de la ONG | → puesto_id |
| `tareas` | Tipos de tareas (cobrables/no cobrables) | → registro_horas |
| `proyectos` | Proyectos de construcción | → oficina_id |
| `vehiculos` | Vehículos de la ONG | → control_kilometraje |
| `registro_horas` | Control de horas trabajadas | → oficina, proyecto, tarea, empleado |
| `control_kilometraje` | Control de uso vehicular | → vehiculo, oficina, proyecto, responsable |
| `usuarios` | Usuarios del sistema (3 roles) | - |

### Datos de Prueba Insertados

- **4 Oficinas:** Nacional, Xela, Joyabaj, Sololá
- **5 Puestos:** Ingeniero Civil, Arquitecto, Topógrafo, Maestro de Obra, Ayudante
- **3 Empleados:** Juan López, María Pérez, Pedro Mendoza
- **6 Tareas:** Diseño, Supervisión, Reunión Cliente, Capacitación, Administrativo, Viaje
- **2 Proyectos:** Escuela Rural Xela, Centro de Salud Joyabaj
- **2 Vehículos:** Toyota Hilux 2020, Nissan Frontier 2019
- **1 Usuario Admin:

📡 Endpoints API
Base URL
http://18.191.1.249:8080/api
Oficinas
GET    /api/oficinas           # Listar todas
GET    /api/oficinas/{id}      # Obtener por ID
POST   /api/oficinas           # Crear nueva
PUT    /api/oficinas/{id}      # Actualizar
DELETE /api/oficinas/{id}      # Eliminar
Puestos
GET    /api/puestos            # Listar todos
GET    /api/puestos/{id}       # Obtener por ID
POST   /api/puestos            # Crear nuevo
PUT    /api/puestos/{id}       # Actualizar
DELETE /api/puestos/{id}       # Eliminar
Empleados
GET    /api/empleados          # Listar todos
GET    /api/empleados/{id}     # Obtener por ID
POST   /api/empleados          # Crear nuevo
PUT    /api/empleados/{id}     # Actualizar
DELETE /api/empleados/{id}     # Eliminar
Tareas
GET    /api/tareas             # Listar todas
GET    /api/tareas/{id}        # Obtener por ID
POST   /api/tareas             # Crear nueva
PUT    /api/tareas/{id}        # Actualizar
DELETE /api/tareas/{id}        # Eliminar
Proyectos
GET    /api/proyectos                  # Listar todos
GET    /api/proyectos/{id}             # Obtener por ID
GET    /api/proyectos/oficina/{id}    # Por oficina
POST   /api/proyectos                  # Crear nuevo
PUT    /api/proyectos/{id}             # Actualizar
DELETE /api/proyectos/{id}             # Eliminar
Vehículos
GET    /api/vehiculos          # Listar todos
GET    /api/vehiculos/activos  # Solo activos
GET    /api/vehiculos/{id}     # Obtener por ID
POST   /api/vehiculos          # Crear nuevo
PUT    /api/vehiculos/{id}     # Actualizar
DELETE /api/vehiculos/{id}     # Eliminar
Registro de Horas
GET    /api/registro-horas                     # Listar todos
GET    /api/registro-horas/{id}                # Obtener por ID
GET    /api/registro-horas/proyecto/{id}      # Por proyecto
GET    /api/registro-horas/empleado/{id}      # Por empleado
GET    /api/registro-horas/rango-fechas       # Por rango fechas
GET    /api/registro-horas/filtros            # Con múltiples filtros
POST   /api/registro-horas                     # Crear nuevo
PUT    /api/registro-horas/{id}                # Actualizar
DELETE /api/registro-horas/{id}                # Eliminar
Control Kilometraje
GET    /api/control-kilometraje                 # Listar todos
GET    /api/control-kilometraje/{id}            # Obtener por ID
GET    /api/control-kilometraje/activos         # Viajes activos
GET    /api/control-kilometraje/proyecto/{id}   # Por proyecto
GET    /api/control-kilometraje/vehiculo/{id}   # Por vehículo
GET    /api/control-kilometraje/rango-fechas    # Por rango fechas
POST   /api/control-kilometraje/iniciar         # Iniciar viaje
PUT    /api/control-kilometraje/cerrar/{id}     # Cerrar viaje
PUT    /api/control-kilometraje/{id}            # Actualizar
DELETE /api/control-kilometraje/{id}            # Eliminar

🔜 Próximos Pasos
1. Frontend React (PRIORITARIO)

Crear aplicación React 18
Implementar Material-UI
Crear componentes para los 3 módulos:

Administración (CRUD)
Control de Horas (Registro + Reportes)
Control Vehicular (Viajes + OCR)


Sistema de navegación por roles (Admin, Usuario Estándar, Externo)
Integración con el backend

2. Nginx + SSL

Configurar Nginx como reverse proxy
Instalar certificado SSL con Let's Encrypt
Configurar dominio app.isf-guatemala.org

3. AWS S3 + Lambda

Configurar bucket S3 para fotos de odómetro
Lambda function para OCR con AWS Textract

4. Autenticación

JWT tokens
Login/Logout
Protección de rutas por rol

5. Reportes PDF

Generación de reportes de horas
Reportes de kilometraje
iText o similar

📝 Notas Importantes
Reglas de Negocio Implementadas
Registro de Horas:

Automáticamente copia esCobrable de la tarea
Calcula horasTotales = horas + (minutos/60)
Calcula costoTotal = horasTotales × salarioPorHora (solo si es cobrable)

Control Kilometraje:

Conversión automática Millas → KM (factor 1.609)
KM Extra solo si supera 50 km
Actualiza odómetro del vehículo al cerrar viaje
Valida que millaje de entrada > millaje de salida

Validaciones Implementadas

Campos obligatorios con @NotNull, @NotBlank
Longitud máxima con @Size
Valores positivos con @Positive
Formato de teléfono (8 dígitos) con @Pattern
Unicidad de códigos, placas, nombres