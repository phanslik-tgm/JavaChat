
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server
{

	ServerSocket server;
	static ArrayList<PrintWriter> list_clientWriter;

	final static int LEVEL_ERROR = 1;
	final static int LEVEL_NORMAL = 0;

	public static void main(String[] args)
	{
		Server s = new Server();
		if (s.runServer())
		{
			s.listenToClients();
		}
		else
		{
			// Do nothing
		}
	}

	public void listenToClients()
	{
		while (true)
		{
			try
			{
				Socket client = server.accept();
				
				Thread clientThread = new Thread(new Handler(client));
				clientThread.start();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public boolean runServer()
	{
		try
		{
			server = new ServerSocket(5050);
			appendTextToConsole("Server wurde gestartet!", LEVEL_ERROR);

			list_clientWriter = new ArrayList<PrintWriter>();
			return true;
		}
		catch (IOException e)
		{
			appendTextToConsole("Server konnte nicht gestartet werden!", LEVEL_ERROR);
			e.printStackTrace();
			return false;
		}
	}

	public static void appendTextToConsole(String message, int level)
	{
		if (level == LEVEL_ERROR)
		{
			System.err.println(message + "\n");
		}
		else
		{
			System.out.println(message + "\n");
		}
	}

	public static void sendToAllClients(String message)
	{
		Iterator it = list_clientWriter.iterator();
		
		while (it.hasNext())
		{
			PrintWriter writer = (PrintWriter) it.next();
			writer.println(message);
			writer.flush();
		}
	}
}