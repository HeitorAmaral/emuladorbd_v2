package br.com.emuladorbd;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author heitor.amaral
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

	public String rua = "";
	public String numero = "";
	public String bairro = "";
	public String cidade = "";

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Endereço: Cidade: " + cidade);
		stringBuilder.append("\n\t\t    Bairro: " + bairro);
		stringBuilder.append("\n\t\t    Rua: " + rua);
		stringBuilder.append("\n\t\t    Número: " + numero + "\n\n\t");
		return stringBuilder.toString();
	}
}
