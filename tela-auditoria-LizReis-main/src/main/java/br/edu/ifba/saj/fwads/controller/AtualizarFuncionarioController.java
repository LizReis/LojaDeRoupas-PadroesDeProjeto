package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.ValidarAtualizacaoException;
import br.edu.ifba.saj.fwads.model.Funcionario;
import br.edu.ifba.saj.fwads.negocio.ValidaUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AtualizarFuncionarioController {
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtLoginFuncionario;
    @FXML
    private TextField txtNomeFuncionario;
    @FXML
    private TextField txtSenhaFuncionario;
    @FXML
    private TextField txtSetor;

    @FXML
    private Label lblNomeFuncionario;
    //--------------------------------------

     //Variável recebe o qual funcionario o funcionário selecionou na tabela para atualizar
    private Funcionario funcionarioAtual;

    //Esse set é apenas um método para mudar o funcionarioAtual quando o funcionário seleciona e manda para atualizar
    public void setFuncionario(Funcionario funcionario){
        this.funcionarioAtual = funcionario;
        lblNomeFuncionario.setText(funcionarioAtual.getNome() + ", do setor: "+ funcionarioAtual.getSetorTrabalho());
    }

    //Método do botão atualizar funcionario
    @FXML
    void atualizarFuncionario(ActionEvent event) throws ValidarAtualizacaoException {
        ValidaUsuario validaUsuario = new ValidaUsuario();

        try{
            validaUsuario.validaAtualizacao(funcionarioAtual, txtNomeFuncionario.getText(), txtSetor.getText());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Funcionario atualizado");
            alert.showAndWait();
            App.setRoot("controller/TelaGerenciamentoFuncionario.fxml");
        }catch(ValidarAtualizacaoException e){
            MeuMasterController.exibirAlertaErro(e.getMessage());
            return;
        }
    }

    //Método do botão cancelar
    @FXML
    void cancelar(ActionEvent event) {
        App.setRoot("controller/TelaGerenciamentoFuncionario.fxml");
    }
}
