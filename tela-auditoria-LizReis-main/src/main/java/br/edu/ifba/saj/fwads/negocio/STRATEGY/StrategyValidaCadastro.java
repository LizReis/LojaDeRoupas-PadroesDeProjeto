package br.edu.ifba.saj.fwads.negocio.STRATEGY;

import br.edu.ifba.saj.fwads.exception.CadastroUsuarioException;
import br.edu.ifba.saj.fwads.model.Usuario;

public interface StrategyValidaCadastro {
    
    boolean validaECadastra(Usuario usuario) throws CadastroUsuarioException;
}
