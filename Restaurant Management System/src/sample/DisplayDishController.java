package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DisplayDishController implements Initializable {

    @FXML
    private TableView<ModelDishTable> dish_table;

    @FXML
    private TableColumn<?, ?> dishid_col;

    @FXML
    private TableColumn<?, ?> dishname_col;

    @FXML
    private TableColumn<?, ?> catname_col;

    @FXML
    private TableColumn<?, ?> catid_col;

    @FXML
    private JFXTextField search;

    ObservableList<ModelDishTable> oblist= FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Connection con= DBConnection.getConnection();
            ResultSet rs=con.createStatement().executeQuery("select * from dishes");

            while(rs.next())
            {
                oblist.add(new ModelDishTable(rs.getString("dish_id"),rs.getString("dish_name"),rs.getString("cat_name"),rs.getString("cat_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dishid_col.setCellValueFactory(new PropertyValueFactory<>("dish_id"));
        dishname_col.setCellValueFactory(new PropertyValueFactory<>("dish_name"));
        catname_col.setCellValueFactory(new PropertyValueFactory<>("cat_name"));
        catid_col.setCellValueFactory(new PropertyValueFactory<>("cat_id"));

        dish_table.setItems(oblist);

        FilteredList<ModelDishTable> filteredData = new FilteredList<>(oblist, p -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(temp -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (temp.getDish_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<ModelDishTable> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(dish_table.comparatorProperty());

        dish_table.setItems(sortedData);
    }
}
