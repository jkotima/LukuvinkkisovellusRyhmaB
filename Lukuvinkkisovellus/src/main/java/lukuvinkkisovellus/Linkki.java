package lukuvinkkisovellus;

public class Linkki implements Lukuvinkki {

    String otsikko;
    String url;

    public Linkki(String otsikko, String url) {
        this.otsikko = otsikko;
        this.url = url;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public String getUrl() {
        return url;
    }
    
    @Override
    public String toString() {
        return "Vinkin otsikko: "  + otsikko + ", vinkin linkki: " + url;
    }

    

}