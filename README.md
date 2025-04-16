# INTRODUCCIÓN

Este respositorio contiene ejercicios del curso [link](https://github.com/blackcub3s/camundaDiagrams). Son diagramas hechos con camunda modeller, con la notación o lenguaje BPMN (Business Process Model and Notation).

# Introducción a BPMN

El ejercicio siguiente [link](https://academy.camunda.com/c7-platform-java/843268) pide hacer un sistema de QA para controlar qué hacen los empleados: cada vez que un empleado necesita publicar algo lo evalúa él.

La versión hecha inicialmente asume que la evaluación la hace un modelo de NLP y, que si no la peude hacer, la hará un empleado de RRHH (en definitiva, una interpretación libre del enunciado):

 ![link](/img/diagramaTwitter.png).

Una versión más sencilla será esta, que es la que se recomienda en el curso:

![no cargo](/img/imatgeTwitterSenzill.png)

El *sequence flow*, de arriba ("twit aprovat"), va a ser un camino del flujo si y solo si la expresión ${*twitAprovat*} evalua a cierto. Si twitAprovat fuese *true* el flujo del programa iría a la siguiente tarea de aprobar el twit del usuario ("Publicar twit"):

![alt text](/img/imatgeFlowAPROVAT.png)

En caso contrario, si twitAprovat fuese *false* o fuese otra (por ejemplo, variable nula) deberíamos asegurarnos que el flujo no se para y que va hacia otro camino: el *default flow* marcado con la rallita nos permite hacer eso:

![alt text](/img/imageFlowREBUTJAT.png)

Definimos el *default flow* por seguridad, ya que si la variable **twitAprovat** no estuviera definida e ingenuamente hubiéramos definido en el *sequence flow* que antes era por defecto, una expresión lógica tal como \${*!twitAprovat*} el flujo no iría por ninguna parte. Aunque  \${*twitAprovat*} y \${*!twitAprovat*} sean expresiones plenamente complementarias, no están cubriendo los posibles escenarios del diagrama BPMN: por ejemplo, que no esté definida la variable. Es buena práctica definir, pues, un default flow como hicimos antes, siendo lo siguiente una práctica no muy óptima según lo explicado:

![alt text](/img/imatgeFlowREBUTJAT_malaPractica.png)


Tomando el diagrama de la penúltima imagen (el que tiene el flujo por defecto) podemos subirlo al camunda remoto en la dirección */engine-rest/* desde el propio camunda modeler:

![alt text](/img/diagramaPujatTwiter.png)

Si lo hacemos, tendremos ya el diagrama subido al cockpit de camunda, es decir, en la nube (es importante notar que en el diagrama de camunda modeler, en local, definamos un ID de diagrama: este en la nube será la *definiton-key*, que vamos a usar a continuación para iniciar el proceso mediante una solicitud POST):

![alt text](/img/diagramaAcamundaA.png)


Como vemos en la documentación de camunda 7.5 [link](https://docs.camunda.org/manual/7.5/reference/rest/process-definition/post-start-process-instance/), para conseguir pasar la variable *twitAprovat* a true en la primera tarea de usuario (Revisar Twit) el usuario deberá hacer una acción manual. Esto se podría traducir en que nuestro cliente, el navegador (con javascript, con typescript -Angular- o el lenguaje que sea), podría recibir un click de una persona que dijera que la revisión es favorable. Al hacer ese click el cliente mandaría una solicitud POST al endpoint de camunda que permite iniciar una instancia de proceso:

En este caso lo que haríamos sería mandar una solicitud a la API de camunda con POSTMAN (que nos hará de cliente) hacia:

 */process-definition/key/**revisio-twit-curs**/start*

Así:

![alt text](/img/postmanCallA.png)

Si la llamada es exitosa y obtenemos el 200 OK, como fue nuestro caso, 
lo que hará Camunda será iniciar la instancia de proceso y nos situará en el primer paso:

![alt text](/img/camundaTokenArevisarTwit.png)

Esta llamada anterior se podría hacer, por ejemplo, cuando el usuario entrase a la página front-end de inicio de la tramitación de revisar twit, pero antes de clicar si acepta el+- twit o no.

Luego una vez el usuario clique si aprueba o no aprueba el twit, mandaríamos otra solicitud POST para completar la tarea a este otro endpoint (pasando también por el body, la variable del usuario en cuestion (*twitAprovat*) que luego aprovechará la gateway xor para redirigir el flujo justo después (ver documentación complete [link](https://docs.camunda.org/manual/7.5/reference/rest/task/post-complete/)-)) hacia:



*/process-definition/task/**taskId**/complete*

Para hacerla vamos a necesitar el *TaskId*, que vamos a obtener de User Tasks:

![alt text](/img/detallUserTasks.png)

Con este identificador, y pasando la variable que necesitamos en el body, podemos hacer ya la llamada POST:

![alt text](/img/callPostmanCompleteTwit.png)

Con esta llamada, y si obtenemos el código 200 de éxito, el diagrama habrá avanzado al caso en que twitAprovat sea true: es decir, hacia otra tarea de usuario denominada "Publicar twit", en donde el proceso se mantendrá a la espera nuevamente a la siguiente tarea de usuario. Como podemos ver, la variable introducida ahora está dentro de la instancia de proceso particular de Camunda y podrá ser reutilizada por los microservicios que se conecten en nodos posteriores del flujo del diagrama.

![alt text](/img/definitiuTwit.png)

Con este enfoque podemos delegar la lógica de estar esperando una respuesta de un usuario a Camunda y no tener que gestionarla nosotros manualmente, por ejemplo.





