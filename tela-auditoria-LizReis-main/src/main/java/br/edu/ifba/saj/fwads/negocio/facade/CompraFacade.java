package br.edu.ifba.saj.fwads.negocio.facade;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifba.saj.fwads.exception.CarrinhoVazioException;
import br.edu.ifba.saj.fwads.exception.CriarCarrinhoException;
import br.edu.ifba.saj.fwads.exception.RemoverCarrinhoException;
import br.edu.ifba.saj.fwads.exception.ValidaRemocaoException;
import br.edu.ifba.saj.fwads.exception.ValidarFinalizarCompraException;
import br.edu.ifba.saj.fwads.exception.VerCarrinhoException;
import br.edu.ifba.saj.fwads.model.Carrinho;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.Departamento;
import br.edu.ifba.saj.fwads.model.ItemCompra;
import br.edu.ifba.saj.fwads.model.Produto;
import br.edu.ifba.saj.fwads.negocio.SessaoUsuario;
import br.edu.ifba.saj.fwads.negocio.ValidaCarrinhos;
import br.edu.ifba.saj.fwads.negocio.ValidaProduto;

public class CompraFacade {
    private final ValidaCarrinhos validaCarrinhos;
    private final ValidaProduto validaProduto;

    public CompraFacade() {
        this.validaCarrinhos = new ValidaCarrinhos();
        this.validaProduto = new ValidaProduto();
    }

    public List<Produto> listarProdutosDoDepartamento(Departamento departamento) {
        return validaProduto.listarProdutos()
                .stream()
                .filter(produto -> produto.getDepartamento().equals(departamento))
                .collect(Collectors.toList());
    }

    public List<Carrinho> listarCarrinhosDoClienteLogado() {
        return obterClienteLogado().getCarrinhos();
    }

    public Carrinho adicionarProdutoAoCarrinho(Produto produto, int quantidade) throws CriarCarrinhoException {
        return validaCarrinhos.temCarrinhos(obterClienteLogado(), produto, quantidade);
    }

    public Carrinho adicionarProdutoAoCarrinho(Produto produto, int quantidade, String nomeCarrinho) {
        return validaCarrinhos.criarCarrinhoNomeSelecao(obterClienteLogado(), nomeCarrinho, produto, quantidade);
    }

    public Carrinho validarCarrinho(Carrinho carrinho) throws VerCarrinhoException {
        validaCarrinhos.verCarrinho(carrinho);
        return carrinho;
    }

    public void excluirTodosCarrinhosDoClienteLogado() throws RemoverCarrinhoException {
        validaCarrinhos.excluirTodosCarrinhos(obterClienteLogado());
    }

    public void removerCarrinho(Carrinho carrinho) throws RemoverCarrinhoException {
        validaCarrinhos.removerCarrinhoLista(carrinho);
    }

    public List<ItemCompra> listarItensDoCarrinho(Carrinho carrinho) throws CarrinhoVazioException {
        return validaCarrinhos.pegarItens(carrinho);
    }

    public Carrinho removerItemDoCarrinho(ItemCompra item) throws ValidaRemocaoException, RemoverCarrinhoException {
        return validaCarrinhos.removerItemCarrinho(item);
    }

    public void finalizarCompra(Carrinho carrinho)
            throws RemoverCarrinhoException, ValidarFinalizarCompraException {
        validaCarrinhos.finalizarCompra(carrinho);
    }

    private Cliente obterClienteLogado() {
        Cliente clienteLogado = SessaoUsuario.getInstance().getClienteLogado();
        if (clienteLogado == null) {
            throw new IllegalStateException("Não há cliente logado para executar a operação.");
        }
        return clienteLogado;
    }
}
