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
public class Test extends JPanel implements Runnable,ActionListener,KeyListener
{
    private int t,score,combo,n,v,c;
    private Parser p;
    private Timer timer;
    private Thread thread;
    private Song song;
    private JFrame f;
    private ArrayList<Integer> times;
    
    public static void main(String[] args) throws Exception
    {
        Test r = new Test("sekai.osu");
        Thread current = new Thread(r);
        r.thread = current;
        current.start();
    }

    public Test(String file)
    {
        p = new Parser(file);
        song = Song.makeNew("sekai.wav", p.delay());
        c = t = v = score = 0;
        f=new JFrame("1kmania");
        f.addKeyListener(this);
        f.setContentPane(this);
        f.setSize(800,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        timer=new Timer(20,this);
    }

    public void run()
    {
        times = p.times();
        song.start();
        try{
            thread.sleep(song.getDelay());
            timer.start();
            thread.sleep(times.get(0));
            v=255;
        }catch(Exception e){}
        n = 1;
        while(n<times.size()){
            try{
                int diff = times.get(n)-times.get(n-1);
                if(times.get(n)+diff/2<=t){
                    
                }
                if(times.get(n)+20<=t){
                    v=255;
                    n++;
                }
            }
            catch(Exception e){}
        }
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        setBackground(new Color(v,v,v));
        g.drawString(Long.toString(song.getms()), 400, 300);
        g.setColor(Color.red);
        g.drawString(score+"", 1, 20);
        g.drawString(t+"", 1, 60);
        g.drawString(song.getms()+"", 1, 80);
        g.drawString(times.get(n)+"", 1, 100);
        g.drawString(n+"", 1, 120);
        v=v<3?0:v-2;
    }

    public void actionPerformed(ActionEvent e)
    {
        t+=20;
        repaint();
    }

    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            long off = Math.abs(times.get(c)-song.getms());
            if(off<300)
            {
                score+=300;
                combo++;
            }
        }
    }

    public void keyReleased(KeyEvent e)
    {

    }

    public void keyTyped(KeyEvent e){}
}
