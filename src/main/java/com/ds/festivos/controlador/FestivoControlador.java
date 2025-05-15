package com.ds.festivos.controlador;

import com.ds.festivos.DTOs.*;
import com.ds.festivos.aplicacion.servicios.*;
import com.ds.festivos.exception.FechaInvalidaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/festivos")
public class FestivoControlador {
 
	 @Autowired
	private FestivoServicio festivoService;
	 
	@GetMapping("/validar")//validar?fecha=dd-MM-yyyy
	public ResponseEntity<ValidacionDTO> validarFestivo(@RequestParam String fecha) {
	    try {
	        ValidacionDTO resultado = festivoService.validarFechaFestiva(fecha);
	        return ResponseEntity.ok(resultado);
	    } catch (FechaInvalidaException e) {
	        return ResponseEntity.badRequest().body(new ValidacionDTO(false, e.getMessage()));
	    }
	}
	   
	 @GetMapping("/listar/{año}")
	 public ResponseEntity<List<FestivoDTO>> listarFestivosPorAnio(@PathVariable int año) {
	     return ResponseEntity.ok(festivoService.listarFestivosPorAnio(año));
	 }
	 
	 @GetMapping("/todos")
	 public ResponseEntity<List<FestivoDTO>> listarTodos() {
	     return ResponseEntity.ok(festivoService.listarTodos());
	 }
	 
	 @GetMapping("/obtener/{id}")
	 public ResponseEntity<FestivoDTO> obtenerPorId(@PathVariable Long id) {
	     return ResponseEntity.ok(festivoService.obtenerPorId(id));
	 }
	 
	  
}
