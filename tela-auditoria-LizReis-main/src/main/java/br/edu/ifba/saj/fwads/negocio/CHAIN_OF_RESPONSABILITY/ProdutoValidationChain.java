package br.edu.ifba.saj.fwads.negocio.CHAIN_OF_RESPONSABILITY;

import br.edu.ifba.saj.fwads.exception.CadastroProdutoException;
import br.edu.ifba.saj.fwads.model.Produto;

public class ProdutoValidationChain {
    private ProdutoValidationHandler firstHandler;

    public ProdutoValidationChain() {
        // Criar todos os handlers
        ProdutoValidationHandler modeloHandler = new ModeloValidationHandler();
        ProdutoValidationHandler tamanhoHandler = new TamanhoValidationHandler();
        ProdutoValidationHandler corHandler = new CorValidationHandler();
        ProdutoValidationHandler precoHandler = new PrecoValidationHandler();
        ProdutoValidationHandler departamentoHandler = new DepartamentoValidationHandler();

        // Encadear todos os handlers
        modeloHandler.setNext(tamanhoHandler);
        tamanhoHandler.setNext(corHandler);
        corHandler.setNext(precoHandler);
        precoHandler.setNext(departamentoHandler);

        // Definir o primeiro handler da cadeia
        this.firstHandler = modeloHandler;
    }

    public boolean validate(Produto produto) throws CadastroProdutoException {
        return firstHandler.handle(produto);
    }
}