/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import cipher.ServerCipher;
import java.util.ResourceBundle;

/**
 *
 * @author 2dam
 */
public class Main {

    public static void main(String[] args) {
       // System.out.println(ResourceBundle.getBundle("cipher.Server").toString());
        System.out.println(new ServerCipher().decipherServerData());
    }

}
