package br.edu.ifba.saj.fwads.dao.Proxy;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.saj.fwads.dao.GenericDAO;
import br.edu.ifba.saj.fwads.exception.AcessoNegadoException;
import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Funcionario;
import br.edu.ifba.saj.fwads.negocio.SessaoUsuario;

public class DAOProxySeguro <T extends AbstractModel<ID>, ID> implements GenericDAO<T,ID>{
    private GenericDAO<T,ID> daoReal;

    public DAOProxySeguro(GenericDAO<T,ID> daoReal) {
        this.daoReal = daoReal;
    }

    @Override
    public ID salvar(T entidade, Optional<Funcionario> funcionario) {
        return (ID) daoReal.salvar(entidade, funcionario);
    }

    @Override
    public void atualizar(T entidadeAtual,  Optional<Funcionario> funcionario) {
        daoReal.atualizar(entidadeAtual, funcionario);
    }

    @Override
    public T buscarPorID(ID id) {
        return daoReal.buscarPorID(id);
    }

    @Override
    public void deletar(ID id) throws AcessoNegadoException {
        if (SessaoUsuario.getInstance().getClienteLogado() != null) {
           throw new AcessoNegadoException ("Clientes não têm permissão para deletar.");  
        }
        
        if (SessaoUsuario.getInstance().getFuncionarioLogado() == null) {
           throw new AcessoNegadoException ("O Funcionário precisa estar logado para deletar.");  
        }

        daoReal.deletar(id);
    }

    @Override
    public List<T> buscarTodos() {
        return daoReal.buscarTodos();
    }

    @Override
    public ID gerarNovoId(){
        return daoReal.gerarNovoId();
    }

}
