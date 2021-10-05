package org.mine;

import java.util.*;
// import java.io.*;
// import javax.swing.JFileChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AppStarter {
    // private String cred_path;
    private String font_fam = "Arial";
    private Font[] fonts = {
        new Font(font_fam, Font.PLAIN, 16),
        new Font(font_fam, Font.PLAIN, 12),
        new Font(font_fam, Font.BOLD,  14)
    };
    private CredentialsIO credio;

    public void getCreds() {
        new WindowNewCredentials(credio, fonts[0], this).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public AppStarter(CredentialsIO credio) {
        // this.cred_path = cred_path;
        this.credio = credio;
    }

    public void start_app() {
        try {
            SQLTalker talker = new SQLTalker(credio);
            new WindowMain(talker, fonts);
        } catch (Exception e) {
            new WindowInfo("Error", 34 + " " + e.getLocalizedMessage(), fonts[0]);
        }
    }
}