package gui.controller;

import be.Category;
import bll.MyTunesManager;
import bll.exceptions.CategoriesException;
import gui.model.NewCategoryModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewCategoryController implements Initializable {
    public ListView categoriesList;
    public TextField newCategoryName;
    public TextField selectedCategoryName;
    public Button cancelPlaylistBtn;
    public TextField selectCategory;
    NewCategoryModel newCategoryModel;
    Category categorySelected;
    MyTunesManager manager;
    SongController songController;
    SongEditController songEditController;

    public NewCategoryController() throws SQLException {
        newCategoryModel = new NewCategoryModel();
        manager = new MyTunesManager();
        songController = new SongController();
        songEditController = new SongEditController();
    }

    public void setController(SongController songController) {
        this.songController = songController;
    }

    public void setController(SongEditController songEditController) {
        this.songEditController = songEditController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            categoriesList.setItems(newCategoryModel.getAllCategories());
        } catch (CategoriesException e) {
            e.printStackTrace();
        }
    }


    public void addCategory(ActionEvent actionEvent) throws CategoriesException {

            try {
                newCategoryModel.addCategory(newCategoryName.getText());
                categoriesList.setItems(newCategoryModel.getAllCategories());
                songController.addMenuItem(newCategoryName.getText());
                songEditController.addMenuItem(newCategoryName.getText());
                newCategoryName.setText("");


            } catch (CategoriesException e) {
                newCategoryName.setText("");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error");
                alert.setHeaderText(e.getExceptionMessage());
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
            }

        }


    public void editCategory(ActionEvent actionEvent) throws CategoriesException, SQLException {
        if (categorySelected == null) {
            return;
        } else {
            newCategoryModel.updateCategory(categorySelected, selectedCategoryName.getText());
            categoriesList.setItems(newCategoryModel.getAllCategories());
            songController.clearMenuBar();
            songController.setMenuBar();
            songEditController.clearMenuBar();
            songEditController.setMenuBar();
            selectCategory.setText(selectedCategoryName.getText());
        }
    }

    public void closeWindow(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert window");
        alert.setHeaderText("Do you want to close this window?");


        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) cancelPlaylistBtn.getScene().getWindow();
            stage.close();
        }
    }



    public void deleteCategory(MouseEvent event) {
        try {
            int index = categoriesList.getSelectionModel().getSelectedIndex();
            newCategoryModel.deleteCategory(getCategorySelected());
            categoriesList.setItems(newCategoryModel.getAllCategories());
            songController.deleteMenuItem(new MenuItem(getCategorySelected().toString()));
            songEditController.deleteMenuItem(new MenuItem(getCategorySelected().toString()));
            newCategoryModel.getAllCategories().remove(getCategorySelected());
            if (index > 0) {
                categoriesList.getSelectionModel().select(index - 1);
                categorySelected = (Category) categoriesList.getItems().get(index - 1);
                populateTextField(categorySelected.getCategoryName());
            } else {
                categoriesList.getItems().clear();
                selectedCategoryName.setText("");
                selectCategory.setText("");
            }


        } catch (CategoriesException s) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert window");
            alert.setHeaderText("One or many songs are associated to this category.\nPlease get rid of this dependency before you can be able to delete it.");

            ButtonType okButton = new ButtonType("OK");
            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait();
        }
    }

    private void populateTextField(String categoryName) {
        selectedCategoryName.setText(categoryName);
        selectCategory.setText(categoryName);
    }
    private void setCategorySelected(){
        categorySelected=(Category) categoriesList.getSelectionModel().selectedItemProperty().get();
    }
    private Category getCategorySelected(){
        return categorySelected;
    }

    @FXML
    private void getSelectedPlayList(MouseEvent event) {
        setCategorySelected();
        populateTextField(getCategorySelected().getCategoryName());
    }
}
