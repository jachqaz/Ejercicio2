package co.edu.ucc.ejercicio2.dominio;

import java.util.List;

import co.edu.ucc.ejercicio2.modelo.Tarea;
import co.edu.ucc.ejercicio2.repositorio.AppDB;

/**
 * Created by jach_ on 5/09/2017.
 */

public class LTarea implements ILTarea {
    private AppDB database;

    public LTarea() {
        database = AppDB.getInstancia();
    }

    @Override
    public void addTarea(Tarea tarea) {
        database.getTareaDAO().insert(tarea);
    }

    @Override
    public List<Tarea> getTareas() {
        return database.getTareaDAO().obtenerTodos();
    }

    @Override
    public void actualizar(Tarea... tareas) {
        database.getTareaDAO().update(tareas);
    }

    @Override
    public Tarea obtenerXID(int id) {
        return database.getTareaDAO().obtenerXID(id);
    }
}
