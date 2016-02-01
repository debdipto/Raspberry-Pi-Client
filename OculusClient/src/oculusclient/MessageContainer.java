/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oculusclient;

/**
 *
 * @author debdipto
 */
public class MessageContainer
{
    public String username;
    public String message;

    public MessageContainer()
    {

    }

    public MessageContainer(String _username,String _message)
    {
        username = _username;
        message = _message;
    }
}
