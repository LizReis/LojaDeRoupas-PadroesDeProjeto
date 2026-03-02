package br.edu.ifba.saj.fwads.dao;

import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Funcionario;

public class GenericDAOImpl<T extends AbstractModel<ID>, ID> implements GenericDAO<T,ID>{
    private Map<ID, T> bancoDeDados = new HashMap<>();
    //private Map<ID, T> bancoAuditorias = new HashMap<>();
    //final = depois de atribuído no construtor, seu valor não pode ser alterado
    private final Class<ID> tipoIdClass;

    public GenericDAOImpl(Class<ID> tipoIdClass){
        this.tipoIdClass = tipoIdClass;
    }

    @Override
    public ID salvar(T entidade, Optional<Funcionario> funcionario){
        ID novoId = gerarNovoId();

        entidade.setCreatedBy(funcionario);

        entidade.setId(novoId);
        entidade.setCreatedAt(LocalDateTime.now());

        bancoDeDados.put(entidade.getId(), entidade);

        return novoId;
    }

    @Override
    public void atualizar(T entidadeAtual,  Optional<Funcionario> funcionario){
        if(bancoDeDados.containsValue(entidadeAtual)){
            entidadeAtual.setUpdatedBy(funcionario);

            entidadeAtual.setUpdatedAt(LocalDateTime.now());
        }
    }

    @Override
    public T buscarPorID(ID id){
        return bancoDeDados.get(id);
    }

    @Override
    public void deletar(ID id){
        if(bancoDeDados.containsKey(id)){
            bancoDeDados.remove(id);
        }
    }

    @Override
    public List<T> buscarTodos(){
        return new ArrayList<>(bancoDeDados.values());
    }

    @Override
    public ID gerarNovoId(){
        if(tipoIdClass.equals(UUID.class)){
            return tipoIdClass.cast(UUID.randomUUID());
        }
        return null;
    }

}