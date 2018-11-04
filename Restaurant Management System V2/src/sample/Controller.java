package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {

    @FXML
    private JFXTextField uname;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private Button login;

    @FXML
    private Button admin;

    @FXML
    public void login(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        Connection con=DBConnection.getConnection();

        String sql="select * from users where username='"+uname.getText()+"' and password='"+pass.getText()+"'";
        PreparedStatement ps=con.prepareStatement(sql);
        ResultSet rs= ps.executeQuery();

        if (rs.next())
        {
            Stage stage = (Stage) login.getScene().getWindow();
            AnchorPane root;
            root = (AnchorPane) FXMLLoader.load(getClass().getResource("home.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Authorisation Error!");
            errorAlert.setContentText("Invalid Username/Password");
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void admin(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) login.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("admin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}