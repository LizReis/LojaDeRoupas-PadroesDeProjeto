package br.edu.ifba.saj.fwads.negocio;

import java.util.List;

import br.edu.ifba.saj.fwads.dao.ProdutoDAO;
import br.edu.ifba.saj.fwads.exception.CadastroProdutoException;
import br.edu.ifba.saj.fwads.exception.ValidaRemocaoException;
import br.edu.ifba.saj.fwads.exception.ValidarAtualizacaoException;
import br.edu.ifba.saj.fwads.model.Produto;

public class ValidaProduto {
    
    public static final ProdutoDAO daoProdutos = new ProdutoDAO();

    //Valida o cadastro de um produto
    public boolean validaCadastroProduto(Produto produto) throws CadastroProdutoException{
        if(produto.getModelo() == null || produto.getModelo().trim().isEmpty()){
            throw new CadastroProdutoException("Preencha todos os campos corretamente");
        }else if(produto.getTamanho() == null || produto.getTamanho().trim().isEmpty()){
            throw new CadastroProdutoException("Preencha todos os campos corretamente");
        }else if(produto.getCor() == null || produto.getCor().trim().isEmpty()){
            throw new CadastroProdutoException("Preencha todos os campos corretamente");
        }else if(produto.getPreco() <= 0){
            throw new NumberFormatException("Digite um valor válido para o produto.");
        }else if(produto.getDepartamento() == null){
            throw new CadastroProdutoException("Selecione ou crie um departamento antes de cadastrar produto.");
        }else{
            daoProdutos.salvar(produto, SessaoUsuario.getFuncionarioLogado());
            return true;
        }
        
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
            daoProdutos.atualizar(produtoAtual, SessaoUsuario.getFuncionarioLogado());
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
