package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class OrdersAdminController implements Initializable {

    @FXML
    private JFXButton ordersbackbtn;

    @FXML
    private JFXButton ordershomebtn;

    @FXML
    private JFXButton orderslogoutbtn;

    @FXML
    private TextArea showarea;

    @FXML
    private TableView<ModelOrdersTable> orderstable;

    @FXML
    private TableColumn<?, ?> orderno_col;

    @FXML
    private JFXTextField search;

    ObservableList<ModelOrdersTable> oblist= FXCollections.observableArrayList();

    @FXML
    public void ordersbackbtnpressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ordersbackbtn.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("home_admin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void ordersbhomebtnpressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ordershomebtn.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("home_admin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void ordersblogoutbtnpressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) orderslogoutbtn.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void searchordertable(ActionEvent actionEvent)
    {
        if(orderstable.getSelectionModel().getSelectedItem()==null)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Select An Order No");
            errorAlert.showAndWait();
        }
        else {
            ModelOrdersTable mod = orderstable.getSelectionModel().getSelectedItem();

            File file = new File("Orders/ord" + mod.getRawOrder() + ".txt");

            if (!(file.exists())) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Input Error!");
                errorAlert.setContentText("Invalid Order No");
                errorAlert.showAndWait();

                showarea.clear();
            } else {
                showarea.clear();

                try {
                    Scanner s = new Scanner(new File("Orders/ord" + mod.getRawOrder() + ".txt"));
                    while (s.hasNext()) {
                        showarea.appendText(s.nextLine() + "\n");
                    }
                } catch (FileNotFoundException ex) {
                    System.err.println(ex);
                }
            }
        }
    }

    public void fillorders() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getConnection();
        ResultSet rs = con.createStatement().executeQuery("select * from orders");

        while (rs.next())
        {
            File file=new File("Orders/ord"+rs.getString("ord_no")+".txt");

            if(file.exists())
                oblist.add(new ModelOrdersTable(rs.getString("ord_no")));
        }

        orderno_col.setCellValueFactory(new PropertyValueFactory<>("ord_no"));

        orderstable.setItems(oblist);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        showarea.setEditable(false);

        try {
            fillorders();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        FilteredList<ModelOrdersTable> filteredData = new FilteredList<>(oblist, p -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(temp -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (temp.getRawOrder().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<ModelOrdersTable> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(orderstable.comparatorProperty());

        orderstable.setItems(sortedData);
    }
}