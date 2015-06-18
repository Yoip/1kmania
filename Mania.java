import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The main class that consists of the GUI and input as well as timing and scoring management.
 * 
 * @author Alex, Austin
 * @version 1
 */
public class Mania extends JPanel implements Runnable,ActionListener,KeyListener
{
    private int score,combo,n,v,c,diff,speed,raw;
    private Parser p;
    private Timer timer;
    private Thread thread;
    private Song song;
    private JFrame f;
    private ArrayList<Integer> times;
    private boolean hit;
    private Color col = new Color(240,120,0);

    public static void main(String[] args) throws Exception
    {
        Mania r = new Mania("river.osu");
        Thread current = new Thread(r);
        r.thread = current;
        current.start();
    }

    /**
     * Constructor for new game instances
     * 
     * @param file the osu file to be parsed
     */
    public Mania(String file)
    {
        p = new Parser(file);
        song = Song.makeNew("river.wav", p.delay());
        c = v = score = 0;
        diff = 300;
        speed = 2; //higher value = slower
        setDoubleBuffered(true);
        setBackground(Color.white);
        f=new JFrame("1kmania");
        f.addKeyListener(this);
        f.setContentPane(this);
        f.setSize(800,600);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        timer=new Timer(20,this);
    }

    /**
     * The method to start off the song player and the input logic loop.
     */
    public void run()
    {
        times = p.times();
        song.start();
        //song.seek(80000);
        hit=false;
        try{
            thread.sleep(song.getDelay());
            timer.start();
            thread.sleep(times.get(0));
            //v=255;
        }catch(Exception e){}
        n = 1;
        while(n<times.size()){
            hit=false;
            try{
                if((times.get(n-1)+diff<=song.getms() && c<n) || (n+1<times.size() && times.get(n+1)-20<song.getms())){
                    c=n;
                    if(!hit)
                        combo=0;
                    hit=false;
                }
                if(times.get(n)+20<=song.getms()){
                    n++;
                }
            }
            catch(Exception e){}
        }
    }

    /**
     * The method called by AWT that draws the GUI.
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        int t=(int)song.getms();
        g.drawString(Long.toString(song.getms()), 400, 300);
        g.setColor(Color.red);
        g.drawString(score+"", 10, 20);
        g.drawString(Math.round(10.*raw/c/3)/10.+"%", 10, 40);
        //g.drawString(times.get(n)+"", 1, 100);
        //g.drawString(n+"", 1, 120);
        //g.drawString(c+"", 1, 140);
        g.drawString("x"+combo, 10, 560);
        g.setColor(Color.GRAY);
        g.drawLine(125,0,125,600);
        g.drawLine(200,0,200,600);
        Color o = new Color(240,120,0);
        int low = 0,high = times.size();
        for(int i = 0;i<times.size();i++)
        {
            if(520-(times.get(i)-t)/speed<0)
            {
                high = i;
                break;
            }
        }
        for(int i = 0;i<times.size();i++)
        {
            if(520-(times.get(i)-(int)t)/speed<520)
            {
                low = i;
                break;
            }
        }
        low=low==0?0:low-1;
        for(int i = low;i<high;i++){
            g.setColor(o);
            g.fillRect(125,515-(times.get(i)-(int)t)/speed,75,6);
            g.setColor(Color.black);
            g.drawRect(125,515-(times.get(i)-(int)t)/speed,75,6);
        }
        g.setColor(Color.red);
        g.setColor(col);
        g.fillRect(126,521,74,499);
    }

    /**
     * The method called by the timer to animate the GUI.
     */
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }

    /**
     * The method called by KeyListener when a key is pressed. Analyzes timing and scores hits.
     */
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            col = new Color(255,255,0);
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
                raw+=k;
                c++;
                combo++;
            }
            else if(off<=diff)
                combo=0;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        col = new Color(240,120,0);
    }

    public void keyTyped(KeyEvent e){}
}
