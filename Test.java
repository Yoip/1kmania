import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Write a description of class Test here.
 * 
 * @author Alex
 * @version 0
 */
public class Test extends JPanel implements Runnable,ActionListener
{
    private int n;
    private Parser p;
    private Timer timer;
    private int v;
    private Thread thread;
    private Song song;
    private JFrame f;

    public static void main(String[] args) throws Exception
    {
        Test r = new Test("another.osu");
        Thread t = new Thread(r);
        r.thread = t;
        t.start();
    }

    public Test(String file)
    {
        p = new Parser(file);
        song = Song.makeNew("f.wav", p.delay());
        n=1;
        v = 0;
        f=new JFrame("1kmania");
        f.setContentPane(this);
        f.setSize(800,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        timer=new Timer(20,this);
        timer.start();
    }

    public void run()
    {
        ArrayList<Integer> ts = p.times();
        song.start();
        try{
            song.notify();
            thread.sleep(ts.get(0));
            v=255;
        }catch(Exception e){}
        while(n<ts.size()){
            try{
                thread.sleep(ts.get(n)-ts.get(n-1));
                v=255;
            }
            catch(Exception e){}
            n++;
        }
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        setBackground(new Color(v,v,v));
        g.drawString(Long.toString(song.getms()), 400, 300);
        v=v<3?0:v-2;
    }

    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }
}
