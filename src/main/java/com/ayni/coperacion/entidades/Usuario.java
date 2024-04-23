package com.ayni.coperacion.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Usuario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column()
    private int id;
    
    @Column()
    private String numeroCelular;
    
    @Column()
    private String nombreUsuario;
    
    @Column()
    private int cliente;
    
    @Column()
    private int mesero;
    
    @Column()
    private int cajero;

}
