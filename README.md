**Cursos2ee**

Ejercicio de desarroyo de aplicación web j2ee sin el uso de frameworks.

Se ha implementado una solución mvc basada en servlets con persistencia con JavaDB (dervy), y el ciclo de vida controlado por maven.

Se ha usado junit y mockito para implementar las pruebas del codigo de negocio.

El paquete esta preparado para construirse y ejecutarse en un servidor jetty en cualquier máquina con maven (y acceso al repositorio de dependencias) y java 8.

Para construir

`mvn clean package`

para lanzar el servidor

`mvn jetty:run`
