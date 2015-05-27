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
    private ArrayList<Integer> times;
    private String song;
    public Parser(String name)
    {
        song = name;
    }
    public ArrayList<Integer> times()
    {
        
        try {
        BufferedReader br = new BufferedReader(new FileReader("songs/"+song+".osu"));
        String line = br.readLine();
        boolean asdf = false;
        times = new ArrayList<Integer>();
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
        for(Integer s:times)
        {
            System.out.println(s);
        }
    } 
    catch(Exception e)
    {
        
    }
    return times;
    }
}
