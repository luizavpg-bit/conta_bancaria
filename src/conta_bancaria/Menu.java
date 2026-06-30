package conta_bancaria;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import conta_bancaria.controller.ContaController;
import conta_bancaria.model.Conta;
import conta_bancaria.model.ContaCorrente;
import conta_bancaria.model.ContaPoupanca;
import conta_bancaria.util.Cores;

public class Menu {

	// Global
	private static final Scanner leia = new Scanner(System.in);
	private static final ContaController contaController = new ContaController();

	public static void main(String[] args) {

		int opcao;

		// Criar dados de teste
		criarContasTeste();

		while (true) {

			System.out.println(Cores.TEXT_YELLOW + Cores.ANSI_BLACK_BACKGROUND
					+ "*****************************************************");
			System.out.println("                                                     ");
			System.out.println("                BANCO DO BRAZIL COM Z                ");
			System.out.println("                                                     ");
			System.out.println("*****************************************************");
			System.out.println("                                                     ");
			System.out.println("            1 - Criar Conta                          ");
			System.out.println("            2 - Listar todas as Contas               ");
			System.out.println("            3 - Buscar Conta por Numero              ");
			System.out.println("            4 - Atualizar Dados da Conta             ");
			System.out.println("            5 - Apagar Conta                         ");
			System.out.println("            6 - Sacar                                ");
			System.out.println("            7 - Depositar                            ");
			System.out.println("            8 - Transferir valores entre Contas      ");
			System.out.println("            9 - Consulta por nome do titular         ");
			System.out.println("            0 - Sair                                 ");
			System.out.println("                                                     ");
			System.out.println("*****************************************************");
			System.out.println("Entre com a opção desejada:                          ");
			System.out.println("                                                     " + Cores.TEXT_RESET);

			try {
				opcao = leia.nextInt();
				leia.nextLine();
			} catch (InputMismatchException e) {
				opcao = -1;
				System.out.println("\nDigite um número inteiro entre 0 e 8");
				leia.nextLine();
			}

			if (opcao == 0) {
				System.out.println(Cores.TEXT_WHITE_BOLD + "\nBanco do Brazil com Z - O seu Futuro começa aqui!");
				sobre();
				leia.close();
				System.exit(0);
			}

			switch (opcao) {
			case 1:
				System.out.println(Cores.TEXT_WHITE + "Criar Conta\n\n");

				cadastrarConta();

				keyPress();
				break;
			case 2:
				System.out.println(Cores.TEXT_WHITE + "Listar todas as Contas\n\n");

				listarContas();

				keyPress();
				break;
			case 3:
				System.out.println(Cores.TEXT_WHITE + "Consultar dados da Conta - por número\n\n");
				procurarContaPorNumero();
				keyPress();
				break;
			case 4:
				System.out.println(Cores.TEXT_WHITE + "Atualizar dados da Conta\n\n");
				atualizarConta();
				keyPress();
				break;
			case 5:
				System.out.println(Cores.TEXT_WHITE + "Apagar a Conta\n\n");
				deletarConta();
				keyPress();
				break;
			case 6:
				System.out.println(Cores.TEXT_WHITE + "Saque\n\n");
				sacar();
				keyPress();
				break;
			case 7:
				System.out.println(Cores.TEXT_WHITE + "Depósito\n\n");
				depositar();
				keyPress();
				break;
			case 8:
				System.out.println(Cores.TEXT_WHITE + "Transferência entre Contas\n\n");
				transferir();
				keyPress();
				break;
			case 9:
				System.out.println(Cores.TEXT_WHITE + "Consulta por nome do titular\n\n");
				listarPorTitular();
				keyPress();
				break;
			default:
				System.out.println(Cores.TEXT_RED_BOLD + "\nOpção Inválida!\n" + Cores.TEXT_RESET);
				keyPress();
				break;
			}
		}
	}

	public static void sobre() {
		System.out.println("\n*********************************************************");
		System.out.println("Projeto Desenvolvido por: ");
		System.out.println("Generation Brasil - generation@generation.org");
		System.out.println("github.com/conteudoGeneration");
		System.out.println("*********************************************************");
	}

	public static void keyPress() {
		System.out.println(Cores.TEXT_RESET + "\n\nPressione Enter para continuar...");
		leia.nextLine();
	}

	public static void criarContasTeste() {
		contaController.cadastrar(
				new ContaCorrente(contaController.gerarNumero(), 456, 1, "Thuany Silva", 1000000.00f, 100000.00f));
		contaController.cadastrar(
				new ContaPoupanca(contaController.gerarNumero(), 456, 2, "Marcia Condarco", 1000000.00f, 10));
	}

	public static void listarContas() {
		contaController.listarTodas();
	}

	public static void cadastrarConta() {

		System.out.println("Digite o número da agência: ");
		int agencia = leia.nextInt();

		System.out.println("Digite o nome do titular da conta: ");
		leia.skip("\\R");
		String titular = leia.nextLine();

		System.out.println("Digite o tipo da conta (1 - CC | 2 - CP): ");
		int tipo = leia.nextInt();

		System.out.println("Digite o saldo da conta: ");
		float saldo = leia.nextFloat();

		switch (tipo) {
		case 1 -> {
			System.out.println("Digite o limite da conta: ");
			float limite = leia.nextFloat();
			leia.nextLine();

			contaController
					.cadastrar(new ContaCorrente(contaController.gerarNumero(), agencia, tipo, titular, saldo, limite));
		}
		case 2 -> {
			System.out.println("Digite o dia do aniversário da conta: ");
			int aniversario = leia.nextInt();
			leia.nextLine();

			contaController.cadastrar(
					new ContaPoupanca(contaController.gerarNumero(), agencia, tipo, titular, saldo, aniversario));
		}
		default -> System.out.println(Cores.TEXT_RED + "Tipo de conta inválida!" + Cores.TEXT_RESET);
		}

	}

	public static void procurarContaPorNumero() {

		System.out.println("Digite o número da conta: ");
		int numero = leia.nextInt();
		leia.nextLine();

		contaController.procurarPorNumero(numero);

	}

	public static void deletarConta() {

		System.out.println("Digite o número da conta: ");
		int numero = leia.nextInt();
		leia.nextLine();

		Optional<Conta> conta = contaController.buscarNaCollection(numero);

		if (conta.isPresent()) {

			// Confirmação da exclusão

			System.out.printf("\nTem certeza que você deseja excluir a conta numero %d (S/N)?", numero);
			String confirmacao = leia.nextLine();

			if (confirmacao.equalsIgnoreCase("S"))
				contaController.deletar(numero);
			else
				System.out.println("\nOperação cancelada!");

		} else {
			System.out.printf("\nA conta número %d não foi encontrada!", numero);
		}

	}

	public static void atualizarConta() {

		System.out.println("Digite o número da conta: ");
		int numero = leia.nextInt();
		leia.nextLine();

		Optional<Conta> conta = contaController.buscarNaCollection(numero);

		if (conta.isPresent()) {

			// Obtém os dados atuais da conta
			int agencia = conta.get().getAgencia();
			String titular = conta.get().getTitular();
			int tipo = conta.get().getTipo();
			float saldo = conta.get().getSaldo();

			// Atualiza a agência ou mantém o valor atual
			System.out.printf("Agência atual: %d"
					+ "%nDigite o número da nova agência (Pressione ENTER para manter o valor atual)", agencia);
			String entrada = leia.nextLine();

			agencia = entrada.isEmpty() ? agencia : Integer.parseInt(entrada);

			// Atualiza o titular ou mantém o valor atual
			System.out.printf("Titular atual: %s"
					+ "%nDigite o nome do novo titular (Pressione ENTER para manter o valor atual)", titular);
			entrada = leia.nextLine();

			titular = entrada.isEmpty() ? titular : entrada;
			
			// Atualiza o saldo ou mantém o valor atual
			System.out.printf("Saldo atual: %.2f"
					+ "%nDigite o novo saldo (Pressione ENTER para manter o valor atual)", saldo);
			entrada = leia.nextLine();

			saldo = entrada.isEmpty() ? saldo : Float.parseFloat(entrada.replace(",","."));
			
			switch(tipo) {
				case 1 ->{
					ContaCorrente contaCorrente = (ContaCorrente) conta.get();
					float limite = contaCorrente.getLimite();
					
					// Atualiza o limite ou mantém o valor atual
					System.out.printf("Limite atual: %.2f"
							+ "%nDigite o novo limite (Pressione ENTER para manter o valor atual)", limite);
					entrada = leia.nextLine();
	
					limite = entrada.isEmpty() ? limite : Float.parseFloat(entrada.replace(",","."));
					
					contaController.atualizar(new ContaCorrente(numero, agencia, tipo, titular, saldo, limite));
					
				}
				case 2 -> {
					ContaPoupanca contaPoupanca = (ContaPoupanca) conta.get();
					int aniversario = contaPoupanca.getAniversario();
					
					// Atualiza o aniversario ou mantém o valor atual
					System.out.printf("Dia do aniversário atual: %d"
							+ "%nDigite o novo dia do aniversário da conta "
							+ "(Pressione ENTER para manter o valor atual)", aniversario);
					entrada = leia.nextLine();
	
					aniversario = entrada.isEmpty() ? aniversario : Integer.parseInt(entrada);
					
					contaController.atualizar(new ContaPoupanca(numero, agencia, tipo, titular, saldo, aniversario));
				}
				default -> System.out.println(Cores.TEXT_RED + "Tipo da conta é inválido!" + Cores.TEXT_RESET);
			}

		} else {
			System.out.printf("\nA conta número %d não foi encontrada!", numero);
		}

	}
	
	public static void sacar() {
		
		System.out.println("Digite o número da conta: ");
		int numero = leia.nextInt();
		
		System.out.println("Digite o valor do saque: ");
		float valor = leia.nextFloat();
		leia.nextLine();
		
		contaController.sacar(numero, valor);		
		
	}
	
	public static void depositar() {
		
		System.out.println("Digite o número da conta: ");
		int numero = leia.nextInt();
		
		System.out.println("Digite o valor do depósito: ");
		float valor = leia.nextFloat();
		leia.nextLine();
		
		contaController.depositar(numero, valor);		
		
	}
	
	public static void transferir() {
		
		System.out.println("Digite o número da conta de origem: ");
		int numeroOrigem = leia.nextInt();
		
		System.out.println("Digite o número da conta de destino: ");
		int numeroDestino = leia.nextInt();
		
		System.out.println("Digite o valor da transferência: ");
		float valor = leia.nextFloat();
		leia.nextLine();

		contaController.transferir(numeroOrigem, numeroDestino, valor);		
		
	}
	
	public static void listarPorTitular(){
		
		System.out.println("Digite o nome do titular da conta: ");
		String titular = leia.nextLine();
		
		contaController.listarPorTitular(titular);
		
	}

}
