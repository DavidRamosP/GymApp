package pe.com.cibertec.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.cibertec.domain.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {
    Usuario findByUsername ( String username );
}
