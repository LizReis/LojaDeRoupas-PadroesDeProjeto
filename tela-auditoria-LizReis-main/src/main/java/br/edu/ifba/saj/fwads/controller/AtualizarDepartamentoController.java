package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.ValidarAtualizacaoException;
import br.edu.ifba.saj.fwads.model.Departamento;
import br.edu.ifba.saj.fwads.negocio.ValidaDepartamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AtualizarDepartamentoController {
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtNomeDepartamento;

    @FXML
    private Label lblNomeDepartamento;

     //Variável recebe o qual departamento o funcionário selecionou na tabela para atualizar
    private Departamento departamentoAtual;

    //Esse setDepartamento é apenas um método para mudar o departamentoAtual quando o funcionário seleciona e manda para atualizar
    public void setDepartamento(Departamento departamento){
        this.departamentoAtual = departamento;
        lblNomeDepartamento.setText(departamentoAtual.getNomeDepartamento());
    }

    //Método do botão atualizar departamento
    @FXML
    void atualizarDepartamento(ActionEvent event) throws ValidarAtualizacaoException {
        ValidaDepartamento validaDepartamento = new ValidaDepartamento();

        try{
            validaDepartamento.validaAtualizacao(departamentoAtual, txtNomeDepartamento.getText());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Departamento atualizado");
            alert.showAndWait();
            App.setRoot("controller/TelaGerenciamentoDepartamento.fxml");
        }catch(ValidarAtualizacaoException e){
            MeuMasterController.exibirAlertaErro(e.getMessage());
            return;
        }
           

       
    }

    //Método do botão cancelar que volta para a tela de gerenciamento
    @FXML
    void cancelar(ActionEvent event) {
        App.setRoot("controller/TelaGerenciamentoDepartamento.fxml");
    }
}
