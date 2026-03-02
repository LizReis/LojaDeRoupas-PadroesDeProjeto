package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.negocio.SessaoUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class MeuMasterController {

    @FXML
    private Button btnDepartamentos;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnSeusCarrinhos;
    //Método do botão departamentos que o cliente pode ir para a tela de departamentos
    @FXML
    void mostrarDepartamentos(ActionEvent event) {
        App.setRoot("controller/TelaListaDepartamentos.fxml");
    }
    //Método do botão seus carrinhos que o  clientes  pode ir para a tela de lista de carrinhos dele
    @FXML
    void mostrarSeusCarrinhos(ActionEvent event) {
        App.setRoot("controller/TelaListaCarrinhos.fxml");
    }
    //Método do botão logout que o cliente termina sua sessão e vai para a tela de login
    @FXML
    void sairSistema(ActionEvent event){
        SessaoUsuario.logoutCliente();
        App.setRoot("controller/Login.fxml");
    }
    //Método estático usado para tirar repetição da tela de erro
    public static void exibirAlertaErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}