package br.sousa.Projeto.login.conexao.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class TokenUtil {

    private static final String SECRET = "shgfhghsgfbxgewh";

    public static String gerarToken(String email){
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static String validarToken(String token){
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET))
                    .build()
                    .verify(token);

            return jwt.getSubject();

        }catch (JWTVerificationException e){
            return null;
        }


    }
}
