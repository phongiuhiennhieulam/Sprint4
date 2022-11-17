package com.example.vmg.security.jwt;

import com.example.vmg.security.userprincal.UserPrinciple;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import java.util.Date;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSecret = "khang@deptrai.vn";
    private int jwtExpiration = 86400;
    public String createToken(Authentication authentication){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e ){
            logger.error("loi Jwt", e);
        }
        catch (MalformedJwtException e ){
            logger.error("loi Jwt", e);
        }
        catch (ExpiredJwtException e ){
            logger.error("loi Jwt", e);
        }
        catch (UnsupportedJwtException e ){
            logger.error("loi Jwt", e);
        }
        catch (IllegalArgumentException e ){
            logger.error("loi Jwt", e);
        }return false;
    }

    public String getUserNameFromToken(String token){
        String userName = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        return userName;
    }

    public Long getIdFromToken(String token){
        return Long.valueOf(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getId());
    }

}
