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
		String option = "";
		Registros.checkFolder();
		List<Pessoa> pessoas = Registros.read();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (option != "6") {
			Registros.saveFile(pessoas);
			System.out.println("*************************");
			System.out.println("1 - Inserir novo registro");
			System.out.println("2 - Exibir registros salvos");
			System.out.println("3 - Editar um registro");
			System.out.println("4 - Excluir um registro");
			System.out.println("5 - Finalizar o programa");
			System.out.println("*************************\n");
			option = scanner.nextLine();
			switch (option) {
			case "1":
				pessoas.add(Registros.add());
				System.out.println("Salvo com sucesso!");
				break;

			case "2":
				System.out.println(pessoas.toString());
				break;

			case "3":
				Registros.edit(pessoas);
				break;

			case "4":
				Registros.delete(pessoas);
				break;

			case "5":
				System.out.println(
						"Aplicação finalizada e dados salvos no Arquivo!\nC:\\RegistrosEmuladorBD\\Registros.bin");
				System.exit(0);
				break;

			default:
				System.out.println("Digite novamente, a opção inserida deve ser de 1 á 5!");
			}
		}
	}
}