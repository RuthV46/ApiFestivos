package com.ds.festivos.aplicacion.servicios;

import com.ds.festivos.dominio.entidades.*;
import com.ds.festivos.infraestructura.repositorios.IFestivoRepository;
import com.ds.festivos.infraestructura.repositorios.ITipoRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataInitializer implements CommandLineRunner {
 
 private ITipoRepository tipoRepository;
 private IFestivoRepository festivoRepository;
 
 public DataInitializer(ITipoRepository tipoRepository, IFestivoRepository festivoRepository) {
     this.tipoRepository = tipoRepository;
     this.festivoRepository = festivoRepository;
 }
 
 @Override
 public void run(String... args) throws Exception {
     if (tipoRepository.count() == 0) {
         // Crear tipos de festivos
         TipoFestivo tipo1 = new TipoFestivo();
         tipo1.setTipo("Fijo");
         tipo1 = tipoRepository.save(tipo1);
         
         TipoFestivo tipo2 = new TipoFestivo();
         tipo2.setTipo("Ley de Puente festivo");
         tipo2 = tipoRepository.save(tipo2);
         
         TipoFestivo tipo3 = new TipoFestivo();
         tipo3.setTipo("Basado en el domingo de pascua");
         tipo3 = tipoRepository.save(tipo3);
         
         TipoFestivo tipo4 = new TipoFestivo();
         tipo4.setTipo("Basado en el domingo de pascua y Ley de Puente festivo");
         tipo4 = tipoRepository.save(tipo4);
         
         // Crear festivos
         crearFestivos(tipo1, tipo2, tipo3, tipo4);
     }
 }
 
 private void crearFestivos(TipoFestivo tipo1, TipoFestivo tipo2, TipoFestivo tipo3, TipoFestivo tipo4) {
     // Festivos fijos (tipo1)
     festivoRepository.save(crearFestivo("Año nuevo", 1, 1, 0, tipo1));
     festivoRepository.save(crearFestivo("Día del Trabajo", 1, 5, 0, tipo1));
     festivoRepository.save(crearFestivo("Independencia Colombia", 20, 7, 0, tipo1));
     festivoRepository.save(crearFestivo("Batalla de Boyacá", 7, 8, 0, tipo1));
     festivoRepository.save(crearFestivo("Inmaculada Concepción", 8, 12, 0, tipo1));
     festivoRepository.save(crearFestivo("Navidad", 25, 12, 0, tipo1));
     
     // Festivos con puente (tipo2)
     festivoRepository.save(crearFestivo("Santos Reyes", 6, 1, 0, tipo2));
     festivoRepository.save(crearFestivo("San José", 19, 3, 0, tipo2));
     festivoRepository.save(crearFestivo("San Pedro y San Pablo", 29, 6, 0, tipo2));
     festivoRepository.save(crearFestivo("Asunción de la Virgen", 15, 8, 0, tipo2));
     festivoRepository.save(crearFestivo("Día de la Raza", 12, 10, 0, tipo2));
     festivoRepository.save(crearFestivo("Todos los santos", 1, 11, 0, tipo2));
     festivoRepository.save(crearFestivo("Independencia de Cartagena", 11, 11, 0, tipo2));
     
     // Festivos basados en Pascua (tipo3 y tipo4)
     festivoRepository.save(crearFestivo("Jueves Santo", 0, 0, -3, tipo3));
     festivoRepository.save(crearFestivo("Viernes Santo", 0, 0, -2, tipo3));
     festivoRepository.save(crearFestivo("Domingo de Pascua", 0, 0, 0, tipo3));
     festivoRepository.save(crearFestivo("Ascensión del Señor", 0, 0, 40, tipo4));
     festivoRepository.save(crearFestivo("Corpus Christi", 0, 0, 61, tipo4));
     festivoRepository.save(crearFestivo("Sagrado Corazón de Jesús", 0, 0, 68, tipo4));
 }
 
 private Festivo crearFestivo(String nombre, int dia, int mes, int diasPascua, TipoFestivo tipo) {
     Festivo festivo = new Festivo();
     festivo.setNombre(nombre);
     festivo.setDia(dia);
     festivo.setMes(mes);
     festivo.setDiasPascua(diasPascua);
     festivo.setTipo(tipo);
     return festivo;
 }
}