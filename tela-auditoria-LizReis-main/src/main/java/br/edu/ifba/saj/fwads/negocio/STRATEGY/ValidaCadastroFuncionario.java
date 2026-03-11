package br.edu.ifba.saj.fwads.negocio.STRATEGY;

import br.edu.ifba.saj.fwads.exception.CadastroUsuarioException;
import br.edu.ifba.saj.fwads.model.Funcionario;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.SessaoUsuario;
import br.edu.ifba.saj.fwads.negocio.ValidaUsuario;

public class ValidaCadastroFuncionario implements StrategyValidaCadastro{

    @Override
    public boolean validaECadastra(Usuario usuario) throws CadastroUsuarioException {
        Funcionario novoFuncionario = (Funcionario) usuario;

        if(novoFuncionario.getNome() == null || novoFuncionario.getNome().trim().isEmpty()){
            throw new CadastroUsuarioException("Digite o nome!");
        } else if(novoFuncionario.getLogin() == null || novoFuncionario.getLogin().trim().isEmpty()){
            throw new CadastroUsuarioException("Digite o login!");
        } else if(novoFuncionario.getSenha() == null || novoFuncionario.getSenha().trim().isEmpty()){
            throw new CadastroUsuarioException("Digite a senha!");
        } else if(novoFuncionario.getSetorTrabalho() == null || novoFuncionario.getSetorTrabalho().trim().isEmpty()){
            throw new CadastroUsuarioException("Digite o setor de trabalho!");
        } else {
            ValidaUsuario.daoUsuarios.salvar(novoFuncionario, SessaoUsuario.getInstance().getFuncionarioLogado());
            return true;
        }
    }
    
}
