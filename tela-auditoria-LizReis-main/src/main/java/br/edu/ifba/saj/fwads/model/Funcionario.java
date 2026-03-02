
package br.edu.ifba.saj.fwads.model;

public class Funcionario extends Usuario {
    private String setorTrabalho;

    public Funcionario(String nome, String login, String senha, String setorTrabalho){
        super(nome, login, senha);
        this.setSetorTrabalho(setorTrabalho);
    }
    
    public String getSetorTrabalho(){
        return this.setorTrabalho;
    }
    public void setSetorTrabalho(String setorTrabalho){
        this.setorTrabalho = setorTrabalho;
    }
}
