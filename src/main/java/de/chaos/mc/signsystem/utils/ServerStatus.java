package de.chaos.mc.signsystem.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

public class ServerStatus {

    private String name, ip, status;

    private int port;
    private int playercount;
    private int maxpcount;

    public ServerStatus(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getIP() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getOnline() {
        return playercount;
    }

    public int getmaxPlayers() {
        return maxpcount;
    }

    public String getStatus() {
        return status;
    }

    public void update() throws IOException {

        Socket socket = new Socket();
        OutputStream outputStream;
        DataOutputStream dataOutputStream;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        socket.setSoTimeout(1000);

        socket.connect(new InetSocketAddress(ip, port), 1000);
        outputStream = socket.getOutputStream();
        dataOutputStream = new DataOutputStream(outputStream);
        inputStream = socket.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream,Charset.forName("UTF-16BE"));
        dataOutputStream.write(new byte[]{(byte) 0xFE,(byte) 0x01});
        int packetId = inputStream.read();

        if (packetId == -1) {
            dataOutputStream.close();
            outputStream.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();
            throw new IOException("Premature end of stream");
        } if (packetId != 0xFF) {
            dataOutputStream.close();
            outputStream.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();
            throw new IOException("Invalid packet ID (" + packetId + ").");
            //packet moze byt spravny
        }

        int length = inputStreamReader.read();

        if (length == -1) {
            dataOutputStream.close();
            outputStream.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();
            throw new IOException("Premature end of stream");
        } if (length == 0) {
            dataOutputStream.close();
            outputStream.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();
            throw new IOException("Invalid string length");
        }

        char[] chars = new char[length];

        if (inputStreamReader.read(chars,0,length) != length) {
            dataOutputStream.close();
            outputStream.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();
            throw new IOException("Premature end of stream.");
        }

        String string = new String(chars);
        if (string.startsWith("§")) {
            String[] data = string.split("\0");
            String motd = data[3];

            int onlinePlayers = Integer.valueOf(data[4]);
            int maxPlayers = Integer.valueOf(data[5]);
            this.status = motd;
            this.playercount = onlinePlayers;
            this.maxpcount = maxPlayers;


            dataOutputStream.close();
            outputStream.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();
        }
    }
}