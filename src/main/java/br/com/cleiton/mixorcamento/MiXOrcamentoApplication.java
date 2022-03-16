package br.com.cleiton.mixorcamento;

import br.com.cleiton.mixorcamento.controller.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MiXOrcamentoApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MiXOrcamentoApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        stage.setTitle("MiXOrçamento");
        stage.setScene(scene);
        stage.setMaximized(Boolean.TRUE);
        stage.setResizable(Boolean.FALSE);
        stage.initStyle(StageStyle.UNDECORATED);
        HomeController home = fxmlLoader.getController();
        stage.show();
        home.expandirMenuBar();
    }

    public static void main(String[] args) {
        launch();
    }
}