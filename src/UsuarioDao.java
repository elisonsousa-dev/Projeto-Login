import conexao.Conexao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;

public class UsuarioDao {

    public void cadastrar(Usuario users){
        String sql = "insert into usuario (nome, email, senha) values (?, ?, ?)";

        try {
            PreparedStatement cad = Conexao.getConexao().prepareStatement(sql);

            cad.setString(1, users.getNome());
            cad.setString(2,users.getEmail());
            cad.setString(3,users.getSenha());

            cad.execute();
            cad.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Usuario login(String email, String senha){
        String sql = "select * from usuario where email = ? and senha = ?";

        try {
            PreparedStatement log = Conexao.getConexao().prepareStatement(sql);

            log.setString(1,email);
            log.setString(2,senha);

            ResultSet login = log.executeQuery();

            if(login.next()){
                Usuario user = new Usuario();

                user.setNome(login.getString("nome"));
                user.setEmail(login.getString("email"));
                user.setSenha(login.getString("senha"));

                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public List<Usuario> lista(){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "select * from usuario";

        try {
            PreparedStatement list = Conexao.getConexao().prepareStatement(sql);

            ResultSet lis = list.executeQuery();

            while (lis.next()){
                Usuario lista = new Usuario();

                lista.setNome(lis.getString("nome"));
                lista.setEmail(lis.getString("email"));

                usuarios.add(lista);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }
    public void delete(String email, String senha){
        String sql = "DELETE from usuario where email = ? and senha = ? ";

        try {
            PreparedStatement del = Conexao.getConexao().prepareStatement(sql);
            del.setString(1,email);
            del.setString(2,senha);

            int linha = del.executeUpdate();

            if(linha > 0){
                System.out.println("Conta excluida");
            }else{
                System.out.println("A conta não foi encontrada");
            }

            del.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
