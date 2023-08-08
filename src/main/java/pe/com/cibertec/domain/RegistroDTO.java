/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.com.cibertec.domain;

import java.sql.Date;
import java.time.LocalDate;

public interface RegistroDTO {

    Integer getCod_Rutina();
    Integer getIde_Cli();
    String getNom_Cli();
    String getApe_Pat();
    String getNom_Entrenador();
    String getApe_Pat_Entrenador();
    String getNom_Rutina();
    String getHora_Entrada();
    String getHora_Salida();
    LocalDate getFecha();
      
}