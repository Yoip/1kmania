import java.io.*;
import java.util.zip.*;
import java.util.ArrayList;
/**
 * Write a description of class Runner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Parser
{
    private String file;

    public Parser(String f)
    {
        file = f;
    }

    public long delay()
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                if(line.length()>12){
                    if(line.substring(0,12).equals("AudioLeadIn:"))
                    {
                        return Long.parseLong(line.substring(13,line.length()));
                    }
                }
                line=br.readLine();
            }
        }catch(Exception e){e.printStackTrace();}
        return 0;
    }

    public ArrayList<Integer> times()
    {
        ArrayList<Integer> times = new ArrayList<Integer>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            boolean asdf = false;
            while (line != null) {
                if(line.equals("[HitObjects]"))
                {
                    asdf = true;
                }
                else if(asdf)
                {
                    times.add(Integer.valueOf(line.split(",")[2]));
                }
                line = br.readLine();
            }
        } catch(Exception e){}
        return times;
    }
}
