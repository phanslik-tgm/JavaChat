import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server
{
	public static void main (String[] args)
	{
		try
		{
			ServerSocket server = new ServerSocket(5555);
			System.out.println("Server gestartet!");
			
			Socket client = server.accept();
			
			//Streams
			OutputStream out = client.getOutputStream();
			PrintWriter writer = new PrintWriter(out);
			
			InputStream in = client.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			//------------------------------
			
			
			String s = null;
			
			while((s = reader.readLine()) != null) 
					{
						writer.write(s + "\n");
						writer.flush();
						System.out.println("Empfangen von client:" + s);
					}
			
			
			writer.close();
			reader.close();
			
			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
