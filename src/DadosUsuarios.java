import java.util.ArrayList;

public class DadosUsuarios {
    ArrayList<Usuario> usuarios = new ArrayList<>();

    public void adicionarUsuario(Usuario usuario){
        if (usuarios.contains(usuario.getEmail())){
            System.out.println("Usuário ja esxistente");
        }else{
            usuarios.add(usuario);
            System.out.println("Conta registrada");
        }

    }
    public void listaDeUsuarios(){
        System.out.println("==========================");
        System.out.println("   Usuarios Cadastrados: ");
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