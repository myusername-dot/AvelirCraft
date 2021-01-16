package com.avelircraft.repositories.comment;

import com.avelircraft.models.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsCrudRepository extends CrudRepository<Comment, Long> {


}