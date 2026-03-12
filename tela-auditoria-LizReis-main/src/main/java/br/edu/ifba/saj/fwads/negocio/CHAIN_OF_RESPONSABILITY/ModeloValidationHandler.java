package br.edu.ifba.saj.fwads.negocio.CHAIN_OF_RESPONSABILITY;

import br.edu.ifba.saj.fwads.exception.CadastroProdutoException;
import br.edu.ifba.saj.fwads.model.Produto;

public class ModeloValidationHandler implements ProdutoValidationHandler {
    private ProdutoValidationHandler next;
    //Configura o próximo handler na cadeia de responsabilidade
    @Override
    public void setNext(ProdutoValidationHandler next) {
        this.next = next;
    }
    //Valida o campo modelo do produto e lança uma exceção caso o campo esteja vazio ou nulo
    @Override
    public boolean handle(Produto produto) throws CadastroProdutoException {
        if (produto.getModelo() == null || produto.getModelo().trim().isEmpty()) {
            throw new CadastroProdutoException("Preencha todos os campos corretamente");
        }
        if (next != null) {
            return next.handle(produto);
        }
        return true;
    }
}