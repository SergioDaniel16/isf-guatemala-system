# Sistema ISF Guatemala

Sistema de gestión para Ingenieros Sin Fronteras Guatemala.

## Tecnologías

### Backend
- Java 17 LTS
- Spring Boot 2.7.x
- PostgreSQL 16
- Maven 3.8+

### Frontend
- React 18
- Node.js 18 LTS
- Material-UI

## Servidor
- AWS EC2 (Ubuntu 24.04 LTS)
- IP: 18.191.1.249
- Dominio: app.isf-guatemala.org

## Módulos

1. **Administración**: Gestión de proyectos, empleados, puestos, tareas y vehículos
2. **Control de Horas**: Registro y reportes de horas trabajadas
3. **Control Vehicular**: Gestión de uso de vehículos y kilometraje

## Estructura del Proyecto
isf-guatemala/
├── backend/          # Spring Boot API
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   └── resources/
│       └── test/
└── frontend/         # React App (por crear)

## Base de Datos

**Database**: isf_guatemala  
**User**: isf_admin  
**Puerto**: 5432

### Tablas:
- oficinas
- proyectos
- empleados
- puestos
- tareas
- vehiculos
- registro_horas
- control_kilometraje
- usuarios
