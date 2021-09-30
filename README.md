## TALLER DE DE MODULARIZACIÓN CON VIRTUALIZACIÓN, DOCKER Y AWS

Se construye una aplicación web pequeña usando el micro-framework de **Spark** LoadBalancer en java, capaz de delegar el procesamiento del mensaje y el retorno de la respuesta a cada instancias del servicio LogService. En él, profundizamos los conceptos de modularización por medio de virtualización usando **Docker** y **AWS**.

## Entendimiento 🎯

La aplicación está compuesta de **3 componentes**: un balanceador de carga cajo el algoritmo de Round Robin, 3 instancias lógicas web de LogService y una instancia del servicio MongoDB, modularizadas in contenedores de Docker.

## Descripción Arquitectura ![Descripción detallada](https://img.icons8.com/windows/32/000000/product-architecture.png)

El proyecto está compuesto por:

+ La **aplicación web LoadBalancer**, que tiene un *cliente web* y *dos servicios REST*, uno para agregar nuevas cadenas y otro para devolver las 10 cadenas más recientes. El *servicio REST* recibe la cadena e implementa el algoritmo de *balanceo de cargas* de **Round Robin**, con el fin de ordenar la atención de las peticiones consecutivamente de las instancias de los servicios LogService. La aplicación corre por el puerto **42000**

+ El **LogService**, es un *servicio REST* que recibe una cadena, la almacena en la base de datos y responde en un objeto JSON con las 10 ultimas cadenas almacenadas en la base de datos y la fecha en que fueron almacenadas. Cada instancia del servicio corre respectivamente por los puertos **34001**, **34002**, **34003**

+ El **servicio MongoDB,** que es una instancia de MongoDB corriendo en un *container de docker* en una *máquina virtual* de **EC2**. El servicio corre por el puerto **27017**

### Diseño de la arquitectura de la aplicación 📝

**Diseño General**
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <img src="https://github.com/Alizeci/AREP_TALLER_Virtualizacion_Docker_AWS/blob/main/img/arquitecturaGeneral.png" alt="AG" width="800"/>
    </body>
</html>

**Diseño Detallado**
<!DOCTYPE html>
<html>
    <head></head>
    <body>
      <p float="left">
        <img src="https://github.com/Alizeci/AREP_TALLER_Virtualizacion_Docker_AWS/blob/main/img/arquitecturaDetallada.png" alt="AG" width="350"/>
        <img src="https://github.com/Alizeci/AREP_TALLER_Virtualizacion_Docker_AWS/blob/main/img/diseñoClases.png" alt="DC" width="400"/>
      </p>
    </body>
</html>

+ La aplicación web **LoadBalancer** corre por el puerto **42000**. 
  
  📍**Tiene 2 endpoints:** 
  + **POST** */addlog*, permite agregar una nueva cadena a *MongoDB*, retorna un mensaje de operación exitosa.
  + **GET** */recentlog*, permite obtener las ultimos 10 cadenas almacenadas en *MongoDB*, con el valor de la cadena y la fecha de creación.

+ Cada instancia del servicio **LogService** corre respectivamente por los puertos **34001**, **34002**, **34003**. 

  📍**Tiene 3 endpoints:**
  + **POST** */create*, permite agregar una nueva cadena a *MongoDB*, retorna un mensaje de operación exitosa.
  + **GET** */log*, permite obtener todas las cadenas almacenadas en la base de datos, retorna una lista de cadenas con el valor de la cadena y la fecha de creación.
  + **GET** */recent*, permite obtener las ultimas 10 cadenas almacenadas en *MongoDB*, retrona una lista de cadenas con el valor de la cadena y la fecha de creación.

+ El **servicio MongoDB,** corre por el puerto **27017**

## Herramientas utilizadas

| Nombre | Uso |
| ------ | ------ |
| **Maven** ![Maven](https://img.icons8.com/ios/25/000000/maven-ios.png) | Gestión y construcción del proyecto |
| **Eclipse IDE** ![Eclipse](https://img.icons8.com/office/25/000000/java-eclipse.png) | Plataforma de desarrollo |
| **Git** ![Git](https://img.icons8.com/color/25/000000/git.png) | Sistema de control de versiones |
| **Github** ![Github](https://img.icons8.com/windows/25/000000/github.png) | Respositorio del código fuente |
| **Docker & DockerHub** [![Docker](https://img.icons8.com/color/48/000000/docker.png)](https://hub.docker.com) | Contrucción de los contenedores |
| **Amazon Web Services** [![AWS](https://img.icons8.com/color/48/000000/amazon-web-services.png)](https://aws.amazon.com/training/awsacademy/) | Plataforma de producción en la nube |

## Autor ![Autor](https://img.icons8.com/fluency/30/000000/person-female.png)
Laura Alejandra Izquierdo Castro
