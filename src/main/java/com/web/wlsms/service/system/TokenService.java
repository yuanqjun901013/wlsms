package com.web.wlsms.service.system;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.web.wlsms.dao.TokenDao;
import com.web.wlsms.entity.TokenEntity;
import com.web.wlsms.entity.UserEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/***
 * token 下发
* @Title: TokenService.java 
* @author MRC
* @date 2019年5月27日 下午5:40:25 
* @version V1.0
 */
@Service("TokenService")
public class TokenService {

	@Resource
	private TokenDao tokenDao;

	public String getToken(UserEntity user) {
		Date start = new Date();
		long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;//一小时有效时间
		Date end = new Date(currentTime);
		return JWT.create().withAudience(user.getUserNo()).withIssuedAt(start).withExpiresAt(end).sign(Algorithm.HMAC256(user.getPwd()));
	}

	public TokenEntity findByUserNo(String userNo){
		return tokenDao.findByUserNo(userNo);
	}


	public void insertUserToken(TokenEntity tokenEntity){
		tokenDao.insertUserToken(tokenEntity);
	}

	public void updateUserToken(TokenEntity tokenEntity){
		tokenDao.updateUserToken(tokenEntity);
	}

	public void deleteUserToken(String userNo){
		tokenDao.deleteUserToken(userNo);
	}
}
