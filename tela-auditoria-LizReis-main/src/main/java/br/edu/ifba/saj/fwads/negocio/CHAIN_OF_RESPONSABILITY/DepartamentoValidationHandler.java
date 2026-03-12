package br.edu.ifba.saj.fwads.negocio.CHAIN_OF_RESPONSABILITY;

import br.edu.ifba.saj.fwads.exception.CadastroProdutoException;
import br.edu.ifba.saj.fwads.model.Produto;

public class DepartamentoValidationHandler implements ProdutoValidationHandler {
    private ProdutoValidationHandler next;

    @Override
    public void setNext(ProdutoValidationHandler next) {
        this.next = next;
    }

    @Override
    public boolean handle(Produto produto) throws CadastroProdutoException {
        if (produto.getDepartamento() == null) {
            throw new CadastroProdutoException("Selecione ou crie um departamento antes de cadastrar produto.");
        }
        if (next != null) {
            return next.handle(produto);
        }
        return true;
    }
}