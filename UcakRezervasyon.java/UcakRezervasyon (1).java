import java.util.*;

class Ucak {
    String model;
    String marka;
    String seriNo;
    int koltukKapasitesi;

    public Ucak(String model, String marka, String seriNo, int koltukKapasitesi) {
        this.model = model;
        this.marka = marka;
        this.seriNo = seriNo;
        this.koltukKapasitesi = koltukKapasitesi;
    }

    @Override
    public String toString() {
        return marka + " " + model + " (Seri No: " + seriNo + ", Kapasite: " + koltukKapasitesi + ")";
    }
}

class Lokasyon {
    String ulke;
    String sehir;
    String havaalani;
    boolean aktif;

    public Lokasyon(String ulke, String sehir, String havaalani, boolean aktif) {
        this.ulke = ulke;
        this.sehir = sehir;
        this.havaalani = havaalani;
        this.aktif = aktif;
    }

    @Override
    public String toString() {
        return havaalani + " - " + sehir + ", " + ulke + (aktif ? " (Aktif)" : " (Pasif)");
    }
}

class Ucus {
    Lokasyon lokasyon;
    String saat;
    Ucak ucak;
    int kalanKoltuk;

    public Ucus(Lokasyon lokasyon, String saat, Ucak ucak) {
        this.lokasyon = lokasyon;
        this.saat = saat;
        this.ucak = ucak;
        this.kalanKoltuk = ucak.koltukKapasitesi;
    }

    public boolean rezervasyonYap() {
        if (kalanKoltuk > 0) {
            kalanKoltuk--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return lokasyon.toString() + " | Saat: " + saat + " | Uçak: " + ucak.toString() + " | Boş Koltuk: " + kalanKoltuk;
    }
}

class Rezervasyon {
    Ucus ucus;
    String ad;
    String soyad;
    int yas;

    public Rezervasyon(Ucus ucus, String ad, String soyad, int yas) {
        this.ucus = ucus;
        this.ad = ad;
        this.soyad = soyad;
        this.yas = yas;
    }

    @Override
    public String toString() {
        return "Yolcu: " + ad + " " + soyad + ", Yaş: " + yas + " | " + ucus.toString();
    }
}

public class UcakRezervasyon {
    static Scanner scanner = new Scanner(System.in);
    static List<Ucak> ucaklar = new ArrayList<>();
    static List<Lokasyon> lokasyonlar = new ArrayList<>();
    static List<Ucus> ucuslar = new ArrayList<>();
    static List<Rezervasyon> rezervasyonlar = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Uçak Bilet Rezervasyon Sistemi ---");
            System.out.println("1. Uçak Ekle");
            System.out.println("2. Lokasyon Ekle");
            System.out.println("3. Uçuş Ekle");
            System.out.println("4. Uçuşları Listele");
            System.out.println("5. Rezervasyon Yap");
            System.out.println("6. Rezervasyonları Listele");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz: ");
            int secim = scanner.nextInt();
            scanner.nextLine();

            switch (secim) {
                case 1 -> ucakEkle();
                case 2 -> lokasyonEkle();
                case 3 -> ucusEkle();
                case 4 -> ucuslariListele();
                case 5 -> rezervasyonYap();
                case 6 -> rezervasyonlariListele();
                case 0 -> {
                    System.out.println("Çıkılıyor...");
                 return;
                }
                default -> System.out.println("Geçersiz seçim!");
            }
        }
    }

    static void ucakEkle() {
        System.out.print("Marka: ");
        String marka = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Seri No: ");
        String seriNo = scanner.nextLine();
        System.out.print("Koltuk Kapasitesi: ");
        int kapasite = scanner.nextInt();
        scanner.nextLine();

        ucaklar.add(new Ucak(model, marka, seriNo, kapasite));
        System.out.println("Uçak eklendi.");
    }

    static void lokasyonEkle() {
        System.out.print("Ülke: ");
        String ulke = scanner.nextLine();
        System.out.print("Şehir: ");
        String sehir = scanner.nextLine();
        System.out.print("Havaalanı: ");
        String havaalani = scanner.nextLine();
        System.out.print("Aktif mi? (true/false): ");
        boolean aktif = scanner.nextBoolean();
        scanner.nextLine();

        lokasyonlar.add(new Lokasyon(ulke, sehir, havaalani, aktif));
        System.out.println("Lokasyon eklendi.");
    }

    static void ucusEkle() {
        if (ucaklar.isEmpty() || lokasyonlar.isEmpty()) {
            System.out.println("Önce uçak ve lokasyon eklemelisiniz.");
            return;
        }

        System.out.println("Lokasyonlar:");
        for (int i = 0; i < lokasyonlar.size(); i++) {
            System.out.println(i + ". " + lokasyonlar.get(i));
        }
        System.out.print("Lokasyon seç: ");
        int lokIndex = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Uçaklar:");
        for (int i = 0; i < ucaklar.size(); i++) {
            System.out.println(i + ". " + ucaklar.get(i));
        }
        System.out.print("Uçak seç: ");
        int ucakIndex = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Uçuş Saati (örn. 12:00): ");
        String saat = scanner.nextLine();

        ucuslar.add(new Ucus(lokasyonlar.get(lokIndex), saat, ucaklar.get(ucakIndex)));
        System.out.println("Uçuş eklendi.");
    }

    static void ucuslariListele() {
        if (ucuslar.isEmpty()) {
            System.out.println("Tanımlı uçuş yok.");
            return;
        }

        for (int i = 0; i < ucuslar.size(); i++) {
            System.out.println(i + ". " + ucuslar.get(i));
        }
    }

    static void rezervasyonYap() {
        ucuslariListele();
        if (ucuslar.isEmpty()) return;

        System.out.print("Uçuş seç (numara): ");
        int ucusIndex = scanner.nextInt();
        scanner.nextLine();

        Ucus secilenUcus = ucuslar.get(ucusIndex);
        if (secilenUcus.kalanKoltuk == 0) {
            System.out.println("Bu uçuşta yer kalmadı.");
            return;
        }

        System.out.print("Ad: ");
        String ad = scanner.nextLine();
        System.out.print("Soyad: ");
        String soyad = scanner.nextLine();
        System.out.print("Yaş: ");
        int yas = scanner.nextInt();
        scanner.nextLine();

        if (secilenUcus.rezervasyonYap()) {
            Rezervasyon rez = new Rezervasyon(secilenUcus, ad, soyad, yas);
            rezervasyonlar.add(rez);
            System.out.println("Rezervasyon başarıyla yapıldı.");
        } else {
            System.out.println("Koltuk bulunamadı.");
        }
    }

    static void rezervasyonlariListele() {
        if (rezervasyonlar.isEmpty()) {
            System.out.println("Henüz rezervasyon yapılmadı.");
            return;
        }

        for (Rezervasyon r : rezervasyonlar) {
            System.out.println(r);
        }
    }
}