package co.edu.ucc.ejercicio2.vistas.presenters;

import java.util.List;

import co.edu.ucc.ejercicio2.modelo.Tarea;

/**
 * Created by jach_ on 12/09/2017.
 */

public interface IListPresenter {
    void addTarea(String descTarea, String Fecha);
    List<Tarea> obtenerTareas();
    void itemCambioEstado(int posicion, boolean realizado);
}
