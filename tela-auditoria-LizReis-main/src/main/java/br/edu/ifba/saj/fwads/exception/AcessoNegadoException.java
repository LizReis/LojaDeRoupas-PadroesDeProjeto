package br.edu.ifba.saj.fwads.exception;

/**
 * Exceção lançada quando um usuário tenta realizar uma operação sem permissão.
 * Utilizada pelo padrão Proxy para controlar acesso a operações sensíveis.
 */
public class AcessoNegadoException extends Exception {
    public AcessoNegadoException(String mensagem) {
        super(mensagem);
    }
}
