package client;

import java.net.Socket;
import java.util.Scanner;

import javax.sound.midi.Soundbank;

import java.io.*;
import relation.Relation;

public class Client{
    public static void main(String[] args) {
        try{
            String AdresseIPServeur = "localhost"; //Adresse IP du serveur 
            int port = 2068; //Le port pour se connecter au serveur
            Socket socketClient = new Socket(AdresseIPServeur,port);  
            ObjectOutputStream oos = new ObjectOutputStream(socketClient.getOutputStream());    //manoratra any @ serveur
            ObjectInputStream ois = new ObjectInputStream(socketClient.getInputStream());       //mamaky ny nmessage avy any @serveur
            String rep = "";
            Relation relation = new Relation();
            rep = (String)ois.readObject();
            System.out.println(rep);
            while(true){
                System.out.print("ROTAsql>>");
                Scanner scanner = new Scanner(System.in);
                String req = scanner.nextLine();
                oos.writeObject(req); 
                Object obj = ois.readObject();  //mamaky an'izay message avy any @serveur
                if(obj instanceof String){
                    rep = String.valueOf(obj);
                    if((rep)!=null){
                        System.out.println(rep);
                        if(rep.toLowerCase().indexOf("exit")!=-1){
                            oos.close();
                            socketClient.close();
                            break;
                        }
                    }
                }
                else if(obj instanceof Relation){
                    relation = (Relation)obj;
                    if(relation != null){
                        relation.printTable();
                    }
                }
                
            }
        }
        catch(Exception io){
            System.err.println("Erreur: Connection Refuser ");
        }

    }
}