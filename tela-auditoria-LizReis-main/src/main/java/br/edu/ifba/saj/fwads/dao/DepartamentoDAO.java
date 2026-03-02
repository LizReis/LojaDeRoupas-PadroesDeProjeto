package br.edu.ifba.saj.fwads.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import br.edu.ifba.saj.fwads.model.Departamento;

public class DepartamentoDAO extends GenericDAOImpl<Departamento, UUID>{
    public DepartamentoDAO(){
        super(UUID.class);
    }

    //Busca um departamento por nome
    public Optional<Departamento> buscarPorNome(String nomeDepartamento) {
        return buscarTodos()
                .stream()
                .filter(departamento -> departamento.getNomeDepartamento().equalsIgnoreCase(nomeDepartamento))
                .findFirst();
    }

    
    //Busca um departamento por nome
    public Departamento buscaUmDepartamento(String nomeDepartamento) {
        return buscarTodos()
                .stream()
                .filter(departamento -> departamento.getNomeDepartamento().equalsIgnoreCase(nomeDepartamento))
                .findFirst()
                .orElse(null);
    }

    //Pega os departamentos que têm createdBy
    public List<Departamento> buscarTemCreatedBy(){
        return buscarTodos()
                .stream()
                .filter(departamento -> departamento.getCreatedBy().isPresent())
                .collect(Collectors.toList());
    }

    //Pega os departamentos que têm updatedBy
    public List<Departamento> buscarTemUpdatedBy(){
        return buscarTodos()
                .stream()
                .filter(departamento -> departamento.getUpdatedBy().isPresent())
                .collect(Collectors.toList());
    }
}
