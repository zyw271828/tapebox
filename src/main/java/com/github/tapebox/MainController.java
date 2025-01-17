package com.github.tapebox;

import java.io.File;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private JFXButton chooseBtn;

    @FXML
    private JFXButton runBtn;

    @FXML
    private JFXButton exitBtn;

    @FXML
    private JFXTextField pathField;

    @FXML
    private JFXTextField txField;

    @FXML
    private JFXTextField durationField;

    @FXML
    private Label tpsLabel;

    @FXML
    private JFXTextArea outputArea;

    @FXML
    void onChooseBtnClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose Config File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Config Files", "*.yaml"),
                new ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog((Stage) chooseBtn.getScene().getWindow());
        if (selectedFile != null) {
            pathField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    void onRunBtnClick(ActionEvent event) {
        String filePath = pathField.getText();
        String tx = txField.getText();

        if (filePath.equals("")) {
            outputArea.setPromptText("Please choose a config file.");
            return;
        } else if (!Helper.isFileExist(filePath)) {
            outputArea.setPromptText("The config file does not exist.");
            return;
        } else if (tx.equals("")) {
            outputArea.setPromptText("Please enter tx.");
            return;
        } else if (!Helper.isNumber(tx)) {
            outputArea.setPromptText("Please enter tx as a number.");
            return;
        } else {
            chooseBtn.setDisable(true);
            runBtn.setDisable(true);
            exitBtn.setDisable(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() {
                    try {
                        String output = TapeRunner.runTape(filePath, tx);
                        outputArea.setText(output);
                        txField.setText(Helper.getTx(output));
                        durationField.setText(Helper.getDuration(output));
                        String tps = Helper.getTps(output);

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                tpsLabel.setText(tps);
                            }
                        });
                    } catch (Exception e) {
                        outputArea.setText(e.getMessage());
                    }

                    chooseBtn.setDisable(false);
                    runBtn.setDisable(false);
                    exitBtn.setDisable(false);

                    return true;
                }
            };

            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent wse) {
                    if (task.getValue()) {

                    }
                }
            });

            new Thread(task).start();
        }
    }

    @FXML
    void onExitBtnClick(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
}
