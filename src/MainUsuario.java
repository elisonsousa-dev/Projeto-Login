void main() {

    DadosUsuarios sistema = new DadosUsuarios();
    Scanner scanner = new Scanner(System.in);
    Validacoes Validador = new Validacoes();
    boolean senhaValida;
    boolean emailValido;
    int opcao;

    do {
        System.out.println("|======================|");
        System.out.println("|[1] Cadastrar usuario |");
        System.out.println("|[2] Fazer login       |");
        System.out.println("|[3] Lista de usuarios |");
        System.out.println("|[4] Sair              |");
        System.out.println("|======================|");

        System.out.print("Opcao: ");
        opcao = scanner.nextInt();

        switch (opcao){
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
                    if(!emailValido){
                        System.out.println("E-mail inválido!");
                    }
                }while (!emailValido);

              do {
                System.out.print("Senha: ");
                senha = scanner.nextLine();
                senhaValida = Validador.validarSenha(senha);
                if(!senhaValida){
                    System.out.println("A senha não pode ser vazia!");
                }
               }while (!senhaValida);
                Usuario novo = new Usuario();
                novo.cadastrarUsuario(nome, email ,  senha);
                sistema.adicionarUsuario(novo);
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
                }while (!emailValido);
                do {
                    System.out.print("Senha: ");
                    senhaLogin = scanner.nextLine();
                    senhaValida = Validador.validarSenha(senhaLogin);
                    if (!senhaValida) {
                        System.out.println("Senha inválida!");
                    }
                }while (!senhaValida);

                Usuario logado =  sistema.login(emailLogin , senhaLogin);
                if(logado != null){
                    System.out.println("Bem-vindo,  "+logado.getNome());

                    System.out.println("Usuario: "+ logado.getNome());
                    System.out.println("E-mail: "+ logado.getEmail());
                }else{
                    System.out.println("Erro: Usuario ou senha invalidas.");
                }
                break;
            case 3:
                sistema.listaDeUsuarios();
                break;
            case 4:
                System.out.println("Saindo..");
                break;
        }
    }while (opcao != 4);



}