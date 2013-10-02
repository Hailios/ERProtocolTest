import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 *
 * @author jonasbromo
 * edited by Jakob Lövhall
 */
public class SerialPortHandler {
    private SerialPort serialPort;
    private OutputStream outStream;
    private InputStream inStream;
    private boolean portOpen = false;


    public String[] getSerialPortNames() {

        ArrayList<String> serialPorts = new ArrayList<String>();
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                serialPorts.add(portId.getName());
            }
        }

        String[] result = serialPorts.toArray(new String[serialPorts.size()]);

        if (result.length == 0) {
            return new String[]{"/dev/ttyACM0"};
        }

        return result;
    }

    public void openSerialPort(String name) {
        try {
            CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(name);
            serialPort = (SerialPort) portId.open("ER Serial Port", 5000);

            int baudRate = 9600;
            serialPort.setSerialPortParams(
                    baudRate,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

            System.out.println("Serial port open: " + name);

            inStream = serialPort.getInputStream();
            outStream = serialPort.getOutputStream();

            portOpen = true;

        } catch (NoSuchPortException ex) {
            System.err.println("Failed to open serial port: NoSuchPortException\n" + ex);
        } catch (PortInUseException ex) {
            System.err.println("Failed to open serial port: PortInUseException\n" + ex);
        } catch (UnsupportedCommOperationException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Failed to get input/output stream\n" + ex);
        }
    }

    /**
     * Get the serial port input stream
     * @return The serial port input stream
     */
    public InputStream getInputStream() {
        return inStream;
    }

    /**
     * Get the serial port output stream
     * @return The serial port output stream
     */
    public OutputStream getOutputStream() {
        return outStream;
    }

    public boolean isPortOpen()
    {
        return portOpen;
    }
}
