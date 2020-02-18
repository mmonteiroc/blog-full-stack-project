package com.esliceu.proyectoglobal.controller;

import com.esliceu.proyectoglobal.entity.Usuario;
import com.esliceu.proyectoglobal.manager.UsuarioManager;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Code created by: mmonteirocl
 * Email: miguelmonteiroclaveri@gmail.com
 * Date: 18/02/2020
 * Package: com.esliceu.proyectoglobal.controller
 * Project: BackEnd
 */
@RestController
public class UsuarioController {
    @Autowired
    UsuarioManager usuarioManager;

    @Autowired
    Gson gson;

    @GetMapping("/user")
    public List<Usuario> getAll() {

        List<Usuario> users = usuarioManager.findAll();
        for (Usuario user : users) {

            // BORRAMOS LAS PASSWORDS ANTES DE RETORNAR EL OBJETO USUARIO AL CLIENTE
            user.setPassword("");
        }

        return usuarioManager.findAll();
    }

    @GetMapping("/user/{id}")
    public Usuario get(@PathVariable("º") Long id) {
        Usuario user = usuarioManager.findById(id);
        user.setPassword("");
        return user;
    }

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody String usuario) {
        Usuario user = usuarioManager.fromJSON(usuario);

        // TODO comprovar que recibimos todos los parametros
        if (false) {
            // TODO cambiar la condicion en if

            return new ResponseEntity<>("No has mandado todos los parametros obligatorios", HttpStatus.BAD_REQUEST);
        }


        // TODO comprabar que el email, es un email, tambien comprobar que la password cumple los requisitos

        if (false) {
            // TODO cambiar la condicion en if

            return new ResponseEntity<>("Los parametros no son correctos", HttpStatus.BAD_REQUEST);
        }

        usuarioManager.saveOrUpdate(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<String> modifiy(@RequestBody String usuario) {
        Usuario user = usuarioManager.fromJSON(usuario);

        // TODO comprovar que recibimos todos los parametros
        if (false) {
            // TODO cambiar la condicion en if

            return new ResponseEntity<>("No has mandado todos los parametros obligatorios", HttpStatus.BAD_REQUEST);
        }

        // TODO comprabar que el email, es un email

        if (false) {
            // TODO cambiar la condicion en if

            return new ResponseEntity<>("Los parametros no son correctos", HttpStatus.BAD_REQUEST);
        }


        usuarioManager.saveOrUpdate(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
