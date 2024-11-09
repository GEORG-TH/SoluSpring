
package com.example.EstudiantesCasoII.repositorios;

import com.example.EstudiantesCasoII.entidades.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepositorio extends JpaRepository<Estudiante,String>{
    @Query("SELECT e FROM Estudiante e WHERE e.id = :id")
    public Estudiante buscarPorId(@Param("id") String id);
}
