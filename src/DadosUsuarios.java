import java.util.ArrayList;

public class DadosUsuarios {
    ArrayList<Usuario> usuarios = new ArrayList<>();
    public void adicionarUsuario(Usuario usuario){
        usuarios.add(usuario);

    }
    public void listaDeUsuarios(){
        System.out.println("==========================");
        System.out.println("   Usuarioss Cadastrados: ");
        System.out.println("==========================");
        for(Usuario list : usuarios){
            System.out.println("Usuario: "+list.getNome()+" | E-mail: "+list.getEmail());
        }
    }

    public Usuario login( String email , String senha){
        for(Usuario c : usuarios){
            if(c.autenticar(email , senha)){
                return c;
            }
        }
        return null;
    }

}