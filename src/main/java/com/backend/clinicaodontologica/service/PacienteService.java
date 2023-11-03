package com.backend.clinicaodontologica.service;

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
