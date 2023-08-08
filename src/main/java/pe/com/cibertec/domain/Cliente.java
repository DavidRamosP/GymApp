package pe.com.cibertec.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
@Table(name = "tb_clientes")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ideCli;

    private String nomCli;

    private String apePat;

    private String apeMat;

    private String dniCli;

    private String dirCli;

    private Integer ideDis;

    private String telCli;

    private String emaCli;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaNac;

    private String sexo;

    private String rolCli;
}
