package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.negocio.SessaoUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MasterFuncionarioController {
    @FXML
    private Button btnGerenciarClientes;
    @FXML
    private Button btnGerenciarDepartamentos;
    @FXML
    private Button btnGerenciarFuncionarios;
    @FXML
    private Button btnGerenciarProdutos;
    @FXML
    private Button btnLogout;
    //Método do botão gerenciar clientes que o funcionario pode ir para a tela de gerenciamento
    @FXML
    void gerenciarClientes(ActionEvent event) {
        App.setRoot("controller/TelaGerenciamentoCliente.fxml");
    }
    //Método do botão gerenciar departamentos que o funcionario pode ir para a tela de gerenciamento
    @FXML
    void gerenciarDepartamentos(ActionEvent event) {
        App.setRoot("controller/TelaGerenciamentoDepartamento.fxml");
    }
    //Método do botão gerenciar funcionarios que o funcionario pode ir para a tela de gerenciamento
    @FXML
    void gerenciarFuncionarios(ActionEvent event) {
        App.setRoot("controller/TelaGerenciamentoFuncionario.fxml");
    }
    //Método do botão gerenciar produtos que o funcionario pode ir para a tela de gerenciamento
    @FXML
    void gerenciarProdutos(ActionEvent event) {
        App.setRoot("controller/TelaGerenciamentoProduto.fxml");
    }
    //Método do botão de logout que chama o logout de sessão usuario e volta para a tela de login
    @FXML
    void logout(ActionEvent event) {
        SessaoUsuario.logout();
        App.setRoot("controller/Login.fxml");
    }


}
