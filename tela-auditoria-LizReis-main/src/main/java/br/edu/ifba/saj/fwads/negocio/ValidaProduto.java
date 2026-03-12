package br.edu.ifba.saj.fwads.negocio;

import java.util.List;

import br.edu.ifba.saj.fwads.dao.ProdutoDAO;
import br.edu.ifba.saj.fwads.exception.CadastroProdutoException;
import br.edu.ifba.saj.fwads.exception.ValidaRemocaoException;
import br.edu.ifba.saj.fwads.exception.ValidarAtualizacaoException;
import br.edu.ifba.saj.fwads.model.Produto;
import br.edu.ifba.saj.fwads.negocio.CHAIN_OF_RESPONSABILITY.ProdutoValidationChain;

public class ValidaProduto {
    
    public static final ProdutoDAO daoProdutos = new ProdutoDAO();

    //Valida o cadastro de um produto
    //Utiliza o padrão Chain of Responsability para validar os campos do produto
    public boolean validaCadastroProduto(Produto produto) throws CadastroProdutoException{
        ProdutoValidationChain chain = new ProdutoValidationChain();
        if (chain.validate(produto)) {
            daoProdutos.salvar(produto, SessaoUsuario.getInstance().getFuncionarioLogado());
            return true;
        }
        return false;
    }
    //-------------------------------------------------------

    //Valida atualização de um produto
    public Boolean validaAtualizacao(Produto produtoAtual, float preco) throws ValidarAtualizacaoException{
        if(produtoAtual == null){
            throw new ValidarAtualizacaoException("Selecione para atualizar.");
        }else if(preco <= 0){
            throw new NumberFormatException("Digite um valor válido para o preço.");
        }else{
            produtoAtual.setPreco(preco);
            daoProdutos.atualizar(produtoAtual, SessaoUsuario.getInstance().getFuncionarioLogado());
            return true;
        }
    }
    //--------------------------------------------------------

    //Valida remoção de um produto
    public Boolean validaRemocao(Produto produto) throws ValidaRemocaoException{
        if(produto == null){
            throw new ValidaRemocaoException("Selecione para remover");
        }else{
            daoProdutos.deletar(produto.getId());
            return true;
        }
    }

    //Listar Produtos do sistema
    public List<Produto> listarProdutos(){
        return daoProdutos.buscarTodos();
    }
}
