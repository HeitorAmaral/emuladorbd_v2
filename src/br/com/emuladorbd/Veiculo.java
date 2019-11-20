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
public class Veiculo {

	public String modelo = "";
	public String placa = "";

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Veículo: Modelo: " + modelo);
		stringBuilder.append("\n\t\t  Placa: " + placa.toUpperCase() + "\n\n\t");
		return stringBuilder.toString();
	}
}
