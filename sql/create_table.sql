CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `username` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
                        `userAccount` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
                        `avatarUrl` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '用户头像',
                        `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
                        `userPassword` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '密码\n',
                        `photo` varchar(256) COLLATE utf8_bin DEFAULT NULL,
                        `email` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
                        `userStatus` int(11) NOT NULL DEFAULT '0' COMMENT '用户状态',
                        `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `updateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                        `isDelete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
                        `role` int(11) NOT NULL DEFAULT '0' COMMENT '0 普通用户 1 管理员',
                        `planetCode` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '星球编号',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户'

