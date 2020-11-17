package segmentedfilesystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.io.File;
import java.io.FileOutputStream;

public class FileRetriever {

        String server;
        int port;
        DatagramSocket socket;

        public FileRetriever(String server, int port) throws SocketException, UnknownHostException {

                this.server = server;
                this.port = port;
                //socket = new DatagramSocket(port);
                // removed DataGramSocket's input to get rid of binding error
                socket = new DatagramSocket();

                // InetAddress address = InetAddress.getByName(server);
                // DatagramSocket s = new DatagramSocket(port);
                // s.connect(address, port);
                // catch (UnknownHostException uhe){
                // System.out.println("Error, unknown host.");
                // }
                // catch (SocketException se){
                // System.out.println("Error, socket exception.");
                // }
        }

        public void packetPackager(List<HeaderPacket> headerList, List<PacketManager> packageList) throws IOException {
                for (int i = 0; i < headerList.size(); i++) {

                        HeaderPacket header = headerList.get(i);
                        byte fileID = header.fileID;
                        PacketManager targetPacket = new PacketManager((byte) 1);

                        for (int m = 0; m < packageList.size(); m++) {
                                if (fileID == packageList.get(m).fileID) {
                                        targetPacket = packageList.get(m);
                                }
                        }
                        
                        assembleFiles(targetPacket.data, header);
                }
        }

        public void assembleFiles(List<DataPacket> dataPackets, HeaderPacket header) throws IOException {
                String file = new String(header.getFileName());
                Map<Integer, DataPacket> list = new HashMap<Integer, DataPacket>();
                for (int i = 0; i < dataPackets.size(); i++) {
                        DataPacket datPack = dataPackets.get(i);
                        list.put(datPack.packetNumber, datPack);
                }
                List<DataPacket> sortedFiles = sortPackets(list);
                writeFile(sortedFiles, file);
        }

        public List<DataPacket> sortPackets(Map<Integer, DataPacket> data) {
                List<Integer> sorted = new ArrayList<Integer>(data.keySet());
                Collections.sort(sorted);
                List<DataPacket> sortedData = new ArrayList<DataPacket>();
                for (Integer key : sorted) {
                        sortedData.add(data.get(key));
                } for (int i = 0; i < sortedData.size(); i++) {
                        System.out.println(sortedData.get(i).packetNumber + "Packet Number");
                }
                return sortedData;
        }

        public void writeFile(List<DataPacket> sortedFiles, String file) throws IOException {
                File finalFile = new File(file);
                FileOutputStream outStream = new FileOutputStream(finalFile);
                for (int i = 0; i < sortedFiles.size(); i++) {
                        outStream.write(sortedFiles.get(i).copy);
                }
                outStream.flush();
                outStream.close();

        }

        public void downloadFiles() throws UnknownHostException, SocketException, IOException {

                InetAddress address = InetAddress.getByName(this.server);

                byte[] buffer = new byte[0];
                List<HeaderPacket> headersList = new ArrayList<HeaderPacket>();
                List<PacketManager> packageList = new ArrayList<PacketManager>();

                // Filling the list to avoid an out of bounds error (COOKIE)
                for (int i = 0; i < 3; i++) {
                        PacketManager packet = new PacketManager(Byte.MAX_VALUE);
                        packageList.add(packet);
                }
                
                DatagramPacket packetSend = new DatagramPacket(buffer, buffer.length, address, this.port);
                socket.send(packetSend);
                System.out.println("Conversation started");

                int files = 3; // as stated within the documentation.

                while (files > 0) {

                        buffer = new byte[1028];
                        DatagramPacket packetReceive = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packetReceive);
                        System.out.println("Data received");

                        // Checking if even which means it is a header packet
                        if (packetReceive.getData()[0] % 2 == 0) {

                                HeaderPacket header = new HeaderPacket(packetReceive);
                                headersList.add(header);
                        }

                        // Handling when it is odd which means it is a data packet
                        else {

                                DataPacket datPack = new DataPacket(packetReceive);

                                // Might need to change the 3 here
                                for (int i = 0; i < 3; i++) {

                                        packageList.get(i).setFileID(datPack.fileID);
                                        packageList.get(i).listAdd(datPack);
                                        datPack.wasAdded = true;

                                        if ((datPack.status % 4) == 3) {

                                                packageList.get(i).maxSize(datPack.packetNumber);

                                        }
                                        // This if statement may not be necessary as at the moment we are assuming
                                        // we only have three files.
                                        if ((packageList.get(i).isFull == true)) {
                                                files--;
                                        }
                                }
                        }
                }

                packetPackager(headersList, packageList);
        }
        // Do all the heavy lifting here.
        // This should
        // * Connect to the server
        // * Download packets in some sort of loop
        // * Handle the packets as they come in by, e.g.,
        // handing them to some PacketManager class
        // Your loop will need to be able to ask someone
        // if you've received all the packets, and can thus
        // terminate. You might have a method like
        // PacketManager.allPacketsReceived() that you could
        // call for that, but there are a bunch of possible
        // ways.

}
