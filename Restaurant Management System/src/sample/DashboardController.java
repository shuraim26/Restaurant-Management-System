package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private JFXButton billing_btn;

    @FXML
    private JFXButton cat_btn;

    @FXML
    private JFXButton orders_btn;

    @FXML
    private JFXButton dish_btn;

    @FXML
    private JFXButton logout_btn;

    @FXML
    public void dish_dash(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) dish_btn.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("dish_dash.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void orders(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) dish_btn.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("orders.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void cat(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) dish_btn.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("cat_dash.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
