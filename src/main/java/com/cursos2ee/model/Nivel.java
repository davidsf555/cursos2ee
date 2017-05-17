package com.cursos2ee.model;

/**
 * Levels and codes for cursos
 * Created by usuario on 14/05/2017.
 */
public enum Nivel {
    basico(1,"Básico"),intermedio(2,"Intermedio"),avanzado(3,"Avanzado");

    private int codigo;
    private String descripcion;

    private Nivel(final int codigo,final String descripcion){
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static Nivel getByCode(final int codigo){
        for(final Nivel nivel:values()){
            if(nivel.getCodigo() == codigo) {
                return nivel;
            }
        }
        throw new RuntimeException("Bad Nivel:codigo value:"+codigo);
    }
}
