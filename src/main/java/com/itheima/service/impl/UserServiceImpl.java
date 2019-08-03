package com.itheima.service.impl;

import com.itheima.constant.Constant;
import com.itheima.dao.IUserDao;
import com.itheima.domain.User;
import com.itheima.exception.PwdException;
import com.itheima.exception.RegisterFaildError;
import com.itheima.exception.UnActiveException;
import com.itheima.exception.UsernameException;
import com.itheima.factory.BeanFactory;
import com.itheima.service.IUserService;
import com.itheima.utils.MailUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.UuidUtil;

public class UserServiceImpl implements IUserService {
    private IUserDao dao = (IUserDao) BeanFactory.getBean("userDao");
    @Override
    public User checkUsername(String username) {

        return dao.checkUsername(username);
    }

    @Override
    public void doRegister(User user) throws Exception {
        /*3.1 将密码进行加密处理，设置激活状态、激活码
        3.2 调用dao层的方法将用户信息存储到数据库表中
        3.3 发送激活邮件到用户的邮箱*/

        user.setStatus(Constant.UNACTIVE);
        String password = user.getPassword();
        password = Md5Util.encodeByMd5(password);
        user.setPassword(password);

        String uuid = UuidUtil.getUuid();
        user.setCode(uuid);

        boolean flag=dao.addUser(user);
        if (flag){
            MailUtil.sendMail(user.getEmail(), "欢迎注册黑马旅游网，请点击<a href='http://localhost:8080/user?action=active&code="+user.getCode()+"'>激活</a>");
        }else {
            throw new RegisterFaildError(Constant.REGISTER_ERROR);
        }

    }

    @Override
    public void doActive(String code) {
        User user=dao.findUserByCode(code);
        if (user!=null){
            if (!user.getStatus().equalsIgnoreCase(Constant.ACTIVE_KEY)) {
                user.setStatus(Constant.ACTIVE_KEY);
                dao.update(user);
            }else {
                //不允许重复激活
                throw new RuntimeException("不允许重复激活");
            }

        }else {
            //激活码被篡改
            throw new RuntimeException("激活码被篡改");
        }
    }

    @Override
    public User doLogin(String username, String password) throws Exception {
        User user = dao.checkUsername(username);
        if (user==null){
            //用户名错误
            throw new UsernameException("用户名错误");
        }else {
            //用户名正确
            String pwd = user.getPassword();
            password = Md5Util.encodeByMd5(password);
            if (!password.equalsIgnoreCase(pwd)){
                //密码错误
                throw new PwdException("密码错误");
            }else {
                //密码正确
                String status = user.getStatus();
                if (!status.equalsIgnoreCase(Constant.ACTIVE_KEY)){
                    //未激活
                    throw new UnActiveException("未激活");
                }else {
                    return user;
                }

            }
        }
    }
}
