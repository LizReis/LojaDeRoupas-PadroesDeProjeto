package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.ValidarAtualizacaoException;
import br.edu.ifba.saj.fwads.model.Produto;
import br.edu.ifba.saj.fwads.negocio.ValidaProduto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AtualizarProdutoController {
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtPreco;

    @FXML
    private Label lblDescricaoProduto;

     //Variável recebe o qual produto o funcionário selecionou na tabela para atualizar
    private Produto produtoAtual;

    //Esse set é apenas um método para mudar o produtoAtual quando o funcionário seleciona e manda para atualizar
    public void setProduto(Produto produto){
        this.produtoAtual = produto;
        lblDescricaoProduto.setText(produtoAtual.getModelo() + " está por: " + produtoAtual.getPreco());
    }

    //Método do botão atualizar produto
    @FXML
    void atualizarProduto(ActionEvent event) throws NumberFormatException, ValidarAtualizacaoException {
        ValidaProduto validaProduto = new ValidaProduto();

        try{
            validaProduto.validaAtualizacao(produtoAtual, Float.parseFloat(txtPreco.getText()));
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Produto atualizado");
            alert.showAndWait();
            App.setRoot("controller/TelaGerenciamentoProduto.fxml");
        }catch(ValidarAtualizacaoException e){
            MeuMasterController.exibirAlertaErro(e.getMessage());
            return;
        }catch(NumberFormatException e){
            MeuMasterController.exibirAlertaErro("Digite um número válido para o preço!");
            return;
        }
    }

    //Método do botão cancelar
    @FXML
    void cancelar(ActionEvent event) {
        App.setRoot("controller/TelaGerenciamentoProduto.fxml");
    }
}
