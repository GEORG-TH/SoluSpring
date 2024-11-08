package com.example.TareasCasoI.controladores;

import com.example.TareasCasoI.entidades.Tarea;
import com.example.TareasCasoI.enumeraciones.Estado;
import com.example.TareasCasoI.excepciones.MyException;
import com.example.TareasCasoI.repositorios.TareaRepositorio;
import com.example.TareasCasoI.servicios.TareaServicio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tarea")
public class TareaControlador {

    @Autowired
    private TareaServicio tareaServicio;

    @Autowired
    private TareaRepositorio tareaRepositorio;

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/registrar") //localhost:8080/libro/registrar
    public String registrar() {

        return "tareaForm.html";
    }

    @PostMapping("/registro")
    public String registro(
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaVencimiento,
            ModelMap modelo) {

        try {

            tareaServicio.crearTarea(titulo, descripcion, fechaVencimiento);

            modelo.put("exito", "La tarea fue cargado correctamente!");

        } catch (MyException ex) {

            modelo.put("error", ex.getMessage());

            return "tareaForm.html";  // volvemos a cargar el formulario.
        }
        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Tarea> tareas = tareaServicio.listarTareas();
        modelo.addAttribute("tareas", tareas);

        return "tareaList.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/modificar/{id}")
    public String modificar(
            @PathVariable String id,
            ModelMap modelo) {

        modelo.put("tarea", tareaServicio.buscarPorId(id));
       

        return "tareaModificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(
            @PathVariable String id,
            String titulo,
            String descripcion,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaVencimiento,
            @RequestParam String estado,
            ModelMap modelo) {

        try {

            Estado estadoEnum = Estado.valueOf(estado);
            modelo.addAttribute("tarea", tareaRepositorio.buscarPorId(id));

            tareaServicio.modificarTarea(id, titulo, descripcion, fechaVencimiento, estadoEnum);

            return "redirect:../lista";

        } catch (MyException ex) {

            modelo.put("error", ex.getMessage());

            return "tareaModificar.html";
        } catch (IllegalArgumentException e) {
            // Error en caso de que el estado no coincida con el enum
            modelo.put("error", "Estado inválido");
            return "tareaModificar.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        try {
            tareaServicio.eliminar(id);
            modelo.put("exito", "La tarea fue eliminada correctamente!");
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
        }
        return "redirect:../lista";  // Redirige a la lista de tareas después de eliminar
    }

}
