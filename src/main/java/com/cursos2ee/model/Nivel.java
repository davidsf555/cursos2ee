package com.cursos2ee.model;

/**
 * Levels and codes for cursos
 * Created by usuario on 14/05/2017.
 */
public enum Nivel {
    basico(1,"Básico"),intermedio(2,"Intermedio"),avanzado(3,"Avanzado");

    private int codigo;
    private String descripcion;

    private Nivel(int codigo,String descripcion){
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static Nivel getByCode(int codigo){
        for(Nivel nivel:values()){
            if(nivel.getCodigo() == codigo) return nivel;
        }
        throw new RuntimeException("Bad Nivel:codigo value:"+codigo);
    }
}
