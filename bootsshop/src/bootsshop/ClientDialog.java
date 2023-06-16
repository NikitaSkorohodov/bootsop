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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientDialog {

    private Client client;
    private boolean confirmed;

    public ClientDialog(Client existingClient) {
        client = new Client(existingClient.getName(), existingClient.getMoney());
        confirmed = false;
    }

    public void show() {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Add Client");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField(client.getName());

        Label moneyLabel = new Label("Money:");
        TextField moneyField = new TextField(Double.toString(client.getMoney()));

        Button updateButton = new Button("Update");
        updateButton.setOnAction(event -> {
            client.setName(nameField.getText());
            client.setMoney(Double.parseDouble(moneyField.getText()));
            confirmed = true;
            dialogStage.close();
        });

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);

        gridPane.add(moneyLabel, 0, 2);
        gridPane.add(moneyField, 1, 2);
        gridPane.add(updateButton, 1, 3);

        Scene dialogScene = new Scene(gridPane, 300, 200);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }

    public Client getClient() {
        return client;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
