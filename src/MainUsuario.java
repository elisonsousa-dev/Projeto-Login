void main() {

    DadosUsuarios sistema = new DadosUsuarios();
    Scanner scanner = new Scanner(System.in);
    int opcao;

    do {
        System.out.println("[1] Cadastrar usuario ");
        System.out.println("[2] Fazer login ");
        System.out.println("[3] Lista de usuarios ");
        System.out.println("[4] Sair");

        System.out.print("Opcao: ");
        opcao = scanner.nextInt();

        switch (opcao){
            case 1:
                scanner.nextLine();
                System.out.print("Usuario: ");
                String nome = scanner.nextLine();

                System.out.print("E-mail: ");
                String email = scanner.nextLine();

                System.out.print("Senha: ");
                String senha = scanner.nextLine();
                Usuario novo = new Usuario();
                novo.cadastrarUsuario(nome, email ,  senha);
                sistema.adicionarUsuario(novo);
                break;
            case 2:
                scanner.nextLine();
                System.out.print("E-mail: ");
                String emailLogin = scanner.nextLine();
                System.out.print("Senha: ");
                String senhaLogin = scanner.nextLine();
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