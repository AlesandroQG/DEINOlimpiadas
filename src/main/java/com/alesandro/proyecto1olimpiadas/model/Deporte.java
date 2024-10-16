package com.alesandro.proyecto1olimpiadas.model;

import java.util.Objects;

/**
 * Clase Deporte
 */
public class Deporte {
    private int id_deporte;
    private String nombre;

    public Deporte(int id_deporte, String nombre) {
        this.id_deporte = id_deporte;
        this.nombre = nombre;
    }

    public Deporte() {}

    public int getId_deporte() {
        return id_deporte;
    }

    public void setId_deporte(int id_deporte) {
        this.id_deporte = id_deporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deporte deporte = (Deporte) o;
        return id_deporte == deporte.id_deporte;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_deporte);
    }

}
