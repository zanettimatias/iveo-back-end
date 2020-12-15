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
*El circulo Amarrillo señala el boton antes mencionado.*



