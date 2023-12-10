package com.example.db2023_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    @FXML
    private Button loginButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;


    public void loginButtonOnAction(ActionEvent e) throws IOException {
        System.out.println("ㅇㅇ 눌림");
        if(!emailTextField.getText().isBlank() && !passwordTextField.getText().isBlank()){
            //뭐라도 입력되어 있으면 로그인
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("inpatient-list.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}