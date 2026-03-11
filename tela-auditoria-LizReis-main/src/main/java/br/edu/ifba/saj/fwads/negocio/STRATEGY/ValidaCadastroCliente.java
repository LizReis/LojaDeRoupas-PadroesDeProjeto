package br.edu.ifba.saj.fwads.negocio.STRATEGY;

import br.edu.ifba.saj.fwads.exception.CadastroUsuarioException;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.SessaoUsuario;
import br.edu.ifba.saj.fwads.negocio.ValidaUsuario;

public class ValidaCadastroCliente implements StrategyValidaCadastro{

    @Override
    public boolean validaECadastra(Usuario usuario) throws CadastroUsuarioException {
        Cliente novoCliente = (Cliente) usuario;

        if(novoCliente.getNome() == null || novoCliente.getNome().trim().isEmpty()){
            throw new CadastroUsuarioException("Digite o nome!");
        } else if(novoCliente.getLogin() == null || novoCliente.getLogin().trim().isEmpty()){
            throw new CadastroUsuarioException("Digite o login!");
        } else if(novoCliente.getSenha() == null || novoCliente.getSenha().trim().isEmpty()){
            throw new CadastroUsuarioException("Digite a senha!");
        } else {
            // Delega para o DAO salvar
            ValidaUsuario.daoUsuarios.salvar(novoCliente, SessaoUsuario.getInstance().getFuncionarioLogado());
            return true;
        }
    }
}
