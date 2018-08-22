package com.zobgo.business.comment.service.impl;

import com.zobgo.business.comment.dao.CommentMapper;
import com.zobgo.business.comment.model.Comment;
import com.zobgo.business.comment.service.api.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
        return commentMapper.selectByUserId(userId);
    }

    @Override
    public Comment getCommentById(Long Id) {
        return commentMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Comment> getCommentByStatus(Integer status) {
        return commentMapper.selectByStatus(status);
    }

    @Override
    public void updateCommentByCommentIds(Byte status, List<Long> commentIds) {
        commentMapper.updateCommentByCommentIds(status,commentIds);
    }
}
