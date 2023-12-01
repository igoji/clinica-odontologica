package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class TurnoServiceTest {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;

    @BeforeAll
    void registrarUnPacienteYUnOdontologo(){
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Juan", "Perez", 123456789, LocalDate.of(2023, 12, 24), new DomicilioEntradaDto("calle", 1234, "Localidad", "Provincia"));
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("asdfasdf12345", "Nestor", "Real");

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);
    }

    @Test
    @Order(1)
    void deberiaRegistrarUnTurnoConId1APartirDeOdontologoYPacienteExistentesEnBDD(){

        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2023,12,10,20,30), 1L, 1L);

        TurnoSalidaDto turnoSalidaDto = null;
        try{
            turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);
        }catch (Exception exception){
            exception.printStackTrace();
        }

        assertNotNull(turnoSalidaDto.getId());

    }


    @Test
    @Order(2)
    void alIntentarRegistrarTurnoConOdontologoIdNoExistente_DeberiaLanzarExcepcionBadRequest(){

        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2023,12,10,20,30), 50L, 1L);


        assertThrows(BadRequestException.class, () -> turnoService.registrarTurno(turnoEntradaDto));

    }

    @Test
    @Order(3)
    void alEliminarTurnoCreadoListarTurnoDeberiaDevolverUnaListaVacia(){

        try{
            turnoService.eliminarTurno(1L);
        }catch (Exception e){
            e.printStackTrace();
        }

        List<TurnoSalidaDto> turnos = turnoService.listarTurnos();

        assertTrue(turnos.isEmpty());
    }


}
