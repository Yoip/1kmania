import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineEvent.Type;

/**
 * A class that represents an audio file and can play it.
 * 
 * @author Alex
 * @version 1
 */
public class Song extends Thread
{
    private Clip clip;
    private long delay;

    /**
     * A static method that spawns and returns a song.
     * 
     * @param  f   the audio file to be read
     * @param  delay   the delay before the audio is played
     * @return a new Thread that can be run to play the file after the delay
     */
    public static Song makeNew(String f, long delay)
    {
        Song s = new Song();
        s.delay = delay;
        try{
            s.clip = AudioSystem.getClip();
            s.clip.open(AudioSystem.getAudioInputStream(new File(f)));
        }catch(Exception e){}
        return s;
    }

    /**
     * Runs the thread / plays the song
     */
    public void run()
    {
        try{
            //wait();
            sleep(delay);
        }catch(Exception e){}
        clip.start();
    }
    
    /**
     * Return the current position of the clip in milliseconds.
     * 
     * @return the position of the clip in milliseconds
     */
    public long getms()
    {
        return clip.getMicrosecondPosition()/1000;
    }
    
    /**
     * Return the delay of the clip in milliseconds.
     * 
     * @return the delay of the clip in milliseconds
     */
    public long getDelay()
    {
        return delay;
    }
    
    /**
     * Seek to the a time in the clip.
     * 
     * @param ms the position on milliseconds to jump to
     */
    public void seek(long ms)
    {
        clip.setMicrosecondPosition(ms*1000);
    }
}
