import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineEvent.Type;

public class Song extends Thread
{
    private Clip clip;
    private long delay;

    public static Song playNew(String f, long delay) throws Exception
    {
        Song s = new Song();
        s.delay = delay;
        s.clip = AudioSystem.getClip();
        s.clip.open(AudioSystem.getAudioInputStream(new File(f)));
        return s;
    }

    public void run()
    {
        try{
            sleep(delay);
        }
        catch(InterruptedException e){}
        clip.start();
    }
}
