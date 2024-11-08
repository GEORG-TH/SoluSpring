package com.example.TareasCasoI.repositorios;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.TareasCasoI.entidades.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TareaRepositorio extends JpaRepository<Tarea,String>{
	
    @Query("SELECT t FROM Tarea t WHERE t.id = :id")
    public Tarea buscarPorId(@Param("id") String id);
    
}
