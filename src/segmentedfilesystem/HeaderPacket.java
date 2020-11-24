package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class HeaderPacket {
    
    DatagramPacket packet;

    byte fileID;
    byte[] filename;

    public HeaderPacket(DatagramPacket packet) {
        
        this.packet = packet;
        
        fileID = packet.getData()[1];
        
        filename = Arrays.copyOfRange(packet.getData(), 2, packet.getLength());
    }

    public byte getFileID(){
        return fileID;
    }

    public byte[] getFileName(){
        return filename;
    }
}
