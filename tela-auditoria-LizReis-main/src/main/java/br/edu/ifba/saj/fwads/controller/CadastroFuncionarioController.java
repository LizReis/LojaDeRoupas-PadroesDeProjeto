package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.CadastroUsuarioException;
import br.edu.ifba.saj.fwads.model.Funcionario;
import br.edu.ifba.saj.fwads.negocio.ValidaUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CadastroFuncionarioController {
    //-------------------------------------
    //BOTÕES 
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnCancelar;
    //--------------------------------------
    //TEXTS
    @FXML
    private TextField txtLoginFuncionario;
    @FXML
    private TextField txtNomeFuncionario;
    @FXML
    private TextField txtSenhaFuncionario;
    @FXML
    private TextField txtSetor;


    //---------------------------------------
    //Método do botão cadastrar Funcionario
    @FXML
    void cadastrarFuncionario(ActionEvent event) {
        ValidaUsuario validaUsuario = new ValidaUsuario();

        try{
            validaUsuario.validaCadastroFuncionario(new Funcionario(txtNomeFuncionario.getText(), txtLoginFuncionario.   getText(), txtSenhaFuncionario.getText(), txtSetor.getText()));
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Funcionário cadastrado com sucesso!");
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
