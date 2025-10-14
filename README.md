# 📱 Proyecto UniBoost — Android + Firestore

## 🧩 Descripción general
UniBoost es una aplicación móvil desarrollada en **Android Studio (Java + XML)** que integra **Firebase Firestore** como base de datos principal.  
El objetivo es permitir el **registro e inicio de sesión de usuarios** mediante correo electrónico, gestionando la información del perfil y manteniendo la sesión activa dentro del sistema.

La arquitectura se diseñó bajo una **estructura modular limpia**, separando la interfaz, la lógica de negocio y la capa de datos.

---

## 🏗️ Arquitectura del sistema

### 🔹 1. Capa de Presentación (Interfaz de Usuario)
Desarrollada con vistas XML y Activities de Android:
- `MainActivity`: Pantalla inicial con botones de **Registro** e **Inicio de sesión**.
- `RegistroActivity`: Formulario para crear cuenta (nombre, correo, contraseña).
- `LoginActivity`: Acceso al sistema mediante correo válido.
- `PantallaPrincipalActivity`: Vista principal del usuario tras iniciar sesión.
- `AppBar`: Incluye botón de perfil con nombre, correo y opción de **Cerrar sesión**.

### 🔹 2. Capa de Lógica (Controladores)
Implementada en Java, contiene:
- `UserController`: Controla flujo de registro, inicio/cierre de sesión.
- `FirestoreService`: Gestiona la conexión con Firestore (CRUD).
- `SharedPreferencesManager`: Maneja almacenamiento local de datos del usuario (persistencia ligera).

### 🔹 3. Capa de Datos (Firestore Database)
Base de datos en la nube de Firebase:
- **Colección:** `usuarios`
  - Campos: `nombre`, `correo`, `contraseña`, `uid`
- **Colección:** `sesiones`
  - Campos: `correo`, `estado`, `ultimo_acceso`

Conexión mediante el archivo `google-services.json` y dependencias del SDK de Firebase.

---

## 🔌 Dependencias principales
```gradle
implementation 'com.google.firebase:firebase-firestore:25.1.1'
implementation 'com.google.firebase:firebase-auth:23.1.0'
implementation 'com.google.firebase:firebase-analytics:22.1.2'
```

Gradle:  
```text
distributionUrl=https://services.gradle.org/distributions/gradle-8.13-bin.zip
```

---

## 🔄 Flujo actual
1. El usuario abre la app → se muestran los botones **Registrar** / **Iniciar sesión**.  
2. Al registrarse → se guarda la información en **Firestore (colección “usuarios”)**.  
3. Al iniciar sesión → se valida el correo y se carga la información del usuario.  
4. En la pantalla principal, el botón de perfil muestra:
   - Nombre
   - Correo
   - Opción de **Cerrar sesión**

---

## 🧭 Próximos pasos
- ✅ Implementar **verificación de correo** con Firebase Authentication.  
- 🔄 Sincronizar automáticamente la sesión entre dispositivos.  
- 📤 Agregar módulo de **subida de imagen de perfil** (Storage).  
- 📈 Crear dashboard básico con estadísticas del usuario.  
- 🧠 Integrar sistema de **recomendaciones personalizadas** usando IA (futuro módulo).

---

## 🧩 Diagrama de componentes
El diagrama describe el flujo vertical entre capas:
1. **Usuario**
2. **UI XML + Activities**
3. **Lógica interna (Java)**
4. **Firestore Database**
5. **Servicios externos (Firebase SDK)**

> Consultar archivo: `diagrama_componentes_firestore.xml`  
> o importar la descripción en Lucidchart/Draw.io para visualizarlo gráficamente.

---

## 👨‍💻 Autor
**David Urrutia Cerón (Bicho)**  
Estudiante de Ingeniería de Software  
Minimalista por convicción.  
📅 Octubre 2025

---

## ⚙️ Licencia
Este proyecto es de uso académico y demostrativo.  
Libre para modificación y mejora bajo crédito al autor original.
