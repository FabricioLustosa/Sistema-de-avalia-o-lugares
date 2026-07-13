# Tarefa 1: Usuário demo com login de um clique

1. Crie um usuário de demonstração no sistema: login "demo", senha "demo123", com papel de usuário comum (menor privilégio). Ele deve ser criado automaticamente na inicialização da aplicação caso não exista (ex: via CommandLineRunner ou data seeder), para funcionar também no banco de produção.
2. Na página de login, adicione um botão "Entrar como visitante" que autentica automaticamente com esse usuário demo, sem digitar credenciais.
3. Adicione também um box discreto na tela de login informando: "Ambiente de demonstração — use o botão abaixo para explorar sem cadastro".
4. NÃO altere as regras de proteção das rotas — a autenticação deve continuar obrigatória.

# Tarefa 2: Redesign da página de login

Redesenhe a página de login com estética moderna e minimalista, consistente com o restante do sistema:
- Card de login centralizado, com bastante espaço em branco
- Tipografia limpa, campos com labels claras e estados de foco visíveis
- Botão principal destacado e o botão "Entrar como visitante" como ação secundária logo abaixo
- Mensagens de erro de login estilizadas (não o texto cru padrão)
- Responsivo para mobile
- Mantenha o Thymeleaf e a estrutura de templates existente — apenas melhore o HTML/CSS da página

# Restrições gerais
- Não modifique entidades ou migrações de banco além do necessário para o seeder do usuário demo
- Não adicione dependências novas ao pom.xml sem me avisar antes
- Ao final, me explique como testar localmente