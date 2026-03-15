package br.edu.ifba.saj.fwads.controller;

import java.io.IOException;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.CarrinhoVazioException;
import br.edu.ifba.saj.fwads.exception.RemoverCarrinhoException;
import br.edu.ifba.saj.fwads.exception.VerCarrinhoException;
import br.edu.ifba.saj.fwads.model.Carrinho;
import br.edu.ifba.saj.fwads.negocio.facade.CompraFacade;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;


public class ListaCarrinhosController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnExcluirTodos;
    @FXML
    private Button btnRemoverCarrinho;
    @FXML
    private Button btnVerCarrinho;

    @FXML
    private TableColumn<Carrinho, String> colunaCarrinhos;
    @FXML
    private TableColumn<Carrinho, Float> colunaValorTotal;
    @FXML
    private TableView<Carrinho> tabelaListaCarrinhos;
    //Fachada que centraliza o fluxo de compra
    private final CompraFacade compraFacade = new CompraFacade();
    //ObservableList armazena a lista de carrinhos do sistema
    private final ObservableList<Carrinho> listaCarrinhos = FXCollections.observableArrayList();

    //Initialize carrega o método carregar carrinhos logo quando entra na tela
    @FXML
    private void initialize(){
        carregarCarrinhos();
    }
    //Método carregar carrinhos que mostra na tela os carrinhos do cliente que está logado
    private void carregarCarrinhos(){
        try{
            listaCarrinhos.setAll(compraFacade.listarCarrinhosDoClienteLogado());
            tabelaListaCarrinhos.setItems(listaCarrinhos);
    
            colunaCarrinhos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
            colunaValorTotal.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getValorTotal()).asObject());

            tabelaListaCarrinhos.refresh();
        }catch(IllegalStateException e){
            MeuMasterController.exibirAlertaErro(e.getMessage());
        }

    }
    //Método do botão excluir todos os carrinhos
    @FXML
    void excluirTodosCarrinhos(ActionEvent event) {
        ObservableList<Carrinho> todosCarrinhos = tabelaListaCarrinhos.getItems();

        if(!todosCarrinhos.isEmpty()){
            try{
                compraFacade.excluirTodosCarrinhosDoClienteLogado();
                listaCarrinhos.clear();

                carregarCarrinhos();
            }catch(RemoverCarrinhoException e){
                MeuMasterController.exibirAlertaErro(e.getMessage());
            }
        }
    }
    //Método do botão remover o carrinho que foi selecionado pelo cliente
    @FXML
    void removerCarrinhoSelecionado(ActionEvent event) {
        Carrinho carrinhoSelecionado = tabelaListaCarrinhos.getSelectionModel().getSelectedItem();

        if(carrinhoSelecionado != null){
            try{
                compraFacade.removerCarrinho(carrinhoSelecionado);
                listaCarrinhos.remove(carrinhoSelecionado);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("Carrinho removido!");
                alert.showAndWait();
            }catch(RemoverCarrinhoException e){
                MeuMasterController.exibirAlertaErro(e.getMessage());
            }
            
        }else{
            MeuMasterController.exibirAlertaErro("Carrinho não selecionado");
        }
    }
    //Método do botão ver carrinho que foi selecionado pelo cliente, aqui vai para a tela do carrinho de compras
    @FXML
    void verCarrinhoSelecionado(ActionEvent event) throws CarrinhoVazioException, IOException {
        Carrinho carrinhoSelecionado = tabelaListaCarrinhos.getSelectionModel().getSelectedItem();

        if(carrinhoSelecionado != null){
            try{
               compraFacade.validarCarrinho(carrinhoSelecionado);

               FXMLLoader loader = new FXMLLoader(App.class.getResource("controller/TelaCarrinhoDeCompras.fxml"));
               Parent root = loader.load();

               CarrinhoDeComprasController controller = loader.getController();
                controller.setCarrinho(carrinhoSelecionado);

               App.setRoot(root);
            }catch(VerCarrinhoException e){
                MeuMasterController.exibirAlertaErro(e.getMessage());
            }
        }
    }
    //Método do botão voltar
    @FXML
    void voltar(){
        App.setRoot("controller/MeuMaster.fxml");
    }
}
