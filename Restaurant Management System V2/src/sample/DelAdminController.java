package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DelAdminController {

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton home;

    @FXML
    private JFXButton out;

    @FXML
    private JFXTextField uname;

    @FXML
    private JFXButton ok;

    Connection con=DBConnection.getConnection();
    PreparedStatement ps=null;
    ResultSet rs=null;

    public DelAdminController() throws SQLException {
    }

    @FXML
    public void del_cat(ActionEvent actionEvent) throws SQLException {

        if (uname.getText().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Enter Username!");
            errorAlert.showAndWait();
        } else {

            String sql = "select username from admins where username=" + uname.getText();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                String sql1 = "delete from admins where username=" + uname.getText();
                ps = con.prepareStatement(sql1);
                ps.executeUpdate();

                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Alert!");
                errorAlert.setContentText("User Deleted Successfully!");
                errorAlert.showAndWait();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Data Error!");
                errorAlert.setContentText("Invalid Username!");
                errorAlert.showAndWait();
            }
        }
    }

    @FXML
    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("admin_dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void home(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) home.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("home_admin.fxml"));
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
