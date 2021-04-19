package lukuvinkkisovellus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import lukuvinkki_dao.LukuvinkkiDao;
import lukuvinkki_dao.TallennusDao;

public class UI {

    private Scanner reader;
    private LukuvinkkiService lukuvinkkiService;

    public UI(Scanner reader) throws FileNotFoundException, IOException, Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        String lukuvinkkiTiedosto = properties.getProperty("lukuvinkkiTiedosto");

        this.reader = reader;
        LukuvinkkiDao lukuvinkkiDao = new TallennusDao("jdbc:sqlite:lukuvinkkisovellus.db");
        this.lukuvinkkiService = new LukuvinkkiService(lukuvinkkiDao);
    }

    public void start() throws Exception {
        System.out.println("Tervetuloa lukuvinkkisovellukseen!");
        printCommands();

    }

    private void printCommands() throws Exception {
        System.out.println("Valitse allaolevista komennoista numero ja paina enter");
        while (true) {
            System.out.println("1 (lisää lukuvinkki), 2 (listaa lukuvinkit), 3 (poista lukuvinkki), 4 (kerro vinkkien määrä), tyhjä lopettaa");
            String komento = reader.nextLine();
            if (komento.equals("") || komento.equals(" ")) {
                break;
            } else if (komento.equals("1")) {
                //Tähän toteutetaan lisääminen
                System.out.println("Lisätään lukuvinkki");
                System.out.println("Anna otsikko: ");
                String otsikko = reader.nextLine();
                System.out.println("Anna url: ");
                String url = reader.nextLine();
                lukuvinkkiService.lisaaLukuvinkki(new Lukuvinkki(otsikko, url));
            } else if (komento.equals("2")) {
                //tähän toteutetaan kaikkien lukuvinkkien tulostus
                List<Lukuvinkki> lukuvinkit = lukuvinkkiService.listaaKaikki();

                if (lukuvinkit.isEmpty()) {
                    System.out.println("ei tallennettuja vinkkejä.");
                } else {
                    System.out.println("Listataan lukuvinkit");
                    lukuvinkit.stream().forEach(lv -> System.out.println(lv));
                }
            } else if (komento.equals("3")) {
                //tähän toteutetaan lukuvinkkien poistaminen
                List<Lukuvinkki> lukuvinkit = lukuvinkkiService.listaaKaikki();

                if (lukuvinkit.isEmpty()) {
                    System.out.println("Ei vielä yhtään lukuvinkkiä");
                } else {
                    System.out.println("Lukuvinkit tällä hetkellä:");

                    lukuvinkit.stream().forEach(lv -> System.out.println(lv));
                    while (true) {
                        System.out.println("Anna otsikko, jonka haluat poistaa:");
                        String otsikko = reader.nextLine();

                        lukuvinkit = lukuvinkkiService.listaaOtsikonPerusteella(otsikko);

                        lukuvinkit.stream().forEach(lv -> System.out.println(lv));
                        if (lukuvinkit.isEmpty()) {
                            System.out.println("Hakusanasi ei vastannut yhtäkään otsikkoa ohjelmassa");
                        } else {
                            break;
                        }

                    }
                    if (lukuvinkit.size() == 1) {
                        kysyPoistetaanko(lukuvinkit, reader, lukuvinkkiService);
                    } else {
                        while (lukuvinkit.size() > 1) {
                            System.out.println("Tarkenna hakusanaa, hakusana otsikolle:");
                            String otsikkotarkennus = reader.nextLine();
                            lukuvinkit = lukuvinkkiService.listaaOtsikonPerusteella(otsikkotarkennus);
                        }
                        kysyPoistetaanko(lukuvinkit, reader, lukuvinkkiService);
                    }
                }

            } else if (komento.equals("4")) {
                System.out.println("Lukuvinkkien määrä järjestelmässä yhteensä: " + lukuvinkkiService.getLukuvinkkienMaara());

            } else {
                System.out.println("Epäkelpo komento. Syötä komento uudelleen");
            }
        }

    }

    private void kysyPoistetaanko(List<Lukuvinkki> lukuvinkit, Scanner reader, LukuvinkkiService lukuvinkkiService) throws Exception {
        System.out.println("Poistetaanko: " + lukuvinkit.get(0).toString());
        System.out.println("1 poistetaan, 2 ei poisteta");
        if (reader.nextLine().equals("1")) {
            System.out.println("Oletko aivan varma? " + lukuvinkit.get(0).toString());
            System.out.println("1 kyllä, 2 ei");
            if (reader.nextLine().equals("1")) {
                lukuvinkkiService.poistaLukuvinkki(lukuvinkit.get(0));
            }
        }
    }

}
