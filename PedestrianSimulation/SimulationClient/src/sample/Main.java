package sample;

//import java.net.*;
import java.io.*;


class Main {
    public static void main(String [] args) throws IOException
    {
        PedestrianClient klientTrad = new PedestrianClient();
        klientTrad.start();
    }
}
