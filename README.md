# iveo-backend

##Como levantar el sistema de manera local

El back end del sistema se encuentra desarrollado en spring boot. El mismo, 
nos brinda muchas herramientas para el facil despliegue y configuraciones.

### Dependencias 

Para poder levantar el sistema de manera local, se tiene que tener previamiente instalado lo siguiente.

1. Java 1.8
2. Intellij IDEA Community
3. Maven > 3
4. MongoDb 4.0.3

### Pasos para levantar la aplicacion mediante Intellij IDEA.

1. Abrir el proyecto en intellij.

2. Abrir el paquete ar.com.mzanetti.iveo. donde encontramos la clase Application.java.

```xtend
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

3. Correr la clase, presionando el boton verde con forma de flecha. Adjunto ejemplo.

 ![alt tag](https://github.com/zanettimatias/iveo-backend/blob/master/assets/intellijrun.png)
*El circulo Amarillo señala el boton antes mencionado.*

Luego de presionar el boton, en la consola del intellij aparecera un codigo como el suiguiente.

```xtend
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.3.4.RELEASE)

2020-12-15 20:26:35.678  INFO 280 --- [           main] ar.com.mzanetti.iveo.Application         : Starting Application on DESKTOP-4QE2QT2 with PID 280 (C:\Users\Usuario\IdeaProjects\iveo-backend\target\classes started by Usuario in C:\Users\Usuario\IdeaProjects\iveo-backend)
2020-12-15 20:26:35.680  INFO 280 --- [           main] ar.com.mzanetti.iveo.Application         : No active profile set, falling back to default profiles: default
2020-12-15 20:26:36.481  INFO 280 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2020-12-15 20:26:36.493  INFO 280 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2020-12-15 20:26:36.493  INFO 280 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.38]
2020-12-15 20:26:36.575  INFO 280 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2020-12-15 20:26:36.575  INFO 280 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 849 ms
2020-12-15 20:26:36.720  INFO 280 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-12-15 20:26:36.873  INFO 280 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2020-12-15 20:26:36.880  INFO 280 --- [           main] ar.com.mzanetti.iveo.Application         : Started Application in 1.534 seconds (JVM running for 1.919)

```
Una vez que en la consola veamos la siguiente linea;

```xtend
main] ar.com.mzanetti.iveo.Application         : Started Application in 1.534 seconds (JVM running for 1.919)**
```
La aplicación estara escuchando en el puerto 8080


