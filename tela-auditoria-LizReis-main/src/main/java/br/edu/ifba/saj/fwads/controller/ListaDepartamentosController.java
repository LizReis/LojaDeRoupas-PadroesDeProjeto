package br.edu.ifba.saj.fwads.controller;

import java.io.IOException;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.model.Departamento;
import br.edu.ifba.saj.fwads.negocio.ValidaDepartamento;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;


public class ListaDepartamentosController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnVerProdutosDepartamento;

    @FXML
    private Button btnVoltar;

    @FXML
    private TableView<Departamento> tabelaListaDepartamento;

    @FXML
    private TableColumn<Departamento, String> colunaDepartamentos;
    //Initialize carrega todos os departamentos do sistema quando entramos nessa tela
    @FXML
    private void initialize(){
        carregarDepartamentos();
    }
    //Método que carrega os departamentos nas colunas
    private void carregarDepartamentos(){
        ValidaDepartamento validaDepartamento = new ValidaDepartamento();
        ObservableList<Departamento> listaDepartamentos = FXCollections.observableArrayList(validaDepartamento.listarDepartamentos());

        
        tabelaListaDepartamento.setItems(listaDepartamentos);

        colunaDepartamentos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeDepartamento()));
        

    }
    //Método do botão ver produtos que leva para tela de produtos daquele departamento selecionado
    @FXML
    void verProdutosDoDepartamento(ActionEvent event) {
        Departamento departamentoSelecionado = tabelaListaDepartamento.getSelectionModel().getSelectedItem();

        if(departamentoSelecionado != null){
            try{
                FXMLLoader loader = new FXMLLoader(App.class.getResource("controller/TelaProdutosDeDepartamento.fxml"));
                Parent root = loader.load();
                
                ProdutosDeDepartamentoController controller = loader.getController();
                controller.setDepartamento(departamentoSelecionado);
                App.setRoot(root);
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            MeuMasterController.exibirAlertaErro("Nenhum departamento foi selecionado ou Não há departamentos :/");
        }
    }
    //Método do botão voltar
    @FXML
    void voltarAnterior(ActionEvent event) {
        App.setRoot("controller/MeuMaster.fxml");
    }
}
