package com.esliceu.proyectoglobal.manager;

import com.esliceu.proyectoglobal.entity.Usuario;
import com.esliceu.proyectoglobal.repository.UsuarioRepository;
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
    private UsuarioRepository usuarioRepository;

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

    public void update(Usuario user) {
        usuarioRepository.save(user);
    }

    public void delete(Usuario user) {
        usuarioRepository.delete(user);
    }
}