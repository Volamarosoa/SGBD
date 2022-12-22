package relation;
import java.util.Vector;  
import java.io.*;
import java.net.SocketImpl;
import file.*;

import lang.Lettre;


public class Relation implements Serializable{
	String nom;
	Vector<String> listeColonnes = new Vector();
	Vector<Vector<String>> donnees = new Vector<Vector<String>>();
	ObjectOutputStream oos;
	ObjectInputStream ois;

    public Relation(){ }

	public Relation(String table,Vector<String> ls,Vector<Vector<String>> don){
		this.setNom(table);
		this.setListeColonnes(ls);
		this.setDonnees(don);
	}

	public Relation(ObjectOutputStream os,ObjectInputStream ois){
		this.setOos(os);
		this.setOis(ois);
	}

	public String getNom(){
		return this.nom;
	}

	public void setNom(String n){
		this.nom = n;	
	}

	public Vector<String> getListeColonnes(){
		return this.listeColonnes;
	}

	public void setListeColonnes(Vector<String> ls){
		this.listeColonnes = ls;
	}

	public Vector<Vector<String>> getDonnees(){
		return this.donnees;
	}

	public void setDonnees(Vector<Vector<String>> d){
		this.donnees = d;
	}

	public ObjectOutputStream getOos() {
		return this.oos;
	}

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

	public ObjectInputStream getOis() {
		return this.ois;
	}

	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}

///selection de tous les attibuts du tableau

	public void printTable() {
        int countCol = this.getListeColonnes().size();
        for (int j = 0; j < countCol; j++) {
            for (int i = 0; i < 15; i++) {
                System.out.print("-");
            }
            System.out.print("+");
        }
        System.out.println();

        for (String colonne : this.getListeColonnes()) {
            System.out.print(colonne);
            for (int i = colonne.length(); i < 15; i++) { System.out.print(" "); }
            System.out.print("|");
        }
        System.out.println();
        for (int j = 0; j < countCol; j++) {
            for (int i = 0; i < 15; i++) {
                System.out.print("-");
            }
            System.out.print("+");
        }
        System.out.println();
        for (Vector<String> donnes : getDonnees()) {
            for (Object donne : donnes) {
                if (donne==null) {donne="null";}
                System.out.print(String.valueOf(donne));
                for (int i = String.valueOf(donne).length(); i < 15; i++) {
                    System.out.print(" ");
                }
                System.out.print("|");
            }
            System.out.println();
			for(String donne : donnes) {
				for (int i = 0; i < 15; i++) {
					System.out.print("-");
				}
				System.out.print("+");
				// System.out.println();
			}
			System.out.println();
		}
    }

///selection du table en specifiant l'attibu
	public Relation printTable(String[] attibu) throws Exception{	
		Relation relation = new Relation();
		if(attibu[0].equals("*")){	//on regarde si toutes les attibus tables sont demandes
			relation = this;
		}
		else{
			Vector<String> attribu = new Vector<String>(); 
			Vector<Integer> isa = new Vector();
			for(int i = 0; i<this.getListeColonnes().size();i++){
				for(int p=0;p<attibu.length;p++){
					if(this.getListeColonnes().get(i).toLowerCase().equals(attibu[p].toLowerCase())){	//on regarde les attibuts demander par le client 
						isa.add(i);
						attribu.add(this.getListeColonnes().get(i));
					}
				}
			}
			this.test(isa.size());
			Vector<Vector<String>> nouveauDossier = new Vector<Vector<String>>();
			for(int j = 0;j<this.getDonnees().size();j++){
				Vector<String> d = new Vector<String>();
				for(int k=0;k<this.getDonnees().get(j).size();k++){
					for(int i = 0;i<isa.size();i++){
						if(k==isa.get(i)){
							d.add(this.getDonnees().get(j).get(k));
						}
					}	
				}
				nouveauDossier.add(d);		
			}	
			relation.setListeColonnes(attribu);
			relation.setDonnees(nouveauDossier);	
		}
		return relation;
	}
///On entre la requette
	public void requette(String req) throws Exception{
		req = req.toLowerCase();
		try{
			if(req.equals("")){
				// this.getOos().writeObject("Erreur: Veuillez ecrire une requette");
				throw new Exception("Veuillez ecrire une requette");
			}
			if(req.contains(";")){
				if(req.contains("create table")){
					this.createTable(req);
				}
				else if(req.contains("insert into")){
					this.insert(req);
				}
				else if(req.contains("select")){
					this.select(req);
				}
				else{
					throw new Exception("Verifier votre syntaxe, commande meconnaissable");
				}
			}
			else{
				throw new Exception("Verifier votre syntaxe, ';' manquante a la derniere ligne");
			}
		}
		catch(Exception io){
			this.getOos().writeObject("Erreur: "+io.getMessage());
			System.out.println("Erreur: "+io.getMessage());
		}
		
	}

///fonction qui test si les attributs demandees sont des colones du tableau
	public void test(int a) throws Exception{
		if(a==0){
			throw new Exception("Attribu innexistant, verifier votre syntaxe");
		}
	}

///fonction qui regarde les conditions dans une requette
	public void condition(String requette){
		Vector<String> listeCondition = new Vector();
		requette = requette.toLowerCase();
		int indexWhere = Lettre.getIndexString(requette,"where");
		String[] tab = requette.split(" ");
		String condition = Lettre.phraseEntre(requette,indexWhere,tab.length);
		System.out.println(condition);
		//String[] ls = condition.split()
	}

///fonction pour creer un table	
	public void createTable(String requette) throws Exception{
		String[] tab = requette.split(" ");
		if(tab[0].equals("create") && tab[1].equals("table") && requette.contains("[") && requette.contains("]")){
			String vao = Lettre.lookAttribu(requette);
			tab = vao.split(" ");
			vao = Lettre.concat(tab);
			String nomTable = tab[0];
			if(this.tableExist(nomTable)==false){
				vao = Lettre.split(vao,nomTable);
				vao = Lettre.split(vao," ");
				tab = vao.split(",");
				Relation relation = new Relation();
				relation.setNom(nomTable);
				relation.addAttribu(tab);
				WriteFile write = new WriteFile();
				write.write(relation);
				System.out.println("Table creer");
				this.getOos().writeObject("Table creer");
			}
			else{	
				throw new Exception("Ce Table existe deja"); 
			}
			
		}
	}

///Ajouter les colones dans un table
	public void addAttribu(String[] tab){
		for(int i=0;i<tab.length;i++){
			this.getListeColonnes().add(tab[i]);
		}
	}

///Ajouter des donnnes dans les colones dans un table
	public void addDonnee(String[] tab) throws Exception{
		if(this.getListeColonnes().size()==tab.length){
			Vector<String> donnees = new Vector();
			for(int i=0;i<tab.length;i++){
				donnees.add(tab[i]);
			}
			this.getDonnees().add(donnees);
		}
		else{ 
			throw new Exception("Colone(s) manquante(s)"); 
		}
	}

///fonction pour inserer des resultats
	public void insert(String requette) throws Exception{
		String[] tab = requette.split(" ");
		if(tab[0].equals("insert") && tab[1].equals("into") && requette.contains("[") && requette.contains("]")){
			String vao = Lettre.lookAttribu(requette);
			tab = vao.split(" ");
			vao = Lettre.concat(tab);
			String nomTable = tab[0];
			vao = Lettre.split(vao,nomTable);
			vao = Lettre.split(vao," ");
			tab = vao.split(",");
			if(this.tableExist(nomTable)){
				ReadFile read = new ReadFile();
				Relation relation = read.read(new File(nomTable));
				relation.addDonnee(tab);
				WriteFile write = new WriteFile();
				write.write(relation);
				System.out.println("Insert valider");
				this.getOos().writeObject("Insert valider");
			}
			else{ 
				throw new Exception("Table innexistant"); 
			}
		}
	}

///Test si un table existe deja
	public boolean tableExist(String nomTable){
		boolean test = false;
		File fichier = new File(nomTable);
		if(fichier.exists()){
			test = true;	
		}
		return test;
	}

///fonction qui selection les donnees dans un tableau
public void select(String requette) throws Exception{
	requette = requette.replace(';',' ');
	String[] tab = requette.split(" ");
	if(tab[0].equals("select") && requette.contains("from")){
		int index = Lettre.getIndexString(requette,"from");
		if(this.tableExist(tab[index+1])){
			String[] att = Lettre.getAttribu(requette);
			Relation relation = new Relation();
			ReadFile read = new ReadFile();
			relation = read.read(new File(tab[index+1]));
			relation = relation.selection(requette);
			if(relation==null){
				relation = read.read(new File(tab[index+1]));
				relation = relation.printTable(att);
				this.getOos().writeObject(relation);
				relation.printTable();
			}
			else{
				this.getOos().writeObject(relation);
				relation.printTable();
			}
		}
		else{ 
			throw new Exception("Table innexistant"); 
		}
	}
}

public Relation selection(String requette) throws Exception{
	Relation relation = null;
	String[] tab = requette.split(" ");
	if(tab[1].equals("distinct")){
		System.out.println();
		String[] att = null;
		if(tab[2].equals("*")){
			att = Lettre.toTable(this.getListeColonnes());
		}
		else{
			att = Lettre.getAttribuProjection(requette);
		}
		relation = this.projection(att);
	}
	else if(requette.contains("union")){
		relation = this.selectUnion(requette);
	}
	else if(requette.contains("intersection")){
		relation = this.selectIntersection(requette);
	}
	else if(requette.contains("difference")){
		relation = this.selectDifference(requette);
	}
	else if(requette.contains("division")){
		relation = this.selectDivision(requette);
	}
	else if(requette.contains("produit")){
		relation = this.selectProduitK(requette);
	}
	
	return relation;
}

public Relation selectUnion(String requette) throws Exception{
	int index = Lettre.getIndexString(requette,"union");
	String[] tab = requette.split(" ");
	String nomTable = tab[index+1];
	Relation r2 = new Relation();
	ReadFile read = new ReadFile();
	r2 = read.read(new File(nomTable));
	r2 = r2.manovaAttribu(requette);
	System.out.println("r1 = "+this.getListeColonnes());
	System.out.println("r2 = "+r2.getListeColonnes());
	Relation r3 = this.union(r2);
	return r3;
}

public Relation selectIntersection(String requette) throws Exception{
	int index = Lettre.getIndexString(requette,"intersection");
	String[] tab = requette.split(" ");
	String nomTable = tab[index+1];
	Relation r2 = new Relation();
	ReadFile read = new ReadFile();
	r2 = read.read(new File(nomTable));
	r2 = r2.manovaAttribu(requette);
	Relation r3 = this.intersection(r2);
	return r3;
}

public Relation selectDifference(String requette) throws Exception{
	int index = Lettre.getIndexString(requette,"difference");
	String[] tab = requette.split(" ");
	String nomTable = tab[index+1];
	Relation r2 = new Relation();
	ReadFile read = new ReadFile();
	r2 = read.read(new File(nomTable));
	r2 = r2.manovaAttribu(requette);
	Relation r3 = this.difference(r2);
	return r3;
}

public Relation selectDivision(String requette) throws Exception{
	int index = Lettre.getIndexString(requette,"division");
	String[] tab = requette.split(" ");
	String nomTable = tab[index+1];
	Relation r2 = new Relation();
	ReadFile read = new ReadFile();
	r2 = read.read(new File(nomTable));
	r2 = r2.manovaAttribu(requette);
	Relation r3 = this.division(r2);
	return r3;
}

public Relation selectProduitK(String requette) throws Exception{
	int index = Lettre.getIndexString(requette,"produit");
	String[] tab = requette.split(" ");
	String nomTable = tab[index+1];
	Relation r2 = new Relation();
	ReadFile read = new ReadFile();
	r2 = read.read(new File(nomTable));
	r2 = r2.manovaAttribu(requette);
	Relation r3 = this.produitK(r2);
	return r3;
}

///union de deux relations
	public Relation union(Relation r2) throws Exception{
		Relation relation = new Relation();
		this.testMAttribu(this, r2);
		for(int i=0;i<this.getDonnees().size();i++){
			int test = this.regardeLesValeurExistants(relation.getDonnees(),this.getDonnees().get(i));
			if(test==-1) relation.getDonnees().add(this.getDonnees().get(i));	
		}
		for(int l=0;l<r2.getDonnees().size();l++){
			Vector<String> ligne1 = new Vector();
			for(int i=0;i<this.getListeColonnes().size();i++){
				for(int j=0;j<r2.getListeColonnes().size();j++){
					if(this.getListeColonnes().get(i).toLowerCase().equals(r2.getListeColonnes().get(j).toLowerCase())){
						ligne1.add(r2.getDonnees().get(l).get(j));
					}
				}
				
			}
			int test = r2.regardeLesValeurExistants(relation.getDonnees(),ligne1);
			if(test==-1){
				relation.getDonnees().add(ligne1);	
			}
		}
		relation.setListeColonnes(this.getListeColonnes());
		return relation;
	}

//fonction qui fait une interaction entre deux tables	
public Relation intersection(Relation r2) throws Exception{
	Relation relation = new Relation();
	Relation temp = new Relation();
	this.testMAttribu(this, r2);
	for(int i=0;i<this.getDonnees().size();i++){
		int test = this.regardeLesValeurExistants(temp.getDonnees(),this.getDonnees().get(i));
		if(test==-1) temp.getDonnees().add(this.getDonnees().get(i));	
	}
	for(int l=0;l<r2.getDonnees().size();l++){
		Vector<String> ligne1 = new Vector();
		for(int i=0;i<this.getListeColonnes().size();i++){
			for(int j=0;j<r2.getListeColonnes().size();j++){
				if(this.getListeColonnes().get(i).toLowerCase().equals(r2.getListeColonnes().get(j).toLowerCase())){
					ligne1.add(r2.getDonnees().get(l).get(j));
				}
			}
			
		}
		int test = r2.regardeLesValeurExistants(temp.getDonnees(),ligne1);
		if(test!=-1){
			relation.getDonnees().add(ligne1);	
		}
	}
	relation.setListeColonnes(this.getListeColonnes());
	return relation;
}

//fonction qui fait la difference entre deux tables
	public Relation difference(Relation r2) throws Exception{
		Relation relation = new Relation();
		this.testMAttribu(this, r2);
		relation.setListeColonnes(this.getListeColonnes());
		boolean toky = false;
		for(int k=0;k<this.getDonnees().size();k++){
			Vector<String> ligne1 = new Vector();
			for(int l=0;l<r2.getDonnees().size();l++){
				boolean test1 = this.getDonnees().get(k).containsAll(r2.getDonnees().get(l));
				boolean test2 = r2.getDonnees().get(l).containsAll(this.getDonnees().get(k));
				if( test1 == true && test2 == true ){
					toky = true;
					break;
				}	
			}
			ligne1 = this.getDonnees().get(k);
			int i = regardeLesValeurExistants(relation.getDonnees(),ligne1);
			if( !toky && i==-1){
				relation.getDonnees().add(ligne1);
				ligne1 = null;
			}
			toky = false;
		}
		return relation; 
	}

	public int regardeLesValeurExistants(Vector<Vector<String>> listes,Vector<String> donne){
		int value = -1;
		for(int i=0;i<listes.size();i++){
			boolean test1 = listes.get(i).containsAll(donne);
			boolean test2 = donne.containsAll(listes.get(i));
			if(test1 && test2){
				value=i;
			}
		}
		return value; 
	}

	public void testMAttribu(Relation r1,Relation r2) throws Exception{
		int count = 0;
		if(r1.getListeColonnes().size()==r2.getListeColonnes().size()){
			for(int i=0;i<r1.getListeColonnes().size();i++){
				for(int j=0;j<r2.getListeColonnes().size();j++){
					if(r1.getListeColonnes().get(i).equals(r2.getListeColonnes().get(j))) count++;
				}
			}
		}
		if(count!=r1.getListeColonnes().size()) throw new Exception("Operation invalide, verifier le nom des tables");		

	}



//fonction qui fait la produit cartesienne entre deux tables
	public Relation produitK( Relation r2){
		Relation relation = new Relation();
		for(int k=0;k<this.getDonnees().size();k++){
			Vector<String> ligne1 = new Vector();
			ligne1 = this.addElementDonnees(this.getDonnees().get(k), ligne1, -1);
			for(int l=0;l<r2.getDonnees().size();l++){
				ligne1 = this.addElementDonnees(r2.getDonnees().get(l), ligne1, -1);
				relation.getDonnees().add(ligne1);
				ligne1 = new Vector();
				ligne1 = this.addElementDonnees(this.getDonnees().get(k), ligne1, -1);
			}
		}
		relation.addColonne(this,-1);
		relation.addColonne(r2,-1);
		return relation;
	}

// /fonction qui fait la division entre deux tables
public Relation division(Relation r2) throws Exception{
	Relation relation = new Relation();
	Vector<String> attribu = new Vector();
	for(int i = 0; i<this.getListeColonnes().size(); i++){
		for(int p=0; p<r2.getListeColonnes().size(); p++){
			if(this.getListeColonnes().get(i).toLowerCase().equals(r2.getListeColonnes().get(p).toLowerCase())==false){	//on regarde les attibuts demander par le client 
				attribu.add(this.getListeColonnes().get(i));
				relation.getListeColonnes().add(this.getListeColonnes().get(i));
			}
		}
	}
	String ray = attribu.toString();
	String[] at = Lettre.toTable(attribu);
	Relation r3 = this.projection(at);
	Relation r4 = r3.produitK(r2);
	Relation r5 = r4.difference(this);
	r5 = r5.projection(at);
	Relation r6 = r3.difference(r5);
	return r6; 
}

//fonction qui ajoute un valeur dans une ligne de vector de donnees	
	public Vector<String> addElementDonnees(Vector<String> liste,Vector<String> ligne,int index){
		for(int i=0;i<liste.size();i++){
			if(i!=index){
				ligne.add(liste.get(i));
			}
		}
		return ligne;
	}

//fonction 	qui ajoute un colonnes dans un tableau	
	public void addColonne(Relation r,int index){
		for(int i=0;i<r.getListeColonnes().size();i++){
			if(i!=index){
				this.getListeColonnes().add(r.getListeColonnes().get(i));
			}
		}
	}

//fonction qui test si l'attribu demander se trouve dans un table
	public boolean attribuExist(String name){
		boolean test = false;
		for(int i=0;i<this.getListeColonnes().size();i++){
			if(this.getListeColonnes().get(i).equals(name)) test = true;
		}
		return test;
	}

	public int indexAttribu(String name){
		int index = -1;
		for(int i=0;i<this.getListeColonnes().size();i++){
			if(this.getListeColonnes().get(i).equals(name)) index = i;
		}
		return index;
	}

///fonction de projection 
	public Relation projection(String[] attribu) throws Exception{
		Relation projection = new Relation();
		projection.setNom(this.getNom());
		Vector<Integer> isa = new Vector();
		for(int i = 0; i<this.getListeColonnes().size();i++){
			for(int p=0;p<attribu.length;p++){
				if(this.getListeColonnes().get(i).toLowerCase().equals(attribu[p].toLowerCase())){	//on regarde les attibuts demander par le client 
					isa.add(i);
					projection.getListeColonnes().add(this.getListeColonnes().get(i));
				}
			}
		}
		this.test(isa.size());
		for(int j = 0;j<this.getDonnees().size();j++){
			Vector<String> d = new Vector<>();
			for(int k=0;k<this.getDonnees().get(j).size();k++){
				for(int i = 0;i<isa.size();i++){
					if(k==isa.get(i)){
						d.add(this.getDonnees().get(j).get(k));
					}
				}
			}
			if(regardeLesValeurExistants(projection.getDonnees(),d) == -1 && d.size()!=0){
				projection.getDonnees().add(d);		
			}
		}
		return projection;
	}

//manova anle anarana attribu ana relation1 amle joiture
public Relation manovaAttribu(String requette){
	if(requette.contains("on")){
		int index = Lettre.getIndexString(requette,"on");
		String[] tabs = requette.split(" ");
		String apres = Lettre.phraseEntreAvec(requette,index,tabs.length);
		if(apres.equals("")==false){
			tabs = apres.split("and");
			for(int i=0;i<tabs.length;i++){
				tabs[i] = Lettre.split(tabs[i]," ");
				String[] liste = tabs[i].split("=");
				for(int j=0;j<this.getListeColonnes().size();j++){
					if(this.getListeColonnes().get(j).toLowerCase().equals(liste[0].toLowerCase())){
						this.getListeColonnes().set(j,liste[1].toLowerCase());
					}
				}
			}
		}
	}
	return this;
}
	

}