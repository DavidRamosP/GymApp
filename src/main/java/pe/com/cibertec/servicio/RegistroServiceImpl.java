/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.com.cibertec.servicio;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.cibertec.Dao.RegistroDao;
import pe.com.cibertec.domain.Registro;
import pe.com.cibertec.domain.RegistroDTO;

@Service
public class RegistroServiceImpl implements RegistroService {
    
    @Autowired
    private RegistroDao registroDao;

    @Transactional(readOnly = true)
    public List<RegistroDTO> listarRegistros(String param) {
         if (param != null) {
            return registroDao.filtrarAsistencia(param.toLowerCase());
        }
        return registroDao.listarRegistros();
    }

    @Override
    public void guardarRegistro(Registro registro) {
        registroDao.save(registro);
    }

    @Override
    public void eliminar(Long id) {
        registroDao.deleteById(id);
    }

    @Override
    public void actualizarRegistro(Long id, Registro registro) {
        Registro registroExistente = registroDao.findById(id).orElse(null);
        if (registroExistente != null) {
            registro.setCod_registro(id);
            registroDao.save(registro);
        }
    }

    @Override
    public Registro encontrarRegistro(Long id) {
        if (id != null) {
            return registroDao.findById(id).orElse(null);
        } else {
            return null;
        }
    }
    
    @Override
    public boolean hayRegistroEnProceso(Integer user_ide_cli) {
        List<Registro> registros = registroDao.findAll();
        for (Registro registro : registros) {
            if (registro.getHora_salida() == null && registro.getIde_cli() == user_ide_cli) {
                return true;
            }
        }
        return false;
    }
    
    public Duration getDuracion(String horaEntrada, String horaSalida) {
        LocalTime entrada = LocalTime.parse(horaEntrada);
        LocalTime salida = LocalTime.parse(horaSalida);
        return Duration.between(entrada, salida);
    }
    
@Override
public Long horasTotales(Integer user_ide_cli) {
    List<Registro> registros = registroDao.findAll();
    Duration totalDuracion = Duration.ZERO;
    for (Registro registro : registros) {
        if (registro.getHora_salida() != null && registro.getIde_cli() == user_ide_cli) {
            String entrada = registro.getHora_entrada();
            String salida = registro.getHora_salida();
            totalDuracion = totalDuracion.plus(getDuracion(entrada,salida));
        }
    }
    return totalDuracion.toHours();
}


    @Override
    public Long obtenerIdRegistroEnProceso() {
        List<Registro> registros = registroDao.findAll();
        for (Registro registro : registros) {
            if (registro.getHora_salida() == null) {
                return registro.getCod_registro();
            }
        }
        return null;
    }

    @Override
    public Integer obtenerUsuariosActivos() {
        List<Registro> registros = registroDao.findAll();
        Integer count = 0;
        for (Registro registro : registros) {
            if (registro.getHora_salida() == null) {
                count++;
            }
        }
        return count;
    }
}
