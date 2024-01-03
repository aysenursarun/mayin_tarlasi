package mayin_tarlasi;

import java.util.Scanner;

public class main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Satır sayısını girin: ");
		int satirSayisi = scanner.nextInt();
		System.out.print("Sütun sayısını girin: ");
		int sutunSayisi = scanner.nextInt();

		// Minimum boyutta matris kontrolü
		if (satirSayisi < 2 || sutunSayisi < 2) {
			System.out.println("Geçersiz matris boyutu! Minimum 2x2 olmalıdır.");
			System.exit(0);
		}

		// Mayın Tarlası Oyunu'nun nesnesini oluştur ve oyunu başlat
		MayinTarlasiOyunu oyun = new MayinTarlasiOyunu(satirSayisi, sutunSayisi);
		oyun.oyunuBaslat();

		scanner.close();
	}
}
