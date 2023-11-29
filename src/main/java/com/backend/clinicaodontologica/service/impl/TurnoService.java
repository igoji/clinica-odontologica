package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final ModelMapper modelMapper;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) {

        LOGGER.info("TurnoEntradaDTO: " + JsonPrinter.toString(turnoEntradaDto));

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorId(turnoEntradaDto.getOdontologo().getId());
        if(odontologoSalidaDto == null){
            LOGGER.warn("El odontologo proporcionado no existe en la base de datos.");
            /*excepcion*/
        }

        PacienteSalidaDto pacienteSalidaDto = pacienteService.buscarPacientePorId(turnoEntradaDto.getPaciente().getId());
        if(pacienteSalidaDto == null){
            LOGGER.warn("El paciente proporcionado no existe en la base de datos.");
            /*excepcion*/
        }

        TurnoSalidaDto turnoSalidaDto = null;

        if(odontologoSalidaDto != null && pacienteSalidaDto != null){
            Odontologo odontologo = modelMapper.map(odontologoSalidaDto, Odontologo.class);
            Paciente paciente = modelMapper.map(pacienteSalidaDto, Paciente.class);

            Turno turnoEntidad = modelMapper.map(turnoEntradaDto, Turno.class);
            turnoEntidad.setOdontologo(odontologo);
            turnoEntidad.setPaciente(paciente);


            Turno turnoAPersistir = turnoRepository.save(turnoEntidad);
            turnoSalidaDto = modelMapper.map(turnoAPersistir, TurnoSalidaDto.class);
            LOGGER.info("TurnoSalidaDTO: " + JsonPrinter.toString(turnoSalidaDto));

        }

        return turnoSalidaDto;
    }

    private void configureMapping(){
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.map(TurnoEntradaDto::getOdontologo, Turno::setOdontologo))
                .addMappings(mapper -> mapper.map(TurnoEntradaDto::getPaciente, Turno::setPaciente));
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Turno::getOdontologo, TurnoSalidaDto::setOdontologoSalidaDto))
                .addMappings(mapper -> mapper.map(Turno::getPaciente, TurnoSalidaDto::setPacienteSalidaDto));
        modelMapper.typeMap(TurnoModificacionEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.map(TurnoModificacionEntradaDto::getOdontologo, Turno::setOdontologo))
                .addMappings(mapper -> mapper.map(TurnoModificacionEntradaDto::getPaciente, Turno::setPaciente));
    }
}
