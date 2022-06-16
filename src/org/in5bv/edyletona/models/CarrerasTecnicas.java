
package org.in5bv.edyletona.models;

/**
 *
 * @author HP
 */

public class CarrerasTecnicas {
    
    private String codigoTecnico;
    private String carrera ;
    private String grado;
    private String seccion;
    private String jornada;

    public CarrerasTecnicas() {
    }

    public CarrerasTecnicas(String codigoTecnico) {
        this.codigoTecnico = codigoTecnico;
    }

    public CarrerasTecnicas(String codigoTecnico, String carrera, String grado, String seccion, String jornada) {
        this.codigoTecnico = codigoTecnico;
        this.carrera = carrera;
        this.grado = grado;
        this.seccion = seccion;
        this.jornada = jornada;
    }

    public String getCodigoTecnico() {
        return codigoTecnico;
    }

    public void setCodigoTecnico(String codigoTecnico) {
        this.codigoTecnico = codigoTecnico;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    @Override
    public String toString() {
        return codigoTecnico + " | " + carrera + " | " + grado + " | " + seccion + " | " + jornada;
    }  
}
