package com.base.jwt;

import com.base.constants.CommonConstants;
import com.base.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

public class JWTHelper {
    private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();


    /**
     * 密钥签名token
     *
     * @param ijwtInfo
     * @param priKeyPath
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(IJWTInfo ijwtInfo, String priKeyPath, int expire) throws Exception {
        String compactJws = Jwts.builder()
                .setSubject(ijwtInfo.getUniqueName())
                .claim(CommonConstants.JWT_KEY_USER_ID, ijwtInfo.getId())
                .claim(CommonConstants.JWT_KEY_NAME, ijwtInfo.getName())
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKeyPath))
                .compact();
        return compactJws;
    }


    /**
     *
     * @param ijwtInfo
     * @param priKey
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(IJWTInfo ijwtInfo, byte priKey[], int expire) throws Exception {
        String compactJws = Jwts.builder()
                .setSubject(ijwtInfo.getUniqueName())
                .claim(CommonConstants.JWT_KEY_USER_ID, ijwtInfo.getId())
                .claim(CommonConstants.JWT_KEY_NAME, ijwtInfo.getName())
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKey))
                .compact();
        return compactJws;
    }


    /**
     * 公钥解析token
     * @param token
     * @param pubKeyPath
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token,String pubKeyPath) throws Exception{
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(rsaKeyHelper.getPublicKey(pubKeyPath))
                .parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, byte[] pubKey) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKey)).parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * 获取Token中用户信息
     * @param token
     * @param pubKeyPath
     * @return
     * @throws Exception
     */
    public static IJWTInfo getInfoFromToken(String token,String pubKeyPath) throws Exception{
        Jws<Claims> claimsJws = parserToken(token,pubKeyPath);
        Claims body = claimsJws.getBody();
        return new JWTInfo(body.getSubject(),
                StringUtils.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_ID))
                ,StringUtils.getObjectValue(body.get(CommonConstants.JWT_KEY_NAME)));
    }

    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param pubKey
     * @return
     * @throws Exception
     */
    public static IJWTInfo getInfoFromToken(String token,byte[] pubKey) throws Exception{
        Jws<Claims> claimsJws = parserToken(token,pubKey);
        Claims body = claimsJws.getBody();
        return new JWTInfo(body.getSubject(),
                StringUtils.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_ID)),
                StringUtils.getObjectValue(body.get(CommonConstants.JWT_KEY_NAME)));
    }


}
