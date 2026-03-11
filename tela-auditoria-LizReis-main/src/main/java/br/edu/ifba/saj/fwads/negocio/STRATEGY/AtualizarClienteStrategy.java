package br.edu.ifba.saj.fwads.negocio.STRATEGY;

import br.edu.ifba.saj.fwads.exception.ValidarAtualizacaoException;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.SessaoUsuario;
import br.edu.ifba.saj.fwads.negocio.ValidaUsuario;

public class AtualizarClienteStrategy implements StrategyAtualizaUsuario{

    @Override
    public boolean validarAtualizar(Usuario usuarioAtual, String novoNome, String novoSetor)
            throws ValidarAtualizacaoException {
        Cliente cliente = (Cliente) usuarioAtual;
        
        if(novoNome == null || novoNome.trim().isEmpty()){
            throw new ValidarAtualizacaoException("Digite o novo nome!");
        } else {
            cliente.setNome(novoNome);
            
            ValidaUsuario.daoUsuarios.atualizar(usuarioAtual, SessaoUsuario.getInstance().getFuncionarioLogado());
            return true;
        }
    }
    
}
