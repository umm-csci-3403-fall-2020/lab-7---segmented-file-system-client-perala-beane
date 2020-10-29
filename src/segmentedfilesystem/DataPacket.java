package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

//the status bytes, file ID, packet number, and data, and store them in 
//fields that are accessible through various get methods
public class DataPacket {
    byte status;
    byte[] data;
    int packetNumber;
    byte fileID;
    boolean wasAdded;

    public DataPacket(DatagramPacket packet) {

    }

    public void setPacketNumber() {
        
    }
}