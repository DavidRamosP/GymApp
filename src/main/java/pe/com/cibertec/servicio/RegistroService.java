/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.com.cibertec.servicio;

import java.time.Duration;
import java.util.List;
import pe.com.cibertec.domain.Registro;
import pe.com.cibertec.domain.RegistroDTO;


public interface RegistroService {
    
    public List<RegistroDTO> listarRegistros(String param);
    
    public void guardarRegistro(Registro registro);
    
    public void eliminar(Long id);
    
    public void actualizarRegistro(Long id, Registro registro);
    
    public Registro encontrarRegistro(Long id);
    
    public boolean hayRegistroEnProceso(Integer user_ide_cli);
    
    public Long obtenerIdRegistroEnProceso();
    
    public Integer obtenerUsuariosActivos();
    
    public Long horasTotales(Integer user_ide_cli);
    
}
