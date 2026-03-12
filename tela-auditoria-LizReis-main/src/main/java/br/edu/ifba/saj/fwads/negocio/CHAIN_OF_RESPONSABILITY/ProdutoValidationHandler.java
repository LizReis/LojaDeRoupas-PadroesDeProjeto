package br.edu.ifba.saj.fwads.negocio.CHAIN_OF_RESPONSABILITY;

import br.edu.ifba.saj.fwads.exception.CadastroProdutoException;
import br.edu.ifba.saj.fwads.model.Produto;
//Interface para o padrão Chain of Responsability, define os métodos para configurar o próximo handler e para processar a validação do produto
public interface ProdutoValidationHandler {
    void setNext(ProdutoValidationHandler next);
    boolean handle(Produto produto) throws CadastroProdutoException;
}