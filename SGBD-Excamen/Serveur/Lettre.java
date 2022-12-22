package lang;
import java.util.Vector;

public class Lettre{

///fonction concat
    public static String concat(String[] tab){
        String req = "";
        for(int i=0;i<tab.length;i++){
            req+= tab[i]+" ";
        }
        return req;
    }

///fonction split
    public static String split(String requette,String mot){
        String[] tab = requette.split(mot);
        String vao = "";
        for(int i=0;i<tab.length;i++){
			vao = vao.concat(tab[i]);
		}
        return vao;
    }

///fonction qui donne la place d'un mot dans une requette
    public static int getIndexString(String requette,String mot){
        String[] tab = requette.split(" ");
        int a = -1;
        for(int i=0;i<tab.length;i++){
            if(tab[i].equals(mot)){
                a = i;
            }
        }
        return a;
    }

///Fonction qui retourne les phrases entre a et b
    public static String phraseEntre(String re,int a,int b){
        String phrase = "";
        String[] req = re.split(" ");
        for(int i = a+1;i<b;i++){
            phrase+=req[i];
        }
        return phrase;
    }

///Fonction qui retourne les phrases entre a et b
    public static String phraseEntreAvec(String re,int a,int b){
        String phrase = "";
        String[] req = re.split(" ");
        for(int i = a+1;i<b;i++){
            phrase+=req[i]+" ";
        }
        return phrase;
    }

///retourne les attributs demander dans une requette
    public static String[] getAttribu(String requette){
        requette = requette.toLowerCase();
        int debut = Lettre.getIndexString(requette,"select");
        int last = Lettre.getIndexString(requette,"from");
        String req = Lettre.phraseEntre(requette,debut,last);
        String[] liste = req.split(",");
        return liste;
    }

    public static String[] getAttribuProjection(String requette){
        requette = requette.toLowerCase();
        int debut = Lettre.getIndexString(requette,"distinct");
        int last = Lettre.getIndexString(requette,"from");
        String req = Lettre.phraseEntre(requette,debut,last);
        String[] liste = req.split(",");
        return liste;
    }

    public static String lookAttribu(String requette){  
        String[] tab = requette.split(" ");
        requette = requette.replace(';',' ');
        String vao = Lettre.phraseEntre(requette,1,tab.length);
        vao = vao.replace('[', ' ');
        vao = vao.replace(']', ' ');
        return vao;
    }

    public static String[] toTable(Vector<String> ls){
        String[] list = new String[ls.size()];
        for(int i=0;i<ls.size();i++){
            list[i] = ls.get(i);
        }
        return list;
    }
}