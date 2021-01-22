package com.web.wlsms.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.RoleUserDao;
import com.web.wlsms.entity.AdminRoleUserEntity;
import com.web.wlsms.request.RoleUsersRequest;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.RoleUserResponse;
import com.web.wlsms.service.system.RoleUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleUserServiceImpl implements RoleUserService {

    @Resource
    private RoleUserDao roleUserDao;


    /**
     * 保存用户角色
     *
     * @param request
     * @return
     */
    @Transactional
    @Override
    public int saveUserRole(RoleUsersRequest request) {
        String[] userNoArray = StringUtils.split(request.getUserNos(), ",");
        String roleCode = request.getRoleCode();
        List<AdminRoleUserEntity> list = new ArrayList<AdminRoleUserEntity>();
        for (String userNo : userNoArray) {
            AdminRoleUserEntity ru = new AdminRoleUserEntity();
            ru.setUserNo(userNo);
            ru.setRoleCode(roleCode);
            list.add(ru);
        }
        roleUserDao.deleteUserRole(list);
        int num = roleUserDao.insertUserRole(list);
        return num;
    }

    /**
     * 删除用户角色
     *
     * @param userNo
     * @return
     */
    @Transactional
    @Override
    public int delUserRole(String userNo) {
        return roleUserDao.deleteUserRoleById(userNo);
    }

    /**
     * 查询用户角色
     * @param userNo
     * @return
     */
    @Override
    public String queryUserRoleCode(String userNo) {
        AdminRoleUserEntity roleUser = roleUserDao.queryRoleCodeByUserNo(userNo);
        if (null == roleUser) {
            return null;
        }
        return roleUser.getRoleCode();
    }

    @Override
    public RoleUserResponse queryRoleUserByCode(String roleCode) {
        AdminRoleUserEntity adminRoleUser = roleUserDao.queryRoleUserByCode(roleCode);
        RoleUserResponse res = new RoleUserResponse();
        BeanUtils.copyProperties(adminRoleUser, res);
        return res;
    }

    @Override
    public PageInfo queryUserByRoleCode(SimpleRequest<String> request) {
        PageHelper.startPage(request.getPage(), request.getPageSize());
        List<AdminRoleUserEntity> list = roleUserDao.queryUserByRoleCode(request.getRequest());
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo queryRoleUserList(SimpleRequest<String> request) {
        PageHelper.startPage(request.getPage(), request.getPageSize());
        List<AdminRoleUserEntity> list = roleUserDao.queryRoleUserListByRoleCode(request.getRequest());
        return new PageInfo<>(list);
    }
}
