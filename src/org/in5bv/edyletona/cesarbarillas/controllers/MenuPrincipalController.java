
package org.in5bv.edyletona.cesarbarillas.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.in5bv.edyletona.system.Principal;


/**
 *
 * @author informatica
 */


public class MenuPrincipalController implements Initializable {

    private Principal escenarioPrincipal;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
    

    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscnerioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    @FXML
    public void clicAlumnos (){
       escenarioPrincipal.mostrarEscenaAlumnos();
    }
    
    @FXML
    public void clicCarrerasTecnicas (){
       escenarioPrincipal.mostrarEscenaCarrerasTecnicas();
    }
    
    @FXML
     public void clicSalones(){
         escenarioPrincipal.mostrarEscenaSalones();
    }
     
    @FXML
    private void clicInstructores() {
        escenarioPrincipal.mostrarEscenaInstructores();
    }
     
    @FXML
    private void clicHorarios() {
        escenarioPrincipal.mostrarEscenaHorarios();
    }
    
    @FXML
    private void clicCursos() {
        escenarioPrincipal.mostrarEscenaCursos();
    }

    @FXML
    private void clicAsignacionesAlumnos() {
        escenarioPrincipal.mostrarEscenaAsignacionesAlumnos();
    }
    
    public void clicRegresar (){
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
}
