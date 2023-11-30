package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final ModelMapper modelMapper;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper, PacienteService pacienteService,
                        OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        configureMapping();
    }

    private TurnoSalidaDto entidadADto(Turno turno, PacienteSalidaDto pacienteSalidaDto,
                                       OdontologoSalidaDto odontologoSalidaDto) {
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turno, TurnoSalidaDto.class);
        turnoSalidaDto.setPacienteSalidaDto(pacienteSalidaDto);
        turnoSalidaDto.setOdontologoSalidaDto(odontologoSalidaDto);
        return turnoSalidaDto;
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> listaTurnos = turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();

        if (LOGGER.isInfoEnabled())
            LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(listaTurnos));
        return listaTurnos;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if (turnoBuscado != null) {
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoEncontrado));
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");

        return turnoEncontrado;
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException {

        LOGGER.info("TurnoEntradaDTO: " + JsonPrinter.toString(turnoEntradaDto));
        Turno turnoRecibido = modelMapper.map(turnoEntradaDto, Turno.class);

        OdontologoSalidaDto odontologoSalidaDto =
                odontologoService.buscarOdontologoPorId(turnoEntradaDto.getOdontologo());
        if (odontologoSalidaDto == null) {
            LOGGER.warn("El odontologo proporcionado no existe en la base de datos.");
            throw new BadRequestException("no se ha encontrado ese odontologo");
        }

        PacienteSalidaDto pacienteSalidaDto = pacienteService.buscarPacientePorId(turnoEntradaDto.getPaciente());
        if (pacienteSalidaDto == null) {
            LOGGER.warn("El paciente proporcionado no existe en la base de datos.");
            throw new BadRequestException("no se ha encontrado ese paciente");
        }

        TurnoSalidaDto turnoSalidaDto = null;

        if (odontologoSalidaDto != null && pacienteSalidaDto != null) {

            Turno turnoEntidad = modelMapper.map(turnoEntradaDto, Turno.class);
            LOGGER.info("turnoEntidad: " + JsonPrinter.toString(turnoEntidad));

            Turno turnoAPersistir = turnoRepository.save(turnoEntidad);
            turnoSalidaDto = entidadADto(turnoAPersistir, pacienteSalidaDto, odontologoSalidaDto);
            LOGGER.info("TurnoSalidaDTO: " + JsonPrinter.toString(turnoSalidaDto));

        }

        return turnoSalidaDto;
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno) throws ResourceNotFoundException{
        return null;
    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        if (turnoRepository.findById(id).orElse(null) != null) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el turno con id {}", id);
            throw new ResourceNotFoundException("No se ha encontrado el turno con id " + id);
        }
    }

    private void configureMapping() {
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class);
                /*.addMappings(mapper -> mapper.map(TurnoEntradaDto::getOdontologo, Turno::setOdontologo))
                .addMappings(mapper -> mapper.map(TurnoEntradaDto::getPaciente, Turno::setPaciente));*/
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Turno::getOdontologo, TurnoSalidaDto::setOdontologoSalidaDto))
                .addMappings(mapper -> mapper.map(Turno::getPaciente, TurnoSalidaDto::setPacienteSalidaDto));
        modelMapper.typeMap(TurnoModificacionEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.map(TurnoModificacionEntradaDto::getOdontologo, Turno::setOdontologo))
                .addMappings(mapper -> mapper.map(TurnoModificacionEntradaDto::getPaciente, Turno::setPaciente));

    }


}
