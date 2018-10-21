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

public class EditCatController {

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton home;

    @FXML
    private JFXButton out;

    @FXML
    private JFXTextField cat_id;

    @FXML
    private JFXButton ok;

    @FXML
    private JFXTextField cat_name;

    @FXML
    private JFXButton ret;

    Connection con=DBConnection.getConnection();
    PreparedStatement ps=null;
    ResultSet rs=null;

    public EditCatController() throws SQLException {
    }

    @FXML
    public void retrieve(ActionEvent actionEvent) throws SQLException {
        if(cat_id.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Enter Category ID!");
            errorAlert.showAndWait();
        }
        String sql="select cat_name from categories where cat_id="+cat_id.getText();
        ps=con.prepareStatement(sql);
        rs=ps.executeQuery();

        if(rs.next())
            cat_name.setText(rs.getString("cat_name"));
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Data Error!");
            errorAlert.setContentText("Invalid Category");
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void update(ActionEvent actionEvent) throws SQLException {
        if(cat_id.getText().isEmpty() || cat_name.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please enter Category ID and Name");
            errorAlert.showAndWait();
        }
        else
        {
            String sql="update categories set cat_name='"+cat_name.getText()+"' where cat_id="+cat_id.getText();
            ps=con.prepareStatement(sql);
            ps.executeUpdate();

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Alert!");
            errorAlert.setContentText("Successfully Updated Category!");
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("dashboard.fxml"));
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
