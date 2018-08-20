package com.anduin.business.comment.service.impl;

import com.anduin.business.comment.dao.CommentMapper;
import com.anduin.business.comment.model.Comment;
import com.anduin.business.comment.service.api.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zongbo.zhang on 8/20/18.
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentByUserId(Long userId) {
        return null;
    }

    @Override
    public Comment getCommentById(Long Id) {
        return null;
    }
}
