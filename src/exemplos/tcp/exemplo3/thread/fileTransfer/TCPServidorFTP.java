package exemplos.tcp.exemplo3.thread.fileTransfer;
/*
 * TCPServidorFTP.java
 *
 * Servidor de arquivo texto. Envia um arquivo texto (teste.txt) para os clientes que
 * se conectam. Os clientes devem passar um ID que será utilizado nas mensagens
 * impressas pelo servidor na sua console.
 */
import java.net.*;
import java.io.*;
public class TCPServidorFTP {
    public static void main(String args[]) {
        try {
            int porta = 6789; // porta do serviço
            if (args.length > 0) porta = Integer.parseInt(args[0]);
            ServerSocket escuta = new ServerSocket(porta);
            System.out.println("*** Servidor ***");
            System.out.println("*** Inicio - porta de escuta (listen): " + porta);
            while (true) {
                // accept bloqueia ateh que chegue um pedido de conexao de um cliente
                // qdo chega, cria um novo socket = cliente
                Socket cliente = escuta.accept();
                System.out.println("*** Socket de escuta (listen): " + escuta.getLocalSocketAddress().toString());
                System.out.println("*** Conexao aceita de (remoto): " + cliente.getRemoteSocketAddress().toString());
                // quando chega, cria nova thread para atender em especial o cliente
                Conexao c = new Conexao(cliente);
            }
        } catch (IOException e) {
            System.out.println("Erro na escuta: " + e.getMessage());
        }
    }
    
}


