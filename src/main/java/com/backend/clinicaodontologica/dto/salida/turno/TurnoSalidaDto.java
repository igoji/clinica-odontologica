package com.backend.clinicaodontologica.dto.salida.turno;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class TurnoSalidaDto {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaYHora;
    private OdontologoTurnoSalidaDto odontologoTurnoSalidaDto;
    private PacienteTurnoSalidaDto pacienteTurnoSalidaDto;

    public TurnoSalidaDto() {
    }

    public TurnoSalidaDto(Long id, LocalDateTime fechaYHora, OdontologoTurnoSalidaDto odontologoTurnoSalidaDto, PacienteTurnoSalidaDto pacienteTurnoSalidaDto) {
        this.id = id;
        this.fechaYHora = fechaYHora;
        this.odontologoTurnoSalidaDto = odontologoTurnoSalidaDto;
        this.pacienteTurnoSalidaDto = pacienteTurnoSalidaDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public OdontologoTurnoSalidaDto getOdontologoTurnoSalidaDto() {
        return odontologoTurnoSalidaDto;
    }

    public void setOdontologoTurnoSalidaDto(OdontologoTurnoSalidaDto odontologoTurnoSalidaDto) {
        this.odontologoTurnoSalidaDto = odontologoTurnoSalidaDto;
    }

    public PacienteTurnoSalidaDto getPacienteTurnoSalidaDto() {
        return pacienteTurnoSalidaDto;
    }

    public void setPacienteTurnoSalidaDto(PacienteTurnoSalidaDto pacienteTurnoSalidaDto) {
        this.pacienteTurnoSalidaDto = pacienteTurnoSalidaDto;
    }
}
