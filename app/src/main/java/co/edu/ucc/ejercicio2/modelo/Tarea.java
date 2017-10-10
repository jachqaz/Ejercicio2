package co.edu.ucc.ejercicio2.modelo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jach_ on 5/09/2017.
 */
@Entity(tableName = "tareas")
public class Tarea {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String nombre;
    @ColumnInfo
    private String fecha;
    @ColumnInfo
    private boolean realizado;

    public Tarea() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}