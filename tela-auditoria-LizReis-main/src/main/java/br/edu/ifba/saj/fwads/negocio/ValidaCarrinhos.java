package br.edu.ifba.saj.fwads.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.edu.ifba.saj.fwads.dao.CarrinhoDAO;
import br.edu.ifba.saj.fwads.exception.CarrinhoVazioException;
import br.edu.ifba.saj.fwads.exception.CriarCarrinhoException;
import br.edu.ifba.saj.fwads.exception.RemoverCarrinhoException;
import br.edu.ifba.saj.fwads.exception.ValidaRemocaoException;
import br.edu.ifba.saj.fwads.exception.ValidarFinalizarCompraException;
import br.edu.ifba.saj.fwads.exception.VerCarrinhoException;
import br.edu.ifba.saj.fwads.model.Carrinho;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.ItemCompra;
import br.edu.ifba.saj.fwads.model.Produto;

public class ValidaCarrinhos {
    public static final CarrinhoDAO daoCarrinhos = new CarrinhoDAO();

    //Vê se o usuário já tem algum carrinho, caso não tenha o sistema cria um carrinho padrão
    public Carrinho temCarrinhos(Cliente cliente, Produto produto, int quantidade) throws CriarCarrinhoException{
        List<Carrinho> carrinhosDoCliente = cliente.getCarrinhos();

        if(carrinhosDoCliente.isEmpty()){
            Carrinho novoCarrinho = cliente.criarCarrinho("Carrinho Padrão");
            daoCarrinhos.salvar(novoCarrinho, SessaoUsuario.getFuncionarioLogado());

            novoCarrinho.adicionarItem(new ItemCompra(produto, quantidade));
            return novoCarrinho;
        }
        throw new CriarCarrinhoException("Selecione um carrinho para o produto");
    }

    //Cria um carrinho novo com o nome que o cliente digitou na tela que mostra que ele ja tem carrinho
    public Carrinho criarCarrinhoNomeSelecao(Cliente cliente, String nomeCarrinho, Produto produto, int quantidade){
        if(daoCarrinhos.buscarPorNome(nomeCarrinho).isPresent()){
            for(Carrinho carrinho : daoCarrinhos.buscarTodos()){
                if(carrinho.getNome().equals(nomeCarrinho)){
                    carrinho.adicionarItem(new ItemCompra(produto, quantidade));

                    return carrinho;
                }
            }
        }else{
            Carrinho novoCarrinho = cliente.criarCarrinho(nomeCarrinho);
            daoCarrinhos.salvar(novoCarrinho, SessaoUsuario.getFuncionarioLogado());

            novoCarrinho.adicionarItem(new ItemCompra(produto, quantidade));

            return novoCarrinho;
        }
        return null;
    }

    //Ver carrinho selecionado pelo usuário
    public boolean verCarrinho(Carrinho carrinho) throws VerCarrinhoException{
        if(carrinho != null){
            return true;
        }
        throw new VerCarrinhoException("Carrinho não foi selecionado.");

    }

    //Remover carrinho da lista do usuário e do dao
    public boolean removerCarrinhoLista(Carrinho carrinho) throws RemoverCarrinhoException{
        if(daoCarrinhos.buscarPorNome(carrinho.getNome()).isPresent()){
            daoCarrinhos.deletar(carrinho.getId());

            carrinho.getCliente().removerCarrinho(carrinho.getNome());

            return true;
        }
        throw new RemoverCarrinhoException("Carrinho não selecionado ou a lista não existe");
    }

    //Exclui todos os carrinhos do cliente
    public boolean excluirTodosCarrinhos(Cliente cliente) throws RemoverCarrinhoException{
        List<Carrinho> listaCarrinhos = new ArrayList<>(cliente.getCarrinhos());

        if(listaCarrinhos.isEmpty()){
            throw new RemoverCarrinhoException("Não tem carrinho para remover.");
        }else{
            for(Carrinho carrinho : listaCarrinhos){
                removerCarrinhoLista(carrinho);
            }
            return true;
        }
    }

    //Pegar os itens do carrinho selecionado
    public List<ItemCompra> pegarItens(Carrinho carrinho) throws CarrinhoVazioException{
        if(carrinho.getItensCompra().isEmpty()){
            throw new CarrinhoVazioException("Não há itens no carrinho.");
        }else{
            List<ItemCompra> listaItens = carrinho.getItensCompra();
            return listaItens;
        }
    }

    //Exclui um item que o cliente selecionou
    public Carrinho removerItemCarrinho(ItemCompra item) throws ValidaRemocaoException, RemoverCarrinhoException{
        Optional<Carrinho> carrinho = daoCarrinhos.buscarPorItem(item);

        if(carrinho.get() != null){

            carrinho.get().removerItem(item);
            carrinho.get().setValorTotal();

            if(carrinho.get().getValorTotal() == 0){
                removerCarrinhoLista(carrinho.get());
            }

            return carrinho.get();
        }else{
            throw new ValidaRemocaoException("Este item não tem no carrinho.");
        }
    }

    //Método de Finalizar Compra do carrinho
    public void finalizarCompra(Carrinho carrinho) throws RemoverCarrinhoException, ValidarFinalizarCompraException{
        if(carrinho.getItensCompra().isEmpty()){
            throw new ValidarFinalizarCompraException("O carrinho não possui itens para finalizar compra.");
        }else{
            carrinho.finalizarCompra();
            removerCarrinhoLista(carrinho);
        }
    }

    //Listar Carrinhos do sistema
    public List<Carrinho> listarCarrinhos(){
        return daoCarrinhos.buscarTodos();
    }

}
