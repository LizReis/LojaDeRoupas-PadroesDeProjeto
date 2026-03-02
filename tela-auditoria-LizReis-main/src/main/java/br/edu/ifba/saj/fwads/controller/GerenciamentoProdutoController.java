package br.edu.ifba.saj.fwads.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.ValidaRemocaoException;
import br.edu.ifba.saj.fwads.model.Funcionario;
import br.edu.ifba.saj.fwads.model.Produto;
import br.edu.ifba.saj.fwads.negocio.ValidaProduto;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;


public class GerenciamentoProdutoController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnAtualizarProduto;
    @FXML
    private Button btnCadastrarProduto;
    @FXML
    private Button btnRemoverProduto;
    @FXML
    private Button btnVoltar;

    @FXML
    private TableColumn<Produto, String> colunaProdutoAtualizadoPor;
    @FXML
    private TableColumn<Produto, String> colunaProdutoCor;
    @FXML
    private TableColumn<Produto, String> colunaProdutoCriadoPor;
    @FXML
    private TableColumn<Produto, String> colunaProdutoDataAtualizacao;
    @FXML
    private TableColumn<Produto, String> colunaProdutoDataCriacao;
    @FXML
    private TableColumn<Produto, String> colunaProdutoDepartamento;
    @FXML
    private TableColumn<Produto, String> colunaProdutoModelo;
    @FXML
    private TableColumn<Produto, Float> colunaProdutoPreco;
    @FXML
    private TableColumn<Produto, String> colunaProdutoTamanho;

    @FXML
    private Label lblTitulo;

    @FXML
    private TableView<Produto> tabelaGerenciarProdutos;
    //Instancia do valida produto para chamar métodos
    private ValidaProduto validaProduto = new ValidaProduto();
    //Initialize para carregar os produtos que tem no sistema logo quando vamos para a tela
    @FXML
    private void initialize(){
        carregarProdutos();
        lblTitulo.setText("Produtos");
    }
    //Método que carrega os produtos nas colunas de forma dinâmica
    private void carregarProdutos(){
        ObservableList<Produto> listaProdutos = FXCollections.observableArrayList(ValidaProduto.daoProdutos.buscarTodos());

        tabelaGerenciarProdutos.setItems(listaProdutos);

        colunaProdutoModelo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelo()));
        colunaProdutoTamanho.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTamanho()));
        colunaProdutoCor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCor()));
        colunaProdutoPreco.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getPreco()).asObject());
        colunaProdutoDepartamento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartamento().getNomeDepartamento()));
        colunaProdutoDataCriacao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        colunaProdutoDataAtualizacao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        colunaProdutoCriadoPor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedBy().map(Funcionario::getNome).orElse("N/A")));
        colunaProdutoAtualizadoPor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUpdatedBy().map(Funcionario::getNome).orElse("N/A")));
    }
    //Método do botão cadastrar produto
    @FXML
    void cadastrarProduto(ActionEvent event) {
        App.setRoot("controller/TelaCadastroProduto.fxml");
    }
    //Método do botão atualizar produto
    @FXML
    void atualizarProduto(ActionEvent event) {
        Produto produtoSelecionado = tabelaGerenciarProdutos.getSelectionModel().getSelectedItem();

        if(produtoSelecionado != null) {
            try{
                FXMLLoader loader = new FXMLLoader(App.class.getResource("controller/TelaAtualizarProduto.fxml"));
                Parent root = loader.load();
                
                AtualizarProdutoController controller = loader.getController();
                controller.setProduto(produtoSelecionado);
                App.setRoot(root);
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            MeuMasterController.exibirAlertaErro("Nenhum produto foi selecionado");
        }
    }
    //Método do botão remover produto
    @FXML
    void removerProduto(ActionEvent event) {
        Produto produtoSelecionado = tabelaGerenciarProdutos.getSelectionModel().getSelectedItem();
        try{
            validaProduto.validaRemocao(produtoSelecionado);
            tabelaGerenciarProdutos.getItems().remove(produtoSelecionado);
        }catch(ValidaRemocaoException e){
            MeuMasterController.exibirAlertaErro(e.getMessage());
        }
    }
    //Método do botão voltar
    @FXML
    void voltar(ActionEvent event) {
        App.setRoot("controller/TelaMasterFuncionario.fxml");
    }
}
