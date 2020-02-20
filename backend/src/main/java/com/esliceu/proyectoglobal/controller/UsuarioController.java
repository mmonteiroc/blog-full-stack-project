package com.esliceu.proyectoglobal.controller;

import com.esliceu.proyectoglobal.entity.Usuario;
import com.esliceu.proyectoglobal.manager.UsuarioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    private UsuarioManager usuarioManager;

    @GetMapping("/p/user")
    @Transactional
    public List<Usuario> getAll() {

        List<Usuario> users = usuarioManager.findAll();
        for (Usuario user : users) {

            // BORRAMOS LAS PASSWORDS ANTES DE RETORNAR EL OBJETO USUARIO AL CLIENTE
            user.setPassword("");
        }

        return usuarioManager.findAll();
    }

    @GetMapping("/p/user/{id}")
    @Transactional
    public Usuario get(@PathVariable("id") Long id) {
        Usuario user = usuarioManager.findById(id);
        user.setPassword("");
        return user;
    }

    /*
     * TODO tenemos que hacer en el futuro la posibilidad de añadir / borrar / actualizar usuarios
     * */

}
