package segmentedfilesystem;

import java.util.ArrayList;
import java.util.List;

public class PacketManager {
    int maxSize;
    byte fileID;
    List<DataPacket> data;
    boolean isFull;

    public PacketManager(byte fileID) {
        this.data = new ArrayList<DataPacket>();
        this.isFull = false;
        this.maxSize = 102404; //1024 * 99 + 1028
        this.fileID = fileID;
    }

    public void listAdd(DataPacket dataPacket) {
        data.add(dataPacket);
        if (data.size() >= this.maxSize) {
            this.isFull = true;
        } else {
            this.isFull = false;
        }
    }

    public void setFileID(byte fileID) {
        this.fileID = fileID;
    }

    public void maxSize(int maxSize) {
        this.maxSize = maxSize;
    }    
}