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
import entity.Client;
import entity.Shoe;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import javafx.scene.image.ImageView;

public class StoreManager extends Application {

    private ObservableList<Shoe> shoes;
    private ObservableList<Client> clients;

    private double totalRevenue; // Общий оборот магазина

    public StoreManager() {
        shoes = FXCollections.observableArrayList();
        clients = FXCollections.observableArrayList();
        totalRevenue = 0.0;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shoe Store");

        // Create the main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));

        // Create the Buttons view
        HBox buttonsBox = new HBox(10);
        Button shoeButton = new Button("Shoe");
        Button clientButton = new Button("Client");
        Button revenueButton = new Button("Total Revenue"); // Кнопка оборота
        buttonsBox.getChildren().addAll(shoeButton, clientButton, revenueButton);
        mainLayout.setBottom(buttonsBox);
        buttonsBox.setAlignment(Pos.CENTER);

        // Handle Shoe button click
        shoeButton.setOnAction(event -> {
            Stage shoeStage = new Stage();
            shoeStage.setTitle("Shoes");

            ListView<Shoe> shoeListViewDialog = new ListView<>(shoes);
            shoeListViewDialog.setCellFactory(param -> new ShoeListCell());
            shoeListViewDialog.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            Button addShoeButtonDialog = new Button("Add Shoe");
            addShoeButtonDialog.setOnAction(addEvent -> {
                ShoeDialog shoeDialog = new ShoeDialog();
                shoeDialog.show();
                if (shoeDialog.isConfirmed()) {
                    shoes.add(shoeDialog.getShoe());
                }
            });

            Button editShoeButtonDialog = new Button("Edit Shoe");
            editShoeButtonDialog.setOnAction(editEvent -> {
                Shoe selectedShoe = shoeListViewDialog.getSelectionModel().getSelectedItem();
                if (selectedShoe != null) {
                    ShoeDialog shoeDialog = new ShoeDialog(selectedShoe);
                    shoeDialog.show();
                    if (shoeDialog.isConfirmed()) {
                        shoeListViewDialog.refresh();
                    }
                }
            });

            Button buyShoeButtonDialog = new Button("Buy");
            buyShoeButtonDialog.setOnAction(buyEvent -> {
                Shoe selectedShoe = shoeListViewDialog.getSelectionModel().getSelectedItem();
                if (selectedShoe != null) {
                    Client selectedClient = showClientSelectionDialog();
                    if (selectedClient != null) {
                        selectedClient.buyShoe(selectedShoe);
                        totalRevenue += selectedShoe.getPrice(); // Увеличение оборота при покупке
                    }
                } else {
                    // Show an alert if no shoe is selected
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No Shoe Selected");
                    alert.setHeaderText(null);
                    alert.setContentText("Please select a shoe to buy.");
                    alert.showAndWait();
                }
            });

            VBox shoeDialogLayout = new VBox(10);
            shoeDialogLayout.setPadding(new Insets(10));
            shoeDialogLayout.getChildren().addAll(shoeListViewDialog, addShoeButtonDialog, editShoeButtonDialog, buyShoeButtonDialog);

            Scene shoeScene = new Scene(shoeDialogLayout);
            shoeStage.setScene(shoeScene);
            shoeStage.show();
        });

        // Handle Client button click
        clientButton.setOnAction(event -> {
            Stage clientStage = new Stage();
            clientStage.setTitle("Clients");

            ListView<Client> clientListViewDialog = new ListView<>(clients);

            Button addClientButtonDialog = new Button("Add Client");
            addClientButtonDialog.setOnAction(addEvent -> {
                ClientDialog clientDialog = new ClientDialog(new Client());
                clientDialog.show();
                if (clientDialog.isConfirmed()) {
                    clients.add(clientDialog.getClient());
                }
            });

            Button editClientButtonDialog = new Button("Edit Client");
            editClientButtonDialog.setOnAction(editEvent -> {
                Client selectedClient = clientListViewDialog.getSelectionModel().getSelectedItem();
                if (selectedClient != null) {
                    ClientDialog clientDialog = new ClientDialog(selectedClient);
                    clientDialog.show();
                    if (clientDialog.isConfirmed()) {
                        // Update the client in the list
                        int selectedIndex = clientListViewDialog.getSelectionModel().getSelectedIndex();
                        clients.set(selectedIndex, clientDialog.getClient());
                    }
                } else {
                    // Show an alert if no client is selected
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No Client Selected");
                    alert.setHeaderText(null);
                    alert.setContentText("Please select a client to edit.");
                    alert.showAndWait();
                }
            });

            VBox clientDialogLayout = new VBox(10);
            clientDialogLayout.setPadding(new Insets(10));
            clientDialogLayout.getChildren().addAll(clientListViewDialog, addClientButtonDialog, editClientButtonDialog);

            Scene clientScene = new Scene(clientDialogLayout);
            clientStage.setScene(clientScene);
            clientStage.show();
        });

        // Handle Total Revenue button click
        revenueButton.setOnAction(event -> {
            // Show the total revenue in an alert dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Total Revenue");
            alert.setHeaderText(null);
            alert.setContentText("Total Revenue: $" + totalRevenue);
            alert.showAndWait();
        });

        // Create the main scene
        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Client showClientSelectionDialog() {
        Stage clientSelectionStage = new Stage();
        clientSelectionStage.setTitle("Select Client");

        ListView<Client> clientListView = new ListView<>(clients);
        clientListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Button selectClientButton = new Button("Select");
        selectClientButton.setOnAction(event -> {
            Client selectedClient = clientListView.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                clientSelectionStage.close();
            } else {
                // Show an alert if no client is selected
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Client Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a client.");
                alert.showAndWait();
            }
        });

        VBox clientSelectionLayout = new VBox(10);
        clientSelectionLayout.setPadding(new Insets(10));
        clientSelectionLayout.getChildren().addAll(clientListView, selectClientButton);

        Scene clientSelectionScene = new Scene(clientSelectionLayout);
        clientSelectionStage.setScene(clientSelectionScene);
        clientSelectionStage.showAndWait();

        return clientListView.getSelectionModel().getSelectedItem();
    }

    class ShoeListCell extends ListCell<Shoe> {
        private final VBox content;
        private final ImageView imageView;
        private final Label brandLabel;
        private final Label sizeLabel;
        private final Label priceLabel;

        public ShoeListCell() {
            content = new VBox();
            content.setSpacing(5);
            imageView = new ImageView();
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            brandLabel = new Label();
            sizeLabel = new Label();
            priceLabel = new Label();

            content.getChildren().addAll(imageView, brandLabel, sizeLabel, priceLabel);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }

        @Override
        protected void updateItem(Shoe item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
            } else {
                imageView.setImage(item.getImage());
                brandLabel.setText("Brand: " + item.getBrand());
                sizeLabel.setText("Size: " + item.getSize());
                priceLabel.setText("Price: " + item.getPrice());
                setGraphic(content);
            }
        }
    }
}
