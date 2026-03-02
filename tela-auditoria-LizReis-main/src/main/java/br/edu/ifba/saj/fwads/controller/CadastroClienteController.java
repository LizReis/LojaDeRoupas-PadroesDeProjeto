package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.CadastroUsuarioException;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.negocio.ValidaUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CadastroClienteController {
    //-------------------------------------
    //BOTÕES 
    @FXML
    private Button btnCadastrarCliente;
    @FXML
    private Button btnCancelar;
    //--------------------------------------
    //TEXTS
    @FXML
    private TextField txtLoginCliente;
    @FXML
    private TextField txtNomeCliente;
    @FXML
    private TextField txtSenhaCliente;
    //---------------------------------------
    //LABEL ERRO OU SUCESSO
    @FXML
    private Label lblErro;

    //---------------------------------------
    //Método do botão cadastrar cliente
    @FXML
    void cadastrarCliente(ActionEvent event) {
        ValidaUsuario validaUsuario = new ValidaUsuario();
        
        try{
            validaUsuario.validaCadastroCliente(new Cliente(txtNomeCliente.getText(), txtLoginCliente.getText(), txtSenhaCliente.getText()));
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Cliente cadastrado com sucesso!");
            alert.showAndWait();
            App.setRoot("controller/Login.fxml");
        }catch(CadastroUsuarioException e){
            MeuMasterController.exibirAlertaErro(e.getMessage());
            return;
        } 

    }

    //Método do botão cancelar
    @FXML
    void cancelar(){
        App.setRoot("controller/Login.fxml");
    }
    


}
