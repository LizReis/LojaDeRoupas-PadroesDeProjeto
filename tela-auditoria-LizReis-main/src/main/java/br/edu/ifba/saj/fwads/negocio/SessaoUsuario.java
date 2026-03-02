package br.edu.ifba.saj.fwads.negocio;

import java.util.Optional;

import br.edu.ifba.saj.fwads.model.Funcionario;
import br.edu.ifba.saj.fwads.model.Cliente;

public class SessaoUsuario {
    //Crio uma variavel estática (que pode ser usada em todo pacote sem instanciar sempre)
    //de funcionário
    private static Funcionario funcionarioLogado;
    private static Cliente clienteLogado;

    public static Optional<Funcionario> getFuncionarioLogado(){
        return Optional.ofNullable(funcionarioLogado);
    }

    public static void setFuncionarioLogado(Funcionario funcionarioLogado){
        //Não precisa do this, porque vai ser estático
        SessaoUsuario.funcionarioLogado = funcionarioLogado;
    }

    //Coloca funcionário como null para simular o logout
    public static void logout(){
        funcionarioLogado = null;
    }
    
    //---------------------------------------------------------
    //Agora faço a mesma lógica para ver qual cliente está logado e pegar seus carrinhos

    public static Cliente getClienteLogado(){
        return clienteLogado;
    }

    public static void setClienteLogado(Cliente clienteLogado){
        //Não precisa do this, porque vai ser estático
        SessaoUsuario.clienteLogado = clienteLogado;
    }

    public static void logoutCliente(){
        clienteLogado = null;
    }

}
