package com.ds.festivos.aplicacion.servicios;

import com.ds.festivos.DTOs.*;
import com.ds.festivos.dominio.entidades.*;
import com.ds.festivos.exception.*;
import com.ds.festivos.infraestructura.repositorios.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.DateTimeException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FestivoServicio {
 
	@Autowired
    private IFestivoRepository festivoRepository;
    
	public ValidacionDTO validarFechaFestiva(String TextoFecha) throws FechaInvalidaException {
	    try {
	        DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toFormatter();
	        LocalDate fecha = LocalDate.parse(TextoFecha, formatter);
	        
	        int dia = fecha.getDayOfMonth();
	        int mes = fecha.getMonthValue();
	        int año = fecha.getYear();
	        
	        // 1. Verificar festivos fijos (tipo 1)
	        List<Festivo> festivosFijos = festivoRepository.buscarPorDiaYMes(dia, mes)
	                .stream()
	                .filter(f -> f.getTipo().getId() == 1)
	                .collect(Collectors.toList());
	        
	        if (!festivosFijos.isEmpty()) {
	            return crearRespuesta(true, "Es festivo: " + festivosFijos.get(0).getNombre(), fecha);
	        }
	        
	        // 2. Verificar festivos de "puente festivo" (tipo 2)
	        List<Festivo> festivosTipo2 = festivoRepository.findAll().stream()
	                .filter(f -> f.getTipo().getId() == 2)
	                .collect(Collectors.toList());
	        
	        for (Festivo festivo : festivosTipo2) {
	            LocalDate fechaOriginal = LocalDate.of(año, festivo.getMes(), festivo.getDia());
	            LocalDate fechaPuente = calcularPuenteFestivo(fechaOriginal);
	            
	            if (fecha.equals(fechaPuente)) {
	                return crearRespuesta(true, "Es festivo (puente): " + festivo.getNombre(), fecha);
	            }
	        }
	        
	        // 3. Verificar festivos basados en Pascua (tipos 3 y 4)
	        LocalDate domingoPascua = calcularDomingoPascua(año);
	        
	        List<Festivo> festivosMovibles = festivoRepository.findAll().stream()
	                .filter(f -> f.getTipo().getId() == 3 || f.getTipo().getId() == 4)
	                .collect(Collectors.toList());
	        
	        for (Festivo festivo : festivosMovibles) {
	            LocalDate fechaFestivo = domingoPascua.plusDays(festivo.getDiasPascua());
	            
	            if (festivo.getTipo().getId() == 4) {
	                fechaFestivo = calcularPuenteFestivo(fechaFestivo);
	            }
	            
	            if (fecha.equals(fechaFestivo)) {
	                return crearRespuesta(true, "Es festivo: " + festivo.getNombre(), fecha);
	            }
	        }
	        
	        return crearRespuesta(false, "No es festivo", fecha);
	        
	    } catch (DateTimeParseException e) {
	        throw new FechaInvalidaException("Formato de fecha inválido. Use dd-MM-yyyy");
	    } catch (DateTimeException e) {
	        throw new FechaInvalidaException("Fecha no válida");
	    }
	}

	private ValidacionDTO crearRespuesta(boolean esFestivo, String mensaje, LocalDate fecha) {
	    ValidacionDTO resultado = new ValidacionDTO(esFestivo, mensaje);
	    resultado.setFechaFormateada(fecha);
	    return resultado;
	}
	 
	 public List<FestivoDTO> listarFestivosPorAnio(int año) {
	     LocalDate domingoPascua = calcularDomingoPascua(año);
	     
	     return festivoRepository.findAll().stream()
	             .map(festivo -> {
	                 FestivoDTO dto = convertirADTO(festivo);
	                 calcularFechaReal(dto, año, domingoPascua);
	                 return dto;
	             })
	             .collect(Collectors.toList());
	 }
	 
	 public List<FestivoDTO> listarTodos() {
	     return festivoRepository.findAll().stream()
	             .map(this::convertirADTO)
	             .collect(Collectors.toList());
	 }
	 
	 public FestivoDTO obtenerPorId(Long id) {
	     Festivo festivo = festivoRepository.findById(id)
	             .orElseThrow(() -> new RuntimeException("Festivo no encontrado"));
	     return convertirADTO(festivo);
	 }
	 
	 private LocalDate calcularPuenteFestivo(LocalDate fecha) {
		 
		 switch(fecha.getDayOfWeek()) {
		 
			 case TUESDAY:
				 return fecha.plusDays(6);
			 case WEDNESDAY:
				 return fecha.plusDays(5);
			 case THURSDAY:
				 return fecha.plusDays(4);
			 case FRIDAY:
				 return fecha.plusDays(3);
			 case SATURDAY:
				 return fecha.plusDays(2);
			 case SUNDAY:
				 return fecha.plusDays(1);
			 default: 
				return fecha;
		 }
	 }
	 
	 private LocalDate calcularDomingoPascua(int año) {
	     int a = año % 19;
	     int b = año / 100;
	     int c = año % 100;
	     int d = b / 4;
	     int e = b % 4;
	     int f = (b + 8) / 25;
	     int g = (b - f + 1) / 3;
	     int h = (19 * a + b - d - g + 15) % 30;
	     int i = c / 4;
	     int k = c % 4;
	     int l = (32 + 2 * e + 2 * i - h - k) % 7;
	     int m = (a + 11 * h + 22 * l) / 451;
	     int mes = (h + l - 7 * m + 114) / 31;
	     int dia = ((h + l - 7 * m + 114) % 31) + 1;
	     
	     return LocalDate.of(año, mes, dia);
	 }
	 
	 private void calcularFechaReal(FestivoDTO dto, int año, LocalDate domingoPascua) {
	     if (dto.getIdTipo() == 1) {
	         dto.setFechaReal(LocalDate.of(año, dto.getMes(), dto.getDia()));
	     } else if (dto.getIdTipo() == 2) {
	         LocalDate fechaOriginal = LocalDate.of(año, dto.getMes(), dto.getDia());
	         dto.setFechaReal(calcularPuenteFestivo(fechaOriginal));
	     } else if (dto.getIdTipo() == 3 || dto.getIdTipo() == 4) {
	         LocalDate fechaCalculada = domingoPascua.plusDays(dto.getDiasPascua());
	         if (dto.getIdTipo() == 4) {
	             fechaCalculada = calcularPuenteFestivo(fechaCalculada);
	         }
	         dto.setFechaReal(fechaCalculada);
	     }
	 }
	 
	 private FestivoDTO convertirADTO(Festivo festivo) {
	     FestivoDTO dto = new FestivoDTO();
	     dto.setId(festivo.getId());
	     dto.setNombre(festivo.getNombre());
	     dto.setDia(festivo.getDia());
	     dto.setMes(festivo.getMes());
	     dto.setDiasPascua(festivo.getDiasPascua());
	     dto.setIdTipo(festivo.getTipo().getId());
	     dto.setTipo(festivo.getTipo().getTipo());
	     return dto;
	 }
	 
	 private Festivo convertirAEntidad(FestivoDTO dto) {
	     Festivo festivo = new Festivo();
	     festivo.setId(dto.getId());
	     festivo.setNombre(dto.getNombre());
	     festivo.setDia(dto.getDia());
	     festivo.setMes(dto.getMes());
	     festivo.setDiasPascua(dto.getDiasPascua());
	     
	     TipoFestivo tipo = new TipoFestivo();
	     tipo.setId(dto.getIdTipo());
	     festivo.setTipo(tipo);
	     
	     return festivo;
	 }
}