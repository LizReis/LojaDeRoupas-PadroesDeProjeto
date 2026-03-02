package br.edu.ifba.saj.fwads.dao;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Funcionario;

//parâmetros genéricos devem ser em maiúsculas por convenção (ID em vez de id)
public interface GenericDAO<T extends AbstractModel<ID>, ID>{
    ID salvar(T entidade, Optional<Funcionario> createdBy);
    void atualizar(T entidadeAtual, Optional<Funcionario> UpdatedBy);
    T buscarPorID(ID id);
    void deletar(ID id);
    ID gerarNovoId();
    List<T> buscarTodos();
}