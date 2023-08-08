/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.com.cibertec.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.cibertec.Dao.RutinaDao;
import pe.com.cibertec.domain.Rutina;

@Service
public class RutinaServiceImpl implements RutinaService {

    @Autowired
    private RutinaDao rutinaDao;
    
    @Transactional(readOnly = true)
    public List<Rutina> listarRutinas() {
        return rutinaDao.findAll();
    }
    
    public Rutina getRutinaById(Long id) {
        return rutinaDao.findById(id).orElse(null);
    }
    
}
