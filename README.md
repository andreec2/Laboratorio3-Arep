# TALLER MICROFRAMEWORKS WEB


Este proyecto implementa un servidor web básico en Java que maneja solicitudes REST (GET, POST) y retorna los archivos solicitados que conforman la página web. El servidor está diseñado para manejar una conexión de cliente y realizar operaciones en objetos JSON y archivos HTML en memoria. Cada solicitud es manejada por ClientHandler. El tema escogido para la página web es de REM.

Además, el proyecto ha evolucionado con el objetivo de convertirse en un marco web completamente funcional, permitiendo el desarrollo de aplicaciones web con servicios REST en el backend.

![{77378D07-17EE-46F6-9941-1207A993392A}](https://github.com/user-attachments/assets/0d10e85d-2502-4756-9b86-7f727cf55d42)

## Arquitectura
Este proyecto sigue la arquitectura cliente-servidor. Utilizando el estilo arquitectonico de REST, Los clientes envían solicitudes HTTP al servidor, que maneja la lógica de los serviciso REST y devuelve respuestas en formato JSON. El servidor también puede retornar archivos estáticos, como HTML, CSS e imágenes. 

![{B47E569F-C1A3-4BF5-A76C-21482C66D2A2}](https://github.com/user-attachments/assets/bfae084e-bc1d-405c-b8b0-ccbab15e84da)

### Componentes del Proyecto 
   - **Servidor** : HttpServer: Maneja las conexiones y enruta las solicitudes a los servicios correspondientes.
     ClientHandler: Gestiona la comunicación con el cliente, procesa la solicitud y decide si debe retornar un archivo estático o delegar la solicitud a un servicio REST.

   - **Servicios REST**: ClientHandler: Maneja operaciones CRUD en objetos JSON y consulta archivos HTML estáticos guardados en el servidor.
Se implementaron dos clases nuevas:
Request: Encapsula los datos de la solicitud del cliente, permitiendo acceder a parámetros y valores de consulta.
Response: Maneja la respuesta que se enviará al cliente, incluyendo el código de estado y los datos devueltos.
Estas clases permiten definir servicios REST de manera más flexible mediante funciones lambda.
   - **Archivos estáticos**: Almacenados en el directorio public, accesibles mediante el servidor para servir contenido HTML, CSS, JS e imágenes.


## Primeros Pasos
Estas instrucciones le permitirán obtener una copia del proyecto en funcionamiento en su máquina local para fines de desarrollo y prueba. 


### Requisitos Previos
Para ejecutar este proyecto, necesitarás tener instalado:

- Java JDK 8 o superior.
- Un IDE de Java como IntelliJ IDEA, Eclipse.
- Maven para manejar las dependencias 
- Un navegador web para interactuar con el servidor.

### Instalación 

1. Tener instalado Git en tu maquina local 
2. Elegir una carpeta en donde guardes tu proyecto
3. abrir la terminal de GIT --> mediante el clik derecho seleccionas Git bash here
4. Clona el repositorio en tu máquina local:
   ```bash
   git clone https://github.com/andreec2/Laboratorio2-Arep.git
   ```
5. Abre el proyecto con tu IDE favorito o navega hasta el directorio del proyecto 
6. Desde la terminal  para compilar el proyecto ejecuta:

   ```bash
   mvn clean install
   ```
7. compila el proyecto  

   ```bash
    mvn clean package
   ```
8. Corra el servidor en la clase httpServer "main"
   Vera que el servidor esta listo y corriendo sobre el puerto 35000
   
9. Puedes Ingresar desde el navegador a la pagina:
    http://localhost:35000/index.html

10. Puedes interactuar con los endpoints RESTful (/api):
   - POST= http://localhost:35000/post.html

11. Para este taller, además de los recursos estáticos ya implementados, hemos definido nuevos endpoints utilizando funciones lambda, lo que mejora la flexibilidad y capacidad de nuestro servidor web.

   - http://localhost:35000/app/hello?name=Andres

     ![image](https://github.com/user-attachments/assets/157e7314-4da7-43c8-b96f-3dc1efa6dbaf)

   - http://localhost:35000/app/hello?name=Andres

   ![image](https://github.com/user-attachments/assets/b92ddfa0-cbb7-4611-87f5-30b4f9665bf2)

   - http://localhost:35000/app/helloWord

   ![image](https://github.com/user-attachments/assets/ff58dcad-ef25-4758-bae5-79c714e9406d)

   - http://localhost:35000/app/pi

   ![image](https://github.com/user-attachments/assets/43e4b2cf-20e9-4c2a-b95a-790e9ff43d94)

   - Funciones lambda

     ![image](https://github.com/user-attachments/assets/ac327b5b-85d3-4a90-84b4-841ce691bf11)

## Ejecutar las pruebas

Se implementaron pruebas unitarias para los métodos de manejo de solicitudes HTTP (GET, POST, PUT, DELETE) en el servidor. Estas pruebas se realizaron utilizando JUnit y Mockito para simular las solicitudes y validar las respuestas.

Para ejecutar las pruebas:  
1. Desde tu IDE, ejecuta las clase AppTest.java o desde la terminal ejecutas:
   ```bash
   mvn test
   ```
![image](https://github.com/user-attachments/assets/167b96c5-1f11-4786-a4bb-3b04b5e43c4c)

![image](https://github.com/user-attachments/assets/b0f3182c-5346-4d78-b5bf-311731fa41af)

2. Pruebas de extremo a extremo

   - testGetRoutesInitialization()
     
  Propósito: Verifica que las rutas /app/helloWord, /app/hello y /app/pi están correctamente registradas en el servidor. 

  ![image](https://github.com/user-attachments/assets/927fe4dd-52c1-4528-8e81-35739174bb5f)

  - testHelloWorldRoute()

   Propósito: Verifica que la ruta /app/helloWord devuelve "Hello, world!".

   ![image](https://github.com/user-attachments/assets/a1a7d453-3867-4cfd-9fbd-16a7fc6a36e8)

   - testHelloPiRoute()

   Propósito: Verifica que la ruta /app/pi devuelve "3.141592653589793" (el valor de π).

   ![image](https://github.com/user-attachments/assets/c2ce88e6-8ea6-44f0-8f6d-09455eea53fc)

   - testHelloNameRoute()

   Propósito: Verifica que la ruta /app/hello devuelve "Hola, Andres!" cuando se envía el parámetro name=Andres.

   ![image](https://github.com/user-attachments/assets/24574f37-335f-43b3-aba0-6db194f67fd8)

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management


## Authors

* **Andres felipe montes ortiz** - 

