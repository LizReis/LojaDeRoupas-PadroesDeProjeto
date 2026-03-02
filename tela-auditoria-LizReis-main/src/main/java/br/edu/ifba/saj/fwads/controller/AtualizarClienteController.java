package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.ValidarAtualizacaoException;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.negocio.ValidaUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AtualizarClienteController {
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtNomeCliente;

    @FXML
    private Label lblNomeCliente;
    //-----------------------------------------------------------

    //Variável recebe o qual cliente o funcionário selecionou na tabela para atualizar
    private Cliente clienteAtual;

    //Esse setCliente é apenas um método para mudar o clienteAtual quando o funcionário seleciona e manda para atualizar
    public void setCliente(Cliente cliente){
        this.clienteAtual = cliente;
        //label que informana tela quem é o cliente selecionado pelo nome
        lblNomeCliente.setText(clienteAtual.getNome());
    }

    //Método do botão atualizar cliente
    @FXML
    void atualizarCliente(ActionEvent event) throws ValidarAtualizacaoException {
        ValidaUsuario validaUsuario = new ValidaUsuario();

        try{
            validaUsuario.validaAtualizacao(clienteAtual, txtNomeCliente.getText(), null);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Cliente atualizado");
            alert.showAndWait();
            App.setRoot("controller/TelaGerenciamentoCliente.fxml");
        }catch(ValidarAtualizacaoException e){
            MeuMasterController.exibirAlertaErro(e.getMessage());
            return;
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        App.setRoot("controller/TelaGerenciamentoCliente.fxml");
    }
}
