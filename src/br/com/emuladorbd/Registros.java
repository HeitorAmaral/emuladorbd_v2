package br.com.emuladorbd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author heitor.amaral
 *
 */
public class Registros {
	static Scanner scanner = new Scanner(System.in);

	public static Pessoa add() {
		System.out.println("\n**********************************************");
		System.out.println("MODO INSERÇÃO");
		System.out.println("**********************************************\n");
		String itemSelecionado = "";
		Pessoa pessoa = new Pessoa();
		System.out.println("Insira o nome da pessoa (No máximo 50 caracteres): ");
		while (true) {
			pessoa.nome = scanner.nextLine();
			if (scanner.nextLine().length() <= 50) {
				break;
			} else {
				System.out.println("AVISO - Insira o nome da pessoa com no máximo 50 caracteres: ");
			}
		}

		System.out.println("\n****Cadastro de Endereço****\n");

		itemSelecionado = "";
		while (!itemSelecionado.equalsIgnoreCase("n")) {
			Endereco endereco = new Endereco();

			System.out.println("Insira a CIDADE: ");
			endereco.cidade = scanner.nextLine();

			System.out.println("Insira o BAIRRO: ");
			endereco.bairro = scanner.nextLine();

			System.out.println("Insira a RUA: ");
			endereco.rua = scanner.nextLine();

			System.out.println("Insira o NÚMERO do endereço: ");
			endereco.numero = scanner.nextLine();

			System.out.println("Dados de ENDEREÇO completos! Deseja cadastrar um novo endereço para o " + pessoa.nome
					+ "? (N para NÃO, S para SIM)");
			itemSelecionado = scanner.nextLine();

			pessoa.listEndereco.add(endereco);
		}

		System.out.println("\n****Cadastro de Veículo****\n");

		Veiculo veiculo;
		itemSelecionado = "";
		while (!itemSelecionado.equalsIgnoreCase("n")) {
			veiculo = new Veiculo();
			System.out.println("Insira o MODELO: ");
			veiculo.modelo = scanner.nextLine();
			System.out.println("Insira a PLACA: ");
			veiculo.placa = scanner.nextLine();
			System.out.println("Dados de VEÍCULO completos! Deseja cadastrar um novo veículo para o " + pessoa.nome
					+ "? (N para NÃO, S para SIM)");
			itemSelecionado = scanner.nextLine();

			pessoa.listVeiculo.add(veiculo);
		}
		return pessoa;
	}

	public static List<Pessoa> read() {
		List<Pessoa> listPessoa = new ArrayList<Pessoa>();
		Pessoa pessoa = null;
		Endereco endereco = null;
		Veiculo veiculo = null;
		File file = new File("C:\\RegistrosEmuladorBD\\Registros.bin");
		try (InputStream inputStream = new FileInputStream(file)) {
			int content;
			/*
			 * controla qual o dado que esta sendo manipulado (& @ # $ %) # = separação de
			 * dados do mesmo tipo $ = inicia um bloco com dados de endereco % = inicia um
			 * bloco com dados de veiculo & = separação de pessoa
			 */
			String controle = "";
			String dado = "";
			int contadorEndereco = 0;
			int contadorVeiculo = 0;

			while ((content = inputStream.read()) != -1) {
				switch ((char) content) {

				case '&':
					if (veiculo != null) {
						pessoa.listVeiculo.add(veiculo);
						veiculo = null;
					}
					if (pessoa != null) {
						listPessoa.add(pessoa);
					}
					pessoa = new Pessoa();
					controle = "&";
					break;

				case '$':

					if (pessoa.nome == null) {
						pessoa.nome = dado;
						dado = "";
					}
					controle = "$";
					contadorEndereco = 0;
					if (endereco != null) {
						pessoa.listEndereco.add(endereco);
					}
					endereco = new Endereco();

					break;

				case '%':
					controle = "%";
					if (endereco != null) {
						pessoa.listEndereco.add(endereco);
						endereco = null;
					}
					if (veiculo != null) {
						pessoa.listVeiculo.add(veiculo);
					}
					veiculo = new Veiculo();
					contadorVeiculo = 0;

					break;

				}

				switch (controle) {
				case "&":
					if ((char) content == '&') {
					} else {
						dado += String.valueOf((char) content);

					}
					break;

				case "$":
					if ((char) content == '$') {

					} else {
						if ((char) content == '#') {
							contadorEndereco++;
						}
						/*
						 * SE contador endereço for ==0 eu sei que esta inserindo rua SE contador
						 * endereço for ==1 eu sei que esta inserindo numero SE contador endereço for
						 * ==2 eu sei que esta inserindo bairro SE contador endereço for ==3 eu sei que
						 * esta inserindo cidade
						 */
						if (contadorEndereco == 0) {
							if ((char) content != '#')
								endereco.rua += String.valueOf((char) content);
						}
						if (contadorEndereco == 1) {
							if ((char) content != '#')
								endereco.numero += String.valueOf((char) content);
						}
						if (contadorEndereco == 2) {
							if ((char) content != '#')
								endereco.bairro += String.valueOf((char) content);
						}
						if (contadorEndereco == 3) {
							if ((char) content != '#')
								endereco.cidade += String.valueOf((char) content);
						}

					}
					break;

				case "%":
					if ((char) content == '%') {

					} else {
						if ((char) content == '#') {
							contadorVeiculo++;
						}
						/*
						 * SE contador veiculo for ==1 eu sei que esta inserindo modelo SE contador
						 * veiculo for ==2 eu sei que esta inserindo placa
						 */
						if (contadorVeiculo == 0) {
							if ((char) content != '#')
								veiculo.modelo += String.valueOf((char) content);
						}
						if (contadorVeiculo == 1) {
							if ((char) content != '#')
								veiculo.placa += String.valueOf((char) content);
						}

					}
					break;

				}

			}
			if ((content = inputStream.read()) != -1) {
			} else {
				pessoa.listVeiculo.add(veiculo);
				listPessoa.add(pessoa);
			}

		} catch (IOException ex) {
			System.out.println("AVISO - Provável arquivo vázio!\n");
		} catch (NullPointerException nullPointer) {
			System.out.println("AVISO - Provável arquivo vázio!\n");
			listPessoa = new ArrayList<Pessoa>();
		}

		return listPessoa;

	}

	public static void saveFile(List<Pessoa> listPessoa) {

		/*
		 * # = separação de dados do mesmo tipo $ = inicia um bloco com dados de
		 * endereco % = inicia um bloco com dados de veiculo & = separação de pessoa
		 */
		List<Endereco> listEndereco;
		List<Veiculo> listVeiculo;
		File file = new File("C:\\RegistrosEmuladorBD\\Registros.bin");
		try (OutputStream outputStream = new FileOutputStream(file)) {
			for (Pessoa p : listPessoa) {
				listEndereco = new ArrayList<>();
				listVeiculo = new ArrayList<>();

				outputStream.write("&".getBytes());
				outputStream.write(p.nome.getBytes());
				listEndereco = p.listEndereco;
				for (Endereco endereco : listEndereco) {
					outputStream.write("$".getBytes());
					outputStream.write(endereco.rua.getBytes());
					outputStream.write("#".getBytes());
					outputStream.write(endereco.numero.getBytes());
					outputStream.write("#".getBytes());
					outputStream.write(endereco.bairro.getBytes());
					outputStream.write("#".getBytes());
					outputStream.write(endereco.cidade.getBytes());
				}
				listVeiculo = p.listVeiculo;
				for (Veiculo veiculo : listVeiculo) {
					outputStream.write("%".getBytes());
					outputStream.write(veiculo.modelo.getBytes());
					outputStream.write("#".getBytes());
					outputStream.write(veiculo.placa.getBytes());

				}
				outputStream.flush();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static void edit(List<Pessoa> listPessoa) {
		if (listPessoa == null || listPessoa.isEmpty()) {
			System.out.println(
					"AVISO - Não existe nenhum registro cadastrado no sistema ainda! Tente novamente após inserir algum cadastro!\n");

		} else {
			System.out.println(read());
			System.out.println("\n**********************************************");
			System.out.println("MODO EDIÇÃO");
			System.out.println("**********************************************\n");
			String nome = "";
			System.out.println("Insira o nome da pessoa que você deseja alterar: ");
			nome = scanner.nextLine();
			int option = 0;
			for (Pessoa pessoa : listPessoa) {
				if (pessoa.nome.equals(nome)) {
					System.out.println("**********************************************");
					System.out.println("Dados da pessoa escolhida");
					System.out.println("----------------------------------------------");
					System.out.println(pessoa.toString());
					System.out.println("**********************************************");
					System.out.println("\nQual dos itens deseja editar?");
					System.out.println("1 - Nome da Pessoa");
					System.out.println("2 - Endereço da Pessoa");
					System.out.println("3 - Veículo da Pessoa");
					option = scanner.nextInt();
					scanner.nextLine();
					String itemSelecionado;
					switch (option) {
					case 1:
						System.out.println("\nInsira o novo NOME da Pessoa: ");
						pessoa.nome = scanner.nextLine();
						System.out.println("Alteração realizada com sucesso!");
						System.out.println(pessoa.toString());
						break;

					case 2:
						System.out.println("**********************************************");
						System.out.println("Novos dados de Endereço a serem inseridos");
						System.out.println("**********************************************");
						pessoa.listEndereco.clear();
						;
						itemSelecionado = "";
						Endereco endereco;
						while (!itemSelecionado.equalsIgnoreCase("n")) {
							endereco = new Endereco();

							System.out.println("\nInsira a nova CIDADE: ");
							endereco.cidade = scanner.nextLine();

							System.out.println("Insira o novo BAIRRO: ");
							endereco.bairro = scanner.nextLine();

							System.out.println("Insira a nova RUA: ");
							endereco.rua = scanner.nextLine();

							System.out.println("Insira o novo NÚMERO do endereço: ");
							endereco.numero = scanner.nextLine();

							System.out.println(
									"Dados do novo ENDEREÇO completos! Deseja cadastrar um novo endereço para o "
											+ pessoa.nome + "? (N para NÃO, S para SIM)");
							itemSelecionado = scanner.nextLine();

							pessoa.listEndereco.add(endereco);
						}
						break;

					case 3:
						System.out.println("**********************************************");
						System.out.println("Novos dados de Veículo a serem inseridos.");
						System.out.println("**********************************************");
						pessoa.listVeiculo.clear();
						;
						itemSelecionado = "";
						Veiculo veiculo;
						while (!itemSelecionado.equalsIgnoreCase("n")) {
							veiculo = new Veiculo();
							System.out.println("\nInsira o novo MODELO: ");
							veiculo.modelo = scanner.nextLine();
							System.out.println("Insira a nova PLACA: ");
							veiculo.placa = scanner.nextLine();
							System.out
									.println("Dados do novo VEÍCULO completos! Deseja cadastrar um novo veículo para o "
											+ pessoa.nome + "? (N para NÃO, S para SIM)");
							itemSelecionado = scanner.nextLine();

							pessoa.listVeiculo.add(veiculo);
							System.out.println("Registro Removido!\n");
						}
						break;
					default:
						System.out.println("Digite novamente, a opção inserida deve ser de 1 á 3!");
					}
				} else {
					System.out.println(
							"AVISO - Não existe nenhum registro cadastrado com este nome! Tente novamente primeiro confirmando o nome a ser editado!\n");
				}
			}
		}
	}

	public static void delete(List<Pessoa> listPessoa) {
		if (listPessoa == null || listPessoa.isEmpty()) {
			System.out.println(
					"AVISO - Não existe nenhum registro cadastrado no sistema ainda! Tente novamente após inserir algum cadastro!\n");

		} else {
			System.out.println(read());
			System.out.println("\n**********************************************");
			System.out.println("MODO EXCLUSÃO");
			System.out.println("**********************************************\n");
			String nome = "";
			System.out.println("Insira o Nome da Pessoa que deve ter o registro removido:");
			nome = scanner.nextLine();
			Pessoa p = null;
			for (Pessoa pessoa : listPessoa) {
				if (pessoa.nome.equals(nome)) {
					p = pessoa;
				}
			}
			if (p != null) {
				listPessoa.remove(p);
				System.out.println("Registro Removido!\n");
			} else {
				System.out.println(
						"Não existe nenhum registro cadastrado com este nome! Tente novamente primeiro confirmando o nome a ser excluído!\n");
			}
		}
	}

	public static void checkFolder() {
		File diretorio = new File("C:\\RegistrosEmuladorBD");
		File file = new File("C:\\RegistrosEmuladorBD\\Registros.bin");

		if (!diretorio.exists()) {
			diretorio.mkdirs();
		} else {
			System.out.println("Diretório C:\\RegistrosEmuladorBD já existente");
		}
	}
}
