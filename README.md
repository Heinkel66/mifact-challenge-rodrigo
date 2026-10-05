# 🚀 MiFact Challenge - Sistema de Inventario de Productos

Este repositorio contiene la solución completa para la evaluación técnica del perfil **Analista Desarrollador Full Stack**, desarrollada por **Rodrigo Chávez Castañeda**.

El proyecto está dividido en dos capas independientes, respetando una arquitectura modular, principios SOLID y las versiones requeridas en el documento de evaluación.

## 🛠️ Stack Tecnológico Utilizado

### Backend (Carpeta `mifact-backend-productos`)
* **Java 8** y **Spring Boot 2.7.x** (Versión LTS compatible con Java 8).
* **SQL Server** (Base de datos relacional).
* **Spring Security + JWT** (Autenticación sin estado).
* **Spring Data JPA** (Persistencia y queries autogenerados).
* **Swagger (OpenAPI 3)** (Documentación de endpoints).
* Patrón arquitectónico de capas (Controllers, Services, Repositories, DTOs).
* Manejo Global de Excepciones (`@ControllerAdvice`).

### Frontend (Carpeta `mifact-frontend-productos`)
* **Angular 18** (Estructura Standalone, sin NgModules).
* **PrimeNG 17** (Librería UI requerida, configurada de forma tradicional para total compatibilidad).
* **RxJS** (Uso de `Subject`, `debounceTime` y `distinctUntilChanged` para la **búsqueda en tiempo real** sin saturar el backend).
* **Interceptors & Guards** (Inyección de token JWT y protección de rutas).
* Formularios Reactivos con validaciones.

---

## 📝 Metodología de Desarrollo y Control de Versiones

Para mantener un flujo de trabajo ordenado y aplicar commits atómicos, el desarrollo se realizó originalmente en **dos repositorios privados separados** (uno para Backend y otro para Frontend). 

Para efectos de esta entrega y facilitar la revisión, ambos proyectos fueron unificados en este monorepositorio público. A continuación, adjunto el historial de commits original (`git log`) de ambos repositorios para evidenciar el proceso constructivo paso a paso:

<details>
<summary><b>Ver Historial de Commits - Backend (Java / Spring Boot)</b></summary>

commit 2105f73fbf84ae88903fcee31dd6a9915439fdc0 (HEAD -> master, origin/master)
Author: Heinkel66 <Heinkel.66@gmail.com>
Date:   Mon Oct 5 13:10:11 2026 -0500

    PC 05-10-2026 - Implementación de JWT y config de Swagger

commit fa04f5cb449de0d84c9487aad9481c714e215413
Author: Heinkel66 <Heinkel.66@gmail.com>
Date:   Mon Oct 5 12:06:56 2026 -0500

    PC 05-10-2026 - Se agregaron los REST Controllers para Producto y se creó un handler global para los exceptions

commit 38bdb244501f71553a862134f4afc1ecb83bb628
Author: Heinkel66 <Heinkel.66@gmail.com>
Date:   Mon Oct 5 11:44:41 2026 -0500

    PC 05-10-2026 - Creé dto para Producto y su service

commit 5b6262be3e830654ac28ee765e66ca6ce1a090c2
Author: Heinkel66 <Heinkel.66@gmail.com>
Date:   Mon Oct 5 11:36:26 2026 -0500

    PC 05-10-2026 - Creé entidades (dominio) de producto y usuario y repos JPA para ambos

commit cad92376610b53e91d0615a74768e4b09c2e9dab
Author: Heinkel66 <Heinkel.66@gmail.com>
Date:   Mon Oct 5 11:28:11 2026 -0500

    PC 05-10-2026 - Setup inicial Spring Boot 2.7.18, Java 8 y config inicial de SQL Server

</details>

<details>
<summary><b>Ver Historial de Commits - Frontend (Typescript / Angular)</b></summary>

commit 72cbe31977112dac415ae12f8441b467ddef26c7 (HEAD -> master, origin/master)
Author: Heinkel66 <Heinkel.66@gmail.com>
Date:   Mon Oct 5 15:08:39 2026 -0500

    PC 05-10-2026 - Terminé las vistas de login, productos y se realizaron pruebas

commit faca2647cba63f23b2a8f1e82e29b1f6651d53c6
Author: Heinkel66 <Heinkel.66@gmail.com>
Date:   Mon Oct 5 13:33:03 2026 -0500

    PC 05-10-2026 - Se implementó JWT auth service, interceptors, guard y app config

commit f0ec754e33e7e57e4495797f9ad0b3c35181f648
Author: Heinkel66 <Heinkel.66@gmail.com>
Date:   Mon Oct 5 13:20:34 2026 -0500

    PC 05-10-2026 - Creando proyecto

commit 75512a724ec5ab0cf9260511a1ea1c5895d86f77
Author: Heinkel66 <Heinkel.66@gmail.com>
Date:   Mon Oct 5 13:18:08 2026 -0500

    initial commit
</details>

---


## ⚙️ Instrucciones de Instalación y Ejecución

### 1. Base de Datos (SQL Server)
1. Crear una base de datos en SQL Server llamada `mifact_db`.
2. Las tablas (`productos` y `usuarios`) se crearán automáticamente por Hibernate.

### 2. Backend
1. Abrir la carpeta `mifact-backend-productos` en el IDE (IntelliJ/Eclipse/VSCode).
2. En `src/main/resources/application.properties`, actualizar las credenciales de SQL Server (`username` y `password`).
3. Ejecutar la clase `DemoApplication.java`. El servidor correrá en `http://localhost:8080`.
4. **Nota de Seguridad (Seeder):** Al iniciar por primera vez, el sistema detectará que la tabla de usuarios está vacía y creará automáticamente el siguiente usuario administrador encriptado con BCrypt:
   * **Usuario:** `admin`
   * **Contraseña:** `admin2026`
5. Puede probar la API documentada ingresando a: `http://localhost:8080/swagger-ui/index.html`

### 3. Frontend
1. Abrir una terminal en `mifact-frontend-productos`.
2. Ejecutar `npm install` (con la bandera `--legacy-peer-deps` si es necesario).
3. Ejecutar `ng serve`.
4. Abrir en el navegador: `http://localhost:4200`
5. Iniciar sesión con las credenciales del Seeder (admin / admin2026).

---

## 🎯 Puntos Clave del Desarrollo
* **No exposición de Entidades:** Se implementaron `DTOs` (Data Transfer Objects) para transitar los datos entre el controlador y el cliente.
* **Búsqueda Optimizada:** La búsqueda por nombre se ejecuta "en tiempo real" al escribir, pero controlada mediante un pipeline de RxJS para evitar peticiones HTTP innecesarias.
* **Commits Atómicos:** El desarrollo se llevó a cabo utilizando metodologías de control de versiones paso a paso.