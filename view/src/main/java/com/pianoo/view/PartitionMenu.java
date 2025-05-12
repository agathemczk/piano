package com.pianoo.view;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.File;

/**
 * Implémentation de la vue de lecture de partition avec JavaFX.
 */
public class PartitionMenu implements IPartitionMenu {
    private Stage primaryStage;

    public PartitionMenu(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public File showScoreFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier de partition");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers de partition", "*.txt"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        return fileChooser.showOpenDialog(primaryStage);
    }

    @Override
    public void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}