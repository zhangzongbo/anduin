package com.anduin.business.comment.service.api;

import com.anduin.business.comment.model.Comment;

import java.util.List;

/**
 * Created by zongbo.zhang on 8/20/18.
 */
public interface CommentService {



    /**
     * 通过用户Id查询
     * @param userId
     * @return
     */
    List<Comment> getCommentByUserId(Long userId);


    /**
     * 通过primaryKey查询
     * @param Id
     * @return
     */
    Comment getCommentById(Long Id);
}

