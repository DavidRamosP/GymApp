package pe.com.cibertec.web;

import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pe.com.cibertec.domain.Cliente;
import pe.com.cibertec.domain.Distrito;
import pe.com.cibertec.domain.Rol;
import pe.com.cibertec.domain.Usuario;
import pe.com.cibertec.servicio.ClienteService;
import pe.com.cibertec.servicio.DistritoService;
import pe.com.cibertec.servicio.RegistroService;
import pe.com.cibertec.servicio.RolService;
import pe.com.cibertec.servicio.UsuarioService;
import pe.com.cibertec.util.EncriptarPassword;

@Controller
@Slf4j
public class ControladorInicio {

    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private DistritoService distritoService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private RolService rolService;
    
    @Autowired 
    private RegistroService registroService;
            
    @GetMapping("/")
    public String inicio(Model model, @Param("palabra") String palabra, @AuthenticationPrincipal User user) {
        Collection<? extends GrantedAuthority> currentUserRoles = user.getAuthorities();
        log.info("User rol:" + currentUserRoles);
        boolean isAdmin = currentUserRoles.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        boolean isClient = currentUserRoles.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CLIENTE"));

        if (isClient && !isAdmin) {
            model.addAttribute("mostrarBoton", true);
        } else {
            model.addAttribute("mostrarBoton", false);
        }
        model.addAttribute("countClientes",clienteService.countClientes());
        model.addAttribute("countUsersActivos",registroService.obtenerUsuariosActivos());
        model.addAttribute("horasTotales",registroService.horasTotales(ideCliByUser()));
        return "home";
    }

    @GetMapping("/clientes")
    public String clientes(Model model, @Param("palabra") String palabra, @AuthenticationPrincipal User user) {
        Collection<? extends GrantedAuthority> currentUserRoles = user.getAuthorities();
        log.info("User rol:" + currentUserRoles);
        boolean isAdmin = currentUserRoles.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        boolean isClient = currentUserRoles.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CLIENTE"));

        if (isClient && !isAdmin) {
            model.addAttribute("mostrarBoton", true);
        } else {
            model.addAttribute("mostrarBoton", false);
        }

        var clientes = clienteService.listarClientes(palabra);

        model.addAttribute("clientes", clientes);
        model.addAttribute("palabra", palabra);
        return "clientes";
    }

    @GetMapping("/agregar")
    public String agregar(Model model, Cliente cliente) {
        List<Distrito> distritos = distritoService.listarDistritos();
        Usuario usuario = new Usuario();
        Rol rol = new Rol();
        model.addAttribute("distritos", distritos);
        model.addAttribute("usuario",usuario);
        model.addAttribute("rol",rol);
        return "agregar";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model,@ModelAttribute("usuario") Usuario usuario,@ModelAttribute("rol") Rol rol) throws BindException {
        if (result.hasErrors()) {
            String mensaje = "Los siguientes campos presentan errores: ";
            for (FieldError error : result.getFieldErrors()) {
                mensaje += error.getField() + " ";
            }
            model.addAttribute("error", mensaje);
            return "agregar";
        } else {
            clienteService.guardar(cliente);
            usuario.setIde_cli(cliente.getIdeCli().intValue());
            usuario.setPassword(EncriptarPassword.encriptarPassword(usuario.getPassword()));
            usuarioService.guardarRegistro(usuario);
            rol.setId_usuario(usuario.getIdUsuario().intValue());
            rol.setNombre("ROLE_CLIENTE");
            rolService.guardarRol(rol);
            String mensaje = "Cliente con código Nro. " + cliente.getIdeCli() + " guardado con éxito.";
            model.addAttribute("mensaje", mensaje);
            return "agregar";
        }
    }

    @GetMapping("/editar/{ideCli}")
    public String editar(Cliente cliente, Model model) {
        List<Distrito> distritos = distritoService.listarDistritos();
        cliente = clienteService.encontrarCliente(cliente);
        model.addAttribute("cliente", cliente);
        model.addAttribute("distritos", distritos);
        return "actualizar";
    }

    @PostMapping("/guardar/{ideCli}")
    public String actualizar(@Valid Cliente cliente, BindingResult result, Model model) throws BindException {

        if (result.hasErrors()) {
            String mensaje = "Los siguientes campos presentan errores: ";
            for (FieldError error : result.getFieldErrors()) {
                mensaje += error.getField() + " ";
            }
            model.addAttribute("error", mensaje);
            return "actualizar";
        }
        log.info("Cliente con código:" + cliente.getIdeCli());

        if (cliente.getIdeCli() != null) { // Verifica si el cliente ya existe en la base de datos

            Cliente clienteExistente = clienteService.encontrarCliente(cliente); // Recupera el objeto Cliente existente

            if (clienteExistente != null) { // Verifica si el objeto existe
                log.info("****usuario existente");
                log.info("Cliente con código:" + clienteExistente.getIdeCli());

                // Actualiza los campos necesarios del objeto Cliente existente
                clienteExistente.setNomCli(cliente.getNomCli());
                clienteExistente.setApePat(cliente.getApePat());
                clienteExistente.setApeMat(cliente.getApeMat());
                clienteExistente.setDniCli(cliente.getDniCli());
                clienteExistente.setDirCli(cliente.getDirCli());
                clienteExistente.setIdeDis(cliente.getIdeDis());
                clienteExistente.setTelCli(cliente.getTelCli());
                clienteExistente.setEmaCli(cliente.getEmaCli());
                clienteExistente.setFechaNac(cliente.getFechaNac());
                clienteExistente.setSexo(cliente.getSexo());
                clienteExistente.setRolCli(cliente.getRolCli());

                log.info(clienteExistente.getApePat());
                // Guarda el objeto Cliente actualizado en la base de datos
                clienteService.guardar(clienteExistente);
                String mensaje = "Cliente con código Nro. " + clienteExistente.getIdeCli() + " actualizado con éxito.";
                model.addAttribute("mensaje", mensaje);
                return "actualizar";
            }
        }

        log.info("****no existe");
        // Si el cliente no existe, crea un nuevo objeto Cliente y guárdalo en la base de datos
        clienteService.guardar(cliente);
        String mensaje = "Cliente con código Nro. " + cliente.getIdeCli() + " guardado con éxito.";
        model.addAttribute("mensaje", mensaje);
        return "actualizar";

    }

    @GetMapping("/eliminar/{ideCli}")
    public String eliminar(Cliente cliente, Model model) {
        log.info("Cliente con código:" + cliente.getIdeCli());
        clienteService.eliminar(cliente);
        model.addAttribute("cliente", cliente);
        return "redirect:/clientes";
    }
    
    private Integer ideCliByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Integer ide_cli = usuarioService.findUserCodeByUsername(currentUserName);
        return ide_cli;
    }
}
