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
public class Mnemonics 
{
    public static enum Actions
    {
        clientlist, datafromothers, senddatanow, senddata, deviceType, extract, SaveDiaryContent, serverReply,
        volup, voldown, setVol, mute, monitorOff, monitorOn, heartBeat        
    };
    public static enum replyStatus { success,failure,internalFailure };
}
