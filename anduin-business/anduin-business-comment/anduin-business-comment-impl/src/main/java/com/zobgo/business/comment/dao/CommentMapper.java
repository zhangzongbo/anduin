package com.zobgo.business.comment.dao;

import com.zobgo.business.comment.model.Comment;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    int deleteByPrimaryKey(Long commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long commentId);

    @Select("select user_id as userId,user_name as userName,like_count as likeCount,content,song_name as songName from comment where user_id = #{userId}")
    List<Comment> selectByUserId(@Param("userId") Long userId);

    @Select("select comment_id as commentId,comment_status as commentStatus from comment where comment_status = #{status}")
    List<Comment> selectByStatus(@Param("status") Integer status);

    @UpdateProvider(type = CommentProvider.class,method = "updateCommentByCommentIds")
    void updateCommentByCommentIds(@Param("status") Byte status, @Param("commentIds") List<Long> orderIds);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);
}