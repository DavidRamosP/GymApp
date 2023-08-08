/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.com.cibertec.servicio;

import java.util.List;
import pe.com.cibertec.domain.Rutina;


public interface RutinaService {
    
    public List<Rutina> listarRutinas();
    
    public Rutina getRutinaById(Long id);
    
}
