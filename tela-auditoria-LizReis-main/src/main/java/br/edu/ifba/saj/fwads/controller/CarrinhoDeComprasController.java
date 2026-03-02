package br.edu.ifba.saj.fwads.controller;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.CarrinhoVazioException;
import br.edu.ifba.saj.fwads.exception.RemoverCarrinhoException;
import br.edu.ifba.saj.fwads.exception.ValidaRemocaoException;
import br.edu.ifba.saj.fwads.exception.ValidarFinalizarCompraException;
import br.edu.ifba.saj.fwads.model.Carrinho;
import br.edu.ifba.saj.fwads.model.ItemCompra;
import br.edu.ifba.saj.fwads.negocio.ValidaCarrinhos;


public class CarrinhoDeComprasController {
    //--------------------------------------------------
    //BOTÃO FINALIZAR COMPRA
    @FXML
    private Button btnFinalizarCompra;
    @FXML
    private Button btnRemoverItem;
    //--------------------------------------------------
    //TABELA DO CARRINHO
    @FXML
    private TableView<ItemCompra> tabelaCarrinhoCompras;
    //--------------------------------------------------
    //COLUNAS DO CARRINHO
    @FXML
    private TableColumn<ItemCompra, String> colunaProdutoCarrinhoCompra;
    @FXML
    private TableColumn<ItemCompra, Integer> colunaQuantidadeCarrinhoCompra;
    @FXML
    private TableColumn<ItemCompra, Float> colunaPrecoCarrinhoCompra;
    @FXML
    private TableColumn<ItemCompra, Void> colunaAcoesCarrinhoCompra;
    //--------------------------------------------------
    //LABEL QUE MOSTRA VALOR TOTAL
    @FXML
    private Label lblValorTotalCompra;

    @FXML
    private TitledPane tituloPaneCarrinho;

     //Variável recebe o qual carrinho o funcionário selecionou na tabela para atualizar
    private Carrinho carrinho;
    //Instancia do Valida carrinhos para chamar métodos 
    ValidaCarrinhos validaCarrinhos = new ValidaCarrinhos();
    
    //Esse set é apenas um método para mudar o carrinho atual quando o cliente seleciona seu carrinho
    public void setCarrinho(Carrinho carrinho) throws CarrinhoVazioException {
        this.carrinho = carrinho;
        carregarItensCarrinho();
        tituloPaneCarrinho.setText(carrinho.getNome());
    }

    //Método que carrega os itens que o cliente tem no carrinho
    private void carregarItensCarrinho() throws CarrinhoVazioException{
        ObservableList<ItemCompra> itensCompra = FXCollections.observableArrayList(validaCarrinhos.pegarItens(carrinho));

        tabelaCarrinhoCompras.setItems(itensCompra);
        //Colunas que pegam os atributos de cada item de forma dinâmica
        colunaProdutoCarrinhoCompra.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduto().descricaoProduto()));
        colunaQuantidadeCarrinhoCompra.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantidade()));
        colunaPrecoCarrinhoCompra.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getValorPorProduto()).asObject());

        //Como essa coluna quantidade tem spinner ela é chamada dessa forma para que todo item tenha seu spinner
        colunaAcoesCarrinhoCompra.setCellFactory(coluna -> new javafx.scene.control.TableCell<>() {
            private final Spinner<Integer> spinner = new Spinner<>(1, 100, 1);

            {
                //Spinner adicionado dinamicamente para a coluna quantidade
                //Ações: Spinner para mudar quantidade
                spinner.setEditable(true);
                spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                    ItemCompra item = getTableView().getItems().get(getIndex());

                    item.setQuantidade(newValue);
                    item.setValorPorProduto(0);

                    carrinho.setValorTotal();
                    tabelaCarrinhoCompras.refresh();
                    lblValorTotalCompra.setText(String.format("R$ %.2f", carrinho.getValorTotal()));
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                ItemCompra currentItem = getTableView().getItems().get(getIndex());
                spinner.getValueFactory().setValue(currentItem.getQuantidade());
                setGraphic(spinner);
            }
        }
    });

        //Calcula o valor total dos itens para jogar no label RS0,00
        carrinho.setValorTotal();
        lblValorTotalCompra.setText(String.format("R$ %.2f", carrinho.getValorTotal()));
    } 

    //Método do botão finalizar compra
    @FXML
    void finalizarCompra(ActionEvent event) throws RemoverCarrinhoException, ValidarFinalizarCompraException {
        try{
            validaCarrinhos.finalizarCompra(carrinho);
            App.setRoot("controller/TelaSucesso.fxml");
        }catch(ValidarFinalizarCompraException e){
            MeuMasterController.exibirAlertaErro(e.getMessage());
        }catch(RemoverCarrinhoException e){
            MeuMasterController.exibirAlertaErro(e.getMessage());
        }     
    }

    //Método do botão remover item, que remove um item selecionado do carrinho
    @FXML
    void removerItem(ActionEvent event) throws ValidaRemocaoException, CarrinhoVazioException, RemoverCarrinhoException {
        ItemCompra itemSelecionado = tabelaCarrinhoCompras.getSelectionModel().getSelectedItem();

        if(itemSelecionado != null){
            try{
                Carrinho carrinhoAtual = validaCarrinhos.removerItemCarrinho(itemSelecionado);

                tabelaCarrinhoCompras.getItems().remove(itemSelecionado);
                lblValorTotalCompra.setText(String.format("R$ %.2f", carrinhoAtual.getValorTotal()));

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("Item removido com sucesso!");
                alert.showAndWait();
            }catch(ValidaRemocaoException e){
                MeuMasterController.exibirAlertaErro(e.getMessage());
            }
        }else{
            MeuMasterController.exibirAlertaErro("Selecione um item para remover.");
            return;
        }

    }

    //Método do botão voltar
    @FXML
    void voltar(ActionEvent event){
        App.setRoot("controller/TelaListaCarrinhos.fxml");
    }
}
