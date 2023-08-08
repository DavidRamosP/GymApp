/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.com.cibertec.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.cibertec.Dao.RolDao;
import pe.com.cibertec.domain.Rol;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolDao rolDao;
    
    @Override
    public void guardarRol(Rol rol) {
        rolDao.save(rol);
    }
    
}
