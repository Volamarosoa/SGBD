package serveurSocket;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import relation.Relation;

public class Serveur extends Thread{
    private Socket socket;
    private int numero;

    public Serveur(Socket socket,int numero){
        this.setSocket(socket);
        this.setNumero(numero);
        this.start();
    }

    public void run(){
        //La conversation entre serveur et un client
        try{
            ObjectOutputStream oos = new ObjectOutputStream(this.getSocket().getOutputStream());     //manoratra any @ client
            ObjectInputStream ois = new ObjectInputStream(this.getSocket().getInputStream());        //mamaky ny message client
            String IP = this.getSocket().getRemoteSocketAddress().toString();
            System.out.println("Connexion du client numero "+this.getNumero() + " Son IP = "+IP);
            oos.writeObject("Bienvenu, vous etes le client numero "+this.getNumero());
            while(true){
                String message = (String)ois.readObject();
                Relation relation = new Relation(oos,ois);
                if(message!=null){
                    if(message.toLowerCase().equals("exit")) {
                        oos.writeObject("exit"); System.out.println("Deconnexion du client numero "+this.getNumero());
                    }
                    else relation.requette(message);
                    System.out.println("#"+message);
                }
            }
        }
        catch(Exception io){
            System.out.println(io.getMessage());
        }   
    }

    public Socket getSocket() {
        return this.socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}