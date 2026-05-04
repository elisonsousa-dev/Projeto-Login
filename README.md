# Sistema login

Projeto desenvolvido em java com foco em Programação Orientada a Objetos.

## 💻 Funcionalidades
- 🤵 Cadastro de usuários
- 🔑 Login
- 📒 Lista de usuários
- 🔐 Alteraçao de senha
- 🔄 Atualização de dados (Nome, E-mail)
- ❌ Exclusão de Conta

## 🛠 Tecnologias utilizadas
- Java
- POO (Programação Orientada a Objetos)
- Tratamento de exceções (try/catch)
- Maven
- BCrypt (criptografia de senha)
- JWT (Token)
- JDBC
- MySQL
- Git e GitHub

## 🔐 Autenticação

A API utiliza autenticação baseada em token enviado no hearder:
Authorizatio: Bearer {token}

## 📡 Endpoints 

### Cadastro
POST /cadastro

### Login
POST /login

### Atualizar dados
PUT /me

### Atualizar senha
PUT /senha

### Delete usuário
DELETE /me

## Observações
- Senhas são armazenadas com criptografia BCrypt
- Projeto focado em estudo de API REST
  
## 🔄 Atualizações
Sistema conectado com o banco de dados MySQL, Estruturas de validaçoes, funçao de Criptografia de senha para a segurança do usuário.
Sistema conectado com rotas (Apis rest), (JWT) Gerar token para validar acesso dos usuários.
Em breve mais atualizações para a melhoria do sistema de login.
Em breve melhoria na criptografia de senha, com a criptografia em argo2id, é uma criptografia mais robusta e a melhor atualmente
## 👨‍💻 Desenvolvedor
Elison Sousa

