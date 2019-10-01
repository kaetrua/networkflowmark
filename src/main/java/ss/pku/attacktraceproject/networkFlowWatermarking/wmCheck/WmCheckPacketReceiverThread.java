package ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

import java.io.IOException;

public class WmCheckPacketReceiverThread extends Thread {
    @Override
    public void run() {
        System.out.println("当前线程名:" + Thread.currentThread().getName());
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        JpcapCaptor captor = null;
        try {
            //devices[0]  根据具体情况自行调整
            captor = JpcapCaptor.openDevice(devices[5], 65535, false, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            captor.loopPacket(-1, new WmCheckPacketReceiverThreadHandler());
        } catch (NullPointerException e) {
            System.out.println("WmCheckPacketReceiverThread.run()发生了异常："+e.getMessage());
        }
    }
}
