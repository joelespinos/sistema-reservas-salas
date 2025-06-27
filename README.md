# Sistema de reservas de salas

## Descripción del proyecto

Este proyecto consiste en un sistema de reservas de salas desarrollado en **Java** utilizando **Maven**. Permite gestionar salas, empleados y reservas a través de una interfaz de consola.

El sistema está estructurado en capas (**controlador**, **modelo**, **utilidades** y **vistas**) para facilitar el mantenimiento y la escalabilidad.

### Resumen de funcionalidades:

- **Gestión de salas**: Alta, baja, modificación y consulta de salas disponibles.  
- **Gestión de empleados**: Registro, edición, eliminación y consulta de empleados.  
- **Gestión de reservas**: Creación, modificación, cancelación y consulta de reservas de salas, validando solapamientos y restricciones de horario.  
- **Validaciones**: Comprobación de datos de entrada (fechas, horas, DNI, etc.) para garantizar la integridad de la información.  
- **Interfaz de usuario**: Menú por consola para navegar entre las distintas gestiones del sistema.

---

## Ejecutar base de datos MySQL

En la estructura del proyecto encontrarás una carpeta llamada `sql` que contiene los scripts necesarios para generar la base de datos del sistema:

- **`init-database.sql`**: Contiene las instrucciones necesarias para crear la estructura de la base de datos (base de datos, tablas y campos).
- **`insert-sample-data.sql`** *(opcional)*: Contiene datos de ejemplo para probar el sistema. Incluye 10 salas, 10 empleados y 25 reservas.

### Instrucciones:

1. Abre tu gestor de base de datos MySQL.
2. Accede al apartado de consola SQL.
3. Copia y ejecuta el contenido de los scripts en el orden indicado.

---

## Ejecutar el proyecto desde un IDE

Este proyecto puede ejecutarse desde cualquier entorno de desarrollo como **Eclipse**, **IntelliJ IDEA**, **VSCode**, entre otros.

### Pasos:

1. Importa el proyecto como un **proyecto Maven** en tu IDE.
2. Navega a la clase `ReservationSystemMain.java`.
3. Ejecuta esa clase.
4. Al ejecutarse, se mostrará el **menú principal** desde donde podrás utilizar todas las funcionalidades del sistema.

