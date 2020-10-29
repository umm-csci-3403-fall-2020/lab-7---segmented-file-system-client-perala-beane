package segmentedfilesystem;

import java.net.DatagramPacket;

public class HeaderPacket {
    
    DatagramPacket packet;

    byte fileID;

    public HeaderPacket() {
        // assign the data packet
        // assign the file id
        // get assign the packet length
        // cut the array to be the appropriate size
    }

    public byte getFileID(){
        return fileID;
    }
}
