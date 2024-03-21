# Employee Manager with Spring Boot and Spring Security

Este es un proyecto de demostración para un sistema de gestión de empleados desarrollado con Spring Boot y Spring Security. Incluye funciones para el manejo de empleados y autenticación de usuarios utilizando tokens JWT.

## Instalación

1. Clona este repositorio en tu máquina local usando `git clone https://github.com/jairzea/employee-manager.git`.
2. Abre el proyecto en tu IDE favorito.
3. Asegúrate de tener Java 17 y Maven instalados en tu sistema.
4. Compila el proyecto usando el comando `mvn clean install`.
5. Ejecuta la aplicación Spring Boot con el comando `mvn spring-boot:run`.

## Uso

### Endpoints de Autenticación

- `POST /api/v1/auth/signup`: Registra un nuevo usuario. Debes enviar un JSON con los campos `username`, `password`, y `name`.
- `POST /api/v1/auth/signin`: Inicia sesión con un usuario existente. Debes enviar un JSON con los campos `username` y `password`.

### Endpoints de Empleados

- `POST /api/v1/employees/save`: Crea un nuevo empleado. Debes enviar un JSON con los campos `name`, `lastNames`, `gender`, `age`, y `email`.
- `GET /api/v1/employees`: Obtiene todos los empleados.
- `GET /api/v1/employees/{id}`: Obtiene un empleado por su ID.
- `DELETE /api/v1/employees/{id}`: Elimina un empleado por su ID.
- `GET /api/v1/employees/age`: Obtiene empleados con una edad igual o mayor a la especificada. El parámetro `age` es opcional y tiene un valor predeterminado de 40.
- `GET /api/v1/employees/female`: Obtiene empleados con género femenino.

## Usuario de Prueba

- **Usuario:** usuario (admin)
- **Contraseña:** admin123\*

## Colección de Postman

También puedes descargar y probar la colección de Postman que se encuentra en el repositorio. La colección se llama "api employee-mutual.postman_collection.json".

Esta colección contiene configurada una variable global llamada "tokenEmployee", la cual está definida en cada una de las rutas que requieren autenticación. Esta variable se actualiza automáticamente cuando el usuario se autentica en la API. Para autenticarse, simplemente realiza una petición al endpoint de signin. Una vez autenticado, las demás rutas estarán protegidas automáticamente.

En caso de que la autenticación automática no funcione correctamente, puedes copiar y pegar el token generado y autenticar manualmente las rutas utilizando la autorización de tipo Bearer Token.

## Contribución

Si deseas contribuir a este proyecto, sigue los siguientes pasos:

1. Haz un fork del repositorio.
2. Crea una rama nueva (`git checkout -b feature/feature-name`).
3. Realiza tus cambios y haz commit (`git commit -am 'Add new feature'`).
4. Haz push a la rama (`git push origin feature/feature-name`).
5. Crea un nuevo Pull Request.

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.

## Contacto

Si tienes alguna pregunta o sugerencia, no dudes en ponerte en contacto conmigo en [tu-email@example.com](mailto:tu-email@example.com).
