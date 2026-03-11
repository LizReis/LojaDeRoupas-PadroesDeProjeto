package br.edu.ifba.saj.fwads.negocio.STRATEGY;

import br.edu.ifba.saj.fwads.exception.ValidarAtualizacaoException;
import br.edu.ifba.saj.fwads.model.Funcionario;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.SessaoUsuario;
import br.edu.ifba.saj.fwads.negocio.ValidaUsuario;

public class AtualizaFuncionarioStrategy implements StrategyAtualizaUsuario{

    @Override
    public boolean validarAtualizar(Usuario usuarioAtual, String novoNome, String novoSetor)
            throws ValidarAtualizacaoException {
        Funcionario funcionario = (Funcionario) usuarioAtual;
        
        if(novoNome == null || novoNome.trim().isEmpty()){
            throw new ValidarAtualizacaoException("Digite o novo nome!");
        } else if(novoSetor == null || novoSetor.trim().isEmpty()){
            throw new ValidarAtualizacaoException("Digite o novo setor de trabalho!");
        } else {
            funcionario.setNome(novoNome);
            funcionario.setSetorTrabalho(novoSetor);
            // Delega para o DAO salvar
            ValidaUsuario.daoUsuarios.atualizar(usuarioAtual, SessaoUsuario.getInstance().getFuncionarioLogado());
            return true;
        }
    }
    
}
