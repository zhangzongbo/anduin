package com.zobgo.job.simple;

import com.zobgo.business.comment.model.Comment;
import com.zobgo.business.comment.service.api.CommentService;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by zongbo.zhang on 8/15/18.
 */

@Slf4j
@Component
public class MySimpleJob implements SimpleJob {

    @Autowired
    @Lazy
    CommentService commentService;

    @Override
    public void execute(ShardingContext shardingContext) {
        List<Comment> comments = commentService.getCommentByStatus(0);
        log.info("待更新条数： {}",comments.size());
        List<Long> commentIds = new ArrayList<>();
        comments.forEach(comment -> commentIds.add(comment.getCommentId()));

        commentService.updateCommentByCommentIds(Byte.valueOf("1"),commentIds);

        log.info("更新了: {}条记录", comments.size());
    }
}
