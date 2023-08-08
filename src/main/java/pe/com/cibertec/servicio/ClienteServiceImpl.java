package pe.com.cibertec.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.cibertec.Dao.ClienteDao;
import pe.com.cibertec.domain.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteDao clienteoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarClientes(String palabra) {

        if (palabra != null) {
            return clienteoDao.filtrarClientes(palabra.toLowerCase());
        }
        return (List<Cliente>) clienteoDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Cliente cliente) {
        clienteoDao.save(cliente);
    }

    @Override
    @Transactional
    public void eliminar(Cliente cliente) {
        clienteoDao.delete(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente encontrarCliente(Cliente cliente) {
        return clienteoDao.findById(cliente.getIdeCli()).orElse(null);
    }

    @Override
    public Long countClientes() {
        return clienteoDao.count();
    }
}
