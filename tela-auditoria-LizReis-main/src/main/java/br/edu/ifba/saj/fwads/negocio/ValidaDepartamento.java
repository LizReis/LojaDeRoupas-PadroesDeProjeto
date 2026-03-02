package br.edu.ifba.saj.fwads.negocio;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.saj.fwads.dao.DepartamentoDAO;
import br.edu.ifba.saj.fwads.exception.CadastroDepartamentoException;
import br.edu.ifba.saj.fwads.exception.ValidaRemocaoException;
import br.edu.ifba.saj.fwads.exception.ValidarAtualizacaoException;
import br.edu.ifba.saj.fwads.model.Departamento;

public class ValidaDepartamento {
    
    public static final DepartamentoDAO daoDepartamentos = new DepartamentoDAO();

    //Valida o cadastro de um departamento
    public boolean validaCadastroDepartamento(Departamento departamento) throws CadastroDepartamentoException{
        if(departamento == null || departamento.getNomeDepartamento().trim().isEmpty()){
            throw new CadastroDepartamentoException("Digite o nome do departamento!");
        }else if(!daoDepartamentos.buscarPorNome(departamento.getNomeDepartamento()).isPresent()){
            daoDepartamentos.salvar(departamento, SessaoUsuario.getFuncionarioLogado());

            return true;
        }
        throw new CadastroDepartamentoException("Esse departamento já existe.");
    }
    //----------------------------------------------------------------------------------

    //Valida atualziação de um departamento
    public Boolean validaAtualizacao(Departamento departamentoAtual, String novoNomeDepartamento) throws ValidarAtualizacaoException{
        if(departamentoAtual == null){
            throw new ValidarAtualizacaoException("Selecione para atualizar.");
        }else if(novoNomeDepartamento == null || novoNomeDepartamento.trim().isEmpty()){
            throw new ValidarAtualizacaoException("Digite o novo nome do departamento!");
        }else if(daoDepartamentos.buscarPorNome(novoNomeDepartamento).isPresent()){
            throw new ValidarAtualizacaoException("Esse departamento já existe.");
        }else{
            departamentoAtual.setNomeDepartamento(novoNomeDepartamento);
            daoDepartamentos.atualizar(departamentoAtual, SessaoUsuario.getFuncionarioLogado());
            return true;
        }
    }
    //--------------------------------------------------------

    //Valida Remoção de um departamento
    public Boolean validaRemocao(Departamento departamento) throws ValidaRemocaoException{
        if(departamento == null){
            throw new ValidaRemocaoException("Selecione para remover");
        }else{
            daoDepartamentos.deletar(departamento.getId());
            return true;
        }
    }

    //Busca um departamento pelo nome e retorna seu objeto
    public Departamento buscarPeloNome(String nomeDepartamento){
        return daoDepartamentos.buscaUmDepartamento(nomeDepartamento);
    }

    //Listar Departamentos do sistema
    public List<Departamento> listarDepartamentos(){
        return daoDepartamentos.buscarTodos();
    }

    //Transformar a lista do objeto departamento em uma lista de nomes de departamento
    public List<String> listarNomesDepartamentos(){
        List<Departamento> lista = listarDepartamentos();

        List<String> listaNomesDepartamento = new ArrayList<>();

        for(Departamento departamento : lista){
            listaNomesDepartamento.add(departamento.getNomeDepartamento());
        }
        return listaNomesDepartamento;
    }
}
