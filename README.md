# Proyecto 1 - Olimpiadas
## DM2 DEIN 2024-2025
### Alesandro Quirós Gobbato

Esta es una aplicación hecha con JavaFX para la gestión de olimpiadas.

#### Estructura

La estructura del proyecto es la siguiente:
- `src > main`:
  - `java > com.alesandro.olimpiadas`:
    - `OlimpiadasApplicacion.java`: Clase que lanza la aplicación
    - `controller`:
      - `DeportesController.java`: Clase que controla los eventos de la ventana deportes
      - `DeportistaController.java`: Clase que controla los eventos de la ventana deportista
      - `EquipoController.java`: Clase que controla los eventos de la ventana equipos
      - `EventoController.java`: Clase que controla los eventos de la ventana evento
      - `MainController.java`: Clase que controla los eventos de la ventana principal de la aplicación
      - `OlimpiadasController.java`: Clase que controla los eventos de la ventana olimpiadas
      - `ParticipacionController.java`: Clase que controla los eventos de la ventana participación
    - `dao`:
      - `DaoDeporte.java`: Clase que hace las operaciones con la base de datos del modelo Deporte
      - `DaoDeportista.java`: Clase que hace las operaciones con la base de datos del modelo Deportista
      - `DaoEquipo.java`: Clase que hace las operaciones con la base de datos del modelo Equipo
      - `DaoEvento.java`: Clase que hace las operaciones con la base de datos del modelo Evento
      - `DaoOlimpiada.java`: Clase que hace las operaciones con la base de datos del modelo Olimpiada
      - `DaoParticipacion.java`: Clase que hace las operaciones con la base de datos del modelo Participación
    - `db`:
      - `DBConnect.java`: Clase que se conecta a la base de datos
      - `DBException.java`: Excepción personalizada para los posibles errores con la base de datos
    - `language`:
      - `LanguageManager.java`: Clase que maneja los idiomas de la aplicación
      - `LanguageSwitcher.java`: Clase que permite cambiar entre los idiomas de la aplicación
    - `model`:
      - `Deporte.java`: Clase que define el objeto Deporte
      - `Deportista.java`: Clase que define el objeto Deportista
      - `Equipo.java`: Clase que define el objeto Equipo
      - `Evento.java`: Clase que define el objeto Evento
      - `Olimpiada.java`: Clase que define el objeto Olimpiada
      - `Participacion.java`: Clase que define el objeto Participación
  - `resources`:
    - `fonts`: Carpeta que contiene las fuentes de la aplicación
    - `fxml`:
      - `Deportes.fxml`: Ventana para edición de deportes
      - `Deportista.fxml`: Ventana para edición de deportistas
      - `Equipos.fxml`: Ventana para edición de equipos
      - `Evento.fxml`: Ventana para edición de eventos
      - `Main.fxml`: Ventana principal de la aplicación
      - `Olimpiadas.fxml`: Ventana para edición de olimpiadas
      - `Participacion.fxml`: Ventana para edición de participaciones
    - `languages`: Carpeta que contiene los idiomas de la aplicación
    - `images`: Carpeta que contiene las imágenes de la aplicación
    - `sql`:
      - `olimpiadas.sql`: Fichero para la creación de la base de datos
    - `style`: Carpeta que contiene los estilos de la aplicación
