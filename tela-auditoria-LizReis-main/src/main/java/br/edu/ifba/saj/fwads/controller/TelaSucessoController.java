package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
public class TelaSucessoController {

    @FXML
    private Button btnVoltar;
    //Método do botão voltar 
    @FXML
    void voltar(ActionEvent event) {
        App.setRoot("controller/TelaCarrinhoDeCompras.fxml");
    }


}
