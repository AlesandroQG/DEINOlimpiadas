package com.alesandro.proyecto1olimpiadas.model;

import java.util.Objects;

/**
 * Clase Olimpiada
 */
public class Olimpiada {
    private int id_olimpiada;
    private String nombre;
    private int anio;
    private SeasonCategory temporada; // Enum
    private String ciudad;

    public Olimpiada(int id_olimpiada, String nombre, int anio, String temporada, String ciudad) {
        this.id_olimpiada = id_olimpiada;
        this.nombre = nombre;
        this.anio = anio;
        this.temporada = getSeasonCategory(temporada);
        this.ciudad = ciudad;
    }

    public Olimpiada() {}

    public enum SeasonCategory {
        WINTER, SUMMER;
    }

    public SeasonCategory getSeasonCategory(String season) {
        if (season.equals("Winter")) {
            return SeasonCategory.WINTER;
        } else if (season.equals("Summer")) {
            return SeasonCategory.SUMMER;
        }
        return null;
    }

    public int getId_olimpiada() {
        return id_olimpiada;
    }

    public void setId_olimpiada(int id_olimpiada) {
        this.id_olimpiada = id_olimpiada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public SeasonCategory getTemporada() {
        return temporada;
    }

    public void setTemporada(SeasonCategory temporada) {
        this.temporada = temporada;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Olimpiada olimpiada = (Olimpiada) o;
        return id_olimpiada == olimpiada.id_olimpiada;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_olimpiada);
    }

}
