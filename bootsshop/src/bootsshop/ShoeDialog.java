/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bootsshop;

/**
 *
 * @author user
 */
import entity.Shoe;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ShoeDialog {

    private Shoe shoe;
    private boolean confirmed;

    public ShoeDialog() {
        shoe = new Shoe("", "", 0.0, 0.0);
        confirmed = false;
    }

    public ShoeDialog(Shoe shoe) {
        this.shoe = shoe;
        confirmed = false;
    }

    public void show() {
    Stage dialogStage = new Stage();
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setTitle("Add Shoe");

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setPadding(new Insets(10));

    Label brandLabel = new Label("Brand:");
    TextField brandField = new TextField();
    brandField.setText(shoe.getBrand());

    Label sizeLabel = new Label("Size:");
    TextField sizeField = new TextField();
    sizeField.setText(String.valueOf(shoe.getSize()));

    Label priceLabel = new Label("Price:");
    TextField priceField = new TextField();
    priceField.setText(String.valueOf(shoe.getPrice()));

    Label imageLabel = new Label("Image:");
    ImageView imageView = new ImageView();
    Button chooseImageButton = new Button("Choose Image");
    chooseImageButton.setOnAction(event -> {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(dialogStage);
        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            imageView.setImage(image);
            shoe.setImage(image);
        }
    });

    Button addButton = new Button("Add");
    addButton.setOnAction(event -> {
        shoe.setBrand(brandField.getText());
        shoe.setSize(Double.parseDouble(sizeField.getText()));
        shoe.setPrice(Double.parseDouble(priceField.getText()));
        confirmed = true;
        dialogStage.close();
    });

    gridPane.add(brandLabel, 0, 0);
    gridPane.add(brandField, 1, 0);

    gridPane.add(sizeLabel, 0, 1);
    gridPane.add(sizeField, 1, 1);

    gridPane.add(priceLabel, 0, 2);
    gridPane.add(priceField, 1, 2);

    gridPane.add(imageLabel, 0, 3);
    
    gridPane.add(chooseImageButton, 1, 4);

    gridPane.add(addButton, 1, 5);

    Scene dialogScene = new Scene(gridPane, 400, 300);
    dialogStage.setScene(dialogScene);
    dialogStage.showAndWait();

    // Создаем ячейку для отображения обуви в списке обуви
    class ShoeListCell extends ListCell<Shoe> {
           private final HBox cellLayout;
    private final ImageView shoeImageView;
    private final Label brandLabel;

    public ShoeListCell() {
        shoeImageView = new ImageView();
        shoeImageView.setFitWidth(500); // Увеличьте ширину изображения
        shoeImageView.setFitHeight(500); // Увеличьте высоту изображения
        brandLabel = new Label();

        cellLayout = new HBox(10);
        cellLayout.setAlignment(Pos.CENTER_LEFT);
        cellLayout.getChildren().addAll(shoeImageView, brandLabel);
    }
        @Override
        protected void updateItem(Shoe shoe, boolean empty) {
            super.updateItem(shoe, empty);

            if (shoe == null || empty) {
                setGraphic(null);
            } else {
                shoeImageView.setImage(shoe.getImage());
                brandLabel.setText(shoe.getBrand());
                setGraphic(cellLayout);
            }
        }
    }

    // Создаем список обуви
    ListView<Shoe> shoeListView = new ListView<>();
    shoeListView.setCellFactory(listView -> new ShoeListCell());

    // Добавляем новую обувь в список при нажатии кнопки "Add"
    addButton.setOnAction(event -> {
        shoe.setBrand(brandField.getText());
        shoe.setSize(Double.parseDouble(sizeField.getText()));
        shoe.setPrice(Double.parseDouble(priceField.getText()));
        shoeListView.getItems().add(shoe);
        confirmed = true;
        dialogStage.close();
    });

    // Отображаем список обуви в новом окне
    }

    public Shoe getShoe() {
        return shoe;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
