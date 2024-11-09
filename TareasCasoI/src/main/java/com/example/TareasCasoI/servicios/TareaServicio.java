package com.example.TareasCasoI.servicios;

import com.example.TareasCasoI.entidades.Tarea;
import com.example.TareasCasoI.enumeraciones.Estado;
import com.example.TareasCasoI.excepciones.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TareasCasoI.repositorios.TareaRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TareaServicio {

    @Autowired
    private TareaRepositorio tareaRepositorio;

    @Transactional
    public void crearTarea(String titulo, String descripcion, Date fechaVencimiento) throws MyException {

        validar(titulo, descripcion, fechaVencimiento);

        Tarea tarea = new Tarea();

        tarea.setTitulo(titulo);

        tarea.setDescripcion(descripcion);

        tarea.setFechaCreacion(new Date());

        tarea.setFechaVencimiento(fechaVencimiento);

        tarea.setEstado(Estado.POR_HACER);

        tareaRepositorio.save(tarea);
    }

    public List<Tarea> listarTareas() {

        List<Tarea> tareas = new ArrayList<>();

        tareas = tareaRepositorio.findAll();

        return tareas;
    }

    @Transactional
    public void modificarTarea(String id, String titulo, String descripcion, Date fechaVencimiento, Estado estado) throws MyException {

        validar(titulo, descripcion, fechaVencimiento);

        Optional<Tarea> respuesta = tareaRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Tarea tarea = respuesta.get();

            tarea.setTitulo(titulo);

            tarea.setDescripcion(descripcion);

            tarea.setFechaVencimiento(fechaVencimiento);

            tarea.setEstado(estado);

            tareaRepositorio.save(tarea);

        }
    }

    @Transactional
    public void eliminar(String id) throws MyException {

        Tarea tarea = tareaRepositorio.getById(id);

        tareaRepositorio.delete(tarea);

    }

    public Tarea buscarPorId(String id) {
        return tareaRepositorio.buscarPorId(id);
    }

    private void validar(String titulo, String descripcion, Date fechaVencimiento) throws MyException {

        if (titulo.isEmpty() || titulo == null) {
            throw new MyException("El titulo no puede ser nulo o estar vacio");
        }
        if (descripcion.isEmpty() || descripcion == null) {
            throw new MyException("La descripcion no puede ser nulo o estar vacio");
        }
        if (fechaVencimiento == null) {
            throw new MyException("La fechaVencimiento no puede ser nulo");
        }
    }

}
