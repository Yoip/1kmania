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
public class GUI extends JPanel implements Runnable,ActionListener,KeyListener
{
    private int score,combo,n,v,c;
    private long t,delay;
    private Parser p;
    private Timer timer;
    private Thread thread;
    private Song song;
    private JFrame f;
    private ArrayList<Integer> times;
    private Color col = new Color(240,120,0);
    
    public static void main(String[] args) throws Exception
    {
        GUI r = new GUI("sekai.osu");
        Thread current = new Thread(r);
        r.thread = current;
        current.start();
    }

    public GUI(String file)
    {
        p = new Parser(file);
        delay = p.delay();
        song = Song.makeNew("sekai.wav", delay);
        c  = v = score = 0;
        t = 0;
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
                    c=n;
                }
                if(times.get(n)+20<=t){
                    v=255;
                    t=song.getms();
                    n++;
                }
            }
            catch(Exception e){}
        }
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawString(Long.toString(song.getms()), 400, 300);
        g.setColor(Color.red);
        g.drawString(score+"", 1, 20);
        g.drawString(t+"", 1, 60);
        g.drawString(song.getms()+"", 1, 80);
        g.drawString(times.get(n)+"", 1, 100);
        g.drawString(n+"", 1, 120);
        
        g.setColor(Color.GRAY);
        g.drawLine(125,0,125,600);
        g.drawLine(200,0,200,600);
        g.setColor(col);
        g.fillRect(125,520,75,500);
        g.setColor(Color.BLACK);
        int low = 0,high = 0;
        for(int i = 0;i<times.size();i++)
        {
            if(520-(times.get(i)-(int)t)/10<0)
            {
                high = i;
                break;
            }
        }
        for(int i = 0;i<times.size();i++)
        {
            if(520-(times.get(i)-(int)t)/10<520)
            {
                low = i;
                break;
            }
        }
        for(int i = low;i<high;i++){
             g.fillRect(125,520-(times.get(i)-(int)t)/10,75,10);
        }
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
            col = new Color(255,255,0);
            if(off<300)
            {
                score+=300;
                combo++;
            }
        }
    }

    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode()==KeyEvent.VK_SPACE)
        {
            col = new Color(240,120,0);
        }
    }
    

    public void keyTyped(KeyEvent e){}
}