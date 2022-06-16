package org.in5bv.edyletona.system;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application; 
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import org.in5bv.edyletona.cesarbarillas.controllers.AlumnosController;
import org.in5bv.edyletona.cesarbarillas.controllers.CarrerasTecnicasController;
import org.in5bv.edyletona.cesarbarillas.controllers.MenuPrincipalController;
import org.in5bv.edyletona.cesarbarillas.controllers.SalonesController;

import java.sql.Statement;
import java.sql.PreparedStatement;
import org.in5bv.edyletona.cesarbarillas.controllers.AsignacionesAlumnosController;
import org.in5bv.edyletona.cesarbarillas.controllers.CursosController;
import org.in5bv.edyletona.cesarbarillas.controllers.HorariosController;
import org.in5bv.edyletona.cesarbarillas.controllers.InstructoresController;

/**
 *
 * @author Edy Leonel Letona Argueta 
 * @date 5/04/2022
 * @time 16:38:15
 * 
 */

public class Principal extends Application {
    
    
    private Stage escenarioPrincipal;
    private final String PAQUETE_IMAGE = "org/in5bv/edyletona/resources/images";
    private final String PAQUETE_VIEW = "../views/";
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.escenarioPrincipal = primaryStage;
        this.escenarioPrincipal.setTitle("Control Academico Kinal");
        this.escenarioPrincipal.getIcons().add(new Image("org/in5bv/edyletona/resources/images/robot.png"));
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.centerOnScreen();
        mostrarEscenaPrincipal()
       ;
    }

    public void mostrarEscenaPrincipal(){
        try {
           MenuPrincipalController menuController = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 900, 516);
           menuController.setEscnerioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe produjo un error al intentar mostrar la escena principal");
            ex.printStackTrace();
        }
    }    
    
    public void mostrarEscenaAlumnos(){
        try {
            AlumnosController alumnosController = (AlumnosController)cambiarEscena("AlumnosView.fxml", 900, 516);
            alumnosController.setEscnerioPrincipal(this);
        } catch(Exception ex){
             System.err.println("\nSe preodujo un error al intentar mostrar la vista de alumnos");
             ex.printStackTrace();
         }
    }     
    
    public void mostrarEscenaCarrerasTecnicas(){
        try {
            CarrerasTecnicasController carrerastecnicasController = (CarrerasTecnicasController)cambiarEscena("CarrerasTecnicasView.fxml", 900, 516);
            carrerastecnicasController.setEscnerioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe produjo un error al intentar mostrar la escena carreras");
            ex.printStackTrace();
        }    
    }  
    
    public void mostrarEscenaSalones(){
        try {
           SalonesController salonesController = (SalonesController)cambiarEscena("SalonesView.fxml", 900, 516);
           salonesController.setEscnerioPrincipal(this);
        } catch (Exception ex) {
             System.err.println("\nSe produjo un error al intentar mostrar la escena salones");
             ex.printStackTrace();
        }   
    }
    
    public void mostrarEscenaInstructores(){
        try{
            InstructoresController instructorController = (InstructoresController) cambiarEscena("InstructoresView.fxml", 1262, 516);
            instructorController.setEscnerioPrincipal(this);
        }catch(Exception ex){
            System.err.println("\nSe produjo un error al intentar mostrar la escena instructores");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaHorarios(){
        try{
            HorariosController horarioController = (HorariosController) cambiarEscena("HorariosView.fxml", 1043, 526); 
            horarioController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.println("\nSe produjo un error al intentar mostrar la escena horarios");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaCursos(){
        try{
            CursosController cursoController = (CursosController) cambiarEscena("CursosView.fxml", 1076, 516);
            cursoController.setEscnerioPrincipal(this);
        }catch(Exception ex){
            System.err.println("\nSe produjo un error al intentar mostrar la escena cursos");
            ex.printStackTrace();
            
        }
    }
    
    public void mostrarEscenaAsignacionesAlumnos(){
        try{
            AsignacionesAlumnosController asignacionController = (AsignacionesAlumnosController) cambiarEscena("AsignacionesAlumnosView.fxml", 900, 516); 
            asignacionController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.println("\nSe produjo un error al intentar mostrar la escena asignaciones");
            ex.printStackTrace();
        }
    }
   
    /*
    public Initializable cambiarEscena(String vistaFxml, int ancho, int alto)throws IOException{
    
        Initializable resultado = null;
        
        // cargador del archivo FXML
        FXMLLoader cargadorFXML = new FXMLLoader();
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VIEW + vistaFxml));
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VIEW + vistaFxml);
        AnchorPane root = cargadorFXML.load(archivo);
        Scene nuevaEscena = new Scene(root, ancho, alto);
        this.escenarioPrincipal.setScene(nuevaEscena);
        
        resultado = (Initializable)cargadorFXML.getController();
              
        return resultado;
    }
    */
    
    public Initializable cambiarEscena(String vistaFxml, int ancho, int alto) throws IOException{
    
        System.out.println(PAQUETE_VIEW + vistaFxml);
        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(PAQUETE_VIEW + vistaFxml));
        Scene scene = new Scene((AnchorPane)cargadorFXML.load(), ancho, alto);
        this.escenarioPrincipal.setScene(scene);
        this.escenarioPrincipal.sizeToScene();
        this.escenarioPrincipal.show();
        return (Initializable)cargadorFXML.getController();
    }
}


