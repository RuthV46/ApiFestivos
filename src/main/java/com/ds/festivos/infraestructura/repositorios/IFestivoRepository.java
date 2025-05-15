package com.ds.festivos.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ds.festivos.dominio.entidades.Festivo;

import java.util.List;

@Repository
public interface IFestivoRepository extends JpaRepository<Festivo, Long> {
    
    @Query("SELECT f FROM Festivo f WHERE f.dia = :dia AND f.mes = :mes")
    List<Festivo> buscarPorDiaYMes(int dia, int mes);
    
    @Query("SELECT f FROM Festivo f WHERE f.tipo.id = 3 OR f.tipo.id = 4")
    List<Festivo> buscarFestivosMovibles();
}
