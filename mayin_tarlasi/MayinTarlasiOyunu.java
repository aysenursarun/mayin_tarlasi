package mayin_tarlasi;

import java.util.Random;
import java.util.Scanner;

public class MayinTarlasiOyunu {

	private char[][] mayinAlani;   // Mayınların bulunduğu alan
    private char[][] oyuncuAlani;  // Oyuncunun gördüğü alan
    private int satirSayisi;       // Matrisin satır sayısı
    private int sutunSayisi;       // Matrisin sütun sayısı
    private int mayinSayisi;       // Mayın sayısı

	// Mayın Tarlası Oyunu sınıfının constructor metodu
    public MayinTarlasiOyunu(int satirSayisi, int sutunSayisi) {
        this.satirSayisi = satirSayisi;
        this.sutunSayisi = sutunSayisi;
        this.mayinAlani = new char[satirSayisi][sutunSayisi];
        this.oyuncuAlani = new char[satirSayisi][sutunSayisi];
        this.mayinSayisi = satirSayisi * sutunSayisi / 4; // Mayın sayısı elemanSayisi / 4 kadar olmalı

        mayinAlaniOlustur();  // Mayın alanını oluşturan metot
        oyuncuAlaniOlustur(); // Oyuncu alanını oluşturan metot
    }

    // Mayın alanını rastgele yerleştiren metot
    private void mayinAlaniOlustur() {
        Random random = new Random();
        for (int i = 0; i < mayinSayisi; i++) {
            int satir = random.nextInt(satirSayisi);
            int sutun = random.nextInt(sutunSayisi);
            if (mayinAlani[satir][sutun] == '*') {
                i--; // Eğer bu konum zaten bir mayına aitse, tekrar seç
            } else {
                mayinAlani[satir][sutun] = '*';
            }
        }
    }

    // Oyuncu alanını "-" ile dolduran metot
    private void oyuncuAlaniOlustur() {
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                oyuncuAlani[i][j] = '-';
            }
        }
    }

    // Oyuncu alanını ekrana yazdıran metot
    private void oyuncuAlaniYazdir() {
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                System.out.print(oyuncuAlani[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Koordinatların geçerliliğini kontrol eden metot
    private boolean gecerliKoordinat(int satir, int sutun) {
        return satir >= 0 && satir < satirSayisi && sutun >= 0 && sutun < sutunSayisi;
    }

    // Seçilen koordinatta mayın olup olmadığını kontrol eden metot
    private boolean mayinVarMi(int satir, int sutun) {
        return mayinAlani[satir][sutun] == '*';
    }

    // Seçilen koordinatın etrafındaki mayın sayısını bulan metot
    private int etrafindakiMayinSayisi(int satir, int sutun) {
        int sayac = 0;
        for (int i = Math.max(0, satir - 1); i <= Math.min(satir + 1, satirSayisi - 1); i++) {
            for (int j = Math.max(0, sutun - 1); j <= Math.min(sutun + 1, sutunSayisi - 1); j++) {
                if (gecerliKoordinat(i, j) && mayinVarMi(i, j)) {
                    sayac++;
                }
            }
        }
        return sayac;
    }

    // Seçilen koordinatı açan metot
    private void hucreyiAc(int satir, int sutun) {
        if (gecerliKoordinat(satir, sutun) && oyuncuAlani[satir][sutun] == '-') {
            if (mayinVarMi(satir, sutun)) {
                System.out.println("Oyunu kaybettiniz! Mayına bastınız.");
                mayinAlaniYazdir();
                System.exit(0);
            } else {
                int etrafindakiMayinlar = etrafindakiMayinSayisi(satir, sutun);
                oyuncuAlani[satir][sutun] = (char) (etrafindakiMayinlar + '0');
                if (etrafindakiMayinlar == 0) {
                    for (int i = Math.max(0, satir - 1); i <= Math.min(satir + 1, satirSayisi - 1); i++) {
                        for (int j = Math.max(0, sutun - 1); j <= Math.min(sutun + 1, sutunSayisi - 1); j++) {
                            hucreyiAc(i, j);
                        }
                    }
                }
            }
        }
    }

    // Mayın alanını ekrana yazdıran metot (test amaçlı)
    private void mayinAlaniYazdir() {
        System.out.println("Mayın Alanı:");
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                System.out.print(mayinAlani[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Oyunu başlatan metot
    public void oyunuBaslat() {
        Scanner scanner = new Scanner(System.in);
        int kalanHucreler = satirSayisi * sutunSayisi - mayinSayisi;

        while (kalanHucreler > 0) {
            System.out.println("Kalan hücre sayısı: " + kalanHucreler);
            oyuncuAlaniYazdir();

            System.out.print("Satır girin: ");
            int satir = scanner.nextInt();
            System.out.print("Sütun girin: ");
            int sutun = scanner.nextInt();

            if (!gecerliKoordinat(satir, sutun)) {
                System.out.println("Geçersiz koordinat! Tekrar girin.");
                continue;
            }

            if (oyuncuAlani[satir][sutun] != '-') {
                System.out.println("Bu koordinat daha önce seçildi. Başka bir koordinat girin.");
                continue;
            }

            hucreyiAc(satir, sutun);
            kalanHucreler--;

            if (kalanHucreler == 0) {
                System.out.println("Tebrikler! Oyunu kazandınız. Tüm hücreler açıldı.");
            }
        }

        scanner.close();
    }
}
