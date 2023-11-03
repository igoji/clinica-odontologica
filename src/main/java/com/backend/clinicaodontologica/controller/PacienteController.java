package com.backend.clinicaodontologica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public class PacienteController {


    @Controller
    @RequestMapping("/pacientes")
    public class PacienteController {

        private IPacienteService pacienteService;

        public PacienteController(IPacienteService pacienteService) {
            this.pacienteService = pacienteService;
        }

        @GetMapping("/buscarId")
        public String buscarPacientePorId(Model model, @RequestParam int id){
            Paciente paciente = pacienteService.buscarPacientePorId(id);

            model.addAttribute("nombre", paciente.getNombre());
            model.addAttribute("apellido", paciente.getApellido());

            return "paciente";
        }

}
