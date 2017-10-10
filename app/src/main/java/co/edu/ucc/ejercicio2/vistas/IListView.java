package co.edu.ucc.ejercicio2.vistas;

import android.view.View;

import java.util.List;

import co.edu.ucc.ejercicio2.modelo.Tarea;

/**
 * Created by jach_ on 12/09/2017.
 */

public interface IListView {
    void clickAddTarea(View view);
    void refrescarListaTareas(List<Tarea> lstTarea);
    void refrescarTarea(Tarea tarea,int posicion);
}
