package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.LoginInvalidoException;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.Funcionario;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.ValidaUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;

public class LoginController {

    @FXML // fx:id="txtSenha"
    private PasswordField txtSenha; // Value injected by FXMLLoader

    @FXML // fx:id="txtUsuario"
    private TextField txtUsuario; // Value injected by FXMLLoader

    @FXML
    private ChoiceBox<String> choiceEntrar;
    //Método initialize que inicializa as opções de como um mesmo usuário com login e senha pode entrar
    @FXML
    public void initialize() {
        choiceEntrar.getItems().addAll("Cliente", "Funcionário");
    }


    //Método do botão entrar que vai para o MeuMaster caso seja cliente
    //E vai para MasterFuncionario caso seja funcionario
    @FXML
    void entrar(ActionEvent event) throws LoginInvalidoException{
        ValidaUsuario validaUsuario = new ValidaUsuario();
        String tipoSelecionado = choiceEntrar.getValue();

        try {
            Usuario resultado = validaUsuario.validaLogin(txtUsuario.getText(), txtSenha.getText(), tipoSelecionado);
    
            if(resultado instanceof Funcionario){
                new Alert(AlertType.INFORMATION, "Usuário e senha corretos").showAndWait();
                App.setRoot("controller/TelaMasterFuncionario.fxml");
            }else if(resultado instanceof Cliente){
                new Alert(AlertType.INFORMATION, "Usuário e senha corretos").showAndWait();
                App.setRoot("controller/MeuMaster.fxml");
            }else{
                MeuMasterController.exibirAlertaErro("Você não tem login para esse tipo ou selecione o tipo correto do seu login.");
            }
        } catch(LoginInvalidoException e) {
            MeuMasterController.exibirAlertaErro(e.getMessage());
        }
    }

    //Método do link que vai para tela de cadastrado do cliente
    @FXML
    void mudarTelaCadastroCliente(ActionEvent event){
        App.setRoot("controller/TelaCadastroCliente.fxml");
    }
    //Método do link que vai para a tela de cadsatro do funcionario
    @FXML
    void mudarTelaCadastroFuncionario(ActionEvent event){
        App.setRoot("controller/TelaCadastroFuncionario.fxml");
    }

    @FXML
    void verAuditorias(ActionEvent event){
        App.setRoot("controller/Auditoria.fxml");
    }
}
