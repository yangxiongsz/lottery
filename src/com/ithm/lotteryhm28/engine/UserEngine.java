package com.ithm.lotteryhm28.engine;

import com.ithm.lotteryhm28.bean.User;
import com.ithm.lotteryhm28.net.protocal.Message;

/**
 * 用户相关的业务操作的接口
 * 
 * @author Administrator
 * 
 */
public interface UserEngine {
	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	Message login(User user);
}
