# CUI
App en Android - Ciudad Universitaria Inteligente (UNL)

Aplicación para Android 4.4 o superior
SDK 24, Android Studio - Gradle

Resumen de clases

-----MainActivity-----
Se ejecuta al iniciar la aplicación. Carga el Navigation Drawer y el menu, e instancio los objetos de las otras clases que utilizo.
Cargo el grafo que está en la clase ArmaCamino.
Los objetos IntentIntegrator e IntentResult manejan todo lo relativo a la lectura de codigos QR para obtener ubicación dentro de un edificio.
FragmentManager gestiona los fragments, lo utilizo para cambiar los fragments en el contenedor principal

-----ArmaCamino-----
Esta clase tiene el grafo, que está compuesto de Puntos. Cada punto tiene los datos de un nodo y la lista de puntos que están conectados a él. Tambíen está el algoritmo de busqueda que recorre el grafo y genera el recorrido o la lista de nodos de interes

-----Punto-----
Representa un nodo del grafo, contiene Lat, Long, Edificio, Nombre, Piso, y una imagen de ese lugar (si la consigo).

-----MyLocationListener-----
Clase propia que extiende de LocationListener para obtener los datos del sensor GPS del telefono. Obtengo mi posición y atiendo a los cambios de la misma

-----Busqueda-----
Fragment que contiene la logica de la pantalla de busqueda
Crea los spinners para gestionar la busqueda, el usuario selecciona el campo a buscar (edificios, aulas, baños, etc). Si fuera un aula se elige en que edificio está y cual de todas

-----ultimasBusquedas-----
Fragment para gestionar la lista de las ultimas busquedas que se realizaron para tenerlas a mano e ingresar a ellas mas rápido. Las mismas están persistidas en una base de datos en el telefono. BaseDatos es la clase que gestiona la DB, utilizo SQLite de Android


-----MapsFragment-----
Este fragmento gestiona todo lo relativo a lo mapas. Una lista de nodos de la clase ArmaCamino y con ella trabajo
En el identifico en que piso estoy estoy parado (PB, P1, P2....), la cantidad de pisos que hay hasta mi objetivo, un vector de polilinas que son las que me marcan el camino por los diferentes pisos, y un vector de vectores de OverLays (para tener los overlays por piso, siendo que la polilinea puede pasar por 1 o mas edificios y debo mostrar los planos de todos aquellos por donde pase, segun el piso que estoy viendo). Mismo caso para los marcadores que muestro sobre el mapa. Tambien gestiona el SensorManager para rotar el mapa segun se mueve uno

