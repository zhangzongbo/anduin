package com.zobgo.business.comment.dao;

import com.zobgo.common.mybatis.util.SqlUtils;

import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zongbo.zhang on 8/22/18.
 */

@Slf4j
public class CommentProvider {
    public String updateCommentByCommentIds(Map<String,Object> params){
        Byte status = (Byte) params.get("status");

        List<Long> CommentIds = (List<Long>) params.get("commentIds");

        return new SQL().UPDATE("comment")
                .SET("comment_status = '" + status + "'")
                .WHERE("comment_id in" + SqlUtils.in(CommentIds)).toString();
    }
}
