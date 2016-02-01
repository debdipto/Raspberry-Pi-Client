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
public class IMPacket
{
    public String SourceUsername, TargetUsername, Message;
    public Mnemonics.Actions Action;

    public IMPacket()
    {
        SourceUsername = "";
        TargetUsername = "";
        Message = "";
    }
}
