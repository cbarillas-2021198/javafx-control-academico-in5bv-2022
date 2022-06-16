package org.in5bv.edyletona.cesarbarillas.controllers;

import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.Initializable;
import org.in5bv.edyletona.system.Principal;
import java.net.URL;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.in5bv.edyletona.db.Conexion;
import org.in5bv.edyletona.models.Horarios;
import org.in5bv.edyletona.system.Principal;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Edy Leonel Letona Argueta
 * @date 2/06/2022
 * @time 13:31:22
 * 
 */
public class HorariosController implements Initializable{
    
    private Principal escenarioPrincipal; 
    
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    
    private Operacion operacion = Operacion.NINGUNO; 
    
    private final String PAQUETE_IMAGE = "org/in5bv/edyletona/resources/images/";
    
    private ObservableList<Horarios> listaObservableHorarios;
    
    @FXML
    private TextField txtId; 
    
    @FXML
    private Button btnNuevo;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    private Button btnModificar;
    
    @FXML
    private Button btnReporte;
    
    @FXML
    private TableView tblHorarios; 
    
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
    private CheckBox ckbLunes;
    
    @FXML
    private CheckBox ckbMartes;
    
    @FXML
    private CheckBox ckbMiercoles;
    
    @FXML
    private CheckBox ckbJueves;
    
    @FXML
    private CheckBox ckbViernes;
    
    @FXML
    private JFXTimePicker jfxTimePickerInicio;
    @FXML
    private JFXTimePicker jfxTimePickerFinal;
    
    @FXML
    private TableColumn colId;
    
    @FXML
    private TableColumn colHorarioInicio;
    @FXML
    private TableColumn colHorarioFinal;
    
    @FXML
    private TableColumn colLunes;
    
    @FXML
    private TableColumn colMartes;
    
    @FXML
    private TableColumn colMiercoles;
    
    @FXML
    private TableColumn colJueves;
    
    @FXML
    private TableColumn colViernes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public ObservableList getHorarios() {
        
        ArrayList<Horarios> arraylistHorarios = new ArrayList<>();
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();

            System.out.println("\n");

            while (rs.next()) {
                Horarios horario = new Horarios();
                horario.setId(rs.getInt("id_horario"));
                horario.setHorarioInicio(rs.getTime("horario_inicio").toLocalTime());
                horario.setHorarioFinal(rs.getTime("horario_final").toLocalTime());
                horario.setLunes(rs.getBoolean("lunes"));
                horario.setMartes(rs.getBoolean("martes"));
                horario.setMiercoles(rs.getBoolean("miercoles"));
                horario.setJueves(rs.getBoolean("jueves"));
                horario.setViernes(rs.getBoolean("viernes"));

                System.out.println(horario.toString());

                arraylistHorarios.add(horario);
            }

            listaObservableHorarios = FXCollections.observableArrayList(arraylistHorarios);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Horarios");
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
        return listaObservableHorarios;
    }
    
    public void cargarDatos() {
        tblHorarios.setItems(getHorarios());
        colId.setCellValueFactory(new PropertyValueFactory("id_horario"));
        colHorarioInicio.setCellValueFactory(new PropertyValueFactory("horario_inicio"));
        colHorarioFinal.setCellValueFactory(new PropertyValueFactory("horario_final"));
        colLunes.setCellValueFactory(new PropertyValueFactory("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory("martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory("miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory("jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory("viernes"));
    }
    
    private boolean existeElementoSeleccionado() {
        return (tblHorarios.getSelectionModel().getSelectedItem() != null);
    }
    
    public void seleccionarElemento() {
        if(existeElementoSeleccionado()) {
            txtId.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getId()));
            jfxTimePickerInicio.setValue(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getHorarioInicio());
            jfxTimePickerFinal.setValue(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getHorarioFinal());
            ckbLunes.setSelected(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isLunes()); 
            ckbMartes.setSelected(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isMartes());
            ckbMiercoles.setSelected(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isMiercoles());
            ckbJueves.setSelected(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isJueves());
            ckbViernes.setSelected(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isViernes());
        }
    }
    
    private boolean agregarHorario() {
        Horarios horario = new Horarios();
        horario.setHorarioInicio(jfxTimePickerInicio.getValue()); 
        horario.setHorarioFinal(jfxTimePickerFinal.getValue());
        horario.setLunes(ckbLunes.isSelected());
        horario.setMartes(ckbMartes.isSelected());
        horario.setMiercoles(ckbMiercoles.isSelected());
        horario.setJueves(ckbJueves.isSelected());
        horario.setViernes(ckbViernes.isSelected());
        
        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_create(?, ?, ?, ?, ?, ?, ?)}");
            pstmt.setTime(1, Time.valueOf(horario.getHorarioInicio()));
            pstmt.setTime(2, Time.valueOf(horario.getHorarioFinal()));
            pstmt.setBoolean(4, horario.isMartes());
            pstmt.setBoolean(5, horario.isMiercoles());
            pstmt.setBoolean(6, horario.isJueves());
            pstmt.setBoolean(7, horario.isViernes());
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaObservableHorarios.add(horario);
        return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjó un error al intentar insertar el siguiente registro" + horario.toString());
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
        return false;
    }
    
    private boolean actualizarHorario() {
        if (existeElementoSeleccionado()) {
            Horarios horario = new Horarios();
            horario.setId(Integer.parseInt(txtId.getText()));
            horario.setHorarioInicio(jfxTimePickerInicio.getValue());
            horario.setHorarioFinal(jfxTimePickerFinal.getValue());
            horario.setLunes(ckbLunes.isSelected());
            horario.setMartes(ckbMartes.isSelected());
            horario.setMiercoles(ckbMiercoles.isSelected());
            horario.setJueves(ckbJueves.isSelected());
            horario.setViernes(ckbViernes.isSelected());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_update(?, ?, ?, ?, ?, ?, ?, ?)}");
                pstmt.setInt(1, horario.getId());
                pstmt.setTime(2, Time.valueOf(horario.getHorarioInicio()));
                pstmt.setTime(3, Time.valueOf(horario.getHorarioFinal()));
                pstmt.setBoolean(4, horario.isLunes());
                pstmt.setBoolean(5, horario.isMartes());
                pstmt.setBoolean(6, horario.isMiercoles());
                pstmt.setBoolean(7, horario.isJueves());
                pstmt.setBoolean(8, horario.isViernes());
                System.out.println(pstmt.toString());
                pstmt.execute();
                listaObservableHorarios.add(horario);
            return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjó un error al intentar insertar el siguiente registro" + horario.toString());
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
        return false;
    }
    
    private boolean eliminarHorario() {
        if (existeElementoSeleccionado()) {
            Horarios horario = (Horarios) tblHorarios.getSelectionModel().getSelectedItem();
            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_delete(?)}");
                pstmt.setInt(1, horario.getId());
                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjó un error al eliminar el siguiente registro" + horario.toString());
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
        return false;
    }
    
    private void habilitarCampos() {
        txtId.setEditable(true);
        
        
        txtId.setDisable(false);
        jfxTimePickerInicio.setDisable(false);
        jfxTimePickerFinal.setDisable(false);
        ckbLunes.setDisable(false);
        ckbMartes.setDisable(false);
        ckbMiercoles.setDisable(false);
        ckbJueves.setDisable(false);
        ckbViernes.setDisable(false); 
    } 

    private void deshabilitarCampos() {
        txtId.setEditable(false);
        
        
        txtId.setDisable(true);
        jfxTimePickerInicio.setDisable(true);
        jfxTimePickerFinal.setDisable(true);
        ckbLunes.setDisable(true);
        ckbMartes.setDisable(true);
        ckbMiercoles.setDisable(true);
        ckbJueves.setDisable(true);
        ckbViernes.setDisable(true);
    }

    private void limpiarCampos() {
        //txtCarne.setText("");
        //Forma 2
        txtId.clear();
        jfxTimePickerInicio.getEditor().clear();
        jfxTimePickerFinal.getEditor().clear();
        ckbLunes.setSelected(false);
        ckbMartes.setSelected(false);
        ckbMiercoles.setSelected(false);
        ckbJueves.setSelected(false);
        ckbViernes.setSelected(false);
    } 
    
    @FXML
    private void clicNuevo(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();
                
                tblHorarios.setDisable(true); 

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
                if (agregarHorario()) {

                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();
                    
                    Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);;
                    alertConfirm.setTitle("Control Académico Kinal");
                    alertConfirm.setHeaderText(null);
                    alertConfirm.setContentText("Registro insertado exitosamente");
                    alertConfirm.show();
                    Stage stage = (Stage) alertConfirm.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "robot.png"));

                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGE + "nuevo.png"));
                    
                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "modificar.png"));
                    
                    btnEliminar.setDisable(false);
                    btnEliminar.setVisible(true);
                    
                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);
                    
                    tblHorarios.setDisable(false);

                    operacion = Operacion.NINGUNO;
                }
                break;
        }
    }

    @FXML
    private void clicModificar(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {

                    habilitarCampos();
                    
                    txtId.setDisable(true);
                    txtId.setEditable(false);
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
            case GUARDAR:
                
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
                
                tblHorarios.setDisable(false);
    
                operacion = Operacion.NINGUNO;
                
                break;
            case ACTUALIZAR:
                if (actualizarHorario()) {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();
                    
                    Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);;
                    alertConfirm.setTitle("Control Académico Kinal");
                    alertConfirm.setHeaderText(null);
                    alertConfirm.setContentText("Registro modificado exitosamente");
                    alertConfirm.show();
                    Stage stage = (Stage) alertConfirm.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "robot.png"));

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
    private void clicEliminar(ActionEvent event) {
        switch (operacion) {
            case ACTUALIZAR: // Cancelar una modificación
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
                    
                tblHorarios.getSelectionModel().clearSelection();
                
                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO: 
                if (existeElementoSeleccionado()) {
                    Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                    alertConfirm.setTitle("Control Académico Kinal");
                    alertConfirm.setHeaderText(null);
                    alertConfirm.setContentText("¿Está seguro que desea eliminar el registro seleccionado?");

                    Stage stage = (Stage) alertConfirm.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "robot.png"));

                    Optional<ButtonType> result = alertConfirm.showAndWait();
                    if (result.get().equals(ButtonType.OK)) {
                        if (eliminarHorario()) {
                            listaObservableHorarios.remove(tblHorarios.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();

                            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
                            alertInformation.setTitle("Control Académico Kinal");
                            alertInformation.setHeaderText(null);
                            alertInformation.setContentText("Registro eliminado exitosamente");
                            alertInformation.show();
                        }
                    } else if (result.get().equals(ButtonType.CANCEL)) {
                        alertConfirm.close();
                        tblHorarios.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un registro");
                    alert.show();
                }
                break;
        }
    }

    @FXML
    private void clicReporte(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Aviso");
        alerta.setHeaderText("");
        alerta.setContentText("Está función solo está disponible en la versión PRO");
        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(PAQUETE_IMAGE + "robot.png"));
        alerta.show();
    }
    
    @FXML
    private void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}

