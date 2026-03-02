package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.CadastroProdutoException;
import br.edu.ifba.saj.fwads.model.Produto;
import br.edu.ifba.saj.fwads.negocio.ValidaDepartamento;
import br.edu.ifba.saj.fwads.negocio.ValidaProduto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;


public class CadastroProdutoController {
    //-----------------------------------------
    //BOTÕES
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnCancelar;
    //-----------------------------------------
    //TEXTS
    @FXML
    private TextField txtCor;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtPreco;
    @FXML
    private TextField txtTamanho;
    //-------------------------------------------
    @FXML
    private ChoiceBox<String> choiceDepartamento;

    //Método initializa que inicializa os departamentos que poderão aparecer no ChoiceBox
    @FXML
    private void initialize(){
        carregarDepartamentosChoice();
    }
    
    ValidaDepartamento validaDepartamento = new ValidaDepartamento();

    //Métodoque carrega a lista de nomes dos departamentos para aparecer no choiceBox
    private void carregarDepartamentosChoice(){

        ObservableList<String> listaDepartamentos = FXCollections.observableArrayList(validaDepartamento.listarNomesDepartamentos());

        choiceDepartamento.setItems(listaDepartamentos);
    }

    //Método do botão cadastrar Produto
    @FXML
    void cadastrarProduto(ActionEvent event) throws NumberFormatException, CadastroProdutoException {
        ValidaProduto validaProduto = new ValidaProduto();

        String departamentoEscolhinho = choiceDepartamento.getValue();

        try{
            validaProduto.validaCadastroProduto(new Produto(txtModelo.getText(), txtTamanho.getText(), txtCor.getText(), Float.parseFloat(txtPreco.getText()), validaDepartamento.buscarPeloNome(departamentoEscolhinho)));
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Produto cadastrado com sucesso!");
            alert.showAndWait();
            App.setRoot("controller/TelaGerenciamentoProduto.fxml");
        }catch(CadastroProdutoException e){
            MeuMasterController.exibirAlertaErro(e.getMessage());
            return;
        }catch(NumberFormatException e){
            MeuMasterController.exibirAlertaErro("Digite um número válido para o preço.");
            return;
        }
    }

    //Método do botão cancelar
    @FXML
    void cancelar(ActionEvent event) {
        App.setRoot("controller/TelaGerenciamentoProduto.fxml");
    }
}
