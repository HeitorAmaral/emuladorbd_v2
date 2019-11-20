package br.com.emuladorbd;

import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author heitor.amaral
 *
 */
public class Main {

	public static void main(String[] args) {
		int option = 0;
		Registros.checkFolder();
		List<Pessoa> pessoas = Registros.read();
		Scanner scanner = new Scanner(System.in);
		while (option != 5) {
			System.out.println("*************************");
			System.out.println("1 - Inserir novo registro");
			System.out.println("2 - Exibir registros salvos");
			System.out.println("3 - Editar um registro");
			System.out.println("4 - Excluir um registro");
			System.out.println("5 - Finalizar o programa");
			System.out.println("*************************\n");
			option = scanner.nextInt();
			switch (option) {
			case 1:
				pessoas.add(Registros.add());
				System.out.println("Salvo com sucesso!");
				break;

			case 2:
				System.out.println(pessoas.toString());
				break;

			case 3:
				Registros.edit(pessoas);
				break;

			case 4:
				Registros.delete(pessoas);
				break;

			case 5:
				Registros.saveFile(pessoas);
				System.out.println(
						"Aplica��o finalizada e dados salvos no Arquivo!\nC:\\RegistrosEmuladorBD\\Registros.bin");
				break;

			default:
				System.out.println("Digite novamente, a op��o inserida deve ser de 1 � 5!");
			}
		}
	}
}