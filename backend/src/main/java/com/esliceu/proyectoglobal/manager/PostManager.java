package com.esliceu.proyectoglobal.manager;

import com.esliceu.proyectoglobal.entity.Post;
import com.esliceu.proyectoglobal.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Code created by: mmonteirocl
 * Email: miguelmonteiroclaveri@gmail.com
 * Date: 19/02/2020
 * Package: com.esliceu.proyectoglobal.manager
 * Project: BackEnd
 */
@Service
public class PostManager {

    @Autowired
    PostRepository postRepository;

    public List<Post> findAll() {
        List<Post> toReturn = new LinkedList<>();

        for (Post postIndividual : postRepository.findAll()) {
            toReturn.add(postIndividual);
        }

        return toReturn;
    }

    public Post findById(Long id) {
        return postRepository.findByIdpost(id);
    }

    public void create(Post post) {
        postRepository.save(post);
    }

    public void update(Post post) {
        postRepository.save(post);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }


}
