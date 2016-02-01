/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oculusclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author debdipto
 */
public class OculusClient {

    /**
     * @param args the command line arguments
     */
    
    Socket clientSocket =null;
    String hostIP = "192.168.1.10";
    int port=6669;
    
    String username=null;
    String password=null;
    
    PollingThread pollingThread=null;
    Thread _pollingThread;
    public OculusClient()
    {
        
    }
    
    public void displayLine(String message)
    {
        System.out.println(message);
    }
    
    public String readLine()
    {
        try
        {
            BufferedReader in  = new BufferedReader(new InputStreamReader(System.in));
            String reply = in.readLine();
            return reply;
        }
        catch(Exception e)
        {
            displayLine(e.getMessage());
            return null;
        }
        
    }
    
    public void writeToServer(String message) throws IOException
    {        
        try 
        {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.write(message + "$$");
            out.flush();
        } 
        catch (IOException ex) 
        {
            displayLine("Unable to write..Aborting...");  
            throw ex;
        }        
    }
    
    public String ReadFromServer() throws IOException 
    {
        String readValue="";
        try 
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            readValue= in.readLine();
            if(readValue.contains("$$"))
                readValue = readValue.substring(0, readValue.lastIndexOf("$$"));            
        } 
        catch (IOException ex) 
        {
            displayLine("Unable to read..Aborting...");  
            throw ex;
        }
        return readValue;
    }
    
    public void setupConnection()
    {
        try 
        {
            File configFile = new File("/home/pi/evergreen/configuration.txt");
            displayLine(Boolean.toString(configFile.exists()));
            if(!configFile.exists())
            {
                displayLine("Enter Host IP: ");
                hostIP=readLine();
                clientSocket = new Socket(hostIP,port);
                displayLine("Connection created with " + hostIP);
                username="Kurt";                
                password="Nightcrawler";
            }
            else
            {
                Charset charset = Charset.forName("ISO-8859-1");
                List <String> lines = Files.readAllLines(Paths.get("/home/pi/evergreen","configuration.txt"), charset);
                for(String line : lines)
                {
                    if(line.contains("hostip"))
                    {
                        hostIP=line.split(":")[1];
                    }
                    
                    if(line.contains("username"))
                    {
                        username=line.split(":")[1];
                    }
                    
                    if(line.contains("password"))
                    {
                        password=line.split(":")[1];
                    }                    
                }
                
                clientSocket = new Socket(hostIP,port);
                displayLine("Connection created with " + hostIP);
            }
        } 
        catch (IOException ex) 
        {
            System.out.println("Unable to create Socket..Aborting..");
            return;
        }
        
        try
        {
            displayLine("Authenticating...");
            writeToServer(username+":"+password);
            String status = ReadFromServer();
            if(status=="Authentication Failed")
            {
                disconnect();
                return;
            }
            else
                displayLine("Authentication Successful");
                
            pollingThread = new PollingThread(this,username, clientSocket);
            _pollingThread = new Thread(pollingThread);
            _pollingThread.start();
        }
        catch(IOException ex)
        {
            displayLine("Authentication failed due to IOException");
        }
    }
    
    public void disconnect()
    {
        if(clientSocket!=null)
        {
            try 
            {
                clientSocket.close();
                clientSocket=null;
            } 
            catch (IOException ex) 
            {}
        }
        
        if(_pollingThread!=null && _pollingThread.isAlive())
        {
            try 
            {
                _pollingThread.join();
            } 
            catch (InterruptedException ex) 
            {
                displayLine("Exception encountered while killing Polling Thread");
            }
        }
    }
    
    public void main()
    {        
        setupConnection();
        String loop="N";
        while(!loop.equalsIgnoreCase("Y"))
        {
            displayLine("Enter Y to Quit=> ");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try 
            {
                loop=input.readLine();
            } 
            catch (IOException ex) 
            {
                displayLine("Error in text Input, retry or manually close the program");
            }
        }
        disconnect();
    }
    
    public static void main(String[] args) {
        OculusClient obj = new OculusClient();
        obj.main();
    }
    
}
