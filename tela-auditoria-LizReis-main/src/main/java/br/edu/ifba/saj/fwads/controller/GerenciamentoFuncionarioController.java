package br.edu.ifba.saj.fwads.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.ValidaRemocaoException;

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

public class GerenciamentoFuncionarioController {
    @FXML
    private Button btnAtualizarFuncionario;
    @FXML
    private Button btnCadastrarFuncionario;
    @FXML
    private Button btnRemoverFuncionario;
    @FXML
    private Button btnVoltar;

    @FXML
    private TableColumn<Funcionario, String> colunaFuncionarioAtualizadoPor;
    @FXML
    private TableColumn<Funcionario, String> colunaFuncionarioCriadoPor;
    @FXML
    private TableColumn<Funcionario, String> colunaFuncionarioDataAtualizacao;
    @FXML
    private TableColumn<Funcionario, String> colunaFuncionarioDataCriacao;
    @FXML
    private TableColumn<Funcionario, String> colunaFuncionarioLogin;
    @FXML
    private TableColumn<Funcionario, String> colunaFuncionarioNome;
    @FXML
    private TableColumn<Funcionario, String> colunaFuncionarioSetorTrabalho;

    @FXML
    private TableView<Funcionario> tabelaGerenciarFuncionarios;

    @FXML
    private Label lblTitulo;
    //Instancia de valida usuario para chamar métodos 
    private ValidaUsuario validaUsuario = new ValidaUsuario();
    //Initialize para carregar os funcionarios que tem no sistema logo quando entramos na tela
    @FXML
    private void initialize(){
        carregarFuncionarios();
        lblTitulo.setText("Funcionários");
    }
    //Método carregar funcionarios, que mostra todos os funcionarios do sistema na tabela de forma dinâmica
    private void carregarFuncionarios(){
        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList(ValidaUsuario.daoUsuarios.buscarTodos());

        ObservableList<Funcionario> listaFuncionarios = FXCollections.observableArrayList();

        for(Usuario usuarios : listaUsuarios){
            if(usuarios instanceof Funcionario funcionario){
                listaFuncionarios.add(funcionario);
            }
        }
        
        tabelaGerenciarFuncionarios.setItems(listaFuncionarios);

        colunaFuncionarioNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colunaFuncionarioLogin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogin()));
        colunaFuncionarioSetorTrabalho.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSetorTrabalho()));
        colunaFuncionarioDataCriacao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        colunaFuncionarioDataAtualizacao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        colunaFuncionarioCriadoPor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedBy().map(Funcionario::getNome).orElse("N/A")));
        colunaFuncionarioAtualizadoPor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUpdatedBy().map(Funcionario::getNome).orElse("N/A")));

    }

    //Método do botão cadastrarFuncionario
    @FXML
    void cadastrarFuncionario(ActionEvent event) {
        App.setRoot("controller/TelaCadastroFuncionario.fxml");
    }
    //Método do botão atualizar funcionario
    @FXML
    void atualizarFuncionario(ActionEvent event) {
        Funcionario funcionarioSelecionado = tabelaGerenciarFuncionarios.getSelectionModel().getSelectedItem();

        if(funcionarioSelecionado != null) {
            try{
                FXMLLoader loader = new FXMLLoader(App.class.getResource("controller/TelaAtualizarFuncionario.fxml"));
                Parent root = loader.load();
                
                AtualizarFuncionarioController controller = loader.getController();
                controller.setFuncionario(funcionarioSelecionado);
                App.setRoot(root);
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            MeuMasterController.exibirAlertaErro("Nenhum funcionario foi selecionado");
        }
    }
    //Método do botão remover funcionário
    @FXML
    void removerFuncionario(ActionEvent event) {
        Funcionario funcionarioSelecionado = tabelaGerenciarFuncionarios.getSelectionModel().getSelectedItem();
        try{
            validaUsuario.validaRemocao(funcionarioSelecionado);
            tabelaGerenciarFuncionarios.getItems().remove(funcionarioSelecionado);
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
