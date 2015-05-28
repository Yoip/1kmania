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
    private int score,combo,n,v,c,diff;
    private Parser p;
    private Timer timer;
    private Thread thread;
    private Song song;
    private JFrame f;
    private ArrayList<Integer> times;
    private boolean hit;

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
        c = v = score = 0;
        diff = 300;
        setDoubleBuffered(true);
        f=new JFrame("1kmania");
        f.addKeyListener(this);
        f.setContentPane(this);
        f.setSize(800,600);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        timer=new Timer(20,this);
    }

    public void run()
    {
        times = p.times();
        song.start();
        hit=false;
        try{
            thread.sleep(song.getDelay());
            timer.start();
            thread.sleep(times.get(0));
            v=255;
        }catch(Exception e){}
        n = 1;
        while(n<times.size()){
            hit=false;
            try{
                if(times.get(n-1)+diff<=song.getms() && c<n){
                    c=n;
                    if(!hit)
                        combo=0;
                    hit=false;
                }
                if(times.get(n)+20<=song.getms()){
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
        g.drawString(song.getms()+"", 1, 80);
        g.drawString(times.get(n)+"", 1, 100);
        g.drawString(n+"", 1, 120);
        g.drawString(c+"", 1, 140);
        g.drawString("x"+combo, 10, 560);
        v=v<3?0:v-1;
    }

    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }

    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            long off = Math.abs(times.get(c)-song.getms());
            if(off<=140){
                hit=true;
                int k=0;
                if(off<=80)
                    k=300;
                else if(off<=110)
                    k=100;
                else if(off<=140)
                    k=50;
                score+=k+k*combo/5;
                c++;
                combo++;
            }
            else if(off<=diff)
                combo=0;
        }
    }

    public void keyReleased(KeyEvent e)
    {

    }

    public void keyTyped(KeyEvent e){}
}
