/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oculusclient;

import flexjson.JSONDeserializer;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author debdipto
 */
public class PollingThread implements Runnable
{
    OculusClient form;
    String username;
    Socket clientSocket;
    
    public PollingThread(OculusClient _form, String _username, Socket _clientSocket)
    {
        form=_form;
        username=_username;
        clientSocket=_clientSocket;
    }
    public void run()
    {
        try
        {
            while(true)
            {   
                String response=form.ReadFromServer(); 
                ReplyPayload replyPayload=null;
                try
                {
                    replyPayload = new JSONDeserializer<ReplyPayload>().deserialize(response, ReplyPayload.class );
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    continue;
                }
                
                switch((int)replyPayload.Action)
                {
                    case 1:
                    {
                        //Do nothing
                    }
                    break;
                        
                    case 14:
                    {
                        
                    }
                    break;
                        
                    case 2:
                    {
                        System.out.println("Data received from " + replyPayload.reply[0].username+ "> "+replyPayload.reply[0].message);
                        
                        switch(replyPayload.reply[0].message)
                        {
                            case "ActivateDev1":
                            {
                                System.out.println("Activating Device 1");
                            }
                            break;
                                
                            case "ActivateDev2":
                            {
                                System.out.println("Activating Device 2");
                            }
                            break;
                                
                            case "ActivateDev3":
                            {
                                System.out.println("Activating Device 3");
                            }
                            break;
                                
                            case "ActivateDev4":
                            {
                                System.out.println("Activating Device 4");
                            }
                            break;
                                
                            case "DeacActivateDev1":
                            {
                                System.out.println("Deactivating Device 1");
                            }
                            break;
                                
                            case "DeacActivateDev2":
                            {
                                System.out.println("Deactivating Device 2");
                            }
                            break;
                                
                            case "DeacActivateDev3":
                            {
                                System.out.println("Deactivating Device 3");
                            }
                            break;
                                
                            case "DeacActivateDev4":
                            {
                                System.out.println("Deactivating Device 4");
                            }
                            break;    
                        }
                    }
                    break;                    
                    
                    default:
                    {
                        System.out.println(response);
                    }
                }
            }
        } 
        catch (IOException ex) 
        {
            System.out.println("Exception encountered inside polling thread..while interacting with Server");
        }       
        catch(Exception ex)
        {
            System.out.println("Generic Exception=> ");
            ex.printStackTrace();
        }
    }
}
