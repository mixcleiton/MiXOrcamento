package br.com.cleiton.mixorcamento.controller;

import br.com.cleiton.mixorcamento.MiXOrcamentoApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CidadeController implements IBaseController {

    @Override
    public void start(Stage window) throws IOException {
        Parent root = FXMLLoader.load(MiXOrcamentoApplication.class.getResource("cidade-view.fxml"));
        Scene scene =  new Scene(root, window.getWidth(), window.getHeight());
        window.setResizable(Boolean.FALSE);
        window.setScene(scene);
        window.show();
    }


}
