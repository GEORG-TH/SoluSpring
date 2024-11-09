package com.example.EstudiantesCasoII.controladores;

import com.example.EstudiantesCasoII.entidades.Estudiante;
import com.example.EstudiantesCasoII.enumeraciones.ProgramaAcademico;
import com.example.EstudiantesCasoII.excepciones.MyException;
import com.example.EstudiantesCasoII.servicios.EstudianteServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/estudiante")
public class EstudianteControlador {

    @Autowired
    private EstudianteServicio estudianteServicio;

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/registrar") //localhost:8080/estudiante/registrar
    public String registrar() {

        return "estudianteForm.html";
    }

    @PostMapping("/registro")
    public String registro(
            @RequestParam String nombreCompleto,
            @RequestParam String correo,
            @RequestParam Integer edad,
            @RequestParam String programaAcademico,
            @RequestParam Integer anioIngreso,
            ModelMap modelo) {

        try {
            ProgramaAcademico programaAcademico1 = ProgramaAcademico.valueOf(programaAcademico);
            estudianteServicio.crearEstuadiante(nombreCompleto, correo, edad, programaAcademico1, anioIngreso);

            modelo.put("exito", "El estudiante fue cargado correctamente!");

        } catch (MyException ex) {

            modelo.put("error", ex.getMessage());

            return "estudianteForm.html";  // volvemos a cargar el formulario.
        }
        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Estudiante> estudiantes = estudianteServicio.listarEstudiantes();
        modelo.addAttribute("estudiantes", estudiantes);

        return "estudianteList.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/modificar/{id}")
    public String modificar(
            @PathVariable String id,
            ModelMap modelo) {

        modelo.put("estudiante", estudianteServicio.buscarPorId(id));

        return "estudianteModificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(
            @PathVariable String id,
            @RequestParam String nombreCompleto,
            @RequestParam String correo,
            @RequestParam Integer edad,
            @RequestParam String programaAcademico,
            @RequestParam Integer anioIngreso,
            ModelMap modelo) {

        try {

            ProgramaAcademico programaAcademico1 = ProgramaAcademico.valueOf(programaAcademico);

            modelo.addAttribute("estudiante", estudianteServicio.buscarPorId(id));

            estudianteServicio.modificarEstudiante(id, nombreCompleto, correo, edad, programaAcademico1, anioIngreso);

            return "redirect:../lista";

        } catch (MyException ex) {

            modelo.put("error", ex.getMessage());

            return "estudianteModificar.html";
        } catch (IllegalArgumentException e) {
            // Error en caso de que el programaAcademico no coincida con el enum
            modelo.put("error", "Estado inválido");
            return "estudianteModificar.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        try {
            estudianteServicio.eliminar(id);
            modelo.put("exito", "El estudiante fue eliminada correctamente!");
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
        }
        return "redirect:../lista";  // Redirige a la lista de estudiantes después de eliminar
    }
}
