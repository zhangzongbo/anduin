package com.zobgo.business.comment.service.api;

import com.zobgo.business.comment.model.Comment;

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

    /**
     * 通过comment_status 筛选
     * @param status
     * @return
     */
    List<Comment> getCommentByStatus(Integer status);

    /**
     * 根据id_list更新状态
     * @param status
     * @param commentIds
     */
    void updateCommentByCommentIds(Byte status,List<Long> commentIds);
}

