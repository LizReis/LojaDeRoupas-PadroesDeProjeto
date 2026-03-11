package br.edu.ifba.saj.fwads.negocio.STRATEGY;

import br.edu.ifba.saj.fwads.exception.ValidarAtualizacaoException;
import br.edu.ifba.saj.fwads.model.Usuario;

public interface StrategyAtualizaUsuario {
    
    //Cada tipo de usuário vai ter que implementar esse método de um jeito diferente.
    boolean validarAtualizar(Usuario usuarioAtual, String novoNome, String novoSetor) throws ValidarAtualizacaoException;
}

