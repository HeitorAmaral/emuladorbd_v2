package br.com.emuladorbd;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author heitor.amaral
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

	public String nome;
	public List<Endereco> listEndereco = new ArrayList<>();
	public List<Veiculo> listVeiculo = new ArrayList<>();

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Nome: " + nome);
		stringBuilder.append("\n\t" + listEndereco.toString());
		stringBuilder.append("\n\t" + listVeiculo.toString());
		stringBuilder.append("\n\n");

		return stringBuilder.toString();
	}

}
