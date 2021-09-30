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

## Generación de imágenes para el despliegue ![despliegue](https://img.icons8.com/plasticine/45/000000/services.png)

**Prerequisitos**
+ Maven, Java
+ Docker instalado en la máquina
+ Repositorio clonado

### ![Docker](https://img.icons8.com/color/38/000000/docker.png) Parte I

1. **Para crear las imágenes, ejecute el siguiente comando. Debe estar ubicado en la raiz del proyecto (donde se encuentra el archivo docker-compose.yml), en este caso en** *dockerdemolab*
    ```sh
    docker-compose up -d
    ```
    > Usamos docker-compose para generar automáticamente una configuración docker, por ejemplo un container y una instancia a de mongo en otro container.
2. **Verifique que se crearon los servicios, usando el siguiente comando.**
    ```sh
    docker ps
    ```
    > Puede ingresar a la aplicación de Docker Desktop y revisar que los contenedores, imágenes y/o volúmenes creados, están corriendo.
3. **Ahora se sube las imágenes del punto 2 a Docker Hub, usando el siguiente bloque de comandos, por cada imagen. Previamente, debe haber creado una cuenta y un repositorio por c/a imagen.**
    ```sh
    1   docker login    --Realizamos la autenticación
    2   docker tag dockerlbroundrobin alizeci/dockerdemolab_lbroundrobin    --Ejemplo de imagen del balanceador de carga con mi cuenta de Docker Hub
    3   docker push alizeci/dockerdemolab_lbroundrobin:latest       --Empujar imagen al repositorio en Docker Hub
    //Se repite por cada imagen en el archivo docker-compose.yml, línea 2 y 3
    ```
    > Véase [docker-compose.yml](https://github.com/Alizeci/AREP_TALLER_Virtualizacion_Docker_AWS/blob/main/docker-compose.yml)
### ![AWS](https://img.icons8.com/color/38/000000/amazon-web-services.png) Parte II

1. **Para el despliegue en aws, debemos tener instala una máquina virtual EC2. Ejecutamos el siguiente bloque de código para instalar docker y actualizarla.**
    ```sh
    sudo yum update -y
    sudo yum install docker
    ```

2. **Posteriormente, iniciamos el servicio de docker y configuramos nuestro usuario en el grupo de docker, usando los siguientes comandos.**
    ```sh
    sudo service docker start
    sudo usermod -a -G docker ec2-user
    ```
    > Nos desconectamos de la máquina virtual e ingresamos nuevamente para que la configuración de grupos de usuarios tenga efecto, usando `exit`.

3. **Ahora clonamos el presente repositorio en Github en nuestra máquina virtual en AWS y ejecutamos `docker-compose up -d` para instalar a partir de las imágenes creadas en Dockerhub las instancias de los contenedores docker.**
    ```sh
    sudo curl -L "https://github.com/docker/compose/releases/download/1.26.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose   --Descarga la versión y guarda el ejecutable, que lo hará accesible globalmente por docker-compose
    sudo chmod +x /usr/local/bin/docker-compose     --Establecemos los permisos del ejecutable
    docker-compose --version        --Verficamos la correcta instalación

    ```
    
4. **Finalmente, ejecutamos el siguiente comando, para tener las imágenes en la máquina virtual. Debemos previamente haber abierto los puertos de entrada del security group de la máxima virtual para acceder al servicio. En este caso, el puerto 42000, en la plataforma de AWS.**
    ```sh
    docker-compose up -d    --Generar automáticamente la configuración docker
    ```
    > Ahora podremos acceder a los servicios a través de aws en el navegador con el **DNS público** de nuestra máquina virtual y el puerto **42000**. Por ejemplo, http://ec2-52-91-24-157.compute-1.amazonaws.com:42000


## Resultado del despliegue ![resultado](https://img.icons8.com/ios-filled/25/000000/test-results.png)

**En funcionamiento**
> En la **imágen de la izquierda** se puede observar las 10 cadenas más recientes; teniendo a fresa como la última agregada al log. Mientras que en la **imágen de la derecha** se puede observar la operación agregando una nueva cadena: **melón**; así como su actualización, dejando como primera a **pera** y la última **melón**.

<!DOCTYPE html>
<html>
    <head></head>
    <body>
      <p float="left">
        <img src="https://github.com/Alizeci/AREP_TALLER_Virtualizacion_Docker_AWS/blob/main/img/resultado.png" alt="R0" width="400"/>
        <img src="https://github.com/Alizeci/AREP_TALLER_Virtualizacion_Docker_AWS/blob/main/img/resultado1.png" alt="R1" width="400"/>
      </p>
    </body>
</html>

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
