package com.alesandro.proyecto1olimpiadas.model;

import java.util.Objects;

/**
 * Clase Equipo
 */
public class Equipo {
    private int id_equipo;
    private String nombre;
    private String iniciales;

    public Equipo(int id_equipo, String nombre, String iniciales) {
        this.id_equipo = id_equipo;
        this.nombre = nombre;
        this.iniciales = iniciales;
    }

    public Equipo() {}

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIniciales() {
        return iniciales;
    }

    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        return id_equipo == equipo.id_equipo;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_equipo);
    }

}
