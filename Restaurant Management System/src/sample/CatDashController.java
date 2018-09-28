package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CatDashController {

    @FXML
    private JFXButton del_cat;

    @FXML
    private JFXButton update_cat;

    @FXML
    private JFXButton add_cat;

    @FXML
    private JFXButton display_cat;

    @FXML
    public void add_cat(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) add_cat.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("add_cat.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void del_cat(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) del_cat.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("delete_cat.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void update_cat(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) update_cat.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("update_cat.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void display_cat(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) display_cat.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("display_cat.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
