package com.alesandro.proyecto1olimpiadas.model;

import java.util.Objects;

/**
 * Clase Evento
 */
public class Evento {
    private int id_evento;
    private String nombre;
    private Olimpiada olimpiada;
    private Deporte deporte;

    public Evento(int id_evento, String nombre, Olimpiada olimpiada, Deporte deporte) {
        this.id_evento = id_evento;
        this.nombre = nombre;
        this.olimpiada = olimpiada;
        this.deporte = deporte;
    }

    public Evento() {}

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Olimpiada getOlimpiada() {
        return olimpiada;
    }

    public void setOlimpiada(Olimpiada olimpiada) {
        this.olimpiada = olimpiada;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return id_evento == evento.id_evento;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_evento);
    }

}
