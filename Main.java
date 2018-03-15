package edytafraszczak;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    public static String generujPesel(int rok, int miesiac, int dzien) {
        String pesel = "";
        pesel+= rok / 10 % 10;
        pesel+= rok % 10;
        pesel+= miesiac / 10;
        pesel+= miesiac % 10;
        pesel+= dzien / 10;
        pesel+= dzien % 10;
        //z zadania wynika ze koncowka zawsze jest 12345
        pesel += "12345";
        return pesel;
    }

    public static String[] generujePesele(int liczbaDoWygenerowania){
        Random r = new Random();
        String[] pesele = new String[liczbaDoWygenerowania];
        for(int i =0;i<liczbaDoWygenerowania;i++){
            int miesiac = r.nextInt(12) + 1;
            int zakresDniDlaMiesiaca = 30;
            if(miesiac == 2){
                //luty
                zakresDniDlaMiesiaca = 28;
            }
            else if(miesiac == 1 || miesiac == 3 || miesiac == 5 ||miesiac == 7 || miesiac == 8 || miesiac == 10 || miesiac == 12){
                zakresDniDlaMiesiaca = 31;
            }
            int roznicaLat = 1999 - 1922;
            pesele[i] = generujPesel(r.nextInt(roznicaLat) + 1922 + 1, miesiac, r.nextInt(zakresDniDlaMiesiaca) + 1);
        }
        return pesele;
    }

    public static String[] wczytajImionaKobiet() throws FileNotFoundException {

        Scanner in = new Scanner(new File("imiona.txt"));
        int liczbaLiniWpliku = 0;
        while(in.hasNextLine()){
            in.nextLine();
            liczbaLiniWpliku++;
        }
        in.close();
        in = new Scanner(new File("imiona.txt"));
        String [] imiona = new String[liczbaLiniWpliku];
        int ostaniWolny = 0;
        while(in.hasNextLine()){
            String linia = in.nextLine();
            if(linia.toLowerCase().endsWith("a") && linia.toLowerCase() != "Jarema"){
                imiona[ostaniWolny++] = linia;
            }
        }
        in.close();
        return imiona;
    }
    public static String[]  wczytajNazwiskaKobiet() throws FileNotFoundException {


        Scanner in = new Scanner(new File("nazwiska.txt"));
        int liczbaLiniWpliku = 0;
        while(in.hasNextLine()){
            in.nextLine();
            liczbaLiniWpliku++;
        }
        in.close();
        in = new Scanner(new File("imiona.txt"));
        String [] nazwiska = new String[liczbaLiniWpliku];

        in = new Scanner(new File("nazwiska.txt"));
        int ostaniWolny = 0;
        while(in.hasNextLine()){
            String linia = in.nextLine();
            //podziel linie po spacji
            String[] podzielonaLinia = linia.split(" ");
            //nazwisko na 2 miejscu
            nazwiska[ostaniWolny++] = podzielonaLinia[1].replace("-","");
        }
        in.close();
        return nazwiska;
    }

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Uzytkowniku podaj liczbe osob do wygenerowania");
        Scanner in = new Scanner(System.in);
        int liczbaOsob = in.nextInt();
        in.close();
        if(liczbaOsob > 100 || liczbaOsob < 1){
            System.out.println("Uzytkowniku podano bledna liczbe");
        }else{
            String[] imiona = wczytajImionaKobiet();
            String[] nazwiska = wczytajNazwiskaKobiet();
            String[] pesele = generujePesele(liczbaOsob);
            int ostatniIndeksImieniaKobiecego = 0;
            for(int i =0;i<imiona.length;i++){
                if(imiona[i] != null){
                    ostatniIndeksImieniaKobiecego = i;
                }
            }

            Random r = new Random();
            File plikSzpieg = new File("szpieg.txt");
            PrintWriter writter = new PrintWriter(plikSzpieg);
            for(int i =0;i<liczbaOsob;i++){
                String imie = imiona[r.nextInt(ostatniIndeksImieniaKobiecego + 1)];
                String nazwisko = nazwiska[r.nextInt(nazwiska.length)];
                String pesel = pesele[i];
                writter.println(imie + " " + nazwisko + " " + pesel);
            }
            writter.flush();
            writter.close();
            System.out.println("Uzytkowniku dane wygenerowane");
        }
    }
}
