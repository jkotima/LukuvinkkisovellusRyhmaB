package lukuvinkki_dao;

import java.util.List;
import lukuvinkkisovellus.Kirja;
import lukuvinkkisovellus.Linkki;
import lukuvinkkisovellus.Lukuvinkki;

public interface LukuvinkkiDao {

    List<Lukuvinkki> listaaKaikki();
    
    List<Lukuvinkki> listaaKirjat();

    void lisaaLinkki(Linkki lukuvinkki) throws Exception;
    
    void lisaaKirja(Kirja lukuvinkki) throws Exception;
    
    int LukuvinkkienMaara();
    
    int KirjojenLukumaara();
    
    void poistaLinkki(Linkki lukuvinkki) throws Exception;
    
    void tyhjennaTietokanta() throws Exception;
    
    void tuoTiedostosta(String tiedosto) throws Exception;
    
    void vieTiedostoon() throws Exception;
}
