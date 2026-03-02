package br.edu.ifba.saj.fwads.negocio;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.saj.fwads.exception.ValidarAuditoriaException;
import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Usuario;

public class ValidaAuditoria {
    //Criei duas listas, uma vai reuni as auditorias de criação
    //A outra vai reunir todas as auditorias de alteração
    List<AbstractModel> todasAuditoriasCriadas = new ArrayList<>();
    List<AbstractModel> todasAuditoriasAlteradas = new ArrayList<>();

    //Dentro de cada dao, criei um buscar que retorna quais objetos tem o createdBy ou updatedBy presentes
    private void reunirAuditoriasCriadas(){
        todasAuditoriasCriadas.clear();
        todasAuditoriasCriadas.addAll(ValidaDepartamento.daoDepartamentos.buscarTemCreatedBy());
        todasAuditoriasCriadas.addAll(ValidaProduto.daoProdutos.buscarTemCreatedBy());
        todasAuditoriasCriadas.addAll(ValidaUsuario.daoUsuarios.buscarTemCreatedBy());

        //Deixei comentada porque, por mais que carrinho possa ser criado pelo cliente, apenas o Funcionário
        //é passado como criador ou atualizar no meu DAO
        //todasAuditoriasCriadas.addAll(ValidaCarrinhos.daoCarrinhos.buscarTemCreatedBy());
    }
    private void reunirAuditoriasAlteradas(){
        todasAuditoriasAlteradas.clear();
        todasAuditoriasAlteradas.addAll(ValidaDepartamento.daoDepartamentos.buscarTemUpdatedBy());
        todasAuditoriasAlteradas.addAll(ValidaProduto.daoProdutos.buscarTemUpdatedBy());
        todasAuditoriasAlteradas.addAll(ValidaUsuario.daoUsuarios.buscarTemUpdatedBy());
    }

    //Agora, criei uma lista para armazenar as criações e outra para as alterações
    //do usuario que foi selecionado
    List<AbstractModel> usuarioCreated = new ArrayList<>();
    List<AbstractModel> usuarioUpdated = new ArrayList<>();

    //Método que pega as criações do usuario selecionado
    public List<AbstractModel> pegarCriacoesDoUsuario(Usuario usuario) throws ValidarAuditoriaException{
        usuarioCreated.clear();
        reunirAuditoriasCriadas();
        if(todasAuditoriasCriadas.isEmpty()){
            throw new ValidarAuditoriaException("Ainda não há criações ou alterações.");
        }else{
            for(AbstractModel objeto : todasAuditoriasCriadas){
                if(objeto.getCreatedBy().get().equals(usuario)){
                    usuarioCreated.add(objeto);
                }
            }
            if(usuarioCreated.isEmpty()){
                throw new ValidarAuditoriaException("Esse usuário não tem criações.");
            }else{
                return usuarioCreated;
            }
        }

    }

    //Método que pega as alterações do usuario selecionado
    public List<AbstractModel> pegarAlteracoesDoUsuario(Usuario usuario) throws ValidarAuditoriaException{
        usuarioUpdated.clear();
        reunirAuditoriasAlteradas();
        if(todasAuditoriasAlteradas.isEmpty()){
            throw new ValidarAuditoriaException("Ainda não há criações ou alterações.");
        }else{
            for(AbstractModel objeto : todasAuditoriasAlteradas){
                if(objeto.getUpdatedBy().get().equals(usuario)){
                    usuarioUpdated.add(objeto);
                }
            }
            if(usuarioUpdated.isEmpty()){
                throw new ValidarAuditoriaException("Esse usuário não tem atualizações.");
            }else{
                return usuarioUpdated;
            }
        }
        
    }
}
