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

public class AddCatController {

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton home;

    @FXML
    private JFXButton out;

    @FXML
    private JFXTextField cat_name;

    @FXML
    private JFXButton ok;

    Connection con=DBConnection.getConnection();
    PreparedStatement ps=null;
    ResultSet rs=null;

    public AddCatController() throws SQLException {
    }

    @FXML
    public void add(ActionEvent actionEvent) throws SQLException {
        if(cat_name.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Enter Category Name");
            errorAlert.showAndWait();
        }
        else {
            String check = "select * from categories where cat_name='" + cat_name.getText() + "'";
            ps = con.prepareStatement(check);
            rs=ps.executeQuery();

            if (rs.next()) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Data Error!");
                errorAlert.setContentText("Category Already Exists");
                errorAlert.showAndWait();
            } else {
                String sql="insert into categories(cat_name) values('"+cat_name.getText()+"')";
                ps = con.prepareStatement(sql);
                ps.executeUpdate();

                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setHeaderText("Alert!");
                infoAlert.setContentText("Category Added Successfully!");
                infoAlert.showAndWait();

                cat_name.setText("");
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
