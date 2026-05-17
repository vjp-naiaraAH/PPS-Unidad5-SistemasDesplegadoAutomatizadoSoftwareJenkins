# Actividad - Servidor de automatización IC/DC Jenkins naiara

## CREAR PRIMER PROYECTO: CLONAR EL REPOSITORIO
Una vez ya tengo instalada la herramienta ***Jenkins*** y le añado la clave de **Github** tal y como dejamos todo en clase lo siguiente que hay que hacer es entrar en la propia herramienta de ***Jenkins*** y  crear el primer proyecto clonando el repositorio.
Para ello
1. Vuelvo a la página principal y pulso sobre *New Item*
![new item](/images/img1.png)
2. A continuación le doy un nombre, en mi caso *storeApp-naiara*, selecciono un tipo que será el indicado en la tarea, es decir *Pipeline* y acepto.
![seleccionar pipeline](/images/img2.png)
3. Se abre una nueva pestaña en la que pongo en la apartado de *script* el siguiente contenido:
```jenkinsfile
pipeline {
    agent any  // Indica que Jenkins puede ejecutar este pipeline en cualquier nodo disponible

    stages {   // Bloque que agrupa las distintas fases del proceso (pipeline)

        stage('Clonar repositorio') {  // Primera fase: descargar el código fuente
            steps {  // Conjunto de acciones que se ejecutan dentro de este stage

                // Clona el repositorio Git especificado
                // - branch: rama que se va a descargar
                // - url: dirección del repositorio
                git branch: 'main',
                    credentialsId: 'TuIDToken', // Tu ID definido en Jenkins
                    url: 'https://github.com/TU_USUARIO/store-app.git'
            }
        }

        stage('Verificar contenido') {  // Segunda fase: comprobar que todo se ha clonado bien
            steps {

                // Muestra un mensaje en consola
                sh 'echo "Repositorio clonado correctamente"'

                // Muestra el directorio actual de trabajo
                sh 'pwd'

                // Lista los archivos del directorio con detalle
                sh 'ls -la'

                // Muestra las ramas disponibles en el repositorio
                sh 'git branch -a'
            }
        }
    }
}
```
Lo guardo en el botón inferior que pone **Save**
![imagen del script](/images/img3.png)
4. Ahora lo ejecuto pulsando el botón **Build now** como se ve en la captura
En la parte inferior izquierda pone el resultado final de la ejecución, en mi caso es **correcta**.
![lo contruyo](/images/img4.png)

---

## ANÁLISIS DE LA EJECUCIÓN
### INFORMACIÓN DE TAREAS
Veo el *console output* ya que es la información en el terminal que producen la ejecución del build. Puedo copiarlo y descargarlo si es necesario. 
![Visualizacion de la consola](/images/img5.png)

### DEPURAR ERRORES EN BUILD
Voy a modificar el Pipeline para ver cómo se tratan y depuran los errores.
Lo que hago a continuación es cambiar el script por el que pone en la tarea, que es el siguiente:
```jenkinsfile
pipeline {
    agent any
    stages {
        stage('Clonar repositorio') {
            steps {
                git branch: 'main',
                    credentialsId: 'TuIDToken', // Tu ID definido en Jenkins
                    url: 'https://github.com/PPSvjpOrganizacion/StoreAPP-TuNombre'
            }
        }
        stage('Verificar contenido') {
            steps {
                sh 'echo "Repositorio clonado correctamente"'
                sh 'pwd'
                sh 'ls -la'
                sh 'git branch -a'
            }
        }
        stage('Build con Maven') {
            steps {
                withEnv(["JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64","PATH+JAVA=/usr/lib/jvm/java-11-openjdk-amd64/bin"]) {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
    }
}
```
Cuando copie esto lo que hago es borrar uno de los **}** (corchetes) finales para simular un **error**.
![pipeline borar un corchete](/images/img6.png)
Ahora al ejecutarlo dará un **error** como se ve en la siguiente captura
![](/images/img7.png)
Clic sobre el *build* que acabo de hacer -> Console Output y ahí da que tengo un **error** 
![volver a darle a contruir](/images/img8.png)
Vuelvo a añadir al script el } que eliminé al final, y vuelvo a lanzarlo, dará otro fallo que es el que se ve a continuación:
![da error](/images/img9.png)

Una vez corregido y puesto bien el script ya dará que está bien
**Cambios realizados respecto al script original:**
- Añadí la etapa **Build con Maven** para compilar el proyecto.
- Dentro de esa etapa se entró en la carpeta *store-app* con *dir('store-app')*.
- Configuro **Java 11** con *JAVA_HOME* y *PATH*.
- Cambio la verificación final: en vez de *git branch -a*, uso *ls -la store-app* para comprobar que existe la carpeta del proyecto.
- Sustituyo *credentialsId: 'TuIDToken'* por mi ID real de credenciales en Jenkins.
Una vez corregido y puesto bien el script ya dará que está bien
```java
pipeline {
    agent any

    stages {
        stage('Clonar repositorio') {
            steps {
                git(
                    branch: 'main',
                    credentialsId: 'b80b0d16-2974-4c29-8ff5-6434d9c184f8',
                    url: 'https://github.com/vjp-naiaraAH/PPS-Unidad5-SistemasDesplegadoAutomatizadoSoftwareJenkins.git'
                )
            }
        }

        stage('Verificar contenido') {
            steps {
                sh 'echo "Repositorio clonado correctamente"'
                sh 'pwd'
                sh 'ls -la'
                sh 'ls -la store-app'
            }
        }

        stage('Build con Maven') {
            steps {
                dir('store-app') {
                    withEnv([
                        "JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64",
                        "PATH+JAVA=/usr/lib/jvm/java-11-openjdk-amd64/bin"
                    ]) {
                        sh 'mvn clean package -DskipTests'
                    }
                }
            }
        }
    }
}    EL SIN CORREGIR pipeline {
    agent any

    stages {
        stage('Clonar repositorio') {
            steps {
                git(
                    branch: 'main',
                    credentialsId: 'b80b0d16-2974-4c29-8ff5-6434d9c184f8',
                    url: 'https://github.com/vjp-naiaraAH/PPS-Unidad5-SistemasDesplegadoAutomatizadoSoftwareJenkins.git'
                )
            }
        }

        stage('Verificar contenido') {
            steps {
                sh 'echo "Repositorio clonado correctamente"'
                sh 'pwd'
                sh 'ls -la'
                sh 'ls -la store-app'
            }
        }

        stage('Build con Maven') {
            steps {
                dir('store-app') {
                    withEnv([
                        "JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64",
                        "PATH+JAVA=/usr/lib/jvm/java-11-openjdk-amd64/bin"
                    ]) {
                        sh 'mvn clean package -DskipTests'
                    }
                }
            }
        }
    }
}
```
Una vez compilado el proyecto se tiene que haber generado nuestra aplicación *.jar* en **java**.
![ahora ya funciona](/images/img10.png)
Cuando finaliza vemos que ha finalizado también con **error**. Para ver el **error** nos vamos a *Pipeline Overview* y vemos como todos los stages 1. Clonar repositorio, 2. verificar contenido y 3. compilar con maven se han realizado ***correctamente***.
Puedo ver la información generada durante la ejecución de cualquiera de las tres etapas:
![etapas o pasos](/images/img11.png)
Voy a inspeccionar los archivos en nuestro área de trabajo: StoreAPP-PPSvjp -> nº Build -> Workspace:
![Workspace](/images/img12.png)
Pulso en el enlace del directorio de trabajo.
![directorios](/images/img13.png)

Puedo observar cómo se muestran todos los archivos de dicho directorio y puedo moverme entre esos directorios y deescargar los archivos que necesite, incluso descargar el directorio comprimido .zip.