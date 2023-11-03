package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.model.Paciente;
import com.backend.clinicaodontologica.repository.IDao;

import java.util.List;

public class PacienteService {


    private IDao<Paciente> pacienteIDao;

    public PacienteService(IDao<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }

    public Paciente registrarPaciente(Paciente paciente) {
        return pacienteIDao.registrar(paciente);
    }

    public List<Paciente> listarPacientes() {
        return pacienteIDao.listarTodos();
    }

}
