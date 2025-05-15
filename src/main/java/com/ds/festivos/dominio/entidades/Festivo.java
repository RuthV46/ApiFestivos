package com.ds.festivos.dominio.entidades;

import jakarta.persistence.*;

@Entity //entidad
@Table(name = "festivo") // llama a la tabla desde la base de datos
public class Festivo {
	
    /*GenerationType.IDENTITY ya que delega la generación del ID a la base de datos
    (equivalente a AUTOINCREMENT en otros motores) */

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String nombre;
   private int dia;
   private int mes;

   @Column (name = "diaspascua")
   private Integer diasPascua;
   
   /* Para relacionar llave foranea*/
   @ManyToOne
   @JoinColumn(name = "idtipo") 
   private TipoFestivo tipo;

   public Festivo() {
   }

   public Festivo(Long id, String nombre, int dia, int mes, int diasPascua, TipoFestivo tipo) {
       this.id = id;
       this.nombre = nombre;
       this.dia = dia;
       this.mes = mes;
       this.diasPascua = diasPascua;
       this.tipo = tipo;
   }

   public Long getId() {
       return id;
   }

   public void setId(Long id) {
       this.id = id;
   }

   public String getNombre() {
       return nombre;
   }

   public void setNombre(String nombre) {
       this.nombre = nombre;
   }

   public int getDia() {
       return dia;
   }

   public void setDia(int dia) {
       this.dia = dia;
   }

   public int getMes() {
       return mes;
   }

   public void setMes(int mes) {
       this.mes = mes;
   }

   public TipoFestivo getTipo() {
       return tipo;
   }

   public void setTipo(TipoFestivo tipo) {
       this.tipo = tipo;
   }

   public Integer getDiasPascua() {
       return diasPascua;
   }

   public void setDiasPascua(Integer diasPascua) {
       this.diasPascua = diasPascua;
   }   

}
