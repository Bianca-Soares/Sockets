public class TCPClienteFTP {
public static void main(String args[]) {
Socket s = null;
String server = "localhost" ; // args[0]
String porta = "6789"; // args[1]
String nomeClient = "MANUEL"; // args[2]
try {
s = new Socket(server, Integer.parseInt(porta)); // conecta o socket aa porta remota
DataInputStream ent = new DataInputStream(s.getInputStream());
DataOutputStream sai = new DataOutputStream(s.getOutputStream());
sai.writeUTF(nomeClient);
String recebido = ent.readUTF();
while (recebido != null) {
System.out.println(recebido);
recebido = ent.readUTF();
}
}