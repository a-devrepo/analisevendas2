package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import model.entities.Sale;

public class Program {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Locale.setDefault(Locale.US);

		System.out.println("Entre o caminho do arquivo: ");
		String path = sc.nextLine();

		Map<String, List<Sale>> salesMap = new HashMap<String, List<Sale>>();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();

			while (line != null) {
				String[] fields = line.split(",");
				Integer month = Integer.parseInt(fields[0]);
				Integer year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				Integer items = Integer.parseInt(fields[3]);
				Double total = Double.parseDouble(fields[4]);
				Sale sale = new Sale(month, year, seller, items, total);

				if (!salesMap.containsKey(seller)) {
					salesMap.put(seller, new ArrayList<Sale>(Arrays.asList(sale)));
				} else {
					salesMap.get(seller).add(sale);
				}
				line = br.readLine();
			}

			Map<String, Double> result = new HashMap<String, Double>();
			salesMap.forEach((k, v) -> result.put(k, v.stream().mapToDouble(s -> s.getTotal()).sum()));
			
			System.out.printf("%n%nTotal de vendas por vendedor: ");
			for(String key: result.keySet()) {
				System.out.printf("%n%s - R$ %.2f",key,result.get(key));
			}

		} catch (IOException e) {
			System.out.printf("Erro: %s (O sistema não pode encontrar o arquivo especificado)", path);
		} catch (NumberFormatException e) {
			System.out.printf("Erro: %s", e.getMessage());
		} finally {
			sc.close();
		}
	}
}
