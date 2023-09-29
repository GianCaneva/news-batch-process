FROM amazoncorretto:17
COPY target/ainews-batch-process-0.0.1-SNAPSHOT.jar ainews-batch-process.jar
ENTRYPOINT ["java", "-jar", "ainews-batch-process.jar"]
#VOLUME /temp
#EXPOSE 8080
#ADD ./target/ainews-batch-process-0.0.1-SNAPSHOT.jar ainews-batch-process.jar
#CMD ["java", "-jar", "ainews-batch-process.jar"]


#Comandos
#docker build -t ainews-batch-process:1.0 . (el punto implica que el archivo esta a la altura del root donde estamos ejecutando el comando)
#docker images (para ver las imagenes)
#copio el id de la imagen y creo un contenedor. Mapeo el network (si se va a conectar a algun lado que sea al localhost) y los puertos
#docker run --net=host -p 8080:8080 0c7d83ab05eb

#docker run -p 3306:3306 --name mysql1 -e MYSQL_ROOT_PASSWORD=password -d mysql --default-authentication-plugin=mysql_native_password -h 127.0.0.1


