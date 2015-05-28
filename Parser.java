import java.io.*;
import java.util.zip.*;
import java.util.ArrayList;

/**
 * Parses a .osu file (beatmap) to get important information.
 * 
 * @author Austin
 * @version 1
 */
public class Parser
{
    private String file;

    /**
     * Constructor for objects of class asdf
     * 
     * @param f the name of the file to be parsed.
     */
    public Parser(String f)
    {
        file = f;
    }

    /**
     * Gets the delay, or the AudioLeadIn from the .osu file
     * 
     * @return the delay
     */
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

    /**
     * Gets the times in milliseconds of all the notes in the beatmap
     * 
     * @return an ArrayList of the timings of the notes in the beatmap
     */
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
