package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UsersController {

    @FXML
    private Button add_emp;

    @FXML
    private Button edit_emp;

    @FXML
    private Button del_emp;

    @FXML
    private Button disp_emp;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton home;

    @FXML
    private JFXButton out;

    @FXML
    private Button add_admin;

    @FXML
    private Button edit_admin;

    @FXML
    private Button del_admin;

    @FXML
    private Button disp_admin;

    @FXML
    public void add_emp(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) add_emp.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("add_emp.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void del_emp(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) del_emp.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("del_emp.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void disp_emp(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) disp_emp.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("disp_emp.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void add_admin(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) add_admin.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("add_admin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void del_admin(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) del_admin.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("del_admin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void disp_admin(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) disp_admin.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("disp_admin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void home(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) home.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void out(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) out.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
