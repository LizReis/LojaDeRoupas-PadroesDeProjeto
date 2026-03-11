package br.edu.ifba.saj.fwads.negocio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifba.saj.fwads.dao.UsuarioDAO;
import br.edu.ifba.saj.fwads.exception.CadastroUsuarioException;
import br.edu.ifba.saj.fwads.exception.LoginInvalidoException;
import br.edu.ifba.saj.fwads.exception.ValidaRemocaoException;
import br.edu.ifba.saj.fwads.exception.ValidarAtualizacaoException;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.Funcionario;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.STRATEGY.AtualizaFuncionarioStrategy;
import br.edu.ifba.saj.fwads.negocio.STRATEGY.AtualizarClienteStrategy;
import br.edu.ifba.saj.fwads.negocio.STRATEGY.StrategyAtualizaUsuario;
import br.edu.ifba.saj.fwads.negocio.STRATEGY.StrategyValidaCadastro;
import br.edu.ifba.saj.fwads.negocio.STRATEGY.ValidaCadastroCliente;
import br.edu.ifba.saj.fwads.negocio.STRATEGY.ValidaCadastroFuncionario;


public class ValidaUsuario{
    
    public static final UsuarioDAO daoUsuarios = new UsuarioDAO();

    private final Map<Class<? extends Usuario>, StrategyAtualizaUsuario> estrategiasAtualizar = new HashMap<>();

    private final Map<Class<? extends Usuario>, StrategyValidaCadastro> estrategiasValidaCadastro = new HashMap<>();

    //Método que permite adicionar a estratégia logo quando a classe é criada
    //Isso é necessário porque não é o usuário que escolhe a estratégia diretamente no sistema
    public ValidaUsuario(){
        estrategiasAtualizar.put(Funcionario.class, new AtualizaFuncionarioStrategy());
        estrategiasAtualizar.put(Cliente.class, new AtualizarClienteStrategy());

        estrategiasValidaCadastro.put(Funcionario.class, new ValidaCadastroFuncionario());
        estrategiasValidaCadastro.put(Cliente.class, new ValidaCadastroCliente());
    }


    //COM O STRATEGY
    //Removemos os dois métodos de validação + cadastro
    //Agora apenas chamamos as estratégias para cada tipo de usuário
    public boolean validaCadastro(Usuario usuario) throws CadastroUsuarioException{
        if (usuario == null) {
            throw new CadastroUsuarioException("Usuário é nulo.");
        }

        StrategyValidaCadastro estrategia = estrategiasValidaCadastro.get(usuario.getClass());

        if(estrategia != null){
            return estrategia.validaECadastra(usuario);
        }

        throw new CadastroUsuarioException("Tipo de usuário não suportado para cadastro!");
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
                    SessaoUsuario.getInstance().setFuncionarioLogado((Funcionario) usuario);
                    return usuario;
                }else if("Cliente".equals(choiceTipo) && usuario instanceof Cliente){
                    SessaoUsuario.getInstance().setClienteLogado((Cliente) usuario);
                    return usuario;
                }
            }
        }
        throw new LoginInvalidoException("Login ou senha incorreta. Lembre de selecionar se é cliente ou funcionário");

    }
    //----------------------------------------------------------------------------------

    //COM O STRATEGY
    //O método atualizar ficou bem mais simples, passando apenas o tipo da classe do usuário
    //deixando a lógica para as classes que implementam StrategyAtualizaUsuario
    public Boolean validaAtualizacao(Usuario usuarioAtual, String novoNome, String novoSetor) throws ValidarAtualizacaoException{
        if(usuarioAtual == null){
            throw new ValidarAtualizacaoException("Selecione para atualizar.");
        }

        StrategyAtualizaUsuario estrategia = estrategiasAtualizar.get(usuarioAtual.getClass());

        if (estrategia != null) {
            return estrategia.validarAtualizar(usuarioAtual, novoNome, novoSetor);
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