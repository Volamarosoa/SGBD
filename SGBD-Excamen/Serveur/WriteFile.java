package file;
import java.io.*;
import relation.Relation;

public class WriteFile{
	public void write(Relation ob) throws Exception{
		File fichier = new File("tables/"+ob.getNom());
		FileOutputStream fos = new FileOutputStream(fichier);
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
        Object on = (Object)ob;
	    oos.writeObject(on);
	    oos.close();
	}

}