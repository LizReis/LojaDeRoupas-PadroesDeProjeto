package br.edu.ifba.saj.fwads.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.ValidaRemocaoException;
import br.edu.ifba.saj.fwads.exception.ValidarAtualizacaoException;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.Funcionario;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.ValidaUsuario;
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

public class GerenciamentoClienteController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnAtualizarCliente;
    @FXML
    private Button btnCadastrarCliente;
    @FXML
    private Button btnRemoverCliente;
    @FXML
    private Button btnVoltar;

    @FXML
    private TableColumn<Cliente, String> colunaClienteAtualizadoPor;
    @FXML
    private TableColumn<Cliente, String> colunaClienteCriadoPor;
    @FXML
    private TableColumn<Cliente, String> colunaClienteDataAtualizacao;
    @FXML
    private TableColumn<Cliente, String> colunaClienteDataCriacao;
    @FXML
    private TableColumn<Cliente, String> colunaClienteLogin;
    @FXML
    private TableColumn<Cliente, String> colunaClienteNome;

    @FXML
    private Label lblTitulo;

    @FXML
    private TableView<Cliente> tabelaGerenciarClientes;

    //Instancia de valida usuário
    private ValidaUsuario validaUsuario = new ValidaUsuario();
    //Initialize inicia os clientes que tem no sistema para mostrar na tela
    @FXML
    private void initialize(){
        carregarClientes();
        lblTitulo.setText("Clientes");
    }

    //Método que carrega os clientes de forma dinâmica nas colunas
    private void carregarClientes(){
        ValidaUsuario validaUsuario = new ValidaUsuario();
        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList(validaUsuario.listarUsuarios());

        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

        for(Usuario usuarios : listaUsuarios){
            if(usuarios instanceof Cliente cliente){
                listaClientes.add(cliente);
            }
        }
        
        tabelaGerenciarClientes.setItems(listaClientes);

        colunaClienteNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colunaClienteLogin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogin()));
        colunaClienteDataCriacao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        colunaClienteDataAtualizacao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        colunaClienteCriadoPor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedBy().map(Funcionario::getNome).orElse("N/A")));
        colunaClienteAtualizadoPor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUpdatedBy().map(Funcionario::getNome).orElse("N/A")));
        

    }
    //Método do botão cadastrar cliente
    @FXML
    void cadastrarCliente(ActionEvent event) {
        App.setRoot("controller/TelaCadastroCliente.fxml");
    }
    //Método do botão atualizar cliente
    @FXML
    void atualizarCliente(ActionEvent event) throws ValidarAtualizacaoException {
        Cliente clienteSelecionado = tabelaGerenciarClientes.getSelectionModel().getSelectedItem();

        if(clienteSelecionado != null) {
            try{
                FXMLLoader loader = new FXMLLoader(App.class.getResource("controller/TelaAtualizarCliente.fxml"));
                Parent root = loader.load();
                
                AtualizarClienteController controller = loader.getController();
                controller.setCliente(clienteSelecionado);
                App.setRoot(root);
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            MeuMasterController.exibirAlertaErro("Nenhum cliente foi selecionado");
        }
    }
    //Método do botão remover cliente
    @FXML
    void removerCliente(ActionEvent event) {
        Cliente clienteSelecionado = tabelaGerenciarClientes.getSelectionModel().getSelectedItem();
        try{
            validaUsuario.validaRemocao(clienteSelecionado);
            tabelaGerenciarClientes.getItems().remove(clienteSelecionado);
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
