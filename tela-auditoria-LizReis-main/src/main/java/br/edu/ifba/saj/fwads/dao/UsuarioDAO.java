package br.edu.ifba.saj.fwads.dao;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import br.edu.ifba.saj.fwads.model.Usuario;

public class UsuarioDAO extends GenericDAOImpl<Usuario, UUID>{
    
    public UsuarioDAO(){
        super(UUID.class);
    }

    //Busca a lista de usuários por nome
    public List<Usuario> buscarOrdenadosPorNome() {
        return buscarTodos()
                .stream()
                .sorted(Comparator.comparing(Usuario::getNome))
                .collect(Collectors.toList());
    }

    //Busca um usuário pelo login
    public Optional<Usuario> buscarPorLogin(String login){
        return buscarTodos()
                .stream()
                .filter(usuario -> usuario.getLogin().equalsIgnoreCase(login))
                .findFirst();
    }

    //Pega os usuarios que têm createdBy
    public List<Usuario> buscarTemCreatedBy(){
        return buscarTodos()
                .stream()
                .filter(usuario -> usuario.getCreatedBy().isPresent())
                .collect(Collectors.toList());
    }

    //Pega os usuarios que têm updatedBy
    public List<Usuario> buscarTemUpdatedBy(){
        return buscarTodos()
                .stream()
                .filter(usuario -> usuario.getUpdatedBy().isPresent())
                .collect(Collectors.toList());
    }
}