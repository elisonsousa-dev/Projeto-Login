public class Usuario {
    private String nome;
    private String email;
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void cadastrarUsuario(String nome, String email , String senha){
        this.setNome(nome);
        this.setEmail(email);
        this.setSenha(senha);
    }

    public boolean autenticar(String email , String senha){
        return email.equals(getEmail()) && senha.equals(getSenha());
    }

}
