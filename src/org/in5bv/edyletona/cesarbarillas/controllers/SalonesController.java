package org.in5bv.edyletona.cesarbarillas.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.in5bv.edyletona.db.Conexion;
import org.in5bv.edyletona.models.Salones;
import org.in5bv.edyletona.system.Principal;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Edy Leonel Letona Argueta 
 * @date 22/04/2022
 * @time 01:18:42
 * 
 */

public class SalonesController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private final String PAQUETE_IMAGE = "org/in5bv/edyletona/resources/images/";

    private ObservableList<Salones> listaObservableSalones;
    
    @FXML
    private TextField txtCodigoSalon;
    
    @FXML
    private TextField txtDescripcion;
    
    @FXML
    private TextField txtCapacidadM;
    
    @FXML
    private TextField txtEdificio;
    
    @FXML
    private TextField txtNivel;
    
    @FXML
    private Button btnNuevo;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    private Button btnModificar;
    
    @FXML
    private Button btnReporte;
    
    @FXML
    private TableView tblSalones;
    
    @FXML
    private TableColumn colCodigoSalon; 
    
    @FXML
    private TableColumn colDescripcion; 
    
    @FXML
    private TableColumn colCapacidadM; 
    
    @FXML
    private TableColumn colEdificio; 
    
    @FXML
    private TableColumn colNivel; 
    
    @FXML
    private ImageView imgRegresar;
    
    @FXML
    private ImageView imgNuevo;
    
    @FXML
    private ImageView imgModificar; 
    
    @FXML
    private ImageView imgEliminar;

    @FXML
    private ImageView imgReporte;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      cargarDatos(); 
    }
    
    public ObservableList getSalones() {

        ArrayList<Salones> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_read()}");
            
            System.out.println(pstmt.toString());
            
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Salones salon = new Salones();
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));
                System.out.println(salon.toString());

                lista.add(salon);
            }

            listaObservableSalones = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla Alumnos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());

            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listaObservableSalones;
    }
    
     public void cargarDatos() {
        tblSalones.setItems(getSalones());
        colCodigoSalon.setCellValueFactory(new PropertyValueFactory<Salones, String>("codigoSalon"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Salones, String>("descripcion"));
        colCapacidadM.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("capacidadMaxima"));
        colEdificio.setCellValueFactory(new PropertyValueFactory<Salones, String>("edificio"));
        colNivel.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("nivel"));
    }
    
    private boolean existeElementoSeleccionado() {
        return (tblSalones.getSelectionModel().getSelectedItem() != null);
    } 
    
    @FXML
    public void seleccionarElemento() {

        if (existeElementoSeleccionado()) {
            txtCodigoSalon.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCodigoSalon());
            txtDescripcion.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getDescripcion());
            txtCapacidadM.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCapacidadMaxima()));
            txtEdificio.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getEdificio());
            txtNivel.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getNivel()));
        }
    }
    
    private boolean eliminarSalon() {
        if (existeElementoSeleccionado()) {

            Salones salon = (Salones) tblSalones.getSelectionModel().getSelectedItem();
            System.out.println(salon);

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_salones_delete(?)}");
                
                pstmt.setString(1, salon.getCodigoSalon());

                System.out.println(pstmt.toString());
                
                pstmt.execute();
                return (true);

            } catch (SQLException e) {
                System.out.println("\nSe profujo un error al intentar eliminar el siguiente registro");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (false);
    }
    
    private void deshabilitarCampos() {
        txtCodigoSalon.setEditable(false);
        txtDescripcion.setEditable(false);
        txtCapacidadM.setEditable(false);
        txtEdificio.setEditable(false);
        txtNivel.setEditable(false);

        txtCodigoSalon.setDisable(true);
        txtDescripcion.setDisable(true);
        txtCapacidadM.setDisable(true);
        txtEdificio.setDisable(true);
        txtNivel.setDisable(true);
    }
    
    private void habilitarCampos(){
        
        txtCodigoSalon.setEditable(true);
        txtDescripcion.setEditable(true);       
        txtCapacidadM.setEditable(true);
        txtEdificio.setEditable(true);
        txtNivel.setEditable(true);
        
        txtCodigoSalon.setDisable(false);
        txtDescripcion.setDisable(false);      
        txtCapacidadM.setDisable(false);
        txtEdificio.setDisable(false);
        txtNivel.setDisable(false);
    }
    
    @FXML
    private void limpiarCampos(){
      txtCodigoSalon.clear();
      txtDescripcion.clear();
      txtCapacidadM.clear();  
      txtEdificio.clear(); 
      txtNivel.clear();
    }
    
    
    @FXML
    private void clicNuevo(){
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();
                
                tblSalones.setDisable(true); 

                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "Guardar.png"));
                
                btnModificar.setText("Cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "cancelar.png"));
                
                btnEliminar.setDisable(true);
                btnEliminar.setVisible(false);
                
                btnReporte.setDisable(true);
                btnReporte.setVisible(false);
                
                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                if (agregarSalon()) {
                    
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();

                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGE + "nuevo.png"));
                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "modificar.png"));
                    btnEliminar.setDisable(false);
                    btnEliminar.setVisible(true);
                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);
                    
                    tblSalones.setDisable(false);

                    operacion = Operacion.NINGUNO;
                }
                break;
        }
      }
    private boolean agregarSalon() {

        Salones salon = new Salones();

        salon.setCodigoSalon(txtCodigoSalon.getText());
        salon.setDescripcion(txtDescripcion.getText());
        salon.setCapacidadMaxima(Integer.parseInt(txtCapacidadM.getText())); 
        salon.setEdificio(txtEdificio.getText());
        salon.setNivel(Integer.parseInt(txtNivel.getText()));

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_salones_create(?,?,?,?,?)}");
            pstmt.setString(1, salon.getCodigoSalon());
            pstmt.setString(2, salon.getDescripcion());
            pstmt.setInt(3, salon.getCapacidadMaxima());
            pstmt.setString(4, salon.getEdificio());
            pstmt.setInt(5, salon.getNivel());
            
            System.out.println(pstmt.toString());
            
            pstmt.execute();
            listaObservableSalones.add(salon);
            
            return (true);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar inssrtar el siguiente ejercicio"
                    + salon.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return (false);
    }
    
    @FXML
    private void clicModificar(){
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {

                    habilitarCampos();
                    
                    txtCodigoSalon.setDisable(true);
                    txtCodigoSalon.setEditable(false);
                    
                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);
                    
                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "Guardar.png"));
                    
                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "cancelar.png"));
                    
                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;

                }else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar seleccone un registro");
                    alert.show();
                }
            break;   
            case 
                GUARDAR:
                
                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGE + "nuevo.png"));
                    
                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "modificar.png"));
                    
                    btnEliminar.setDisable(false);
                    btnEliminar.setVisible(true);
                    
                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);

                    limpiarCampos();
                    deshabilitarCampos();

                    tblSalones.setDisable(false);

                    operacion = Operacion.NINGUNO;
                
            break;
            case 
                ACTUALIZAR:
                    if (actualizarSalon()) {
                        limpiarCampos();
                        deshabilitarCampos();
                        cargarDatos();

                        btnNuevo.setDisable(false);
                        btnNuevo.setVisible(true);
                        
                        btnModificar.setText("Modificar");
                        imgModificar.setImage(new Image(PAQUETE_IMAGE + "modificar.png"));
                        
                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image(PAQUETE_IMAGE + "basura.png"));
                        
                        btnReporte.setDisable(false);
                        btnReporte.setVisible(true);

                        operacion = Operacion.NINGUNO;
                    }
            break;
        }
    }
    
    private boolean actualizarSalon() {

        if(existeElementoSeleccionado()){
        
            Salones salon = new Salones();
            salon.setCodigoSalon(txtCodigoSalon.getText());
            salon.setDescripcion(txtDescripcion.getText());
            salon.setCapacidadMaxima(Integer.parseInt(txtCapacidadM.getText())); 
            salon.setEdificio(txtEdificio.getText());
            salon.setNivel(Integer.parseInt(txtNivel.getText()));

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_salones_update(?,?,?,?,?)}");
                pstmt.setString(1, salon.getCodigoSalon());
                pstmt.setString(2, salon.getDescripcion());
                pstmt.setInt(3, salon.getCapacidadMaxima());
                pstmt.setString(4, salon.getEdificio());
                pstmt.setInt(5, salon.getNivel());

                System.out.println(pstmt.toString());
                pstmt.execute();
                return (true);

            } catch (SQLException e) {
                System.err.println("Se produjo un error al intentar actualizar el siguiente ejercicio" + salon.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return (false);
    }
    
    @FXML
    private void clicEliminar(){
        switch (operacion) {
            case ACTUALIZAR:
                
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);
                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "modificar.png"));
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGE + "basura.png"));
                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();
                    
                tblSalones.getSelectionModel().clearSelection();
                
                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("¿Está seguro que desea eliminar el registro seleccionado?");
                    
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow(); 
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "robot.png")); 
                    
                    Optional<ButtonType> result = alert.showAndWait();   
                    if(result.get().equals(ButtonType.OK)){
                        if (eliminarSalon()) {
                        listaObservableSalones.remove(tblSalones.getSelectionModel().getFocusedIndex());
                        limpiarCampos();
                        cargarDatos();
                        
                        Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
                        alertInformation.setTitle("Control Academico Kinal");
                        alertInformation.setHeaderText(null);
                        alertInformation.setContentText("Regitro eliminado exitosamente");
                        alertInformation.show();
                        }
                    }else{
                        alert.close();
                        tblSalones.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }
                    
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Seleccione un registro para poder eliminar");
                    alert.show();
                    
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "robot.png"));
                }
                break;
        }
    }
    
    @FXML
    private void clicReporte (){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Aviso");
        alerta.setHeaderText("");
        alerta.setContentText("Está función solo está disponible en la versión PRO");
        alerta.show();      
    }
    
    @FXML
    private void clicRegresar(){
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscnerioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
}
