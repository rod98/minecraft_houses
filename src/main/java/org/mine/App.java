package org.mine;

import java.util.*;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.locks.*;

public class App 
{    
    public static void main( String[] args )
    {
        // TopWindow window = new TopWindow();
        // window.createWindow();
        
        String doc_path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        String cred_path = doc_path + "/.sql_credentials.txt";

        String font_fam = "Arial";
        Font[] fonts = {
            new Font(font_fam, Font.PLAIN, 16),
            new Font(font_fam, Font.PLAIN, 12),
            new Font(font_fam, Font.BOLD,  14)
        };
        
        // System.out.println(args.length);
        if (args.length == 1)
            cred_path = args[0];

        if (args.length < 2) {
            // File cred_file = new File(cred_path);
            try {
                CredentialsIO credio  = new CredentialsIO(cred_path);
                AppStarter    starter = new AppStarter   (credio);

                if (credio.fileExists())
                    starter.start_app();        
                else
                    new WindowNewCredentials(credio, fonts[0], starter).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
            catch (Exception e) {

            }

            // if (!cred_file.exists()) {
                // starter.getCreds();
                // new WindowNewCredentials(cred_path, fonts[0]).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // }
            // else {
                // in the prev. case, I don't even know what to do lol
                // SQLTalker talker = new SQLTalker(cred_path);
                // new WindowMain(talker, fonts);
                // starter.start_app();
            // }
        }
        else {
            System.out.println("Why so many arguments?");
            new WindowInfo("Error", "Too many args", fonts[0]).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
}
