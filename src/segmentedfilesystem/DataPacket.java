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
    byte[] copy;
    int length; //length of array

    public DataPacket(DatagramPacket packet) {
        this.data = packet.getData();
        this.setPacketNumber();
        this.fileID = data[1];
        this.status = data[0];
        length = packet.getLength();
        this.copy = Arrays.copyOfRange(packet.getData(), 4, length); //skips the first 4 because those are for bookkeeping.
        this.wasAdded = false;

    }

    public void setPacketNumber() { //from documentation
        int x = data[2];
        int y = data[3];

        if (x < 0) {
            x += 256;
        }
        if (y < 0) {
            x += 256;
        }

        this.packetNumber = (256 * x + y);
    }

    public byte returnFileID() {
        return fileID;
    }
}