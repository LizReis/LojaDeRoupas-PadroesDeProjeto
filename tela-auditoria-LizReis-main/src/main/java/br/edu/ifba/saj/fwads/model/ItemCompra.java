package br.edu.ifba.saj.fwads.model;


public class ItemCompra{
    private Produto produto;
    private int quantidade;
    private float valorPorProduto = 0;

    public ItemCompra(Produto produto, int quantidade){
        this.setProduto(produto);
        this.setQuantidade(quantidade);
        this.setValorPorProduto(0);
    }

    public Produto getProduto(){
        return this.produto;
    }
    private void setProduto(Produto produto){
         this.produto = produto;
    }

    public int getQuantidade() {
        return this.quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValorPorProduto(){
        return this.valorPorProduto;
    }
    public float setValorPorProduto(float valorPorProduto) {
        float preco = this.getProduto().getPreco();

        float valor = preco * this.getQuantidade();

        this.valorPorProduto = valor;

        return valorPorProduto;
    }
    
}