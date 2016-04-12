import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client implements Runnable{
	protected static DatagramSocket clientSocket;
	protected static InetAddress inetAddress;
	protected static clientState = "clientReceive";

	public static void main(String[] args){
		try{
			inetAddress = InetAddress.getByName("127.0.0.1");
			new Client(inetAddress);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public Client(InetAddress inetAddress){
		try{
			this.inetAddress = inetAddress;
			this.clientSocket = new DatagramSocket(1111);
			this.clientSocket.setSoTimeout(5000);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.run();
		}
	}

	public void run(){
		while(true){
			switch(clientState){
				// client receives
				case "clientReceive":
					try{
						Thread.sleep(2000);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					finally{
						try{
							byte[] buff = new byte[256];
							DatagramPacket packet = new DatagramPacket(buff, buff.length);
							clientSocket.receive(packet);
							String msg = new String(buff);
							msg = msg.trim();
							System.out.println(msg);
						}
						catch(Exception e){
							e.printStackTrace();
						}
						finally{
							clientState = "clientSend";
						}
					}
				break;

				// client sends
				case "clientSend":
					try{
						Thread.sleep(2000);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					finally{
						try{
							String msg = "Test Server";
							byte[] buff = msg.getBytes();
							DatagramPacket packet = new DatagramPacket(buff, buff.length, this.inetAddress, 4444);
							clientSocket.send(packet);
						}
						catch(Exception e){
							e.printStackTrace();
						}
						finally{
							clientState = "clientReceive";
						}
					}
				break;
			}
		}
	}
}