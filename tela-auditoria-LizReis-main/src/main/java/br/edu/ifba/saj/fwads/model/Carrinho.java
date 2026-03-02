package br.edu.ifba.saj.fwads.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Carrinho extends AbstractModel<UUID>{
    private String nome;
    private Cliente cliente;
    private ArrayList<ItemCompra> itensCompra;
    private float valorTotal = 0;

    
    public Carrinho(String nome){
        this.setNome(nome);
        this.itensCompra = new ArrayList<>();
    }

    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public List<ItemCompra> getItensCompra(){
        return List.copyOf(itensCompra);
    }

    public float getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal() {
        this.valorTotal = 0;
        for(ItemCompra item: this.itensCompra){
            this.valorTotal += item.getProduto().getPreco() * item.getQuantidade();
        }
    }
    
    public String adicionarItem(ItemCompra itemCompra){
        this.itensCompra.add(itemCompra);

        itemCompra.setValorPorProduto(0);

        setValorTotal();


        return "\nProduto adicionado ao carrinho";
    }
    
    
    public String removerItem(ItemCompra itemCompra){
        this.itensCompra.remove(itemCompra);

        setValorTotal();

        return "\nProduto removido do carrinho";
    }
    
    public String finalizarCompra(){
        this.cliente.removerCarrinho(this.getNome());

        this.itensCompra.clear();

        return "\nCompra finalizada";
    }

    @Override
    public String toString(){
        return this.getNome();
    }
}

