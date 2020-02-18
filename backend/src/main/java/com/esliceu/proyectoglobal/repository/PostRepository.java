package com.esliceu.proyectoglobal.repository;

import com.esliceu.proyectoglobal.entity.Post;
import org.springframework.data.repository.CrudRepository;

/**
 * Code created by: mmonteirocl
 * Email: miguelmonteiroclaveri@gmail.com
 * Date: 18/02/2020
 * Package: com.esliceu.proyectoglobal.repository
 * Project: BackEnd
 */
public interface PostRepository extends CrudRepository<Post, Long> {
    Post findByIdpost(Long id);
}
