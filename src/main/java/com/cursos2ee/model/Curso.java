package com.cursos2ee.model;

import java.util.Objects;

/**
 * A curso define description data for a course
 * Created by usuario on 14/05/2017.
 */
public class Curso {

    private String titulo;
    private int horas;
    private Nivel nivel;
    private boolean activo;

    public Curso(String titulo, int horas, Nivel nivel, boolean activo) {
        this.titulo = titulo;
        this.horas = horas;
        this.nivel = nivel;
        this.activo = activo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        return horas == curso.horas &&
                Objects.equals(titulo, curso.titulo) &&
                nivel == curso.nivel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, horas, nivel);
    }

    @Override
    public String toString() {
        return "Curso{" +
                "titulo='" + titulo + '\'' +
                ", horas=" + horas +
                ", nivel=" + nivel +
                ", activo=" + activo +
                '}';
    }
}
