package beans;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class FileHandler  {

    protected void writeToFile(String path, String data){
        try {
            OutputStreamWriter writer 
            = new OutputStreamWriter(new FileOutputStream(path, false), StandardCharsets.UTF_8); 
            BufferedWriter bfWriter = new BufferedWriter(writer);
            bfWriter.write(data);
            bfWriter.flush();
            bfWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
}