package org.in5bv.edyletona.cesarbarillas.controllers;

/**
 *
 * @author Edy Leonel Letona Argueta
 * @date 21/04/2022
 * @time 00:22:06
 *
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.in5bv.edyletona.system.Principal;

import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.in5bv.edyletona.db.Conexion;
import org.in5bv.edyletona.models.Alumnos;

public class AlumnosController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private final String PAQUETE_IMAGE = "org/in5bv/edyletona/resources/images/";

    private Principal escenarioPrincipal;

    private ObservableList<Alumnos> listaAlumnos;

    @FXML
    private TextField txtCarne;

    @FXML
    private TextField txtNombre1;

    @FXML
    private TextField txtNombre2;

    @FXML
    private TextField txtNombre3;

    @FXML
    private TextField txtApellido1;

    @FXML
    private TextField txtApellido2;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnReporte;

    @FXML
    private TableView tblAlumnos;

    @FXML
    private TableColumn colCarne;

    @FXML
    private TableColumn colNombre1;

    @FXML
    private TableColumn colNombre2;

    @FXML
    private TableColumn colNombre3;

    @FXML
    private TableColumn colApellido1;

    @FXML
    private TableColumn colApellido2;

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

    public ObservableList getAlumnos() {

        ArrayList<Alumnos> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_read()}");
            
            System.out.println(pstmt.toString());
            
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Alumnos alumno = new Alumnos();
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));
                System.out.println(alumno.toString());

                lista.add(alumno);
            }

            listaAlumnos = FXCollections.observableArrayList(lista);

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
        return listaAlumnos;
    }

    public void cargarDatos() {
        tblAlumnos.setItems(getAlumnos());
        colCarne.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("carne"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido2"));
    }

    private boolean existeElementoSeleccionado() {
        return (tblAlumnos.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {

        if (existeElementoSeleccionado()) {
            txtCarne.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getCarne());
            txtNombre1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido2());
        }
    }

    private boolean eliminarAlumno() {
        if (existeElementoSeleccionado()) {

            Alumnos alumno = (Alumnos) tblAlumnos.getSelectionModel().getSelectedItem();
            System.out.println(alumno);

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_alumnos_delete(?)}");
                
                pstmt.setString(1, alumno.getCarne());

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
        txtCarne.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);

        txtCarne.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
    }

    private void habilitarCampos() {

        txtCarne.setEditable(true);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);

        txtCarne.setDisable(false);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
    }

    @FXML
    private void limpiarCampos() {
        // txtCarrera.setText("");
        txtCarne.clear();
        txtNombre1.clear();
        txtNombre2.clear();
        txtNombre3.clear();
        txtApellido1.clear();
        txtApellido2.clear();
    }

    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO:
                    habilitarCampos();
                    limpiarCampos();

                    tblAlumnos.setDisable(true); 

                    btnNuevo.setText("Guardar");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGE + "Guardar.PNG"));

                    btnModificar.setText("Cancelar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "cancelar.png"));

                    btnEliminar.setDisable(true);
                    btnEliminar.setVisible(false);

                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    operacion = Operacion.GUARDAR;
                    
                    break;   
            case GUARDAR:
                if (agregarAlumno()){

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

                    tblAlumnos.setDisable(false);

                    operacion = Operacion.NINGUNO;                  
                }
                break;
        }
    }
    

    @FXML
    private void clicModificar() {
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {

                    habilitarCampos();
                    
                    txtCarne.setDisable(true);
                    txtCarne.setEditable(false);
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
                
                tblAlumnos.setDisable(false);
    
                operacion = Operacion.NINGUNO;
                
                break;
            case ACTUALIZAR:
                if (actualizarAlumno()) {
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

    private boolean agregarAlumno() {

        Alumnos alumno = new Alumnos();

        alumno.setCarne(txtCarne.getText());
        alumno.setNombre1(txtNombre1.getText());
        alumno.setNombre2(txtNombre2.getText());
        alumno.setNombre3(txtNombre3.getText());
        alumno.setApellido1(txtApellido1.getText());
        alumno.setApellido2(txtApellido2.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_create(?,?,?,?,?,?)}");
            pstmt.setString(1, alumno.getCarne());
            pstmt.setString(2, alumno.getNombre1());
            pstmt.setString(3, alumno.getNombre2());
            pstmt.setString(4, alumno.getNombre3());
            pstmt.setString(5, alumno.getApellido1());
            pstmt.setString(6, alumno.getApellido2());
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaAlumnos.add(alumno);
            return (true);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar inssrtar el siguiente ejercicio" + alumno.toString());
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

    private boolean actualizarAlumno() {

        if(existeElementoSeleccionado()){
        
            Alumnos alumno = new Alumnos();
            alumno.setCarne(txtCarne.getText());
            alumno.setNombre1(txtNombre1.getText());
            alumno.setNombre2(txtNombre2.getText());
            alumno.setNombre3(txtNombre3.getText());
            alumno.setApellido1(txtApellido1.getText());
            alumno.setApellido2(txtApellido2.getText());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_update(?,?,?,?,?,?)}");
                pstmt.setString(1, alumno.getCarne());
                pstmt.setString(2, alumno.getNombre1());
                pstmt.setString(3, alumno.getNombre2());
                pstmt.setString(4, alumno.getNombre3());
                pstmt.setString(5, alumno.getApellido1());
                pstmt.setString(6, alumno.getApellido2());

                System.out.println(pstmt.toString());
                pstmt.execute();
                return (true);

            } catch (SQLException e) {
                System.err.println("Se produjo un error al intentar actualizar el siguiente ejercicio" + alumno.toString());
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
    private void clicEliminar() {
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
                    
                tblAlumnos.getSelectionModel().clearSelection();
                
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
                        if (eliminarAlumno()) {
                        listaAlumnos.remove(tblAlumnos.getSelectionModel().getFocusedIndex());
                        limpiarCampos();
                        cargarDatos();
                        
                        Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
                        alertInformation.setTitle("Control Academico Kinal");
                        alertInformation.setHeaderText(null);
                        alertInformation.setContentText("Registro eliminado exitosamente");
                        alertInformation.show();
                        }
                    }else{
                        alert.close();
                        tblAlumnos.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }
                    
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Seleccione un registro para poder eliminar");
                    alert.show();
                }
                break;
        }
    }

    @FXML
    private void clicReporte() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Aviso");
        alerta.setHeaderText("");
        alerta.setContentText("Está función solo está disponible en la versión PRO");
        alerta.show();
    }

    @FXML
    private void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscnerioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}
