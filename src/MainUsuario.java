import conexao.Conexao;

void main() {
    Scanner scanner = new Scanner(System.in);
    Validacoes Validador = new Validacoes();
    UsuarioDao dados = new UsuarioDao();
   List<Usuario> dao = dados.lista();
   Usuario loginUsuario = null;
    boolean senhaValida;
    boolean emailValido;
    boolean login = false;
    int opcao;
    int opcao2;
do {

    System.out.println("|======================|");
    System.out.println("|[1] Cadastrar usuario |");
    System.out.println("|[2] Fazer login       |");
    System.out.println("|======================|");

    System.out.print("Opcao: ");
    opcao2 = scanner.nextInt();
    switch (opcao2) {
        case 1:
            String senha;
            String email;
            scanner.nextLine();
            System.out.print("Usuario: ");
            String nome = scanner.nextLine();
            do {

                System.out.print("E-mail: ");
                email = scanner.nextLine();
                emailValido = Validador.validarEmail(email);
                if (!emailValido) {
                    System.out.println("E-mail inválido!");
                }
            } while (!emailValido);

            do {
                System.out.print("Senha: ");
                senha = scanner.nextLine();
                senhaValida = Validador.validarSenha(senha);
                if (!senhaValida) {
                    System.out.println("A senha não pode ser vazia!");
                }
            } while (!senhaValida);
            Usuario novo = new Usuario();
            novo.cadastrarUsuario(nome, email, senha);
            dados.cadastrar(novo);
            break;
        case 2:
            String emailLogin;
            String senhaLogin;
            scanner.nextLine();
            do {
                System.out.print("E-mail: ");
                emailLogin = scanner.nextLine();
                emailValido = Validador.validarEmail(emailLogin);
                if (!emailValido) {
                    System.out.println("E-mail inválido!");
                }
            } while (!emailValido);
            do {
                System.out.print("Senha: ");
                senhaLogin = scanner.nextLine();
                senhaValida = Validador.validarSenha(senhaLogin);
                if (!senhaValida) {
                    System.out.println("Senha inválida!");
                }
            } while (!senhaValida);

               loginUsuario = dados.login(emailLogin, senhaLogin);
               if (loginUsuario != null) {
                   System.out.println("Bem-vindo,  " + loginUsuario.getNome());

                   System.out.println("Usuario: " + loginUsuario.getNome());
                   System.out.println("E-mail: " + loginUsuario.getEmail());
                   login = true;
               } else {
                   System.out.println("Erro: Usuario ou senha invalidas.");
                   System.out.println("Tente novamente.");
               }
            break;
    }
}while (!login);

    do {
        System.out.println("|======================|");
        System.out.println("|[1] Lista de usuarios |");
        System.out.println("|[2] Excluir           |");
        System.out.println("|[3] Sair              |");
        System.out.println("|======================|");

        System.out.print("Opcao: ");
        opcao = scanner.nextInt();

        switch (opcao){
            case 1:
              for(Usuario a : dao){
                  System.out.println("=========================");
                  System.out.println("Nome:"+ a.getNome());
                  System.out.println("E-mail: "+a.getEmail());
                  System.out.println("=========================");
              }
                break;
            case 2:
                scanner.nextLine();
                System.out.println("Usuario:"+ loginUsuario.getNome());
                System.out.println("Digite sua senha Senha: ");
                String loginSenha = scanner.nextLine();

                if(loginSenha.equals(loginUsuario.getSenha())){

                    dados.delete(loginUsuario.getEmail(),loginUsuario.getSenha());
                }

                break;
            case 3:
                System.out.println("Saindo..");
                break;
        }
    }while (opcao != 3);



}