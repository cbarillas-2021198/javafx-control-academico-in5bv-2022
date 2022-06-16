package org.in5bv.edyletona.cesarbarillas.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.in5bv.edyletona.models.CarrerasTecnicas;
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

/**
 *
 * @author Usuario
 * @date 26/04/2022
 * @time 02:50:50
 *
 */
public class CarrerasTecnicasController implements Initializable {

    private Principal escenarioPrincipal;

    private final String PAQUETE_IMAGE = "org/in5bv/edyletona/resources/images/";

    private ObservableList<CarrerasTecnicas> listaCarrerasTecnicas;

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtGrado;

    @FXML
    private TextField txtCarrera;

    @FXML
    private TextField txtSeccion;

    @FXML
    private TextField txtJornada;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnReportes;

    @FXML
    private TableView tblCarrerasTecnicas;

    @FXML
    private TableColumn colCodigo;

    @FXML
    private TableColumn colGrado;

    @FXML
    private TableColumn colCarrera;

    @FXML
    private TableColumn colSeccion;

    @FXML
    private TableColumn colJornada;

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

    @FXML
    private Button btnReporte;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public ObservableList getCarrerasTecnicas() {

        ArrayList<CarrerasTecnicas> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CarrerasTecnicas carrera = new CarrerasTecnicas();
                carrera.setCodigoTecnico(rs.getString(1));
                carrera.setCarrera(rs.getString(2));
                carrera.setGrado(rs.getString(3));
                carrera.setSeccion(rs.getString(4));
                carrera.setJornada(rs.getString(5));
                System.out.println(carrera.toString());

                lista.add(carrera);
            }

            listaCarrerasTecnicas = FXCollections.observableArrayList(lista);

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
        return listaCarrerasTecnicas;
    }

    public void cargarDatos() {
        tblCarrerasTecnicas.setItems(getCarrerasTecnicas());
        colCodigo.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("codigoTecnico"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("carrera"));
        colGrado.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("grado"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, Character>("seccion"));
        colJornada.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("jornada"));
    }
    
    private boolean existeElementoSeleccionado() {
        return (tblCarrerasTecnicas.getSelectionModel().getSelectedItem() != null);
    } 
    
    @FXML
    public void seleccionarElemento() {

        if (existeElementoSeleccionado()) {
            txtCodigo.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCodigoTecnico());
            txtCarrera.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCarrera());
            txtGrado.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getGrado());
            txtJornada.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getJornada());
            txtSeccion.setText(((CarrerasTecnicas)tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getSeccion());   
        }
    }

    private void habilitarCampos() {

        txtCodigo.setEditable(true);
        txtGrado.setEditable(true);
        txtCarrera.setEditable(true);
        txtSeccion.setEditable(true);
        txtJornada.setEditable(true);

        txtCodigo.setDisable(false);
        txtGrado.setDisable(false);
        txtCarrera.setDisable(false);
        txtSeccion.setDisable(false);
        txtJornada.setDisable(false);
    }

    private void deshabilitarCampos() {
        txtCodigo.setEditable(false);
        txtGrado.setEditable(false);
        txtCarrera.setEditable(false);
        txtSeccion.setEditable(false);
        txtJornada.setEditable(false);

        txtCodigo.setDisable(true);
        txtGrado.setDisable(true);
        txtCarrera.setDisable(true);
        txtSeccion.setDisable(true);
        txtJornada.setDisable(true);
    }

    @FXML
    private void limpiarCampos() {

        txtCodigo.clear();
        txtGrado.clear();
        txtCarrera.clear();
        txtSeccion.clear();
        txtJornada.clear();
    }

    private boolean eliminarCarrera() {
        if (existeElementoSeleccionado()) {

            CarrerasTecnicas carrera = (CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem();
            System.out.println(carrera);

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_delete(?)}");
                pstmt.setString(1, carrera.getCodigoTecnico());

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

    private boolean actualizarCarrera() {

        if (existeElementoSeleccionado()) {

            CarrerasTecnicas carrera = new CarrerasTecnicas();
            carrera.setCodigoTecnico(txtCodigo.getText());
            carrera.setGrado(txtGrado.getText());
            carrera.setCarrera(txtCarrera.getText());
            carrera.setSeccion(txtSeccion.getText());
            carrera.setJornada(txtJornada.getText());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_update(?,?,?,?,?)}");
                pstmt.setString(1, carrera.getCodigoTecnico());
                pstmt.setString(2, carrera.getCarrera());
                pstmt.setString(3, carrera.getGrado());
                pstmt.setString(4, carrera.getSeccion());
                pstmt.setString(5, carrera.getJornada());

                System.out.println(pstmt.toString());
                pstmt.execute();
                return (true);

            } catch (SQLException e) {
                System.err.println("Se produjo un error al intentar actualizar el siguiente ejercicio" + carrera.toString());
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

    private boolean agregarCarrera() {

        CarrerasTecnicas carrera = new CarrerasTecnicas();

        carrera.setCarrera(txtCarrera.getText());
        carrera.setCodigoTecnico(txtCodigo.getText());
        carrera.setGrado(txtGrado.getText());
        carrera.setSeccion(txtSeccion.getText());
        carrera.setJornada(txtJornada.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_create(?,?,?,?,?)}");
            pstmt.setString(1, carrera.getCodigoTecnico());
            pstmt.setString(2, carrera.getCarrera());
            pstmt.setString(3, carrera.getGrado());
            pstmt.setString(4, carrera.getSeccion());
            pstmt.setString(5, carrera.getJornada());
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaCarrerasTecnicas.add(carrera);
            return (true);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar inssrtar el siguiente ejercicio" + carrera.toString());
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
    private void clicNuevo() {

        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();
                
                
                tblCarrerasTecnicas.setDisable(true); 


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

                if (agregarCarrera()) {
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
                    
                    tblCarrerasTecnicas.setDisable(false);

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

                    txtCodigo.setDisable(true);
                    txtCodigo.setEditable(false);

                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);

                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "Guardar.png"));

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "cancelar.png"));

                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;

                } else {
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
                    
                    tblCarrerasTecnicas.setDisable(false); 


                    operacion = Operacion.NINGUNO;
                break;

            case 
                ACTUALIZAR:
                    if (actualizarCarrera()) {
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

                tblCarrerasTecnicas.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("¿Esta seguro que desea eliminar el registro seleccionado?");

                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "robot.png"));

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get().equals(ButtonType.OK)) {
                        if (eliminarCarrera()) {
                            listaCarrerasTecnicas.remove(tblCarrerasTecnicas.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();

                            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
                            alertInformation.setTitle("Control Academico Kinal");
                            alertInformation.setHeaderText(null);
                            alertInformation.setContentText("Regitro eliminado exitosamente");

                        }
                    } else {
                        alert.close();
                        tblCarrerasTecnicas.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un registro");
                    alert.show();

                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "robot.png"));
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
