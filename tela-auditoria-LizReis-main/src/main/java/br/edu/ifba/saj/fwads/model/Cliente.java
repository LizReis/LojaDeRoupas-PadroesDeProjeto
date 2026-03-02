package br.edu.ifba.saj.fwads.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private ArrayList<Carrinho> carrinhos;
    
    public Cliente(String nome, String login, String senha){
        super(nome, login, senha);
        this.carrinhos = new ArrayList<>();
    }
    
    public List<Carrinho> getCarrinhos(){
        return this.carrinhos;
    }
    
    public Carrinho criarCarrinho(String nome){
        Carrinho novoCarrinho = new Carrinho(nome);
        this.carrinhos.add(novoCarrinho);
        
        novoCarrinho.setCliente(this);
        
        return novoCarrinho;
    }
    
    public String removerCarrinho(String nome){
        for(int i = 0; i < carrinhos.size(); i++){
            if(carrinhos.get(i).getNome().equals(nome)){
                this.carrinhos.remove(i);
                return "\nCarrinho removido";
            }
        }
        return "\nCarrinho não encontrado ou não existe";
    }
    
    
}