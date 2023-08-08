/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.com.cibertec.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.cibertec.Dao.DistritoDao;
import pe.com.cibertec.domain.Distrito;

@Service
public class DistritoServiceImpl implements DistritoService {

    @Autowired
    private DistritoDao distritoDao;
    
    @Override
    public List<Distrito> listarDistritos() {
        return distritoDao.findAll();
    }
    
}
