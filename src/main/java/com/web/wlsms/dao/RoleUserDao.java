package com.web.wlsms.dao;


import com.web.wlsms.entity.AdminRoleUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleUserDao {

    List<AdminRoleUserEntity> queryRoleUserList(Map<String, Object> map);

    Map<String,Object> queryRoleUserInfo(Map<String, Object> map);

    int deleteUserRole(List<AdminRoleUserEntity> list);

    int insertUserRole(List<AdminRoleUserEntity> list);

    int deleteUserRoleById(String userNo);

    AdminRoleUserEntity queryRoleCodeByUserNo(String userNo);

    AdminRoleUserEntity queryRoleUserByCode(String roleCode);

    List<AdminRoleUserEntity> queryUserByRoleCode(String roleCode);

    List<AdminRoleUserEntity> queryRoleUserListByRoleCode(String roleCode);
}
