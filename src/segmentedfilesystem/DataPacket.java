package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

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
        this.fileID = data[1]; //from slideshow
        this.status = data[0]; //from slideshow
        length = packet.getLength();
        this.copy = Arrays.copyOfRange(packet.getData(), 4, length); //skips the first 4 because those are for bookkeeping.
        this.wasAdded = false;

    }

    public void setPacketNumber() { //from documentation
        int x = data[2]; //from slideshow
        int y = data[3]; //from slideshow

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