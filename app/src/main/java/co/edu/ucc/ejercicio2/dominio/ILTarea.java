package co.edu.ucc.ejercicio2.dominio;

import java.util.List;

import co.edu.ucc.ejercicio2.modelo.Tarea;

/**
 * Created by jach_ on 12/09/2017.
 */

public interface ILTarea {
    void addTarea(Tarea tarea);
    List<Tarea> getTareas();
    void actualizar(Tarea... tareas);
    Tarea obtenerXID(int id);
}
