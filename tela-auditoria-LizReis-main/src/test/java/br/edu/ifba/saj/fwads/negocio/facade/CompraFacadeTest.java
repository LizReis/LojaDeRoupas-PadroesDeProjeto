package br.edu.ifba.saj.fwads.negocio.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifba.saj.fwads.model.Carrinho;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.Departamento;
import br.edu.ifba.saj.fwads.model.Produto;
import br.edu.ifba.saj.fwads.negocio.SessaoUsuario;
import br.edu.ifba.saj.fwads.negocio.ValidaCarrinhos;
import br.edu.ifba.saj.fwads.negocio.ValidaDepartamento;
import br.edu.ifba.saj.fwads.negocio.ValidaProduto;
import br.edu.ifba.saj.fwads.negocio.ValidaUsuario;

class CompraFacadeTest {
    private final CompraFacade compraFacade = new CompraFacade();

    @BeforeEach
    void setUp() {
        limparEstado();
    }

    @AfterEach
    void tearDown() {
        limparEstado();
    }

    @Test
    void deveListarProdutosSomenteDoDepartamentoInformado() {
        Departamento feminino = salvarDepartamento("Feminino");
        Departamento masculino = salvarDepartamento("Masculino");

        Produto vestido = salvarProduto("Vestido", feminino, 99.9f);
        Produto saia = salvarProduto("Saia", feminino, 49.9f);
        salvarProduto("Camisa", masculino, 59.9f);

        List<Produto> produtos = compraFacade.listarProdutosDoDepartamento(feminino);

        assertEquals(2, produtos.size());
        assertTrue(produtos.contains(vestido));
        assertTrue(produtos.contains(saia));
    }

    @Test
    void deveCriarCarrinhoPadraoQuandoClienteNaoTemCarrinhos() throws Exception {
        Cliente cliente = new Cliente("Ana", "ana", "123");
        SessaoUsuario.getInstance().setClienteLogado(cliente);

        Produto produto = salvarProduto("Vestido", salvarDepartamento("Feminino"), 120f);

        Carrinho carrinho = compraFacade.adicionarProdutoAoCarrinho(produto, 2);

        assertEquals("Carrinho Padrão", carrinho.getNome());
        assertEquals(1, cliente.getCarrinhos().size());
        assertEquals(1, carrinho.getItensCompra().size());
        assertEquals(240f, carrinho.getValorTotal());
    }

    @Test
    void deveCriarCarrinhoParaClienteLogadoMesmoSeOutroClienteJaTiverMesmoNome() {
        Produto produto = salvarProduto("Vestido", salvarDepartamento("Feminino"), 75f);

        Cliente primeiroCliente = new Cliente("Maria", "maria", "123");
        Carrinho carrinhoPrimeiroCliente = primeiroCliente.criarCarrinho("Favoritos");
        ValidaCarrinhos.daoCarrinhos.salvar(carrinhoPrimeiroCliente, Optional.empty());

        Cliente segundoCliente = new Cliente("Joana", "joana", "123");
        SessaoUsuario.getInstance().setClienteLogado(segundoCliente);

        Carrinho carrinhoSegundoCliente = compraFacade.adicionarProdutoAoCarrinho(produto, 1, "Favoritos");

        assertSame(segundoCliente, carrinhoSegundoCliente.getCliente());
        assertEquals(1, segundoCliente.getCarrinhos().size());
        assertEquals(0, carrinhoPrimeiroCliente.getItensCompra().size());
        assertEquals(1, carrinhoSegundoCliente.getItensCompra().size());
    }

    @Test
    void deveFinalizarCompraRemovendoCarrinhoDoClienteEDoDao() throws Exception {
        Cliente cliente = new Cliente("Clara", "clara", "123");
        SessaoUsuario.getInstance().setClienteLogado(cliente);

        Produto produto = salvarProduto("Blusa", salvarDepartamento("Feminino"), 80f);
        Carrinho carrinho = compraFacade.adicionarProdutoAoCarrinho(produto, 1);

        compraFacade.finalizarCompra(carrinho);

        assertTrue(cliente.getCarrinhos().isEmpty());
        assertTrue(carrinho.getItensCompra().isEmpty());
        assertTrue(ValidaCarrinhos.daoCarrinhos.buscarPorNome(carrinho.getNome()).isEmpty());
    }

    private Departamento salvarDepartamento(String nome) {
        Departamento departamento = new Departamento(nome);
        ValidaDepartamento.daoDepartamentos.salvar(departamento, Optional.empty());
        return departamento;
    }

    private Produto salvarProduto(String modelo, Departamento departamento, float preco) {
        Produto produto = Produto.builder()
                .modelo(modelo)
                .tamanho("M")
                .cor("Azul")
                .preco(preco)
                .departamento(departamento)
                .build();
        ValidaProduto.daoProdutos.salvar(produto, Optional.empty());
        return produto;
    }

    private void limparEstado() {
        SessaoUsuario.getInstance().logout();
        SessaoUsuario.getInstance().logoutCliente();

        limparCarrinhos();
        limparProdutos();
        limparDepartamentos();
        limparUsuarios();
    }

    private void limparCarrinhos() {
        List<Carrinho> carrinhos = new ArrayList<>(ValidaCarrinhos.daoCarrinhos.buscarTodos());
        for (Carrinho carrinho : carrinhos) {
            ValidaCarrinhos.daoCarrinhos.deletar(carrinho.getId());
        }
    }

    private void limparProdutos() {
        List<Produto> produtos = new ArrayList<>(ValidaProduto.daoProdutos.buscarTodos());
        for (Produto produto : produtos) {
            ValidaProduto.daoProdutos.deletar(produto.getId());
        }
    }

    private void limparDepartamentos() {
        List<Departamento> departamentos = new ArrayList<>(ValidaDepartamento.daoDepartamentos.buscarTodos());
        for (Departamento departamento : departamentos) {
            ValidaDepartamento.daoDepartamentos.deletar(departamento.getId());
        }
    }

    private void limparUsuarios() {
        List<br.edu.ifba.saj.fwads.model.Usuario> usuarios = new ArrayList<>(ValidaUsuario.daoUsuarios.buscarTodos());
        for (br.edu.ifba.saj.fwads.model.Usuario usuario : usuarios) {
            ValidaUsuario.daoUsuarios.deletar(usuario.getId());
        }
    }
}
