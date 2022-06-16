package org.in5bv.edyletona.cesarbarillas.controllers;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bv.edyletona.db.Conexion;
import org.in5bv.edyletona.models.Alumnos;
import org.in5bv.edyletona.models.AsignacionesAlumnos;
import org.in5bv.edyletona.models.Cursos;
import org.in5bv.edyletona.system.Principal;

/**
 *
 * @author Edy Leonel Letona Argueta
 * 
 */
public class AsignacionesAlumnosController implements Initializable{
    
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private final String PAQUETE_IMAGE = "org/in5bv/edyletona/resources/images/";

    private ObservableList<Alumnos> listaObservableAlumnos;
    private ObservableList<Cursos> listaObservableCursos;
    private ObservableList<AsignacionesAlumnos> listaObservableAsignacionesAlumnos;
    
    private Principal escenarioPrincipal;
    
    @FXML
    private Button btnNuevo;
    
    @FXML
    private ImageView imgNuevo;
    
    @FXML
    private Button btnModificar;
    
    @FXML
    private ImageView imgModificar;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    private ImageView imgEliminar;
    
    @FXML
    private Button btnReporte;
    
    @FXML
    private ImageView imgReporte;
    
    @FXML
    private DatePicker dpkFechaAsignacion;
    
    @FXML
    private TextField txtId;
    
    @FXML
    private ComboBox<Alumnos> cmbAlumno;
    
    @FXML
    private ComboBox<Cursos> cmbCurso;
    
    @FXML
    private TableView<?> tblAsignacionesAlumnos;
    
    @FXML
    private TableColumn<AsignacionesAlumnos, Integer> colId;
    @FXML
    private TableColumn<AsignacionesAlumnos, String> colCarne;
    @FXML
    private TableColumn<AsignacionesAlumnos, Integer> colIdCurso;
    @FXML
    private TableColumn<AsignacionesAlumnos, LocalDateTime> colFechaAsignacion;
    
    @FXML
    private ImageView imgRegresar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //cargarDatos(); 
    }
    
    public ObservableList getAlumnos() {
        ArrayList<Alumnos> arrayListAlumnos = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_alumnos_read()}");
            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                // Opción 1:
                Alumnos alumno = new Alumnos();
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));

                System.out.println(alumno.toString());
                arrayListAlumnos.add(alumno);
            }

            listaObservableAlumnos = FXCollections.observableArrayList(arrayListAlumnos);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Alumnos");
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
        return listaObservableAlumnos;
    }
    
    public ObservableList getAsignacionesAlumnos() {
        ArrayList<AsignacionesAlumnos> arrayListAsignaciones = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                AsignacionesAlumnos asignacion = new AsignacionesAlumnos();
                asignacion.setId(rs.getInt("id"));
                asignacion.setAlumnoId(rs.getString("alumno_id"));
                asignacion.setCursoId(rs.getInt("curso_id"));
                asignacion.setFechaAsignacion(rs.getTimestamp("fecha_asignacion").toLocalDateTime());

                System.out.println(asignacion);

                arrayListAsignaciones.add(asignacion);
            }

            listaObservableAsignacionesAlumnos = FXCollections.observableArrayList(arrayListAsignaciones);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Asignaciones alumnos");
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

        return listaObservableAsignacionesAlumnos;
    }
    
     private ObservableList getCursos(){
        ArrayList<Cursos> arrayListCursos = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
        
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_cursos_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            
            while (rs.next()){
            Cursos curso = new Cursos();
            curso.setId(rs.getInt("id_curso"));
            curso.setNombreCurso(rs.getString("nombre_curso"));
            curso.setCiclo(rs.getInt("ciclo"));
            curso.setCupoMaximo(rs.getInt("cupo_max"));
            curso.setCupoMinimo(rs.getInt("cupo_min"));
            curso.setCarreraTecnicaId(rs.getString("carrera_tecnica_id"));
            curso.setHorarioId(rs.getInt("horario_id"));
            curso.setIntructorId(rs.getInt("instructor_id"));
            curso.setSalonId(rs.getString("salon_id"));
           
            System.out.println(curso.toString());
            arrayListCursos.add(curso);
            } 
            
            listaObservableCursos = FXCollections.observableArrayList(arrayListCursos);
            
            
        }catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla Cursos");
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
        
        return listaObservableCursos;
    }
    
     public void cargarDatos() {
        tblAsignacionesAlumnos.setItems(getAsignacionesAlumnos());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCarne.setCellValueFactory(new PropertyValueFactory<>("alumnoId"));
        colIdCurso.setCellValueFactory(new PropertyValueFactory<>("cursoId"));
        colFechaAsignacion.setCellValueFactory(new PropertyValueFactory<>("fechaAsignacion"));

        cmbAlumno.setItems(getAlumnos());
        cmbCurso.setItems(getCursos());
        

    }

    private boolean existeElementoSeleccionado() {
        return (tblAsignacionesAlumnos.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtId.setText(String.valueOf(((AsignacionesAlumnos) tblAsignacionesAlumnos.getSelectionModel().getSelectedItem()).getId()));
            cmbAlumno.getSelectionModel().select(buscarAlumnos((((AsignacionesAlumnos) tblAsignacionesAlumnos.getSelectionModel().getSelectedItem()).getAlumnoId())));

            cmbCurso.getSelectionModel().select(buscarCursos(((AsignacionesAlumnos) tblAsignacionesAlumnos.getSelectionModel().getSelectedItem()).getCursoId()));

            dpkFechaAsignacion.setValue(((AsignacionesAlumnos) tblAsignacionesAlumnos.getSelectionModel().getSelectedItem()).getFechaAsignacion().toLocalDate());
        }
    }
    
    private void deshabilitarCampos() {
        txtId.setEditable(false);
        cmbAlumno.setEditable(false);
        cmbCurso.setEditable(false);
        dpkFechaAsignacion.setEditable(false);

        txtId.setDisable(true);
        cmbAlumno.setDisable(true);
        cmbCurso.setDisable(true);
        dpkFechaAsignacion.setDisable(true);
    }

    private void habilitarCampos() {
        txtId.setEditable(true);
        dpkFechaAsignacion.setEditable(true);

        txtId.setDisable(false);
        cmbAlumno.setDisable(false);
        cmbCurso.setDisable(false);
        dpkFechaAsignacion.setDisable(false);
    }

    private void limpiarCampos() {
        txtId.clear();
        cmbAlumno.valueProperty().set(null);
        cmbCurso.valueProperty().set(null);
        dpkFechaAsignacion.getEditor().clear();
    }
    
    @FXML
    private void clicNuevo(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();
                
                tblAsignacionesAlumnos.setDisable(true); 

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
                if (agregarAsignacion()) {

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
                    
                    tblAsignacionesAlumnos.setDisable(false);

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
                
                tblAsignacionesAlumnos.setDisable(false);
    
                operacion = Operacion.NINGUNO;
                
                break;
            case ACTUALIZAR:
                if (actualizarAsignacion()) {
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
                    
                tblAsignacionesAlumnos.getSelectionModel().clearSelection();
                
                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO: // Eliminar un registro
                if (existeElementoSeleccionado()) {
                    Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                    alertConfirm.setTitle("Control Académico Kinal");
                    alertConfirm.setHeaderText(null);
                    alertConfirm.setContentText("¿Está seguro que desea eliminar el registro seleccionado?");

                    Stage stage = (Stage) alertConfirm.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "robot.png"));

                    Optional<ButtonType> result = alertConfirm.showAndWait();
                    if (result.get().equals(ButtonType.OK)) {
                        if (eliminarAsignacion()) {
                            listaObservableCursos.remove(tblAsignacionesAlumnos.getSelectionModel().getFocusedIndex());
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
                        tblAsignacionesAlumnos.getSelectionModel().clearSelection();
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
    private void clicReporte() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Aviso");
        alerta.setHeaderText("");
        alerta.setContentText("Está función solo está disponible en la versión PRO");
        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(PAQUETE_IMAGE + "robot.png"));
        alerta.show();
    }
  
    private Cursos buscarCursos(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cursos curso = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_read_by_id(?)}");

            pstmt.setInt(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                curso = new Cursos();
                curso.setId(rs.getInt("id"));
                curso.setNombreCurso(rs.getString("nombre_curso"));
                curso.setCiclo(rs.getInt("ciclo"));
                curso.setCupoMaximo(rs.getInt("cupo_maximo"));
                curso.setCupoMinimo(rs.getInt("cupo_minimo"));
                curso.setCarreraTecnicaId(rs.getString("carrera_tecnica_id"));
                curso.setHorarioId(rs.getInt("horario_id"));
                curso.setIntructorId(rs.getInt("instructor_id"));
                curso.setSalonId(rs.getString("salon_id"));

                System.out.println(curso.toString());

            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Cursos");
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

        return curso;
    }
    
     public Alumnos buscarAlumnos(String id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Alumnos alumno = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_alumnos_read_by_id(?)}");

            pstmt.setString(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                alumno = new Alumnos(rs.getString("carne"),rs.getString("nombre1"),rs.getString("nombre2"),rs.getString("nombre3"),rs.getString("apellido1"),rs.getString("apellido2"));

                System.out.println(alumno.toString());
            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Alumnos");
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
        return alumno;
    }
     
    private boolean agregarAsignacion() {

        AsignacionesAlumnos asignacion = new AsignacionesAlumnos(
            
                cmbAlumno.getValue().getCarne(), 
                cmbCurso.getValue().getId(), 
                dpkFechaAsignacion.getValue().atStartOfDay()
        );

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_create(?, ?, ?)}");

            pstmt.setString(1, asignacion.getAlumnoId());
            pstmt.setInt(2, asignacion.getCursoId());
            pstmt.setTimestamp(3, Timestamp.valueOf(asignacion.getFechaAsignacion()));
            System.out.println(pstmt.toString());

            pstmt.execute();

            listaObservableAsignacionesAlumnos.add(asignacion);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar "
                    + "el siguiente registro: " + asignacion.toString());
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

    private boolean actualizarAsignacion() {
        if (existeElementoSeleccionado()) {
            AsignacionesAlumnos asignacion = new AsignacionesAlumnos(
                Integer.parseInt(txtId.getText()),
                cmbAlumno.getValue().getCarne(), 
                cmbCurso.getValue().getId(), 
                dpkFechaAsignacion.getValue().atStartOfDay()
        );

            PreparedStatement pstmt = null;
            try {
                 pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_asignaciones_alumnos_update(?, ?, ?, ?)");
                 pstmt.setInt(1, asignacion.getId());
                 pstmt.setString(2, asignacion.getAlumnoId());
                 pstmt.setInt(3, asignacion.getCursoId());
                 pstmt.setTimestamp(4, Timestamp.valueOf(asignacion.getFechaAsignacion()));

                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar actualizar el siguiente registro:" + asignacion.toString());
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
    
    private boolean eliminarAsignacion() {

        if (existeElementoSeleccionado()) {
            AsignacionesAlumnos asignacion = (AsignacionesAlumnos) tblAsignacionesAlumnos.getSelectionModel().getSelectedItem();
            System.out.println(asignacion);
            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_asignaciones_alumnos_delete(?)}");

                pstmt.setInt(1, asignacion.getId());

                System.out.println(pstmt.toString());

                pstmt.execute();

                listaObservableAsignacionesAlumnos.remove(tblAsignacionesAlumnos.getSelectionModel().getFocusedIndex());
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el siguinete registro: " + asignacion.toString());
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
        return true;
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
} 
    