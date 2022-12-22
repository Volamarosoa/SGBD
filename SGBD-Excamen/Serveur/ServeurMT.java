package serveurMT;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;

import serveurSocket.Serveur;

public class ServeurMT extends Thread{
    private int numeroClient;

    public ServeurMT(){
        this.start();
    }

    public void run(){ 
        try{
            ServerSocket listener = new ServerSocket(2068);
            System.out.println("En attente d'un client...");
            while(true){
                Socket socketOfSever = listener.accept();
                this.setNumeroClient(this.getNumeroClient()+1);
                Serveur serverConversation = new Serveur(socketOfSever,this.getNumeroClient());
            }
        }
        catch(Exception io){
            System.out.println(io.getMessage());
        }
    }

    public int getNumeroClient() {
        return this.numeroClient;
    }

    public void setNumeroClient(int numeroClient) {
        this.numeroClient = numeroClient;
    }

    public static void main(String[] ars){
        ServeurMT serveurMT = new ServeurMT();
          //Quand il execute il va appeler la methode run
    }

}