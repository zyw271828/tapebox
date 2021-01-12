package com.github.tapebox;

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

    }

    @FXML
    void onRunBtnClick(ActionEvent event) {
        String inputText = pathField.getText();

        if (inputText.equals("")) {
            pathField.setPromptText("Please choose a config file.");
            return;
        } else {
            runBtn.setDisable(true);
            exitBtn.setDisable(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() {
                    try {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                outputArea.setText(inputText);
                            }
                        });
                    } catch (Exception e) {
                        outputArea.setText(e.getMessage());
                    }

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
