package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.CriarCarrinhoException;
import br.edu.ifba.saj.fwads.model.Carrinho;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.Departamento;
import br.edu.ifba.saj.fwads.model.Produto;
import br.edu.ifba.saj.fwads.negocio.SessaoUsuario;
import br.edu.ifba.saj.fwads.negocio.ValidaCarrinhos;
import br.edu.ifba.saj.fwads.negocio.ValidaProduto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableCell;

public class ProdutosDeDepartamentoController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnAdicionarCarrinho;
    @FXML
    private Button btnVoltar;

    @FXML
    private TableColumn<Produto, Float> colunaPrecos;
    @FXML
    private TableColumn<Produto, String> colunaProdutos;
    @FXML
    private TableColumn<Produto, String> colunaQuantidades;

    @FXML
    private TableView<Produto> tabelasDeProdutos;
    //Variavel departamento que armazena o departamento atual selecionado pelo usuario para ver os produtos
    private Departamento departamento;
    //Varivale que armazena a quantidade digitada pelo usuario no textField de cada linha
    private final ObservableMap<Produto, StringProperty> quantidadeMap = FXCollections.observableHashMap();
    //Instancia de valida carrinhos para chamar metodos
    private ValidaCarrinhos validaCarrinhos = new ValidaCarrinhos();
    //variavel que armazena o cliente que estálogado no sistema
    Cliente clienteLogado = (Cliente) SessaoUsuario.getClienteLogado();
    
    ////Esse set é apenas um método para mudar o departamento quando o cliente seleciona para ver um novo departamento
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
        carregarProdutosDoDepartamento();
    }
    //Carrega os produtos do departamento selecionado pelo cliente e mostra nas colunas
    private void carregarProdutosDoDepartamento(){
        ValidaProduto validaProduto = new ValidaProduto();
        ObservableList<Produto> todosProdutos = FXCollections.observableArrayList(validaProduto.listarProdutos());
        ObservableList<Produto> produtosDoDepartamento = FXCollections.observableArrayList();

        for(Produto produtos : todosProdutos){
            if(produtos.getDepartamento().equals(departamento)){
                produtosDoDepartamento.add(produtos);
            }
        }
        tabelasDeProdutos.setItems(produtosDoDepartamento);

        colunaProdutos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().descricaoProduto()));
        colunaPrecos.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPreco()));

        //Mapeia os valores digitados em cada linha
        colunaQuantidades.setCellValueFactory(cellData -> quantidadeMap.computeIfAbsent(cellData.getValue(), p -> new SimpleStringProperty("")));

        //Adiciona o TextField à célula
        colunaQuantidades.setCellFactory(tc -> new TableCell<>() {
        private final TextField textField = new TextField();

        {
            textField.setMaxWidth(70);
            textField.setPromptText("Qtd");
            textField.setOnKeyReleased(event -> {
                Produto produto = getTableView().getItems().get(getIndex());
                quantidadeMap.get(produto).set(textField.getText()); // Atualiza o valor no mapa
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || getIndex() >= getTableView().getItems().size()) {
                setGraphic(null);
            } else {
                Produto produto = getTableView().getItems().get(getIndex());
                textField.setText(quantidadeMap.get(produto).get());
                setGraphic(textField);
            }
        }
    });
}
    //Método do botão adicionar ao carrinho que vai olhar se o usuário tem carrinhos primeiro
    //Caso ele não tenha, ele cria um carrinho padrão. Caso ele já tenha exibi uma telinha com os carrinhos que ele
    //ja tem para selecionar ou digitar o nome de um que será criado
    @FXML
    void adicionarAoCarrinho(ActionEvent event) {
        Produto produtoSelecionado = tabelasDeProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            String quantidadeTexto = quantidadeMap.get(produtoSelecionado).get();
                    
            if (quantidadeTexto == null || quantidadeTexto.trim().isEmpty()) {
                MeuMasterController.exibirAlertaErro("Digite a quantidade antes de adicionar ao carrinho!");
                return;
            }
            
            try {
                int quantidadeInteger = Integer.parseInt(quantidadeTexto);
                Carrinho resultado = validaCarrinhos.temCarrinhos(clienteLogado, produtoSelecionado, quantidadeInteger);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("Produto adicionado ao " + resultado.getNome() +" !");
                alert.showAndWait();
            } catch (NumberFormatException e) {
                MeuMasterController.exibirAlertaErro("Digite um número válido para a quantidade!");
            } catch (CriarCarrinhoException e) {
                int quantidadeInteger = Integer.parseInt(quantidadeTexto);
                exibirTelaCarrinhosSelecao(clienteLogado, produtoSelecionado, quantidadeInteger);
                e.printStackTrace();
            }
        }else{
            MeuMasterController.exibirAlertaErro("Selecione um carrinho.");
        }

    }   
    //Esse método é chamado caso o cliente tenha optado por adicionar o produto ao carrinho que ele criou na telinha
    //exibida para selecionar carrinho
    private void adicionarProdutoAoCarrinho(Cliente cliente, Produto produto, int quantidade, String nomeCarrinho){
        Carrinho resultado = validaCarrinhos.criarCarrinhoNomeSelecao(clienteLogado, nomeCarrinho, produto, quantidade);
        if(resultado != null){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Produto adicionado ao " + resultado.getNome() +" !");
            alert.showAndWait();
        }else{
            MeuMasterController.exibirAlertaErro("Não foi possível criar um carrinho.");
            return;
        }
    }


    //Essa janela será exibida caso o cliente tenha carrinhos, então ele tem que selecionar o nome do
    //carrinho que ele quer adicionar o produto ou digitar o nome de um que será criado 
    private void exibirTelaCarrinhosSelecao(Cliente cliente, Produto produto, int quantidade){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Selecionar Carrinho");
        dialog.setHeaderText("Escolha um carrinho ou digite um nome para um novo.");

        ButtonType adicionarButton = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelarButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(adicionarButton, cancelarButton);

        ListView<Carrinho> listView = new ListView<>();
        listView.getItems().addAll(cliente.getCarrinhos());

        TextField nomeCarrinhoField = new TextField();
        nomeCarrinhoField.setPromptText("Digite o nome do carrinho");

        VBox vbox = new VBox(10, listView, nomeCarrinhoField);
        dialog.getDialogPane().setContent(vbox);

        // Obtendo o resultado
         dialog.setResultConverter(dialogButton -> {
            if (dialogButton == adicionarButton) {
                // Verifica se foi digitado um nome para um novo carrinho
                String nomeCarrinho = nomeCarrinhoField.getText().trim();
                Carrinho carrinhoSelecionado = listView.getSelectionModel().getSelectedItem();
            
            if(!nomeCarrinho.isEmpty()) {
                return nomeCarrinho;
            }else if(carrinhoSelecionado != null) {
                return carrinhoSelecionado.getNome();
            }
            }
            return null;
        });

        //Mostrar e aguardar ação do usuário
        dialog.showAndWait().ifPresent(nomeCarrinho -> {
            if (nomeCarrinho != null && !nomeCarrinho.isEmpty()) {
                adicionarProdutoAoCarrinho(cliente, produto, quantidade, nomeCarrinho);
            }
        });
    }

    //Método do botão voltar
    @FXML
    void voltarAnterior(ActionEvent event) {
        App.setRoot("controller/TelaListaDepartamentos.fxml");
    }
}