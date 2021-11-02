package org.mine;
import java.io.*;
import java.util.Properties;

public class CredentialsIO {
    private String cred_path;
    private String url = "";
    private Properties props;

    public boolean fileExists() {
        File cred_file = new File(cred_path);

        return cred_file.exists();
    }

    public String url() {
        return this.url;
    }

    public Properties properties() {
        return this.props;
    }

    public CredentialsIO(String cred_path) {
        this.cred_path = cred_path;
        // props = new Properties();
        // read();
    }

    public void read() throws Exception {
        System.out.println("Trying to openg file: " + cred_path);
        String type = "postgresql";
        String db   = "minehouses";

        props = new Properties();
        url   = "192.168.195.28";
       
        BufferedReader br = new BufferedReader(new FileReader(cred_path));
        String line = br.readLine();

        while (line != null) {
            String param = line.split("=")[0].trim();
            String value = line.split("=")[1].trim();
            System.out.println("[" + param + "] " + value);

            if (param.equals("url"))
                url = value;
            else if (param.equals("type"))
                type = value;
            else if (param.equals("database") || param.equals("db"))
                db = value;
            else
                props.setProperty(param, value);

            line = br.readLine();
        }
        br.close();

        url = "jdbc:" + type + "://" + url + "/" + db;
    }

    public void write(String url, String type, String db, String user, String password, String ssl) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(cred_path, true));
        writer.write ("");
        writer.append("url = "      + url      + "\n");
        writer.append("type = "     + type     + "\n");
        writer.append("db = "       + db       + "\n");
        writer.append("user = "     + user     + "\n");
        writer.append("password = " + password + "\n");
        writer.append("ssl = "      + ssl      + "\n");
        
        writer.close();   
    }
}
