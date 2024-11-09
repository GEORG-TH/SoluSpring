package com.example.EstudiantesCasoII.entidades;

import com.example.EstudiantesCasoII.enumeraciones.ProgramaAcademico;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Estudiante {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String nombreCompleto;
    
    private String correo;
    
    private Integer edad;
    
    @Enumerated(EnumType.STRING)
    private ProgramaAcademico programaAcademico;
    
    private Integer anioIngreso;

    public Estudiante() {
    }

    public Estudiante(String id, String nombreCompleto, String correo, Integer edad, ProgramaAcademico programaAcademico, Integer anioIngreso) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.edad = edad;
        this.programaAcademico = programaAcademico;
        this.anioIngreso = anioIngreso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public ProgramaAcademico getProgramaAcademico() {
        return programaAcademico;
    }

    public void setProgramaAcademico(ProgramaAcademico programaAcademico) {
        this.programaAcademico = programaAcademico;
    }

    public Integer getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(Integer anioIngreso) {
        this.anioIngreso = anioIngreso;
    }
    
    
    
    
}
