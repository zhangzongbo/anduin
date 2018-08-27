CREATE TABLE `comment` (
  `comment_id` bigint(20) NOT NULL COMMENT '评论id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `like_count` int(11) NOT NULL COMMENT '点赞数',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '评论内容',
  `song_id` bigint(20) NOT NULL COMMENT '歌曲id',
  `song_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '歌曲名',
  `artist_id` bigint(20) NOT NULL COMMENT '歌手id',
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `comment_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '评论状态,0：删除，1：正常',
  PRIMARY KEY (`comment_id`),
  KEY `comment_user_id` (`user_id`) USING BTREE,
  KEY `comment_song_id` (`song_id`) USING BTREE,
  KEY `comment_artist_id` (`artist_id`) USING BTREE,
  KEY `comment_like_count_IDX` (`like_count`) USING BTREE,
  KEY `comment_user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网易云评论表';
