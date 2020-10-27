package segmentedfilesystem;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class FileRetriever {
        String server;
        int port;
        DatagramSocket socket;

	public FileRetriever(String server, int port) throws SocketException, UnknownHostException {

                this.server = server;
                this.port = port;
                socket = new DatagramSocket(port);

                //InetAddress address = InetAddress.getByName(server);
                //DatagramSocket s = new DatagramSocket(port);
                //s.connect(address, port);
                // catch (UnknownHostException uhe){
                //         System.out.println("Error, unknown host.");
                //     }
                //     catch (SocketException se){
                //         System.out.println("Error, socket exception.");
                //     }
	}

	public void downloadFiles() throws UnknownHostException, SocketException, IOException{
                InetAddress address = InetAddress.getByName(this.server);

                byte[] buffer = new byte[0];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, this.port);
                socket.send(packet);

                int files = 3; //as stated within the documentation.

                while (files > 0) {

                }
                
        // Do all the heavy lifting here.
        // This should
        //   * Connect to the server
        //   * Download packets in some sort of loop
        //   * Handle the packets as they come in by, e.g.,
        //     handing them to some PacketManager class
        // Your loop will need to be able to ask someone
        // if you've received all the packets, and can thus
        // terminate. You might have a method like
        // PacketManager.allPacketsReceived() that you could
        // call for that, but there are a bunch of possible
        // ways.
        }

}
