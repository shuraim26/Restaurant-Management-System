package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DishDashController {

    @FXML
    private JFXButton rem_dish;

    @FXML
    private JFXButton update_dish;

    @FXML
    private JFXButton add_dish;

    @FXML
    public void add_dish(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) add_dish.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("add_dish.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void delete_dish(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) rem_dish.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("delete_dish.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void update_dish(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) update_dish.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("update_dish.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

}
