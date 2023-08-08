package pe.com.cibertec.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.cibertec.domain.Rutina;


@Repository
public interface RutinaDao extends JpaRepository<Rutina, Long> {}
