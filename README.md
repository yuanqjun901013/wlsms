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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色表，自增主键',
  `ROLE_CODE` varchar(10) DEFAULT NULL COMMENT '角色编码',
  `ROLE_NAME` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`ID`),
  KEY `index_n_rc` (`ROLE_CODE`)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8 COMMENT='角色表';

DROP TABLE IF EXISTS `admin_role_auth`;
CREATE TABLE `admin_role_auth` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限表,自增主键',
  `MENU_ID` int(11) DEFAULT NULL COMMENT '菜单编码',
  `ROLE_CODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色编码',
  PRIMARY KEY (`ID`),
  KEY `index_n_rc` (`ROLE_CODE`)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8 COMMENT='角色权限表';

DROP TABLE IF EXISTS `admin_role_user`;
CREATE TABLE `admin_role_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色用户表，自增主键',
  `ROLE_CODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色代码',
  `USER_NO` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户登录工号',
  PRIMARY KEY (`ID`),
  KEY `index_n_rc_un` (`ROLE_CODE`,`USER_NO`)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8 COMMENT='角色用户表';

DROP TABLE IF EXISTS `admin_token`;
CREATE TABLE `admin_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增长',
  `user_no` varchar(100) NOT NULL COMMENT '工号',
  `token` varchar(300) NOT NULL COMMENT '登录token',
  `build_time` timestamp NULL DEFAULT NULL COMMENT '生效时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8 COMMENT='用户Token表';

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
) ENGINE=InnoDB   DEFAULT CHARSET=utf8 COMMENT='用户信息表';

#初始化数据

INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(1, 'admin', '系统管理', NULL, 0, 0, 'systemManage', 'icon-config', '1');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(2, 'admin', '系统配置', NULL, 1, 1, 'authorityManage', NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(3, 'admin', '菜单配置', NULL, 1, 1, 'menuConfig', NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(4, 'admin', '基础参数', NULL, 2, 1, 'roleManage', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(5, 'admin', '系统运维', NULL, 2, 1, 'basicManage', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(6, 'admin', '菜单列表', 'views/menu/menuPage', 3, 1, 'menuConfigList', NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(7, 'admin', '用户管理', NULL, 0, 0, 'operation', 'icon-system', '1');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(8, 'admin', '用户设置', NULL, 7, 1, 'testManage', NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(9, 'admin', '个人信息', NULL, 7, 0, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(10, 'admin', '用户初始化', NULL, 8, 1, NULL, NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(11, 'admin', '个人资料', NULL, 9, 0, NULL, NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(12, 'admin', '权限管理', NULL, 0, 0, NULL, 'icon-manager', '1');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(13, 'admin', '角色配置', NULL, 12, 1, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(14, 'admin', '角色组配置', NULL, 12, 1, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(15, 'admin', '角色列表', NULL, 13, 1, NULL, NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(16, 'admin', '菜单角色设置', NULL, 14, 1, NULL, NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(17, 'admin', '用户角色设置', NULL, 14, 1, NULL, NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(18, 'admin', '协同管理', NULL, 0, 0, NULL, 'icon-large-smartart', '1');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(19, 'admin', '阵地配置', NULL, 18, 1, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(20, 'admin', '数据管理', NULL, 18, 0, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(21, 'admin', '阵地列表', NULL, 19, 1, NULL, NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(22, 'admin', '数据上报', NULL, 20, 0, NULL, NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(23, 'admin', '告警管理', NULL, 0, 0, NULL, 'icon-qudong', '1');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(24, 'admin', '告警配置', NULL, 23, 1, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(25, 'admin', '告警处理', NULL, 23, 0, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(26, 'admin', '告警参数', NULL, 24, 1, NULL, NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(27, 'admin', '告警列表', NULL, 25, 0, NULL, NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(28, 'admin', '统计分析', NULL, 0, 0, NULL, 'icon-large-chart', '1');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(29, 'admin', '数据分析', NULL, 28, 0, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(30, 'admin', '报表统计', NULL, 28, 0, NULL, NULL, '2');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(31, 'admin', '数据分析处理', NULL, 29, 0, NULL, NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(32, 'admin', '报表总览', NULL, 30, 0, NULL, NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(33, 'admin', '详细报表', NULL, 30, 0, NULL, NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(34, 'admin', '阵地报表', NULL, 30, 0, NULL, NULL, '3');
INSERT INTO admin_menu (ID, SYS_CODE, NAME, URL, PARENT_ID, IS_NEED_AUTH, menuCode, iconCls, `level`) VALUES(35, 'admin', '修改密码', NULL, 9, 0, NULL, NULL, '3');

INSERT INTO admin_role (ID, ROLE_CODE, ROLE_NAME) VALUES(1, 'admin', '超级管理员');
INSERT INTO admin_role (ID, ROLE_CODE, ROLE_NAME) VALUES(2, 'coding', '开发');
INSERT INTO admin_role (ID, ROLE_CODE, ROLE_NAME) VALUES(3, 'testing', '测试');
INSERT INTO admin_role (ID, ROLE_CODE, ROLE_NAME) VALUES(4, 'pm', '产品经理');

INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(1, 1, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(2, 2, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(3, 3, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(4, 4, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(5, 5, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(6, 6, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(7, 7, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(8, 8, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(9, 9, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(10, 10, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(11, 11, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(12, 12, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(13, 13, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(14, 14, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(15, 15, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(16, 16, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(17, 17, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(18, 18, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(19, 19, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(20, 20, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(21, 21, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(22, 22, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(23, 23, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(24, 24, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(25, 25, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(26, 26, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(27, 27, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(28, 28, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(29, 29, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(30, 30, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(31, 31, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(32, 32, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(33, 33, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(34, 34, 'admin');
INSERT INTO admin_role_auth (ID, MENU_ID, ROLE_CODE) VALUES(35, 35, 'admin');

INSERT INTO admin_role_user (ID, ROLE_CODE, USER_NO) VALUES(1, 'admin', '15090387');
INSERT INTO admin_role_user (ID, ROLE_CODE, USER_NO) VALUES(3, 'coding', '15090388');
INSERT INTO admin_role_user (ID, ROLE_CODE, USER_NO) VALUES(4, 'pm', '15090386');
INSERT INTO admin_role_user (ID, ROLE_CODE, USER_NO) VALUES(2, 'testing', '15090389');

INSERT INTO admin_user (id, user_no, pwd, user_name, sex, age, job, tel, phone, email) VALUES(1, '15090387', '123', '袁其军', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO admin_user (id, user_no, pwd, user_name, sex, age, job, tel, phone, email) VALUES(3, '15090388', '123', '程序猿', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO admin_user (id, user_no, pwd, user_name, sex, age, job, tel, phone, email) VALUES(4, '15090389', '123', '测试', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO admin_user (id, user_no, pwd, user_name, sex, age, job, tel, phone, email) VALUES(5, '15090386', '123', '产品', 1, NULL, NULL, NULL, NULL, NULL);