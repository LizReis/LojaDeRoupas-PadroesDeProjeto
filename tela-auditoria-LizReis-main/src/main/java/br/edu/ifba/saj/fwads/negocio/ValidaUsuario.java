package br.edu.ifba.saj.fwads.negocio;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.saj.fwads.dao.UsuarioDAO;
import br.edu.ifba.saj.fwads.exception.CadastroUsuarioException;
import br.edu.ifba.saj.fwads.exception.LoginInvalidoException;
import br.edu.ifba.saj.fwads.exception.ValidaRemocaoException;
import br.edu.ifba.saj.fwads.exception.ValidarAtualizacaoException;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.Funcionario;
import br.edu.ifba.saj.fwads.model.Usuario;


public class ValidaUsuario{
    
    public static final UsuarioDAO daoUsuarios = new UsuarioDAO();

    //Valida cadastro de um cliente
    public boolean validaCadastroCliente(Cliente novoCliente) throws CadastroUsuarioException{
        if(novoCliente == null){
            throw new CadastroUsuarioException("Cliente é nulo.");
        }else if(novoCliente.getNome() == null || novoCliente.getNome().trim().isEmpty()){
            throw new CadastroUsuarioException("Digite o nome!");
        }else if(novoCliente.getLogin() == null || novoCliente.getLogin().trim().isEmpty()){
            throw new CadastroUsuarioException("Digite o login!");
        }else if(novoCliente.getSenha() == null || novoCliente.getSenha().trim().isEmpty()){
            throw new CadastroUsuarioException("Digite a senha!");
        }else{
            daoUsuarios.salvar(novoCliente, SessaoUsuario.getFuncionarioLogado());
            return true;
        }
    }
    //------------------------------------------------------------------------------

    //Valida cadastro de um funcionario
    public boolean validaCadastroFuncionario(Funcionario novoFuncionario) throws CadastroUsuarioException{
        if(novoFuncionario == null){
            throw new CadastroUsuarioException("Funcionário é nulo.");
        }else if(novoFuncionario.getNome() == null || novoFuncionario.getNome().trim().isEmpty()){
            throw new CadastroUsuarioException("Digite o nome!");
        }else if(novoFuncionario.getLogin() == null || novoFuncionario.getLogin().trim().isEmpty()){
            throw new CadastroUsuarioException("Digite o login!");
        }else if(novoFuncionario.getSenha() == null || novoFuncionario.getSenha().trim().isEmpty()){
            throw new CadastroUsuarioException("Digite a senha!");
        }else if(novoFuncionario.getSetorTrabalho() == null || novoFuncionario.getSetorTrabalho().trim().isEmpty()){
            throw new CadastroUsuarioException("Digite o setor de trabalho!");
        }else{
            daoUsuarios.salvar(novoFuncionario, SessaoUsuario.getFuncionarioLogado());
            return true;
        }
    }
    //-------------------------------------------------------------------------------

    //Valida login de cliente e funcionario
    public Usuario validaLogin(String login, String senha, String choiceTipo) throws LoginInvalidoException{
        
        for (Usuario usuario : daoUsuarios.buscarTodos()) {
            if(usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)){
                //Se esse usuário logado for uma instancia de Funcionario, eu armazeno o Funcionario logado
                //caso ele faça algum cadastro ou alteração
                if("Funcionário".equals(choiceTipo) && usuario instanceof Funcionario){
                    //Como essa validação de login é para cliente e funcionario, ent o que tem na variavel "usuario" tem que ser convertida para Funcionario
                    SessaoUsuario.setFuncionarioLogado((Funcionario) usuario);
                    return usuario;
                }else if("Cliente".equals(choiceTipo) && usuario instanceof Cliente){
                    SessaoUsuario.setClienteLogado((Cliente) usuario);
                    return usuario;
                }
            }
        }
        throw new LoginInvalidoException("Login ou senha incorreta. Lembre de selecionar se é cliente ou funcionário");

    }
    //----------------------------------------------------------------------------------

    //Valida atualização de cliente ou funcionario
    public Boolean validaAtualizacao(Usuario usuarioAtual, String novoNome, String novoSetor) throws ValidarAtualizacaoException{
        if(usuarioAtual == null){
            throw new ValidarAtualizacaoException("Selecione para atualizar.");
        }else if(usuarioAtual instanceof Funcionario funcionario){
            if(novoNome == null || novoNome.trim().isEmpty()){
                throw new ValidarAtualizacaoException("Digite o novo nome!");
            }else if(novoSetor == null || novoSetor.trim().isEmpty()){
                throw new ValidarAtualizacaoException("Digite o novo setor de trabalho!");
            }else{
                funcionario.setNome(novoNome);
                funcionario.setSetorTrabalho(novoSetor);
                daoUsuarios.atualizar(usuarioAtual, SessaoUsuario.getFuncionarioLogado());
                return true;
            }
        }else if(usuarioAtual instanceof Cliente cliente){
            if(novoNome == null || novoNome.trim().isEmpty()){
                throw new ValidarAtualizacaoException("Digite o novo nome!");
            }else{
                cliente.setNome(novoNome);
                daoUsuarios.atualizar(usuarioAtual, SessaoUsuario.getFuncionarioLogado());
                return true;
            }
        }
        throw new ValidarAtualizacaoException("Algo deu errado!");
    }
    //------------------------------------------------------------------------------------

    //Valida remocao de cliente ou funcionario
    public Boolean validaRemocao(Usuario usuario) throws ValidaRemocaoException{
        if(usuario == null){
            throw new ValidaRemocaoException("Selecione para remover");
        }else{
            daoUsuarios.deletar(usuario.getId());
            return true;
        }
    }

    //Listar Usuários do sistema
    public List<Usuario> listarUsuarios(){
        return daoUsuarios.buscarTodos();
    }

    //Pega apenas as instancias de funcionario
    public List<Funcionario> listarFuncionarios(){
        List<Funcionario> listaFuncionarios = new ArrayList<>();
        for(Usuario usuario : listarUsuarios()){
            if(usuario instanceof Funcionario funcionario){
                listaFuncionarios.add(funcionario);
            }
        }
        return listaFuncionarios;
    }
}