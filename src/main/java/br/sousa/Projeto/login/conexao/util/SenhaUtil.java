package br.sousa.Projeto.login.conexao.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class SenhaUtil {
   public static String gerarHash(String senha){
        return BCrypt.hashpw(senha, BCrypt.gensalt());
   }
   public static boolean verificar(String senhaDigitada, String hash){
       return BCrypt.checkpw(senhaDigitada, hash);
   }
}
