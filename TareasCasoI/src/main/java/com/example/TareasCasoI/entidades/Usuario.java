package com.example.TareasCasoI.entidades;

import com.example.TareasCasoI.enumeraciones.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import org.hibernate.annotations.GenericGenerator;



@Entity
public class Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;
    
    protected String nombre;
    
    protected String email;
    
    protected String password;
    
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    
    @Enumerated(EnumType.STRING)
    protected Rol rol;

    public Usuario(String id, String nombre, String email, String password, Date fechaCreacion, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fechaCreacion = fechaCreacion;
        this.rol = rol;
    }

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    

    
    
    

   
    
}
