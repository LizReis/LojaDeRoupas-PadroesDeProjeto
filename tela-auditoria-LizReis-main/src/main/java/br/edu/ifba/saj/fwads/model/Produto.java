package br.edu.ifba.saj.fwads.model;

import java.util.UUID;

public class  Produto extends AbstractModel<UUID>{
    private String modelo, tamanho, cor;
    private float preco;
    private Departamento departamento;
    
    private Produto(Builder builder){
        this.setModelo(builder.modelo);
        this.setCor(builder.cor);
        this.setTamanho(builder.tamanho);
        this.setPreco(builder.preco);
        this.setDepartamento(builder.departamento);
    }

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private String modelo;
        private String tamanho;
        private String cor;
        private float preco;
        private Departamento departamento;

        public Builder modelo(String modelo) {
            this.modelo = modelo;
            return this;
        }

        public Builder tamanho(String tamanho) {
            this.tamanho = tamanho;
            return this;
        }

        public Builder cor(String cor) {
            this.cor = cor;
            return this;
        }

        public Builder preco(float preco) {
            this.preco = preco;
            return this;
        }

        public Builder departamento(Departamento departamento) {
            this.departamento = departamento;
            return this;
        }

        public Produto build() {
            return new Produto(this);
        }
    }
}
