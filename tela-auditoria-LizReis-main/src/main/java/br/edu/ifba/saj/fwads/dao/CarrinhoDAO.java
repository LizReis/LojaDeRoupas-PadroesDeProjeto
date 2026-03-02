package br.edu.ifba.saj.fwads.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import br.edu.ifba.saj.fwads.model.Carrinho;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.ItemCompra;


public class CarrinhoDAO extends GenericDAOImpl<Carrinho, UUID>{

    public CarrinhoDAO() {
        super(UUID.class);
    }
    
    //Busca um carrinho por nome
    public Optional<Carrinho> buscarPorNome(String nomecarrinho) {
        return buscarTodos()
                .stream()
                .filter(carrinho -> carrinho.getNome().equalsIgnoreCase(nomecarrinho))
                .findFirst();
    }

    //Busca uma lista de carrinhos de um cliente
    public List<Carrinho> buscarPorCliente(Cliente cliente){
        return buscarTodos()
                .stream()
                .filter(carrinho -> carrinho.getCliente().equals(cliente))
                .toList();
    }

    //Busca um carrinho por item
    public Optional<Carrinho> buscarPorItem(ItemCompra item){
        return buscarTodos()
                .stream()
                .filter(carrinho -> carrinho.getItensCompra().contains(item))
                .findFirst();
    }

    //Pega os carrinhos que têm createdBy
    public List<Carrinho> buscarTemCreatedBy(){
        return buscarTodos()
                .stream()
                .filter(carrinho -> carrinho.getCreatedBy().isPresent())
                .collect(Collectors.toList());
    }
}
