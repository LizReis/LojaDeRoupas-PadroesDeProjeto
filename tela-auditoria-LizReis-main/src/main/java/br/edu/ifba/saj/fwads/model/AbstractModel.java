package br.edu.ifba.saj.fwads.model;

import java.time.LocalDateTime;
import java.util.Optional;

public abstract class AbstractModel<T>{
    private T id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Optional<Funcionario> createdBy;
    private Optional<Funcionario> updatedBy;


    public AbstractModel(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        this.createdBy = Optional.empty();
        this.updatedBy = Optional.empty();
    }

    public T getId(){
        return this.id;
    }
    public void setId(T id){
        this.id = id;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt(){
        return this.updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }

    public Optional<Funcionario> getCreatedBy(){
        return this.createdBy;
    }
    public void setCreatedBy(Optional<Funcionario> funcionario){
        this.createdBy = funcionario;
    }

    public Optional<Funcionario> getUpdatedBy(){
        return this.updatedBy;
    }
    public void setUpdatedBy(Optional<Funcionario> funcionario){
        this.updatedBy = funcionario;
    }

    public String getClassName(){
        return this.getClass().getSimpleName();
    }
}