package pe.com.cibertec.Dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pe.com.cibertec.domain.Cliente;

public interface ClienteDao extends CrudRepository<Cliente, Long> {
    
    public List<Cliente> findByDniCliContainingIgnoreCase(String palabra);
    
  
    @Query("SELECT c FROM Cliente c WHERE LOWER(concat(c.nomCli, c.apePat, c.apeMat, c.dniCli, c.dirCli)) LIKE %:palabra%")
    List<Cliente> filtrarClientes(@Param("palabra") String palabra);
}