package com.esliceu.proyectoglobal.manager;

import com.esliceu.proyectoglobal.entity.Usuario;
import com.esliceu.proyectoglobal.repository.UsuarioRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Code created by: mmonteirocl
 * Email: miguelmonteiroclaveri@gmail.com
 * Date: 18/02/2020
 * Package: com.esliceu.proyectoglobal.manager
 * Project: BackEnd
 */
@Service
public class UsuarioManager {

    @Autowired
    Gson gson;

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        List<Usuario> toReturn = new LinkedList<>();

        for (Usuario user : usuarioRepository.findAll()) {
            toReturn.add(user);
        }

        return toReturn;
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findByIdusuario(id);
    }

    public void saveOrUpdate(Usuario user) {
        usuarioRepository.save(user);
    }

    public void delete(Usuario user) {
        usuarioRepository.delete(user);
    }


    public Usuario fromJSON(String usuario) {
        Usuario user = new Usuario();

        JsonObject jsonObject = gson.fromJson(usuario, JsonObject.class);


        Long idusuario = jsonObject.get("idusuario").getAsLong();
        String nombre = jsonObject.get("nombre").getAsString();
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();
        String username = jsonObject.get("username").getAsString();
        String apellidos = jsonObject.get("apellidos").getAsString();
        String authMode = jsonObject.get("authMode").getAsString();

        user.setEmail(email);
        user.setPassword(password);
        user.setIdusuario(idusuario);
        user.setNombre(nombre);
        user.setUsername(username);
        user.setApellidos(apellidos);
        user.setAuthMode(authMode);

        return user;
    }

}