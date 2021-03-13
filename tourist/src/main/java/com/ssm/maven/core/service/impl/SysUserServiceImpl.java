package com.ssm.maven.core.service.impl;

import com.ssm.maven.core.dao.SysUserMapper;
import com.ssm.maven.core.entity.SysUser;
import com.ssm.maven.core.service.SysyUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("sysuserservice")
public class SysUserServiceImpl implements SysyUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser login(SysUser user) {
        return sysUserMapper.login(user);
    }

    @Override
    public SysUser getUserInfo(String account) {
        return sysUserMapper.selectUserInfo(account);
    }

    @Override
    public void updateUserInfo(SysUser sysUser) {
        sysUserMapper.updateUserInfo(sysUser);
    }


}
