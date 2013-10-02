import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: liten
 * Date: 2013-09-21
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        SerialPortHandler sp=new SerialPortHandler();

        String[] names= sp.getSerialPortNames();

        for (String name : names) System.out.println(name);

        sp.openSerialPort(sc.nextLine());

        if(!sp.isPortOpen()){
            System.out.println("port not opened. exiting");
            return;
        }

        OutputStream os=sp.getOutputStream();

        for(int i=0;i<15000;i++){
            try {
                os.write(DataGenerator.generatePackage());
            } catch (IOException e) {
                System.out.println("failed at transmission number " + i +" with IOException");
                e.printStackTrace();
            }
        }

    }

}
