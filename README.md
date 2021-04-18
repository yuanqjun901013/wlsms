read me please

#建表
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu` (
`ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`SYS_CODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT 'admin' COMMENT '系统代码',
`NAME` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '菜单名',
`URL` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '菜单页面路径',
`PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父节点ID，如果是0则是一级节点',
`IS_NEED_AUTH` smallint(6) DEFAULT '1' COMMENT '是否需要权限控制 0：不要 1：要',
`menuCode` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单请求编码',
`iconCls` varchar(100) DEFAULT NULL COMMENT '菜单图标',
`level` varchar(100) DEFAULT NULL COMMENT '菜单级别',
PRIMARY KEY (`ID`),
KEY `idex_n_pi` (`PARENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

DROP TABLE IF EXISTS `admin_menu_copy`;
CREATE TABLE `admin_menu_copy` (
`ID` bigint(20) DEFAULT NULL,
`SYS_CODE` varchar(10) DEFAULT NULL,
`NAME` varchar(15) DEFAULT NULL,
`URL` varchar(100) DEFAULT NULL,
`PARENT_ID` bigint(20) DEFAULT NULL,
`IS_NEED_AUTH` smallint(6) DEFAULT NULL,
`menuCode` varchar(100) DEFAULT NULL,
`iconCls` varchar(100) DEFAULT NULL,
`level` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单表备份';

DROP TABLE IF EXISTS `admin_operation`;
CREATE TABLE `admin_operation` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`user_no` varchar(100) NOT NULL COMMENT '工号',
`build_time` timestamp NOT NULL COMMENT '操作时间',
`title` varchar(100) NOT NULL COMMENT '操作标题',
`content` text COMMENT '操作内容',
`feedback_content` text COMMENT '反馈内容',
`state` int(11) DEFAULT '1' COMMENT '状态1:未反馈；2:已反馈',
`feedback_time` timestamp NULL DEFAULT NULL COMMENT '反馈时间',
`feedback_user` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '反馈人工号',
`operation_type` int(11) DEFAULT NULL COMMENT '任务状态类型1:资料共享2:任务运行',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作审计日志';

DROP TABLE IF EXISTS `admin_parameters`;
CREATE TABLE `admin_parameters` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`param_type` varchar(100) DEFAULT NULL COMMENT '类型',
`param_name` varchar(100) DEFAULT NULL COMMENT '参数名称',
`param_value` varchar(200) DEFAULT NULL COMMENT '参数值',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础参数';

DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
`ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色表，自增主键',
`ROLE_CODE` varchar(10) DEFAULT NULL COMMENT '角色编码',
`ROLE_NAME` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
PRIMARY KEY (`ID`),
KEY `index_n_rc` (`ROLE_CODE`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色表';

DROP TABLE IF EXISTS `admin_role_auth`;
CREATE TABLE `admin_role_auth` (
`ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限表,自增主键',
`MENU_ID` int(11) DEFAULT NULL COMMENT '菜单编码',
`ROLE_CODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色编码',
PRIMARY KEY (`ID`),
KEY `index_n_rc` (`ROLE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

DROP TABLE IF EXISTS `admin_role_user`;
CREATE TABLE `admin_role_user` (
`ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色用户表，自增主键',
`ROLE_CODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色代码',
`USER_NO` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户登录工号',
PRIMARY KEY (`ID`),
KEY `index_n_rc_un` (`ROLE_CODE`,`USER_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色用户表';

DROP TABLE IF EXISTS `admin_token`;
CREATE TABLE `admin_token` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增长',
`user_no` varchar(100) NOT NULL COMMENT '工号',
`token` varchar(300) NOT NULL COMMENT '登录token',
`build_time` timestamp NULL DEFAULT NULL COMMENT '生效时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='token登录表';

DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`user_no` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工号',
`pwd` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '123' COMMENT '密码',
`user_name` varchar(100) NOT NULL COMMENT '姓名',
`sex` int(11) NOT NULL DEFAULT '1' COMMENT '性别',
`age` int(11) DEFAULT NULL COMMENT '年龄',
`job` varchar(200) DEFAULT NULL COMMENT '职务',
`tel` varchar(100) DEFAULT NULL COMMENT '电话',
`phone` varchar(100) DEFAULT NULL COMMENT '手机',
`email` varchar(100) DEFAULT NULL COMMENT '邮箱',
PRIMARY KEY (`id`),
UNIQUE KEY `admin_user_user_no_IDX` (`user_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户资料表';

DROP TABLE IF EXISTS `wlsms_alarm_config`;
CREATE TABLE `wlsms_alarm_config` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`alarm_code` varchar(100) DEFAULT NULL COMMENT '告警项编码',
`alarm_name` varchar(100) DEFAULT NULL COMMENT '告警项名称',
`alarm_start_value` decimal(10,2) DEFAULT NULL COMMENT '预警数值范围开始',
`alarm_end_value` decimal(10,2) DEFAULT NULL COMMENT '预警数值范围结束',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='告警预值配置表';

DROP TABLE IF EXISTS `wlsms_alarm_data`;
CREATE TABLE `wlsms_alarm_data` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`alarm_title` varchar(100) DEFAULT NULL COMMENT '告警标题',
`alarm_content` text COMMENT '告警内容',
`build_time` timestamp NULL DEFAULT NULL COMMENT '发生时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='告警采集表';

DROP TABLE IF EXISTS `wlsms_data`;
CREATE TABLE `wlsms_data` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`sxzfq_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上行转发器',
`sxpl_value` decimal(10,3) DEFAULT NULL COMMENT '上行频率',
`bpqpl_value` decimal(10,3) DEFAULT NULL COMMENT '变频器频率',
`zpl_value` decimal(10,3) DEFAULT NULL COMMENT '中频',
`xxpl_value` decimal(10,3) DEFAULT NULL COMMENT '下行频率',
`system_name` varchar(50) DEFAULT NULL COMMENT '系统',
`tzsl_value` decimal(10,3) DEFAULT NULL COMMENT '调制速率',
`xxsl_value` decimal(10,3) DEFAULT NULL COMMENT '信息速率',
`tzfs_name` varchar(100) DEFAULT NULL COMMENT '调制方式',
`xdbm_code` varchar(100) DEFAULT NULL COMMENT '信道编码',
`content` varchar(100) DEFAULT NULL COMMENT '内容',
`xzb_value` decimal(10,3) DEFAULT NULL COMMENT '信噪比',
`error_content` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '错误信息',
`remark` varchar(500) DEFAULT NULL COMMENT '备注',
`cj_time` timestamp NULL DEFAULT NULL COMMENT '采集时间',
`wzl_value` decimal(10,3) DEFAULT NULL COMMENT '误帧率',
`other_user_code` varchar(50) DEFAULT NULL COMMENT '额外用户数据序列',
`other_kx_code` varchar(50) DEFAULT NULL COMMENT '额外开销数据序列',
`other_user_title` varchar(50) DEFAULT NULL COMMENT '额外用户数据指示',
`other_kx_title` varchar(50) DEFAULT NULL COMMENT '额外开销数据指示',
`qrxd_value` varchar(50) DEFAULT NULL COMMENT '嵌入信道',
`qrxd_content` varchar(500) DEFAULT NULL COMMENT '嵌入信道内容',
`zhsj_content` varchar(500) DEFAULT NULL COMMENT '载荷数据内容',
`passport_remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码情况',
`mobile_unit_value` varchar(100) DEFAULT NULL COMMENT '移动属性判定',
`create_time` timestamp NULL DEFAULT NULL COMMENT '添加时间',
`edit_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
`position_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '阵地编码',
`data_value` decimal(10,0) DEFAULT NULL COMMENT '采集预值',
`pro_code_manual` varchar(100) DEFAULT NULL COMMENT '人工底数公文号',
`pro_code_machine` varchar(100) DEFAULT NULL COMMENT '机器公文号',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='校对后数据主表';

DROP TABLE IF EXISTS `wlsms_doc`;
CREATE TABLE `wlsms_doc` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增长',
`doc_name` varchar(150) NOT NULL COMMENT '资源名称',
`create_time` timestamp NULL DEFAULT NULL COMMENT '添加时间',
`edit_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
`file_name` varchar(500) DEFAULT NULL COMMENT '文件名称',
`file_path` varchar(1000) DEFAULT NULL COMMENT '文件路径',
`user_no` varchar(100) DEFAULT NULL COMMENT '上传人工号',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资料统一管理';

DROP TABLE IF EXISTS `wlsms_machine_data`;
CREATE TABLE `wlsms_machine_data` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`wx_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '卫星名称',
`zpl_value` decimal(10,3) DEFAULT NULL COMMENT '中频',
`dpl_value` decimal(10,3) DEFAULT NULL COMMENT '电平频率',
`tkpl_value` decimal(10,3) DEFAULT NULL COMMENT '天空频率',
`xh_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '信号类型',
`msl_value` decimal(10,3) DEFAULT NULL COMMENT '码速率',
`cj_time` timestamp NULL DEFAULT NULL COMMENT '采集时间',
`zzb_value` decimal(10,3) DEFAULT NULL COMMENT '载噪比',
`tzys_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '调制样式',
`create_time` timestamp NULL DEFAULT NULL COMMENT '添加时间',
`edit_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
`position_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '阵地编码',
`data_value` decimal(10,0) DEFAULT NULL COMMENT '采集预值',
`state` int(11) DEFAULT '0' COMMENT '是否已校对0未校对；1已校对',
`pro_code` varchar(100) DEFAULT NULL COMMENT '公文号',
`user_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '工号',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机器数据表';

DROP TABLE IF EXISTS `wlsms_manual_data`;
CREATE TABLE `wlsms_manual_data` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`sxzfq_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上行转发器',
`sxpl_value` decimal(10,3) DEFAULT NULL COMMENT '上行频率',
`bpqpl_value` decimal(10,3) DEFAULT NULL COMMENT '变频器频率',
`zpl_value` decimal(10,3) DEFAULT NULL COMMENT '中频',
`xxpl_value` decimal(10,3) DEFAULT NULL COMMENT '下行频率',
`system_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统',
`tzsl_value` decimal(10,3) DEFAULT NULL COMMENT '调制速率',
`xxsl_value` decimal(10,3) DEFAULT NULL COMMENT '信息速率',
`tzfs_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '调制方式',
`xdbm_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '信道编码',
`xzb_value` decimal(10,3) DEFAULT NULL COMMENT '信噪比',
`cj_time` timestamp NULL DEFAULT NULL COMMENT '采集时间',
`wzl_value` decimal(10,3) DEFAULT NULL COMMENT '误帧率',
`create_time` timestamp NULL DEFAULT NULL COMMENT '添加时间',
`edit_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
`position_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '阵地编码',
`data_value` decimal(10,0) DEFAULT NULL COMMENT '采集预值',
`state` int(11) DEFAULT '0' COMMENT '是否已校对0未校对；1已校对',
`pro_code` varchar(100) DEFAULT NULL COMMENT '公文号',
`user_no` varchar(100) DEFAULT NULL COMMENT '工号',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人工数据表';

DROP TABLE IF EXISTS `wlsms_position_config`;
CREATE TABLE `wlsms_position_config` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`position_name` varchar(100) DEFAULT NULL COMMENT '阵地名称',
`position_code` varchar(100) DEFAULT NULL COMMENT '阵地代码',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='阵地信息配置表';

-- 新增数据
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(1, 'admin', '用户权限', NULL, 0, 0, NULL, 'icon-system', '1');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(2, 'admin', '用户管理', NULL, 1, 0, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(3, 'admin', '用户初始化', 'views/user/userPage', 2, 1, 'getUserList', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(4, 'admin', '个人资料', 'views/index/userModalPage', 2, 0, 'userModalPage', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(5, 'admin', '修改密码', 'views/index/pwdModalPage', 2, 0, 'pwdModalPage', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(6, 'admin', '权限管理', NULL, 1, 0, NULL, '', '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(7, 'admin', '默认角色', 'views/user/rolePage', 6, 1, 'queryRole', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(8, 'admin', '用户授权', 'views/user/roleUserPage', 6, 1, 'queryUserRoleInfo', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(9, 'admin', '组网协同', NULL, 0, 0, NULL, 'icon-large-smartart', '1');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(10, 'admin', '阵地配置', NULL, 9, 0, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(11, 'admin', '数据上报分析', NULL, 9, 0, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(12, 'admin', '位置信息维护', 'views/position/positionPage', 10, 1, 'getPositionList', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(13, 'admin', '人工底数上报', 'views/data/manualPage', 11, 1, 'getManualList', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(14, 'admin', '机器底数上报', 'views/data/machinePage', 11, 1, 'getMachineList', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(15, 'admin', '数据校对汇总', 'views/data/dataPage', 11, 1, 'getDataList', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(16, 'admin', '数据告警', NULL, 0, 0, NULL, 'icon-qudong', '1');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(17, 'admin', '告警管理', NULL, 16, 0, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(18, 'admin', '告警列表', 'views/alarm/alarmInfoPage', 17, 1, 'getAlarmInfoList', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(19, 'admin', '统计分析', NULL, 0, 0, NULL, 'icon-large-chart', '1');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(20, 'admin', '报表统计', NULL, 19, 0, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(21, 'admin', '数据分析', 'views/data/dataQueryPage', 20, 1, 'queryDataList', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(22, 'admin', '报表总览', 'views/data/dataBiPage', 20, 1, 'getDataBi', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(23, 'admin', '详细报表', 'views/data/dataDetailPage', 20, 1, 'getDataDetail', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(24, 'admin', '位置报表', 'views/data/dataByPositionPage', 20, 1, 'getDataByPosition', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(25, 'admin', '资源管理', NULL, 0, 0, NULL, 'icon-doc', '1');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(26, 'admin', '资源管理', NULL, 25, 0, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(27, 'admin', '资料统一管理', 'views/doc/docManger', 26, 1, 'docManager', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(28, 'admin', '任务管理', NULL, 9, 0, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(29, 'admin', '任务状态及反馈', 'views/message/operation', 28, 1, 'getOperationList', NULL, '3');

INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(1, 'admin', '系统管理', NULL, 0, 0, 'systemManage', 'icon-config', '1');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(2, 'admin', '系统配置', NULL, 1, 1, 'authorityManage', NULL, '2');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(3, 'admin', '菜单配置', NULL, 1, 1, 'menuConfig', NULL, '2');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(4, 'admin', '基础参数', 'views/param/parameters', 2, 1, 'getParametersList', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(5, 'admin', '系统审计', 'views/message/operation', 2, 1, 'getOperationList', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(6, 'admin', '菜单列表', 'views/menu/menuPage', 3, 1, 'menuConfigList', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(7, 'admin', '用户管理', NULL, 0, 0, 'operation', 'icon-system', '1');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(8, 'admin', '用户设置', NULL, 7, 1, 'testManage', NULL, '2');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(9, 'admin', '个人信息', NULL, 7, 0, NULL, NULL, '2');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(10, 'admin', '用户初始化', 'views/user/userPage', 8, 1, 'getUserList', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(11, 'admin', '个人资料', 'views/index/userModalPage', 9, 0, 'userModalPage', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(12, 'admin', '权限管理', NULL, 0, 0, NULL, 'icon-manager', '1');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(13, 'admin', '角色配置', NULL, 12, 1, NULL, NULL, '2');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(14, 'admin', '角色组配置', NULL, 12, 1, NULL, NULL, '2');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(15, 'admin', '角色列表', 'views/user/rolePage', 13, 1, 'queryRole', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(16, 'admin', '菜单角色设置', 'views/user/roleAuthPage', 14, 1, 'getAuthMenuRole', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(17, 'admin', '用户角色设置', 'views/user/roleUserPage', 14, 1, 'queryUserRoleInfo', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(18, 'admin', '组网协同', NULL, 0, 0, NULL, 'icon-large-smartart', '1');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(19, 'admin', '阵地配置', NULL, 18, 1, NULL, NULL, '2');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(20, 'admin', '数据管理', NULL, 18, 0, NULL, NULL, '2');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(21, 'admin', '阵地列表', 'views/position/positionPage', 19, 1, 'getPositionList', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(22, 'admin', '数据上报', 'views/data/dataPage', 20, 0, 'getDataList', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(23, 'admin', '侦查告警', NULL, 0, 0, NULL, 'icon-qudong', '1');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(24, 'admin', '告警配置', NULL, 23, 1, NULL, NULL, '2');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(25, 'admin', '告警处理', NULL, 23, 0, NULL, NULL, '2');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(26, 'admin', '告警参数', 'views/alarm/alarmConfig', 24, 1, 'getAlarmConfig', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(27, 'admin', '告警列表', 'views/alarm/alarmInfoPage', 25, 0, 'getAlarmInfoList', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(28, 'admin', '统计分析', NULL, 0, 0, NULL, 'icon-large-chart', '1');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(29, 'admin', '数据分析', NULL, 28, 0, NULL, NULL, '2');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(30, 'admin', '报表统计', NULL, 28, 0, NULL, NULL, '2');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(31, 'admin', '数据分析处理', 'views/data/dataQueryPage', 29, 0, 'queryDataList', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(32, 'admin', '报表总览', 'views/data/dataBiPage', 30, 0, 'getDataBi', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(33, 'admin', '详细报表', 'views/data/dataDetailPage', 30, 0, 'getDataDetail', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(34, 'admin', '阵地报表', 'views/data/dataByPositionPage', 30, 0, 'getDataByPosition', NULL, '3');
INSERT INTO admin_menu_copy (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(35, 'admin', '修改密码', 'views/index/pwdModalPage', 9, 0, 'pwdModalPage', NULL, '3');

INSERT INTO admin_role (ID, ROLE_CODE, ROLE_NAME) VALUES(1, 'admin', '超级管理员');
INSERT INTO admin_role (ID, ROLE_CODE, ROLE_NAME) VALUES(2, 'user', '普通用户');
INSERT INTO admin_role (ID, ROLE_CODE, ROLE_NAME) VALUES(3, 'leder', '高管');

INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(1, 3, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(2, 7, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(3, 8, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(4, 12, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(5, 13, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(6, 14, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(7, 15, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(8, 18, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(9, 21, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(10, 22, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(11, 23, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(12, 24, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(13, 27, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(14, 29, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(16, 12, 'leder');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(17, 13, 'leder');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(18, 14, 'leder');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(19, 15, 'leder');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(20, 18, 'leder');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(21, 21, 'leder');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(22, 22, 'leder');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(23, 23, 'leder');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(24, 24, 'leder');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(25, 27, 'leder');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(26, 29, 'leder');

INSERT INTO admin_role_user (ID, ROLE_CODE, USER_NO) VALUES(14, 'admin', '15090387');
INSERT INTO admin_role_user (ID, ROLE_CODE, USER_NO) VALUES(28, 'user', '15090383');
INSERT INTO admin_role_user (ID, ROLE_CODE, USER_NO) VALUES(29, 'user', '15090384');
INSERT INTO admin_role_user (ID, ROLE_CODE, USER_NO) VALUES(31, 'user', '15090388');
INSERT INTO admin_role_user (ID, ROLE_CODE, USER_NO) VALUES(30, 'user', '15090389');

INSERT INTO admin_user (id, user_no, pwd, user_name, sex, age, job, tel, phone, email) VALUES(1, '15090387', '123', '袁其军', 1, 31, '管理员', '0000', '18600000000', 'yuanjun901013@163.com');
INSERT INTO admin_user (id, user_no, pwd, user_name, sex, age, job, tel, phone, email) VALUES(3, '15090388', '123456a', '用户a', 2, 22, '高工', NULL, NULL, NULL);
INSERT INTO admin_user (id, user_no, pwd, user_name, sex, age, job, tel, phone, email) VALUES(4, '15090389', '123', '用户b', 2, 33, '高工', NULL, NULL, NULL);
INSERT INTO admin_user (id, user_no, pwd, user_name, sex, age, job, tel, phone, email) VALUES(32, '15090384', '123', '大爷的', 1, NULL, '', '', '', '');
INSERT INTO admin_user (id, user_no, pwd, user_name, sex, age, job, tel, phone, email) VALUES(34, '15090383', '123', '张大大', 1, NULL, 'dfsf ff ', '213', '', '');



