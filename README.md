# EjercicioAlMundo<br>
Consigna<br>

Existe un call center donde hay 3 tipos de empleados: operador, supervisor y director. El proceso de la atención de una llamada telefónica en primera instancia debe ser atendida por un operador, si no hay ninguno libre debe ser atendida por un supervisor, y de no haber tampoco supervisores libres debe ser atendida por un director.
<br>
 

Requerimientos<br>
Diseñar el modelado de clases y diagramas UML necesarios para documentar y comunicar el diseño.<br>
<br>Debe existir una clase Dispatcher encargada de manejar las llamadas, y debe contener el método dispatchCall para que las asigne a los empleados disponibles.
<br>La clase Dispatcher debe tener la capacidad de poder procesar 10 llamadas al mismo tiempo (de modo concurrente).
<br>Cada llamada puede durar un tiempo aleatorio entre 5 y 10 segundos.
<br>Debe tener un test unitario donde lleguen 10 llamadas.<br>
<br><br>

Extras/Plus<br>
<br>Dar alguna solución sobre qué pasa con una llamada cuando no hay ningún empleado libre.<br>
<br>Dar alguna solución sobre qué pasa con una llamada cuando entran más de 10 llamadas concurrentes.
<br>Agregar los tests unitarios que se crean convenientes.
<br>Agregar documentación de código
<br> 
<br>
Tener en Cuenta<br>
El proyecto debe ser creado con Maven.<br>
De ser necesario, anexar un documento con la explicación del cómo y porqué resolvió los puntos extras, o comentarlo en las clases donde se encuentran sus tests unitarios respectivos.


<br><H1> Para resolver el problema de mas llamadas que empleados, se resolvio una cola de pendientes, donde se van encolando todas las llamadas que no pudieron tener atencion y luego cuando se desocupan los empleados las van atendiendo.
Se agrego test para dicho caso y se documento cada uno de los test con la explicacion de los mismos.

<br>Pendiente: Me quedo pendiente armar el UML por cuestiones de falta de tiempo en mi trabajo.

<br>Saludos
