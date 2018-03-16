import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Handler implements Runnable
	{

		Socket client;
		BufferedReader reader;

		public Handler(Socket client)
		{
			try
			{
				this.client = client;
				reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void run()
		{
			String nachricht;

			try
			{
				
				while ((nachricht = reader.readLine()) != null)
				{
					Server.appendTextToConsole("Vom Client: \n" + nachricht, Server.LEVEL_NORMAL);
					PrintWriter writer = new PrintWriter(client.getOutputStream());
					Server.list_clientWriter.add(writer);
					if(nachricht.equals("exit"))
					{
           	 			client.close();
           	 			Server.list_clientWriter.remove(writer);
           	 			break;
					}
					else
					{
						Server.sendToAllClients(nachricht);
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}