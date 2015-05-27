import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineEvent.Type;

public class AudioPlayer implements Runnable
{
    private Clip clip;
    private AudioListener listener;
    private AudioInputStream ais;
    private long time;
    private boolean stopped;
    private String file;
    private Thread t;

    public static AudioPlayer newPlayer(String f)
    {
        AudioPlayer a = new AudioPlayer();
        a.file = f;
        Thread t = new Thread(a);
        t.start();
        a.t = t;
        return a;
    }
    
    public void run()
    {
        time=0;
        try{
            listener = new AudioListener();
            ais = AudioSystem.getAudioInputStream(new File(file));
        }
        catch(Exception e){}
    }

    public void start() throws Exception
    {
        try{
            clip = AudioSystem.getClip();
            clip.addLineListener(listener);
            clip.open(ais);
            try {
                clip.setMicrosecondPosition(time);
                clip.start();
                listener.waitUntilDone();
            }
            finally{
                clip.close();
            }
        }finally {
            ais.close();
        }
    }

    public void pause()
    {
        time=clip.getMicrosecondPosition();
        clip.stop();
    }

    public void seek(long us)
    {
        clip.setMicrosecondPosition(us);
    }
    
    public void stop()
    {
        time=0;
        clip.stop();
        stopped=true;
    }

    public void end()
    {
        try{
            clip.stop();
            clip.close();
            ais.close();
            t.join();
        }
        catch(Exception e){}
    }

    class AudioListener implements LineListener {
        private boolean done = false;
        @Override public synchronized void update(LineEvent event) {
            Type eventType = event.getType();
            if (eventType == Type.STOP || eventType == Type.CLOSE) {
                done = true;
                notifyAll();
            }
        }

        public void waitUntilDone() throws InterruptedException {
            while (!done) { wait(); }
        }
    }
}