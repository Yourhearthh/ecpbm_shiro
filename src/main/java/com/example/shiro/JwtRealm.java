package com.example.shiro;

import com.example.dao.mapper.UserInfoMapper;
import com.example.jwt.JwtToken;
import com.example.pojo.SysPermission;
import com.example.pojo.SysRole;
import com.example.pojo.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @ClassName:
 * @Description: 只负责校验 JwtToken
 * @author: baoguangyu
 * @date: 2021-06-02 17:42
 * @version: 1.0
 */
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 限定这个 Realm 只处理我们自定义的 JwtToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 此处的 SimpleAuthenticationInfo 可返回任意值，密码校验时不会用到它
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authcToken;
        if (jwtToken.getPrincipal() == null) {
            throw new AccountException("JWT token参数异常！");
        }
        // 从 JwtToken 中获取当前用户
        String username = jwtToken.getPrincipal().toString();
        // 查询数据库获取用户信息，此处使用 Map 来模拟数据库
        UserInfo user = userInfoMapper.findByUsername(username);

        // 用户不存在
        if (user == null) {
            throw new UnknownAccountException("用户不存在！");
        }

        // 用户被锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("该用户已被锁定,暂时无法登录！");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, username, getName());
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取当前用户
        UserInfo currentUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 获取当前用户的角色与权限，让 simpleAuthorizationInfo 去验证
        for (SysRole sysRole : currentUser.getRoleList()) {
            simpleAuthorizationInfo.addRole(sysRole.getRole());
            for (SysPermission sysPermission : sysRole.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(sysPermission.getPermission());
            }
        }
//        // 添加用户权限
//        simpleAuthorizationInfo.addStringPermission("user");
        return simpleAuthorizationInfo;
    }
}
