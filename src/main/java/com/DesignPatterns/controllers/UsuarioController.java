package com.DesignPatterns.controllers;

import com.DesignPatterns.exceptions.DataBaseException;
import com.DesignPatterns.models.Ayuda;
import com.DesignPatterns.models.Usuario;
import com.DesignPatterns.services.UsuarioService;
import com.DesignPatterns.services.admin.AyudaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {
    //prueba
    @GetMapping("hello world")
    public List<Usuario> helloW() {
        List<Usuario> listPrueba = new ArrayList<>();
        Usuario uPrueba = new Usuario("ale","mosq", "ejemplo@email.com", "pass", "img");
        listPrueba.add(uPrueba);
        return listPrueba;
    }
    @GetMapping("base")
    public String conectarBase() {
        try {
            return new UsuarioService().conectar();
        } catch (Exception e) {
            return e.getMessage() + "error";
        }
    }
    //prueba
    @GetMapping("/users/all")
    public ResponseEntity<List<Usuario>> obtenerUsuariosController() {
        try {
            List<Usuario> listUsuarios = new UsuarioService().obtenerUsuarios();
            return new ResponseEntity<List<Usuario>>(listUsuarios,HttpStatus.OK);//http status code 200
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/registrar")
    public ResponseEntity<String> registrarUsuarioController(@RequestBody Usuario usuario) {
        try {
            int resultado = new UsuarioService().crearUsuario(usuario);
            if(resultado == 0) {throw new Exception("no se puedo registrar el usuario");}
            return new ResponseEntity<String>("usuario registrado exitosamente", HttpStatus.CREATED);// http status 201
        }catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage()+ " no se registro",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/users/actualizar")
    public ResponseEntity<String> actualizarUsuarioController(@RequestBody Usuario usuario) {
        try {
            int resultado = new UsuarioService().actualizarUsuario(usuario);
            if(resultado == 0) {throw new Exception("no se puedo Actualizar el usuario");}
            return new ResponseEntity<String>("usuario actualizado exitosamente", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("users/eliminar/{idUsuario}")
    public ResponseEntity<String> eliminarUsuarioController(@PathVariable("idUsuario") int idUsuario) {
        try {
            int resultado = new UsuarioService().eliminarUsuario(idUsuario);
            if(resultado == 0) {throw new Exception("no se puedo eliminar el usuario");}
            return new ResponseEntity<String>("usuario eliminado exitosamente", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/users/login")
    public ResponseEntity<Usuario> inicioUsuarioController(@RequestBody Usuario usuario) {

        try {
            Usuario usuarioRes = new UsuarioService().iniciarSesion(usuario.getEmail(), usuario.getContra());
            if (usuarioRes != null) {
                return new ResponseEntity<>(usuarioRes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("users/obtenerUsuario/{idUsuario}")
    public ResponseEntity<Usuario> obtenerUsuarioController(@PathVariable("idUsuario") int idUsuario) {
        try {
            Usuario usuario = new UsuarioService().obtenerUsuario(idUsuario);
            if (usuario == null) {
                throw new Exception("No se pudo obtener la información del usuario");
            }
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>((Usuario) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Ranking")
    public ResponseEntity<List<Usuario>> listarRanking() {
        try {
            List<Usuario> usuarios = new UsuarioService().listarRanking();
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @GetMapping("users/ayuda")
    public ResponseEntity<List<Ayuda>> obtenerAyuda() {
        try {
            List<Ayuda> ayuda= new AyudaService().readAyuda();
            if (ayuda == null) {
                throw new Exception("No se pudo obtener la información de la ayuda");
            }
            return new ResponseEntity<List<Ayuda>>(ayuda, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
