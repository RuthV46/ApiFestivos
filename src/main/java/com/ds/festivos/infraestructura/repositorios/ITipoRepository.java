package com.ds.festivos.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ds.festivos.dominio.entidades.TipoFestivo;

@Repository
public interface ITipoRepository extends JpaRepository<TipoFestivo, Long> {
}

