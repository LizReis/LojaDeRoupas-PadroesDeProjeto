package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.CadastroDepartamentoException;
import br.edu.ifba.saj.fwads.model.Departamento;
import br.edu.ifba.saj.fwads.negocio.ValidaDepartamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CadastroDepartamentoController {
    //------------------------------------------------
    //BOTÕES
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnCancelar;
    //-------------------------------------------------
    //TEXTS
    @FXML
    private TextField txtNomeDepartamento;
    //--------------------------------------------------
    //Método do botão cadastrar Departamento
    @FXML
    void cadastrarDepartamento(ActionEvent event) {
        ValidaDepartamento validaDepartamento = new ValidaDepartamento();

        try{
            validaDepartamento.validaCadastroDepartamento(new Departamento(txtNomeDepartamento.getText()));
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Departamento cadastrado com sucesso!");
            alert.showAndWait();
            App.setRoot("controller/TelaGerenciamentoDepartamento.fxml");
        }catch(CadastroDepartamentoException e){
            MeuMasterController.exibirAlertaErro(e.getMessage());
            return;
        }
    }

    //Método do botão cancelar
    @FXML
    void cancelar(ActionEvent event) {
        App.setRoot("controller/TelaGerenciamentoDepartamento.fxml");
    }

}
