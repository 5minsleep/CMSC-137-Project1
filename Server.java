import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Server implements Runnable{
	protected static DatagramSocket serverSocket;
	protected static InetAddress inetAddress;
	protected static serverState = "serverSend";
	protected static String host = "127.0.0.1";

	public static void main(String[] args){
		try{
			inetAddress = InetAddress.getByName("127.0.0.1");
			new Server(inetAddress);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public Server(InetAddress inetAddress){
		try{
			this.inetAddress = inetAddress;
			this.serverSocket = new DatagramSocket(4444);
			this.serverSocket.setSoTimeout(5000);
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
			switch(serverState){
				// server receives
				case "serverReceive":
					try{
						Thread.sleep(2000);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					finally{
						try{
							byte[] buff = new byte [256];
							DatagramPacket packet = new DatagramPacket(buff, buff.length);
							serverSocket.receive(packet);
							String msg = new String(buff);
							System.out.println(msg);
						}
						catch(Exception e){
							e.printStackTrace();
						}
						finally{
							serverState = "serverSend";
						}
					}
				break;

				// server sends
				case "serverSend":
					try{
						Thread.sleep(2000);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					finally{
						try{
							String msg = "Test Client";
							byte[] buff = msg.getBytes();
							DatagramPacket packet = new DatagramPacket(buff, buff.length, this.inetAddress, 1111);
							serverSocket.send(packet);
						}
						catch(Exception e){
							e.printStackTrace();
						}
						finally{
							serverState = "serverReceive";
						}
					}
				break;
			}
		}
	}
}