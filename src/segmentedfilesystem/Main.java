package segmentedfilesystem;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {
    
    // If there's one command line argument, it is assumed to
    // be the server. If there are two, the second is assumed
    // to be the port to use.
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException{
        
        String server = "csci-4409.morris.umn.edu";
	    //String server = "localhost";
        int port = 6014;

        if (args.length >= 1) {
            server = args[0];
        }
        if (args.length >= 2) {
            port = Integer.parseInt(args[1]);
        }
        
        FileRetriever fileRetriever = new FileRetriever(server, port);
        System.out.println("Downloading files");
        fileRetriever.downloadFiles(); 
        System.out.println("Files downloaded");
    }
    
}
