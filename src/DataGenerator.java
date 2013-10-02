/**
 * Created with IntelliJ IDEA.
 * User: liten
 * Date: 2013-09-21
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
public class DataGenerator {
    private static byte _startByte= (byte) 0xaa;
    private static byte _endByte= (byte) 0xcc;
    private static byte _esc= (byte) 0xbb;
    private static byte _xor=(byte) 0xff;

    public static byte [] generatePackage(){
        byte[] data=new byte[0];
        data=generateAirTemp((short) 25,data);
        data=generateCoolantTemp((short) 98,data);
        data=generateTPS((short) 324, data);
        data=generateEGO((short) 20204, data);
        data=generateMAP((short) 102, data);
        data=generateBRV((short) 1200, data);
        data=generateRPM((short) 3200, data);
        data=generateLoadMain((short) 100, data);
        data=generateVEMain((short) 50, data);
        data=generateLambda((short) 1, data);
        data=generateAccelX((short) 0, data);
        data=generateAccelY((short) 1, data);
        data=generateAccelZ((short) 2, data);
        data=generateGyroX((short) 1, data);
        data=generateGyroY((short) 0, data);
        data=generateGyroZ((short) 0, data);
        data=generateWheelSRR((short) 70, data);
        data=generateWheelSRL((short) 70, data);
        data=generateWheelSFR((short) 70, data);
        data=generateWheelSFL((short) 70, data);

        return pack(data);
    }

    private static byte[] _addBytes(short i,byte[] data){
        byte[] b=new byte[data.length+2];
        System.arraycopy(data,0,b,0,data.length);
        System.arraycopy(toByte(i),0,b,data.length,2);
        return b;
    }

    private static byte[] toByte(short n) {
        int packageLen=2;
        byte[] b=new byte[packageLen];
        b[0]=(byte)(n&0xff);
        b[1]=(byte)((n>>8)&0xff);

        return b;
    }

    private static byte[] pack(byte[] b) {
        b=_addLength(b);
        byte [] send = new byte[b.length+3];
        send[0]=_startByte;
        send[b.length]= _getCheckSum(b);
        send[b.length+1]=_endByte;
        return _escBytes(send);
    }

    private static byte[] _addLength(byte[] b) {
        byte [] n=new byte[b.length+1];
        System.arraycopy(b,0,n,1,b.length);
        n[0]= (byte) b.length;
        return n;
    }

    private static byte[] _escBytes(byte[] b) {
        int counter=0;
        int idx=0;
        for (byte aB1 : b)
            if (aB1 == _startByte || aB1 == _endByte || aB1 == _esc)
                counter++;

        if(counter==0)
            return b;

        byte[] ret = new byte[b.length + counter];


        for (byte aB : b)
            if (aB == _startByte || aB == _endByte || aB == _esc) {
                ret[idx++] = _esc;
                ret[idx++] = (byte) (aB ^ _xor);
            } else
                ret[idx++] = aB;

        return ret;
    }

    private static byte _getCheckSum(byte[] b){
        byte sum=0;
        for (byte aB : b)
            sum+=aB;

        return sum;
    }

    private static byte[] generateTemp(short temp, byte[] data) {
        temp+=273;
        return _addBytes(temp,data);
    }

    private static byte[] generateAirTemp(short temp, byte[] data){return generateTemp(temp, data);}

    private static byte[] generateCoolantTemp(short temp,byte[] data){return generateTemp(temp, data);}

    private static byte[] generateWheelSFL(short i, byte[] data) {return _addBytes(i,data);}

    private static byte[] generateWheelSFR(short i, byte[] data) {return _addBytes(i,data);}

    private static byte[] generateWheelSRL(short i, byte[] data) {return _addBytes(i,data);}

    private static byte[] generateWheelSRR(short i, byte[] data) {return _addBytes(i,data);}

    private static byte[] generateGyroZ(short i, byte[] data) {return _addBytes(i,data);}

    private static byte[] generateGyroY(short i, byte[] data) {return _addBytes(i,data);}

    private static byte[] generateGyroX(short i, byte[] data) {return _addBytes(i,data);}

    private static byte[] generateAccelZ(short i, byte[] data) {return _addBytes(i,data);}

    private static byte[] generateAccelY(short i, byte[] data) {return _addBytes(i,data);}

    private static byte[] generateAccelX(short i, byte[] data) {return _addBytes(i,data);}

    private static byte[] generateLambda(short i, byte[] data) {return _addBytes(i,data);}

    private static byte[] generateVEMain(short i, byte[] data) {return _addBytes(i,data);}

    private static byte[] generateLoadMain(short i, byte[] data) {return _addBytes(i,data);}

    private static byte[] generateBRV(short i, byte[] data) {return _addBytes(i, data);}

    private static byte[] generateMAP(short i, byte[] data) {return _addBytes(i,data);}

    private static byte[] generateEGO(short i, byte[] data) {return _addBytes(i, data);}

    private static byte[] generateRPM(short i,byte[] data) {return _addBytes(i,data);}

    private static byte[] generateTPS(short i, byte[] data) {return _addBytes(i,data);}
}
