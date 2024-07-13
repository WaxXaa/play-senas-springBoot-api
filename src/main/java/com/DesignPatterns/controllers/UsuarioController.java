package com.DesignPatterns.controllers;

import com.DesignPatterns.models.UsuarioDTO;
import com.DesignPatterns.services.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuarioController {
    @GetMapping("/users/all")
    public List<UsuarioDTO> obtenerUsuarios() {
        return new UsuarioService().opbtenerUsuarios();
    }

    @PostMapping("/users/registrar")
    public int registrarUsuario(@RequestBody UsuarioDTO usuario) {
        return new UsuarioService().crearUsuario(usuario);
    }
}
