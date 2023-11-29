package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ITurnoService {

    List<TurnoSalidaDto> listarTurnos();
    TurnoSalidaDto buscarTurnoPorID(Long id);
    TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto);
    TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno);
    void eliminarTurno(Long id) throws ResourceNotFoundException;

}
