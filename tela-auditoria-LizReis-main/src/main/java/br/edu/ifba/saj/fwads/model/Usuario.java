package br.edu.ifba.saj.fwads.model;

import java.util.UUID;

public abstract class Usuario extends AbstractModel<UUID>{
    private String nome, login, senha;

    public Usuario(String nome, String login, String senha){

        this.setNome(nome);
        this.setLogin(login);
        this.setSenha(senha);
    }

    public String getNome(){
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getLogin() {
        return this.login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    
    
    public String getSenha(){
        return this.senha;
    }
    private void setSenha(String senha){
        this.senha = senha;
    }

    public String descricaoUsuario(){
        return "Usuário{\n"+"Nome: "+this.getNome()+"\n"+"Login: "+this.getLogin()+"\n"+"Senha: "+this.getSenha()+"\n";
    }
}