package com.ds.festivos.dominio.entidades;

import jakarta.persistence.*; // para relacionar columnas, entidades y tablas

@Entity
@Table(name = "tipo")
public class TipoFestivo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo;
    
    public TipoFestivo() {
    }

    public TipoFestivo(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
