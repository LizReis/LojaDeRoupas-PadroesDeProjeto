package br.edu.ifba.saj.fwads.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.ValidarAuditoriaException;
import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Funcionario;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.ValidaAuditoria;
import br.edu.ifba.saj.fwads.negocio.ValidaUsuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;

public class AuditoriaControllerLiz {

    @FXML
    private ChoiceBox<Usuario> chbUsuarios;

    @FXML
    private TableView<AbstractModel> tabelaAtualizadas;

    @FXML
    private TableColumn<AbstractModel, String> clnAtualizadaData;

    @FXML
    private TableColumn<AbstractModel, String> clnAtualizadaNome;

    @FXML
    private TableView<AbstractModel> tabelaInseridas;

    @FXML
    private TableColumn<AbstractModel, String> clnInseridaData;

    @FXML
    private TableColumn<AbstractModel, String> clnInseridaNome;

    private List<Usuario> listaUsuario;

    @FXML
    void sair(ActionEvent event) {
        App.setRoot("controller/Login.fxml");
    }

    ValidaUsuario validaUsuario = new ValidaUsuario();
    ValidaAuditoria validaAuditoria = new ValidaAuditoria();

    void usuarioSelecionado(){
        new Alert(AlertType.INFORMATION,
                "Usuario Selecionado:" + chbUsuarios.getSelectionModel().getSelectedItem().getNome()).showAndWait();

        carregarDadosInseridos();
        carregarDadosAtualizados();
    }


    @FXML
    public void initialize() {
        chbUsuarios.setConverter(new StringConverter<Usuario>() {

            @Override
            public String toString(Usuario object) {
                if (object != null) {
                    return object.getNome() + ":" + object.getNome();
                }
                return "";
            }

            @Override
            public Funcionario fromString(String string) {
                return (Funcionario) listaUsuario
                        .stream()
                        .filter(autor -> string.equals(autor.getNome() + ":" + autor.getNome()))
                        .findAny()
                        .orElse(null);
            }

        });

        chbUsuarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                usuarioSelecionado();
            }
        });

        carregarListaUsuario();
        

    }

    private void carregarDadosInseridos(){
        tabelaInseridas.getItems().clear();
        Usuario usuarioSelecionado = chbUsuarios.getValue();

        if(usuarioSelecionado != null){
            try{
                ObservableList<AbstractModel> listaDeCriacoes = FXCollections.observableArrayList(validaAuditoria.pegarCriacoesDoUsuario(usuarioSelecionado));

                tabelaInseridas.setItems(listaDeCriacoes);

                clnInseridaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClassName()));
                clnInseridaData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));


            }catch(ValidarAuditoriaException e){
                MeuMasterController.exibirAlertaErro(e.getMessage());
            }
        }else{
            MeuMasterController.exibirAlertaErro("Escolha um funcionário para ver suas criações e atualizações");
        }
    }

    private void carregarDadosAtualizados(){
        tabelaAtualizadas.getItems().clear();
        Usuario usuarioSelecionado = chbUsuarios.getValue();

        if(usuarioSelecionado != null){
            try{
                ObservableList<AbstractModel> listaDeAlteracoes = FXCollections.observableArrayList(validaAuditoria.pegarAlteracoesDoUsuario(usuarioSelecionado));

                tabelaAtualizadas.setItems(listaDeAlteracoes);

                clnAtualizadaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClassName()));
                clnAtualizadaData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
            }catch(ValidarAuditoriaException e){
                MeuMasterController.exibirAlertaErro(e.getMessage());
            }
        }else{
            MeuMasterController.exibirAlertaErro("Escolha um funcionário para ver suas criações e atualizações.");
        }
    }

    private void carregarListaUsuario() {
        List<Funcionario> listaUsuario = validaUsuario.listarFuncionarios();
        chbUsuarios.setItems(FXCollections.observableArrayList(listaUsuario));
    }
}
