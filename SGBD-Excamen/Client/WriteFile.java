package file;
import java.io.*;
import relation.Relation;

public class WriteFile{
	public void write(Relation ob) throws Exception{
		File fichier = new File(""+ob.getNom());
		FileOutputStream fos = new FileOutputStream(fichier);
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
        Object on = (Object)ob;
	    oos.writeObject(on);
	    oos.close();
	}
	// public void ajouter(Relation v) throws Exception{
	// 	File fichier = new File(""+v.getNom());
    //     Relation op = null;
	// 	if(fichier.exists()){
	// 		ReadFile fe = new ReadFile();
	// 		op = (Relation)fe.read(fichier);
	// 	}
	// 	liste.add(v);
	// 	this.write(liste);
	// }
	// public void delete(Vector ob,int index) throws Exception{
	// 	Vector object = Fonction.supprimer(ob,index);
	// 	this.write(object);
	// }
}