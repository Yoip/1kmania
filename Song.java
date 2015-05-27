import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineEvent.Type;

public class Song extends Thread
{
    private Clip clip;
    private long delay;

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

    public void run()
    {
        try{
            //wait();
            sleep(delay);
        }catch(Exception e){}
        clip.start();
    }
}
