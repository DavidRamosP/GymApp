package pe.com.cibertec.Dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.cibertec.domain.Registro;
import pe.com.cibertec.domain.RegistroDTO;

@Repository
public interface RegistroDao extends JpaRepository<Registro, Long> {

//    @Query(value = "{call listado_registros()}", nativeQuery = true)
    @Query(nativeQuery = true, value = "SELECT \n" +
"        ru.cod_rutina,\n" +
"        c.ide_cli,\n" +
"        c.nom_cli,\n" +
"        c.ape_pat,\n" +
"		e.nom_entrenador,\n" +
"        e.ape_pat_entrenador,\n" +
"        ru.nom_rutina, \n" +
"        reg.hora_entrada,\n" +
"        reg.hora_salida,\n" +
"        reg.fecha\n" +
"    FROM \n" +
"        tb_rutinas AS ru \n" +
"        JOIN tb_entrenadores AS e ON ru.cod_entrenador = e.cod_entrenador \n" +
"        JOIN tb_registros AS reg ON reg.cod_rutina = ru.cod_rutina \n" +
"        JOIN tb_clientes AS c ON c.ide_cli = reg.ide_cli\n" +
"	ORDER BY \n" +
"    reg.fecha DESC,\n" +
"    reg.hora_entrada DESC;")
    List<RegistroDTO> listarRegistros();  
    
    
    @Query(nativeQuery = true, value = "	SELECT \n" +
"        ru.cod_rutina,\n" +
"        c.ide_cli,\n" +
"        c.nom_cli,\n" +
"        c.ape_pat,\n" +
"		e.nom_entrenador,\n" +
"        e.ape_pat_entrenador,\n" +
"        ru.nom_rutina, \n" +
"        reg.hora_entrada,\n" +
"        reg.hora_salida,\n" +
"        reg.fecha\n" +
"    FROM \n" +
"        tb_rutinas AS ru \n" +
"        JOIN tb_entrenadores AS e ON ru.cod_entrenador = e.cod_entrenador \n" +
"        JOIN tb_registros AS reg ON reg.cod_rutina = ru.cod_rutina \n" +
"        JOIN tb_clientes AS c ON c.ide_cli = reg.ide_cli\n" +
"        WHERE LOWER(concat(c.nom_cli, c.ape_pat, c.ape_pat, e.nom_entrenador,ape_pat_entrenador,ru.nom_rutina)) LIKE %:param%\n" +
"	ORDER BY \n" +
"    reg.fecha DESC,\n" +
"    reg.hora_entrada DESC;")
    List<RegistroDTO> filtrarAsistencia(@Param("param") String param);
}
