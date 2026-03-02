package br.edu.ifba.saj.fwads.model;

import java.util.ArrayList;
import java.util.UUID;

public class Departamento extends AbstractModel<UUID>{
    private String nomeDepartamento;
    private ArrayList<Produto> produtos;

    public Departamento(String nomeDepartamento){
        this.setNomeDepartamento(nomeDepartamento);
        this.produtos = new ArrayList<>();
    }


    public String getNomeDepartamento(){
        return this.nomeDepartamento;
    }
    public void setNomeDepartamento(String nomeDepartamento){
        this.nomeDepartamento = nomeDepartamento;
    }

    public String getProdutos(){
        StringBuilder lista = new StringBuilder();
        for(Produto produto: this.produtos){
            lista.append("\n").append(produto.descricaoProduto());
        }

        return lista.toString();
    }

    public Produto adicionarProduto(String modelo, String cor, String tamanho, float preco, Departamento departamento){
        Produto novoProduto = new Produto(modelo, cor, tamanho, preco, departamento);
        produtos.add(novoProduto);
        
        return novoProduto;
    }

    public String verProdutosDepartamento(){
        if(!this.produtos.isEmpty()){
            return this.getProdutos();
        }else{
            return "\nO departamento não existe";
        }
    }


}