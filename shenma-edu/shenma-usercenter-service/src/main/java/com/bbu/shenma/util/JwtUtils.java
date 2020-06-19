package com.bbu.shenma.util;

import com.bbu.shenma.bean.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author helen
 * @since 2019/3/16
 */
public class JwtUtils {


	public static final String APPSECRET = "shenma123456";

	/**
	 * 1 生成jwt令牌的方法
	 * @param
	 * @return
	 */
	public static String genJsonWebToken(User user){

	if(user == null
			|| StringUtils.isEmpty(user.getId())
			|| StringUtils.isEmpty(user.getUsername())
			|| StringUtils.isEmpty(user.getHeadImg())){
		return null;
	}

		String token = Jwts.builder().setSubject("shenma")
				.claim("id", user.getId())
				.claim("nickName", user.getUsername())
				.claim("headImg", user.getHeadImg())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(SignatureAlgorithm.HS256, APPSECRET).compact();

		return token;
	}

	/**
	 * 2 根据jwt令牌获取里面用户信息
	 * @param token
	 * @return
	 */
	public static Claims checkJwt(String token){
		Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
		return claims;
	}

}
