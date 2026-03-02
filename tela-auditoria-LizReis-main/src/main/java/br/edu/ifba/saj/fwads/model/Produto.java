package br.edu.ifba.saj.fwads.model;

import java.util.UUID;

public class  Produto extends AbstractModel<UUID>{
    private String modelo, tamanho, cor;
    private float preco;
    private Departamento departamento;
    
    public Produto(String modelo, String cor, String tamanho, float preco, Departamento departamento){
        this.setModelo(modelo);
        this.setCor(cor);
        this.setTamanho(tamanho);
        this.setPreco(preco);
        this.setDepartamento(departamento);
    }
    
    public String getModelo() {
        return this.modelo;
    }
    private void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTamanho() {
        return this.tamanho;
    }
    private void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCor() {
        return this.cor;
    }
    private void setCor(String cor) {
        this.cor = cor;
    }

    public float getPreco() {
        return this.preco;
    }
    public void setPreco(float preco) {
        this.preco = preco;
    }

    public Departamento getDepartamento(){
        return this.departamento;
    }
    public void setDepartamento(Departamento departamento){
        this.departamento = departamento;
    }
     
    public String descricaoProduto(){
        return "{\n" + getModelo() + " " + getTamanho() + " " + getCor() + "\n}";
    }
    
}