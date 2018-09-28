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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DisplayCatController implements Initializable {

    @FXML
    private TableView<ModelCatTable> cat_table;

    @FXML
    private TableColumn<?, ?> catid_col;

    @FXML
    private TableColumn<?, ?> catname_col;

    @FXML
    private JFXTextField search;

    ObservableList<ModelCatTable> oblist= FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Connection con= DBConnection.getConnection();

            String sql="select * from dishes";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();

            while(rs.next())
            {
                oblist.add(new ModelCatTable(rs.getString("cat_id"),rs.getString("cat_name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        catid_col.setCellValueFactory(new PropertyValueFactory<>("cat_id"));
        catname_col.setCellValueFactory(new PropertyValueFactory<>("cat_name"));

        cat_table.setItems(oblist);

        /*FilteredList<ModelCatTable> filteredData = new FilteredList<>(oblist, p -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(temp -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (temp.getCat_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<ModelCatTable> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(cat_table.comparatorProperty());

        cat_table.setItems(sortedData);*/
    }
}
