package com.example.shiro;

import com.example.dao.mapper.*;
import com.example.jwt.JwtToken;
import com.example.pojo.SysPermission;
import com.example.pojo.SysRole;
import com.example.pojo.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-06-02 17:51
 * @version: 1.0
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    SysPermissionMapper sysPermissionMapper;
    /**
     * 限定这个 Realm 只处理 UsernamePasswordToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 此处的 SimpleAuthenticationInfo 可返回任意值，密码校验时不会用到它
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        // 从 AuthenticationToken 中获取当前用户
        String username = (String) authcToken.getPrincipal();
        if (authcToken.getPrincipal() == null) {
            throw new AccountException("JWT token参数异常！");
        }
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

        //根据salt来验证token中的密码是否跟从数据库查找的密码匹配，匹配则登录成功。getName()设置当前Realm的唯一名称，可自定义
        return new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),//盐
                getName());
    }


    /**
     * 当访问需要权限的 URL 时调用，用于验证当前用户是否有权限
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        UserInfo currentUser = (UserInfo) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        List<SysRole> roleList = sysRoleMapper.selectByUid(currentUser.getUid());
        List<Integer> list = roleList.stream().map(SysRole::getId).collect(Collectors.toList());
        List<SysPermission> permissionList = sysPermissionMapper.selectByRoleId(list);
        // 获取当前用户的角色与权限，让 simpleAuthorizationInfo 去验证
        for (SysRole sysRole : roleList) {
            simpleAuthorizationInfo.addRole(sysRole.getRole());
            for (SysPermission sysPermission : permissionList) {
                simpleAuthorizationInfo.addStringPermission(sysPermission.getPermission());
            }
        }
//        // 添加用户权限
//        simpleAuthorizationInfo.addStringPermission("user");
        return simpleAuthorizationInfo;
    }

    public static void main(String[] args) {
        // newPassword(密文密码)：d3c59d25033dbf980d29554025c23a75
        String newPassword = new SimpleHash("MD5",//散列算法:这里使用MD5算法
                "123456",//明文密码
                ByteSource.Util.bytes("tom8d78869f470951332959580424d4bf4f"),//salt：用户名 + salt
                2//散列的次数，相当于MD5(MD5(**))
        ).toHex();

        // 生成一个32位数的salt
        byte[] saltByte = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(saltByte);
        String salt = Hex.encodeToString((saltByte));

        System.out.println(newPassword);
//            System.out.println(salt);
    }
}
