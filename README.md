# Unchained News 🟩
> Aplicación para la optimización de lectura de noticias digitales y libre de sesgos.
---
## Descripción
El presente codigo forma parte de una constelación de servicios que, en conjunto, trabajan para conseguir el objetivo deseado.
En particular, la presente aplicación se encarga de todo lo relacionado con la busqueda y procesamiento de noticias crudas para su posterior almecenamiento en la base de datos (que luego seran consumidas por el modulo de gestor de usuarios y noticias).
Posee y accede a las direcciones URL para obtener las fuentes RSS, accede a los articulos extrayendo la información, realiza la extracción de palabras claves, combina aquellos artículos de diferentes fuentes que hablen del mismo tópico y almacena en la base de datos.
Como medida adicional, se encarga de la deducción de puntos de interés para todos los usuarios.

Esta aplicación fue concebida pera ser ejecutada utilizando un CRON JOB, donde su frecuencia puede ser modificiada.


## Pre-requisitos

Este programa fue diseñado para ser utilizado con la siguientes especificaciones:
* [Amazon Corretto 17.0.7](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)
* [Maven 3.8.7](https://maven.apache.org/docs/3.8.7/release-notes.html)

Si bien la utilización de otras versiones pueden permitir la ejecución del programa, no se puede asegurar su correcto funcionamiento.

Además, el servicio de Python debe estar inicializado y ejecutándose correctamente.

## Instalación

Use la librería de Maven para asegurarse para instalar las librerías necesarias mediante el comando a continuación.

```bash
mvn clean install
```

Esto generará un archivo jar (con todas las dependencias) que se ejecutará una vez creado.

## Ejecución

Ejecute el método principal en Main.java mediante el siguiente comando

```bash
mvn spring-boot:run
```
Alternativamente, puede ejecutar el método principal en Main.java en su IDE elegido, por ejemplo: IntelliJ.

## Uso

* Si bien el sistema para ser ejecutado a través de un CRON JOB esta implementado y funcionado. Se decidió desactivar temporalmente con el fin de realizar el testeo de una forma más controlada.
* Adicionalmente, se puede ejecutar los dos servicios REST que han sido creados.
  * `/batch/news/collectSources` : para iniciar el proceso de búsqueda, procesamiento y carga de noticias en la base de datos.
  * `/batch/news/reduceInterest` : para reducir el interés de todos los usuarios en todas las secciones.

## Cloud

El presente servicio se encuentra desplegado en un servicio cloud de Google (Google Cloud), el cual se puede acceder a través del siguiente link.

`https://batch-app-dot-unchainednews.rj.r.appspot.com/`


## Autores
Caneva, Gianfranco

## Contacto

Por cualquier sugerencia, problema o inconveniente comunicarse con `gfocaneva@gmail.com`