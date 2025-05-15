package com.ds.festivos.DTOs;

import java.time.LocalDate;

public class ValidacionDTO {
    private boolean esFestivo;
    private String mensaje;
    private LocalDate fechaFormateada;

    // Constructores
    public ValidacionDTO() {
    }

    public ValidacionDTO(boolean esFestivo, String mensaje) {
        this.esFestivo = esFestivo;
        this.mensaje = mensaje;
    }
    
    public ValidacionDTO(boolean esFestivo, String mensaje, LocalDate fechaFormateada) {
        this.esFestivo = esFestivo;
        this.mensaje = mensaje;
        this.fechaFormateada = fechaFormateada;
    }

    // Getters y Setters
    public boolean isEsFestivo() {
        return esFestivo;
    }

    public void setEsFestivo(boolean esFestivo) {
        this.esFestivo = esFestivo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;    
    }
    
    public LocalDate getFechaFormateada() {
        return fechaFormateada;
    }

	public void setFechaFormateada(LocalDate fecha) {
		this.fechaFormateada = fecha;
	}
    
}