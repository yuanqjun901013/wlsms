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

DROP TABLE IF EXISTS `admin_operation`;
CREATE TABLE `admin_operation` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`user_no` varchar(100) NOT NULL COMMENT '工号',
`build_time` timestamp NOT NULL COMMENT '操作时间',
`title` varchar(100) NOT NULL COMMENT '操作标题',
`content` text COMMENT '操作内容',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作审计日志';

DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
`ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色表，自增主键',
`ROLE_CODE` varchar(10) DEFAULT NULL COMMENT '角色编码',
`ROLE_NAME` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
PRIMARY KEY (`ID`),
KEY `index_n_rc` (`ROLE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

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
`pwd` varchar(100) NOT NULL COMMENT '密码',
`user_name` varchar(100) NOT NULL COMMENT '姓名',
`sex` int(11) NOT NULL DEFAULT '1' COMMENT '性别',
`age` int(11) DEFAULT NULL COMMENT '年龄',
`job` varchar(200) DEFAULT NULL COMMENT '职务',
`tel` varchar(100) DEFAULT NULL COMMENT '电话',
`phone` varchar(100) DEFAULT NULL COMMENT '手机',
`email` varchar(100) DEFAULT NULL COMMENT '邮箱',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户资料表';

DROP TABLE IF EXISTS `wlsms_alarm_config`;
CREATE TABLE `wlsms_alarm_config` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`alarm_code` varchar(100) DEFAULT NULL COMMENT '告警项编码',
`alarm_name` varchar(100) DEFAULT NULL COMMENT '告警项名称',
`alarm_sart_value` decimal(10,2) DEFAULT NULL COMMENT '预警数值范围开始',
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
  `data_value_a` decimal(10,0) DEFAULT NULL COMMENT '数值a',
  `data_value_b` decimal(10,0) DEFAULT NULL COMMENT '数值b',
  `data_value_c` decimal(10,0) DEFAULT NULL COMMENT '数值c',
  `data_value_d` decimal(10,0) DEFAULT NULL COMMENT '数值d',
  `data_value_e` decimal(10,0) DEFAULT NULL COMMENT '数值e',
  `data_value_f` decimal(10,0) DEFAULT NULL COMMENT '数值f',
  `data_value_g` decimal(10,0) DEFAULT NULL COMMENT '数值g',
  `data_value_h` decimal(10,0) DEFAULT NULL COMMENT '数值h',
  `data_value_i` decimal(10,0) DEFAULT NULL COMMENT '数值i',
  `data_value_j` decimal(10,0) DEFAULT NULL COMMENT '数值j',
  `data_value_k` decimal(10,0) DEFAULT NULL COMMENT '数值k',
  `data_value_l` decimal(10,0) DEFAULT NULL COMMENT '数值l',
  `data_value_m` decimal(10,0) DEFAULT NULL COMMENT '数值m',
  `data_value_n` decimal(10,0) DEFAULT NULL COMMENT '数值n',
  `data_value_o` decimal(10,0) DEFAULT NULL COMMENT '数值o',
  `data_value_p` decimal(10,0) DEFAULT NULL COMMENT '数值p',
  `data_value_q` decimal(10,0) DEFAULT NULL COMMENT '数值q',
  `data_value_r` decimal(10,0) DEFAULT NULL COMMENT '数值r',
  `data_value_s` decimal(10,0) DEFAULT NULL COMMENT '数值s',
  `data_value_t` decimal(10,0) DEFAULT NULL COMMENT '数值t',
  `data_value_u` decimal(10,0) DEFAULT NULL COMMENT '数值u',
  `data_value_v` decimal(10,0) DEFAULT NULL COMMENT '数值v',
  `data_value_w` decimal(10,0) DEFAULT NULL COMMENT '数值w',
  `data_value_x` decimal(10,0) DEFAULT NULL COMMENT '数值x',
  `data_value_y` decimal(10,0) DEFAULT NULL COMMENT '数值y',
  `data_value_z` decimal(10,0) DEFAULT NULL COMMENT '数值z',
  `creat_time` timestamp NULL DEFAULT NULL COMMENT '添加时间',
  `edit_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `position_code` varchar(100) DEFAULT NULL COMMENT '阵地编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采集数据主表';

DROP TABLE IF EXISTS `wlsms_position_config`;
CREATE TABLE `wlsms_position_config` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`position_name` varchar(100) DEFAULT NULL COMMENT '阵地名称',
`position_code` varchar(100) DEFAULT NULL COMMENT '阵地代码',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='阵地信息配置表';

DROP TABLE IF EXISTS `admin_parameters`;
CREATE TABLE `admin_parameters` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`param_type` varchar(100) DEFAULT NULL COMMENT '类型',
`param_name` varchar(100) DEFAULT NULL COMMENT '参数名称',
`param_value` varchar(200) DEFAULT NULL COMMENT '参数值',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础参数';

#新增数据
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '系统管理', NULL, 0, 0, 'systemManage', 'icon-config', '1');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '系统配置', NULL, 1, 1, 'authorityManage', NULL, '2');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '菜单配置', NULL, 1, 1, 'menuConfig', NULL, '2');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '基础参数', 'views/param/parameters', 2, 1, 'getParametersList', NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '系统审计', 'views/message/operation', 2, 1, 'getOperationList', NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '菜单列表', 'views/menu/menuPage', 3, 1, 'menuConfigList', NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '用户管理', NULL, 0, 0, 'operation', 'icon-system', '1');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '用户设置', NULL, 7, 1, 'testManage', NULL, '2');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '个人信息', NULL, 7, 0, NULL, NULL, '2');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '用户初始化', NULL, 8, 1, NULL, NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '个人资料', 'views/index/userModalPage', 9, 0, 'userModalPage', NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '权限管理', NULL, 0, 0, NULL, 'icon-manager', '1');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '角色配置', NULL, 12, 1, NULL, NULL, '2');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '角色组配置', NULL, 12, 1, NULL, NULL, '2');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '角色列表', NULL, 13, 1, NULL, NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '菜单角色设置', NULL, 14, 1, NULL, NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '用户角色设置', NULL, 14, 1, NULL, NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '协同管理', NULL, 0, 0, NULL, 'icon-large-smartart', '1');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '阵地配置', NULL, 18, 1, NULL, NULL, '2');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '数据管理', NULL, 18, 0, NULL, NULL, '2');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '阵地列表', NULL, 19, 1, NULL, NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '数据上报', NULL, 20, 0, NULL, NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '告警管理', NULL, 0, 0, NULL, 'icon-qudong', '1');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '告警配置', NULL, 23, 1, NULL, NULL, '2');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '告警处理', NULL, 23, 0, NULL, NULL, '2');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '告警参数', NULL, 24, 1, NULL, NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '告警列表', NULL, 25, 0, NULL, NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '统计分析', NULL, 0, 0, NULL, 'icon-large-chart', '1');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '数据分析', NULL, 28, 0, NULL, NULL, '2');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '报表统计', NULL, 28, 0, NULL, NULL, '2');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '数据分析处理', NULL, 29, 0, NULL, NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '报表总览', NULL, 30, 0, NULL, NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '详细报表', NULL, 30, 0, NULL, NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '阵地报表', NULL, 30, 0, NULL, NULL, '3');
INSERT INTO admin_menu (SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES('admin', '修改密码', 'views/index/pwdModalPage', 9, 0, 'pwdModalPage', NULL, '3');

INSERT INTO admin_role (ROLE_CODE, ROLE_NAME) VALUES('admin', '超级管理员');
INSERT INTO admin_role (ROLE_CODE, ROLE_NAME) VALUES('coding', '开发');
INSERT INTO admin_role (ROLE_CODE, ROLE_NAME) VALUES('testing', '测试');
INSERT INTO admin_role (ROLE_CODE, ROLE_NAME) VALUES('pm', '产品经理');

INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(1, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(2, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(3, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(4, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(5, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(6, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(7, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(8, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(9, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(10, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(11, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(12, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(13, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(14, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(15, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(16, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(17, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(18, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(19, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(20, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(21, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(22, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(23, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(24, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(25, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(26, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(27, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(28, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(29, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(30, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(31, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(32, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(33, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(34, 'admin');
INSERT INTO admin_role_auth (MENU_ID, ROLE_CODE) VALUES(35, 'admin');

INSERT INTO admin_role_user (ROLE_CODE, USER_NO) VALUES('admin', '15090387');
INSERT INTO admin_role_user (ROLE_CODE, USER_NO) VALUES('coding', '15090388');
INSERT INTO admin_role_user (ROLE_CODE, USER_NO) VALUES('pm', '15090386');
INSERT INTO admin_role_user (ROLE_CODE, USER_NO) VALUES('testing', '15090389');

INSERT INTO admin_user (user_no, pwd, user_name, sex, age, job, tel, phone, email) VALUES('15090387', '123', '袁其军', 1, 31, '小开发', '0000', '18600000000', 'yuanjun901013@163.com');
INSERT INTO admin_user (user_no, pwd, user_name, sex, age, job, tel, phone, email) VALUES('15090388', '123456a', '程序猿', 2, 22, '技术经理', NULL, NULL, NULL);
INSERT INTO admin_user (user_no, pwd, user_name, sex, age, job, tel, phone, email) VALUES('15090389', '123', '测试', 2, NULL, NULL, NULL, NULL, NULL);
INSERT INTO admin_user (user_no, pwd, user_name, sex, age, job, tel, phone, email) VALUES('15090386', '123', '产品', 1, NULL, NULL, NULL, NULL, NULL);


