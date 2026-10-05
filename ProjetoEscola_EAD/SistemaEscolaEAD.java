import java.util.Scanner;

public class SistemaEscolaEAD
{
    private Scanner entrada;
    private ListaDeAlunos listaAlunos;

    // Matriz bidimensional de cursos
    private Curso[][] matrizCursos;

    private int totalCursos;

    public SistemaEscolaEAD()
    {
        entrada = new Scanner(System.in);
        listaAlunos = new ListaDeAlunos(50);

        // 3 turmas com 5 cursos cada
        matrizCursos = new Curso[3][5];

        totalCursos = 0;
    }

    public void iniciar()
    {
        int opcao;

        do
        {
            exibirMenu();

            opcao = entrada.nextInt();
            entrada.nextLine();

            switch (opcao)
            {
                case 1:
                    listaAlunos.exibirLista();
                    break;

                case 2:
                    adicionarAluno();
                    break;

                case 3:
                    System.out.println("Sistema encerrado.");
                    break;

                case 4:
                    verificarNotas();
                    break;

                case 5:
                    verificarFinanceiro();
                    break;

                case 6:
                    cadastrarCursos();
                    break;

                case 7:
                    matricularAlunoCurso();
                    break;

                case 8:
                    exibirCursos();
                    break;

                default:
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 3);
    }

    private void exibirMenu()
    {
        System.out.println();
        System.out.println("======================================");
        System.out.println("          SISTEMA ESCOLA EAD");
        System.out.println("======================================");
        System.out.println("1 - Visualizar alunos");
        System.out.println("2 - Cadastrar aluno");
        System.out.println("3 - Sair");
        System.out.println("4 - Consultar notas");
        System.out.println("5 - Consultar financeiro");
        System.out.println("6 - Cadastrar cursos");
        System.out.println("7 - Matricular aluno em curso");
        System.out.println("8 - Visualizar cursos");
        System.out.println("======================================");
        System.out.print("Escolha uma opcao: ");
    }

    // ==============================
    // ALUNOS
    // ==============================

    private void adicionarAluno()
    {
        System.out.println();
        System.out.println("===== NOVO ALUNO =====");

        System.out.print("Codigo: ");
        int codigo = entrada.nextInt();
        entrada.nextLine();

        System.out.print("Nome: ");
        String nome = entrada.nextLine();

        System.out.print("Data de nascimento: ");
        String nascimento = entrada.nextLine();

        System.out.print("E-mail: ");
        String email = entrada.nextLine();

        System.out.print("Senha: ");
        String senha = entrada.nextLine();

        System.out.print("Aluno bolsista? (s/n): ");
        String resposta = entrada.nextLine();

        Aluno aluno;

        if (resposta.equalsIgnoreCase("s"))
        {
            System.out.print("Tipo de bolsa: ");
            String bolsa = entrada.nextLine();

            aluno = new AlunoBolsista(
                codigo,
                nome,
                nascimento,
                email,
                senha,
                bolsa
            );
        }
        else
        {
            aluno = new Aluno(
                codigo,
                nome,
                nascimento,
                email,
                senha
            );
        }

        if (listaAlunos.adicionarAluno(aluno))
        {
            System.out.println();
            System.out.println("Aluno cadastrado!");

            if (totalCursos > 0)
            {
                System.out.print(
                    "Deseja matricular este aluno agora? (s/n): "
                );

                String respostaCurso = entrada.nextLine();

                if (respostaCurso.equalsIgnoreCase("s"))
                {
                    matricularAluno(aluno);
                }
            }
        }
        else
        {
            System.out.println("Nao foi possivel cadastrar o aluno.");
            System.out.println("Verifique se o codigo ja existe.");
        }
    }

    // ==============================
    // CURSOS
    // ==============================

    private void cadastrarCursos()
    {
        System.out.println();
        System.out.println("===== CADASTRO DE CURSOS =====");

        System.out.print("Quantidade de cursos: ");
        int quantidade = entrada.nextInt();
        entrada.nextLine();

        for (int i = 0; i < quantidade; i++)
        {
            if (totalCursos >= 15)
            {
                System.out.println("Limite de cursos atingido.");
                break;
            }

            System.out.println();
            System.out.println("Curso " + (i + 1));

            System.out.print("Codigo: ");
            int codigo = entrada.nextInt();
            entrada.nextLine();

            System.out.print("Nome: ");
            String nome = entrada.nextLine();

            System.out.print("Duracao em horas: ");
            int duracao = entrada.nextInt();
            entrada.nextLine();

            Curso novoCurso = new Curso(
                codigo,
                nome,
                duracao
            );

            int linha = totalCursos / 5;
            int coluna = totalCursos % 5;

            matrizCursos[linha][coluna] = novoCurso;

            totalCursos++;

            System.out.println("Curso cadastrado com sucesso!");
        }
    }

    private Curso buscarCurso(int codigo)
    {
        for (int linha = 0; linha < matrizCursos.length; linha++)
        {
            for (int coluna = 0;
                 coluna < matrizCursos[linha].length;
                 coluna++)
            {
                Curso curso = matrizCursos[linha][coluna];

                if (curso != null &&
                    curso.getCodigo() == codigo)
                {
                    return curso;
                }
            }
        }

        return null;
    }

    private void exibirSomenteCursos()
    {
        for (int linha = 0; linha < matrizCursos.length; linha++)
        {
            for (int coluna = 0;
                 coluna < matrizCursos[linha].length;
                 coluna++)
            {
                Curso curso = matrizCursos[linha][coluna];

                if (curso != null)
                {
                    System.out.println(
                        "Codigo: " + curso.getCodigo() +
                        " - " + curso.getNome()
                    );
                }
            }
        }
    }

    // ==============================
    // MATRICULA
    // ==============================

    private void matricularAlunoCurso()
    {
        System.out.println();
        System.out.println("===== MATRICULA =====");

        System.out.print("Codigo do aluno: ");
        int codigo = entrada.nextInt();
        entrada.nextLine();

        Aluno aluno = listaAlunos.buscarAluno(codigo);

        if (aluno == null)
        {
            System.out.println("Aluno nao encontrado.");
            return;
        }

        matricularAluno(aluno);
    }

    private void matricularAluno(Aluno aluno)
    {
        if (totalCursos == 0)
        {
            System.out.println("Nenhum curso cadastrado.");
            return;
        }

        System.out.println();
        System.out.println("Cursos disponiveis:");

        exibirSomenteCursos();

        System.out.print("Codigo do curso: ");
        int codigoCurso = entrada.nextInt();
        entrada.nextLine();

        Curso curso = buscarCurso(codigoCurso);

        if (curso == null)
        {
            System.out.println("Curso nao encontrado.");
            return;
        }

        aluno.setCursoMatriculado(curso);

        System.out.println("Matricula realizada!");
        System.out.println(
            "Aluno: " + aluno.getNome()
        );
        System.out.println(
            "Curso: " + curso.getNome()
        );
    }

    private void exibirCursos()
    {
        System.out.println();
        System.out.println("===== CURSOS CADASTRADOS =====");

        if (totalCursos == 0)
        {
            System.out.println("Nenhum curso cadastrado.");
            return;
        }

        for (int linha = 0;
             linha < matrizCursos.length;
             linha++)
        {
            for (int coluna = 0;
                 coluna < matrizCursos[linha].length;
                 coluna++)
            {
                Curso curso = matrizCursos[linha][coluna];

                if (curso != null)
                {
                    System.out.println();
                    curso.exibeDados();

                    System.out.println("Alunos matriculados:");

                    listaAlunos.exibirAlunosDoCurso(curso);

                    System.out.println("-------------------------");
                }
            }
        }
    }

    // ==============================
    // NOTAS
    // ==============================

    private void verificarNotas()
    {
        System.out.println();
        System.out.println("===== NOTAS =====");

        System.out.print("Codigo do aluno: ");
        int codigo = entrada.nextInt();

        Aluno aluno = listaAlunos.buscarAluno(codigo);

        if (aluno == null)
        {
            System.out.println("Aluno nao encontrado.");
            return;
        }

        System.out.print("Nota 1: ");
        double nota1 = entrada.nextDouble();

        System.out.print("Nota 2: ");
        double nota2 = entrada.nextDouble();

        System.out.print("Nota 3: ");
        double nota3 = entrada.nextDouble();

        aluno.lancarNotas(
            nota1,
            nota2,
            nota3
        );

        System.out.println();
        System.out.println("===== RESULTADO =====");

        aluno.exibirNotas();
    }

    // ==============================
    // FINANCEIRO
    // ==============================

    private void verificarFinanceiro()
    {
        System.out.println();
        System.out.println("===== FINANCEIRO =====");

        System.out.print("Codigo do aluno: ");
        int codigo = entrada.nextInt();

        Aluno aluno = listaAlunos.buscarAluno(codigo);

        if (aluno == null)
        {
            System.out.println("Aluno nao encontrado.");
            return;
        }

        System.out.print("Valor total: R$ ");
        double valorTotal = entrada.nextDouble();

        System.out.print("Numero de parcelas: ");
        int quantidade = entrada.nextInt();

        if (quantidade <= 0)
        {
            System.out.println("Quantidade invalida.");
            return;
        }

        double valorParcela = valorTotal / quantidade;

        double[] valores = new double[quantidade];

        for (int i = 0; i < quantidade; i++)
        {
            valores[i] = valorParcela;
        }

        aluno.adicionarMensalidades(valores);

        System.out.println();
        System.out.println("Plano criado!");
        System.out.println(
            "Valor de cada parcela: R$ " +
            valorParcela
        );

        aluno.exibirMensalidades();

        System.out.print(
            "Deseja pagar uma parcela? (1 = sim / 0 = nao): "
        );

        int pagar = entrada.nextInt();

        if (pagar == 1)
        {
            System.out.print("Numero da parcela: ");
            int parcela = entrada.nextInt();

            aluno.pagarMensalidade(parcela - 1);

            System.out.println();
            System.out.println("Situacao atual:");

            aluno.exibirMensalidades();
        }
    }

    // ==============================
    // MAIN
    // ==============================

    public static void main(String[] args)
    {
        SistemaEscolaEAD sistema =
            new SistemaEscolaEAD();

        sistema.iniciar();
    }
}