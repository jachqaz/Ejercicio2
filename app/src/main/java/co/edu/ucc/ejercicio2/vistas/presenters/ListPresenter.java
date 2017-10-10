package co.edu.ucc.ejercicio2.vistas.presenters;

import java.util.List;

import co.edu.ucc.ejercicio2.dominio.ILTarea;
import co.edu.ucc.ejercicio2.dominio.LTarea;
import co.edu.ucc.ejercicio2.modelo.Tarea;
import co.edu.ucc.ejercicio2.vistas.IListView;

/**
 * Created by jach_ on 12/09/2017.
 */

public class ListPresenter implements IListPresenter {
    private IListView view;
    private ILTarea ltarea;

    public ListPresenter(IListView view) {
        this.view=view;
        ltarea=new LTarea();
    }

    @Override
    public void addTarea(String descTarea, String Fecha) {
        Tarea objTarea=new Tarea();
        objTarea.setNombre(descTarea);
        objTarea.setFecha(Fecha);
        objTarea.setRealizado(false);
        ltarea.addTarea(objTarea);
        view.refrescarListaTareas(ltarea.getTareas());
    }

    @Override
    public List<Tarea> obtenerTareas() {
    return ltarea.getTareas();
    }

    @Override
    public void itemCambioEstado(int posicion, boolean realizado) {
        Tarea tarea=ltarea.obtenerXID(posicion+1);
        tarea.setRealizado(realizado);
        ltarea.actualizar(tarea);
        view.refrescarTarea(tarea,posicion);
    }
}
