# myitem backstage为前端项目
# xiaobai_start为后端项目
# 后端项目启动 启动readis 启动ngix
# nginx配置location ~/calendar/ {
#             proxy_pass http://localhost:8817;
#            }
#         location ~/educenter/ {
#              proxy_pass http://localhost:8150;
#            }
#         location ~/study/{
#              proxy_pass http://localhost:8816;
#           }
#        location ~/eduvod/{
#              proxy_pass http://localhost:8818;
#           }
#        location ~/commen/{
#              proxy_pass http://localhost:8819;
#           }
# 启动上面对应的服务
# 前端对应的用户名密码 13700000011 123456
# 数据库中建表登录表
# INSERT INTO `ucenter_member`(`id`, `openid`, `mobile`, `password`, `nickname`, `sex`, `age`, `avatar`, `sign`, `is_disabled`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1080736474267144193', NULL, '13700000011', '96e79218965eb72c92a549dd5a330112', '用户XJtDfaYeKk', 1, 19, 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3922290090,3177876335&fm=26&gp=0.jpg', NULL, 0, 0, '2019-01-02 12:12:45', '2019-01-02 12:12:56');
# INSERT INTO `ucenter_member`(`id`, `openid`, `mobile`, `password`, `nickname`, `sex`, `age`, `avatar`, `sign`, `is_disabled`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1080736474355224577', NULL, '13700000002', '96e79218965eb72c92a549dd5a330112', '用户wUrNkzAPrc', 1, 27, 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132', NULL, 0, 0, '2019-01-02 12:13:56', '2019-01-02 12:14:07');
# INSERT INTO `ucenter_member`(`id`, `openid`, `mobile`, `password`, `nickname`, `sex`, `age`, `avatar`, `sign`, `is_disabled`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1086387099449442306', NULL, '13520191388', '96e79218965eb72c92a549dd5a330112', '用户XTMUeHDAoj', 2, 20, 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132', NULL, 0, 0, '2019-01-19 06:17:23', '2019-01-19 06:17:23');
# INSERT INTO `ucenter_member`(`id`, `openid`, `mobile`, `password`, `nickname`, `sex`, `age`, `avatar`, `sign`, `is_disabled`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1086387099520745473', NULL, '13520191389', '96e79218965eb72c92a549dd5a330112', '用户vSdKeDlimn', 1, 21, 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132', NULL, 0, 0, '2019-01-19 06:17:23', '2019-01-19 06:17:23');
# INSERT INTO `ucenter_member`(`id`, `openid`, `mobile`, `password`, `nickname`, `sex`, `age`, `avatar`, `sign`, `is_disabled`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1086387099608825858', NULL, '13520191381', '96e79218965eb72c92a549dd5a330112', '用户EoyWUVXQoP', 1, 18, 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132', NULL, 0, 0, '2019-01-19 06:17:23', '2019-01-19 06:17:23');
# INSERT INTO `ucenter_member`(`id`, `openid`, `mobile`, `password`, `nickname`, `sex`, `age`, `avatar`, `sign`, `is_disabled`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1086387099701100545', NULL, '13520191382', '96e79218965eb72c92a549dd5a330112', '用户LcAYbxLNdN', 2, 24, 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132', NULL, 0, 0, '2019-01-19 06:17:23', '2019-01-19 06:17:23');
# INSERT INTO `ucenter_member`(`id`, `openid`, `mobile`, `password`, `nickname`, `sex`, `age`, `avatar`, `sign`, `is_disabled`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1086387099776598018', NULL, '13520191383', '96e79218965eb72c92a549dd5a330112', '用户dZdjcgltnk', 2, 25, 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132', NULL, 0, 0, '2019-01-19 06:17:23', '2019-01-19 06:17:23');
# INSERT INTO `ucenter_member`(`id`, `openid`, `mobile`, `password`, `nickname`, `sex`, `age`, `avatar`, `sign`, `is_disabled`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1086387099852095490', NULL, '13520191384', '96e79218965eb72c92a549dd5a330112', '用户wNHGHlxUwX', 2, 23, 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132', NULL, 0, 0, '2019-01-19 06:17:23', '2019-01-19 06:17:23');
# INSERT INTO `ucenter_member`(`id`, `openid`, `mobile`, `password`, `nickname`, `sex`, `age`, `avatar`, `sign`, `is_disabled`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1191600824445046786', NULL, '15210078344', '96e79218965eb72c92a549dd5a330112', 'IT妖姬', 1, 5, 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132', NULL, 0, 0, '2019-11-05 14:19:10', '2019-11-08 18:04:43');

#表中添加用户名密码13700000011 123456登录
