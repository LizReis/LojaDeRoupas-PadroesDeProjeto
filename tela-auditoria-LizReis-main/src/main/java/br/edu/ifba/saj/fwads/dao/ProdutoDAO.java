package br.edu.ifba.saj.fwads.dao;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.edu.ifba.saj.fwads.model.Departamento;
import br.edu.ifba.saj.fwads.model.Produto;

public class ProdutoDAO extends GenericDAOImpl<Produto, UUID>{

    public ProdutoDAO(){
        super(UUID.class);
    }

    //Busca o menor preco de um produto 
    public List<Produto> buscarMenorPreco(double preco){
        return buscarTodos()
                .stream()
                //LAMBDA: (parametro) -> expressão
                //(parametro1, parametro2) -> { bloco de código }
                .filter(produto -> produto.getPreco() < preco)
                .collect(Collectors.toList());
    }

    //Pega o produto que tem o departamento 
    public List<Produto> buscarPorDepartamento(Departamento departamento){
        return buscarTodos()
                .stream()
                .filter(produto -> produto.getDepartamento().equals(departamento))
                .collect(Collectors.toList());
    }

    //Pega os produtos que têm createdBy
    public List<Produto> buscarTemCreatedBy(){
        return buscarTodos()
                .stream()
                .filter(produto -> produto.getCreatedBy().isPresent())
                .toList();
    }

    //Pega os produtos que têm updatedBy
    public List<Produto> buscarTemUpdatedBy(){
        return buscarTodos()
                .stream()
                .filter(produto -> produto.getUpdatedBy().isPresent())
                .collect(Collectors.toList());
    }
}