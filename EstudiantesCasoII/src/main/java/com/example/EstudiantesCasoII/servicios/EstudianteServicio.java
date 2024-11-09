package com.example.EstudiantesCasoII.servicios;

import com.example.EstudiantesCasoII.entidades.Estudiante;
import com.example.EstudiantesCasoII.enumeraciones.ProgramaAcademico;
import com.example.EstudiantesCasoII.excepciones.MyException;
import com.example.EstudiantesCasoII.repositorios.EstudianteRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstudianteServicio {

    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

    @Transactional
    public void crearEstuadiante(String nombreCompleto, String correo, Integer edad, ProgramaAcademico programaAcademico, Integer anioIngreso) throws MyException {

        validar(nombreCompleto, correo, edad, anioIngreso);

        Estudiante estudiante = new Estudiante();

        estudiante.setNombreCompleto(nombreCompleto);

        estudiante.setCorreo(correo);

        estudiante.setEdad(edad);

        estudiante.setProgramaAcademico(programaAcademico);

        estudiante.setAnioIngreso(anioIngreso);

        estudianteRepositorio.save(estudiante);
    }

    public List<Estudiante> listarEstudiantes() {

        List<Estudiante> estudiantes = new ArrayList<>();

        estudiantes = estudianteRepositorio.findAll();

        return estudiantes;
    }

    @Transactional
    public void modificarEstudiante(String id, String nombreCompleto, String correo, Integer edad, ProgramaAcademico programaAcademico, Integer anioIngreso) throws MyException {

        validar(nombreCompleto, correo, edad, anioIngreso);

        Optional<Estudiante> respuesta = estudianteRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Estudiante estudiante = respuesta.get();

            estudiante.setNombreCompleto(nombreCompleto);

            estudiante.setCorreo(correo);

            estudiante.setEdad(edad);

            estudiante.setProgramaAcademico(programaAcademico);

            estudiante.setAnioIngreso(anioIngreso);
            
            estudianteRepositorio.save(estudiante);

        }
    }

    @Transactional
    public void eliminar(String id) throws MyException {

        Estudiante estudiante = estudianteRepositorio.getById(id);

        estudianteRepositorio.delete(estudiante);

    }

    public Estudiante buscarPorId(String id) {
        return estudianteRepositorio.buscarPorId(id);
    }

    private void validar(String nombreCompleto, String correo, Integer edad, Integer anioIngreso) throws MyException {

        if (nombreCompleto.isEmpty() || nombreCompleto == null) {
            throw new MyException("El nombreCompleto no puede ser nulo o estar vacio");
        }

        if (correo.isEmpty() || correo == null) {
            throw new MyException("El correo no puede ser nulo o estar vacio");
        }

        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        if (!correo.matches(regex)) {
            throw new MyException("El correo no tiene un formato válido");
        }

        if (edad == null || edad < 18 || edad > 65) {
            throw new MyException("La edad no puede estar vacio y debe estar en el rango de 18-65");
        }

        if (anioIngreso == null || anioIngreso < 2000 || anioIngreso > 2024) {
            throw new MyException("El año de Ingreso no puede estar vacio y debe estar en el rango de 2000-2024");
        }
    }

}
