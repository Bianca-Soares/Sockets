package exemplos.tcp.exemplo3.thread.fileTransfer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

class Conexao extends Thread {
    private static final String FILENAME ="c:/tmp/arqtesteUTF.txt";
    		// "c:/tmp/arqteste.txt";
    
	protected BufferedReader arq = null;
    DataInputStream ent;
    DataOutputStream sai;
    Socket cliente;
    String idCliente = null;
    
    public Conexao(Socket s) {
        try {
            cliente = s;
            ent = new DataInputStream(cliente.getInputStream());
            sai = new DataOutputStream(cliente.getOutputStream());
            idCliente = ent.readUTF();
            this.start();
        } catch (IOException e) {
            System.out.println("Erro IO Conexao: " + e.getMessage());
        }
    }
    
    public void enviarMsg(String msg) {
        try {
            sai.writeUTF(msg);
        } catch (IOException e1) {
            System.out.println("Erro de escrita no buffer da conexao ("+idCliente+")");
        }
    }
    public void run() {
        try {
            arq = new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo nao econtrado: " + FILENAME);
            enviarMsg("!!! Erro ao tentar abrir arquivo teste.txt"+e.getMessage());
        }
        
        if (arq != null) {
            try {
                String l = arq.readLine();
                while (l != null) {
                    enviarMsg(l);
                    l = arq.readLine();
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler linha do arquivo "
                	+ FILENAME	+ " ("+idCliente+")");
                enviarMsg("!!! Erro ao ler arquivo "
                		+ FILENAME +" - "+ e.getMessage());
            }
            try {
                arq.close();
            } catch (IOException e) {
                System.out.println("Erro fechamento do arquivo "
                		+ FILENAME	+ " ("+idCliente+")");
            }
            try {
                cliente.close();
            } catch (IOException e) {
                System.out.println("Erro fechamento do socket cliente ("+idCliente+")");
            }
            System.out.println("*** Conexao encerrada com "+idCliente + "\n");
        }
    }
} // fim da classe Conexao    
