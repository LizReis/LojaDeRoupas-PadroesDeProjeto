package br.edu.ifba.saj.fwads.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.ValidaRemocaoException;
import br.edu.ifba.saj.fwads.model.Departamento;
import br.edu.ifba.saj.fwads.model.Funcionario;
import br.edu.ifba.saj.fwads.negocio.ValidaDepartamento;
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

public class GerenciamentoDepartamentoController {
    @FXML
    private Button btnAtualizarDepartamento;
    @FXML
    private Button btnCadastrarDepartamento;
    @FXML
    private Button btnRemoverDepartamento;
    @FXML
    private Button btnVoltar;

    @FXML
    private TableColumn<Departamento, String> colunaDepartamentoAtualizadoPor;
    @FXML
    private TableColumn<Departamento, String> colunaDepartamentoCriadoPor;
    @FXML
    private TableColumn<Departamento, String> colunaDepartamentoDataAtualizacao;
    @FXML
    private TableColumn<Departamento, String> colunaDepartamentoDataCriacao;
    @FXML
    private TableColumn<Departamento, String> colunaDepartamentoNome;

    @FXML
    private Label lblTitulo;

    @FXML
    private TableView<Departamento> tabelaGerenciarDepartamentos;

    //Instancia do valida departamento para chamar métodos
    private ValidaDepartamento validaDepartamento = new ValidaDepartamento();
    //Initialize para inicializar os departamentos que tem no sistema
    @FXML
    private void initialize(){
        carregarDepartamentos();
        lblTitulo.setText("Departamentos");
    }
    //Método carrgar departamento, carrega nas colunas os departamentos de forma dinâmica
    private void carregarDepartamentos(){
        ObservableList<Departamento> listaDepartamentos = FXCollections.observableArrayList(ValidaDepartamento.daoDepartamentos.buscarTodos());

        
        tabelaGerenciarDepartamentos.setItems(listaDepartamentos);

        colunaDepartamentoNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeDepartamento()));
        colunaDepartamentoDataCriacao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        colunaDepartamentoDataAtualizacao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        colunaDepartamentoCriadoPor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedBy().map(Funcionario::getNome).orElse("N/A")));
        colunaDepartamentoAtualizadoPor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUpdatedBy().map(Funcionario::getNome).orElse("N/A")));

    }

    //Método do botão cadastrar departamento
    @FXML
    void cadastrarDepartamento(ActionEvent event) {
        App.setRoot("controller/TelaCadastroDepartamento.fxml");
    }
    //Método do botão atualizar departamento selecionado
    @FXML
    void atualizarDepartamento(ActionEvent event) {
        Departamento departamentoSelecionado = tabelaGerenciarDepartamentos.getSelectionModel().getSelectedItem();

        if(departamentoSelecionado != null) {
            try{
                FXMLLoader loader = new FXMLLoader(App.class.getResource("controller/TelaAtualizarDepartamento.fxml"));
                Parent root = loader.load();
                
                AtualizarDepartamentoController controller = loader.getController();
                controller.setDepartamento(departamentoSelecionado);
                App.setRoot(root);
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            MeuMasterController.exibirAlertaErro("Nenhum departamento foi selecionado");
        }
    }
    //Método do botão remover departamento seleiconado
    @FXML
    void removerDepartamento(ActionEvent event) {
        Departamento departamentoSelecionado = tabelaGerenciarDepartamentos.getSelectionModel().getSelectedItem();
        try{
            validaDepartamento.validaRemocao(departamentoSelecionado);
            tabelaGerenciarDepartamentos.getItems().remove(departamentoSelecionado);
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
