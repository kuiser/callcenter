# Callcenter

## Extras 
**Dar alguna solución sobre qué pasa con una llamada cuando no
hay ningún empleado libre:**  
En este caso lo que implementé fue que cuando el servicio que provee los operadores
no tiene ninguno para asignar espere un segundo (una mejora seria poder parametrizar esto) 
por alguno disponible y si no lance un excepción que es tomada por el dispacher y vuelve a encolar 
la llamada ( quedo mejorar esto seteando una cantidad de reintentos por llamada)

**Dar alguna solución sobre qué pasa con una llamada cuando entran más de 10 llamadas concurrentes:**   
Para resolver esto lo que se hizo asignarle al ThreadPoolExecutor una workQueue donde se encolan los 
workers que no puede ser procesados aún. Si bien se utiliza una implementación de lista ( infinita en un principio )
tambien se implemento RejectedExecutionHandler que en el caso que la lista de workers se llene maneja la llamadas que no 
fueron procesadas y no pueden ser encoladas (aca también queda mejorar la cantidad de reintentos)

## Consideraciones
Se vera que se utiliza Spring Boot. La idea es poder ampliar el projecto agregando REST endpoint
para agregar operador y manejar las llamadas.