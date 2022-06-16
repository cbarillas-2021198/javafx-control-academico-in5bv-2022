
package org.in5bv.edyletona.models;

import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Edy Leonel Letona Argueta
 * 
 */
public class AsignacionesAlumnos {
    private IntegerProperty id;
    private StringProperty alumnoId;
    private IntegerProperty cursoId;
    private ObjectProperty<LocalDateTime>fechaAsignacion;
    
   public AsignacionesAlumnos() {
        this.id = new SimpleIntegerProperty();
        this.alumnoId = new SimpleStringProperty();
        this.cursoId = new SimpleIntegerProperty();
        this.fechaAsignacion = new SimpleObjectProperty<>();
    }

    public AsignacionesAlumnos(String alumnoId, int cursoId, LocalDateTime fechaAsignacion) {
        this.alumnoId = new SimpleStringProperty(alumnoId);
        this.cursoId = new SimpleIntegerProperty(cursoId);
        this.fechaAsignacion = new SimpleObjectProperty<>(fechaAsignacion);
    }
    
    public AsignacionesAlumnos(int id, String alumnoId, int cursoId, LocalDateTime fechaAsignacion) {
        this.id = new SimpleIntegerProperty(id);
        this.alumnoId = new SimpleStringProperty(alumnoId);
        this.cursoId = new SimpleIntegerProperty(cursoId);
        this.fechaAsignacion = new SimpleObjectProperty<>(fechaAsignacion);
    }

    public int getId(){
        return id.get();   
    }
    
    public void setId(int id){
        this.id.set(id);
    }
    
    public IntegerProperty id(){
        return id;
    }
        
    public String getAlumnoId(){
            return alumnoId.get();
    }
    
    public void setAlumnoId(String alumnoId ){
        this.alumnoId.set(alumnoId);
    
    }
    public StringProperty alumnoId(){
        return alumnoId;
    }

    public  ObjectProperty<LocalDateTime> fechaAsignacion(){
        return fechaAsignacion;
    }
    
    public void setFechaAsignacion(LocalDateTime fechaAsignacion ){
        this.fechaAsignacion.set(fechaAsignacion);
    }
    
    public LocalDateTime getFechaAsignacion(){
            return fechaAsignacion.get();
    }

    public void setCursoId(Integer cursoId ){
            this.cursoId.set(cursoId); 
    }
    
    public int getCursoId(){
            return cursoId.get();
    }
        
    public  IntegerProperty cursoId(){
            return cursoId;
    }

      @Override
    public String toString() {
        return id + " | " + fechaAsignacion;
    }
}

