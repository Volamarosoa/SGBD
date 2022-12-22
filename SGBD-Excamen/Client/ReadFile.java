package file;
import java.io.*;
import relation.Relation;
// import java.lang.reflect.*;
// import operation.Fonction;
public class ReadFile {

	public Relation read(File fichier) throws Exception{
		FileInputStream fis = new FileInputStream(fichier);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object object = ois.readObject();
		Relation relation = (Relation) object;
      	ois.close();
      	return relation;
	}

	

}

