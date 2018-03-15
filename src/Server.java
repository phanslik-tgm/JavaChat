
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

//	public class ClientHandler implements Runnable
//	{
//
//		Socket client;
//		BufferedReader reader;
//
//		public ClientHandler(Socket client)
//		{
//			try
//			{
//				this.client = client;
//				reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//		}
//
//		@Override
//		public void run()
//		{
//			String nachricht;
//
//			try
//			{
//				while ((nachricht = reader.readLine()) != null)
//				{
//					appendTextToConsole("Vom Client: \n" + nachricht, LEVEL_NORMAL);
//					sendToAllClients(nachricht);
//				}
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//		}
//	}

	public void listenToClients()
	{
		while (true)
		{
			try
			{
				Socket client = server.accept();

				//PrintWriter writer = new PrintWriter(client.getOutputStream());
				//list_clientWriter.add(writer);

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