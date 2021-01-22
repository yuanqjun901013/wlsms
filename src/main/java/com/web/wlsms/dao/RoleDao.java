package com.web.wlsms.dao;


import com.web.wlsms.entity.AdminRoleAuthEntity;
import com.web.wlsms.entity.AdminRoleUserEntity;
import com.web.wlsms.request.AdminRoleRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleDao {

    List<AdminRoleAuthEntity> queryAllMenuAuths(Map<String, Object> map);

    List<AdminRoleUserEntity> queryRoleList(AdminRoleRequest map);

    int deleteOneRole(String roleCode);

    int addRole(AdminRoleRequest request);

    int deleteRoleAuthConfig(String roleCode);

    int insertRoleAuthConfig(List<AdminRoleAuthEntity> list);
}
