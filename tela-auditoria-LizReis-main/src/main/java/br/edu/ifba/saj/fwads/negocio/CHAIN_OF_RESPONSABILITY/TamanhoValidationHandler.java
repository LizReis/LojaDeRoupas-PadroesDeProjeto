package br.edu.ifba.saj.fwads.negocio.CHAIN_OF_RESPONSABILITY;

import br.edu.ifba.saj.fwads.exception.CadastroProdutoException;
import br.edu.ifba.saj.fwads.model.Produto;

public class TamanhoValidationHandler implements ProdutoValidationHandler {
    private ProdutoValidationHandler next;

    @Override
    public void setNext(ProdutoValidationHandler next) {
        this.next = next;
    }

    @Override
    public boolean handle(Produto produto) throws CadastroProdutoException {
        if (produto.getTamanho() == null || produto.getTamanho().trim().isEmpty()) {
            throw new CadastroProdutoException("Preencha todos os campos corretamente");
        }
        if (next != null) {
            return next.handle(produto);
        }
        return true;
    }
}