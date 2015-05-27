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
    private int n;
    private Parser p;
    private Timer timer;
    private int v;
    private Thread thread;
    private Song song;
    private JFrame f;

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
        n=1;
        v = 0;
        f=new JFrame("1kmania");
        f.addKeyListener(this);
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
            thread.sleep(song.getDelay());
            //song.notify();
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
    
    public void keyPressed(KeyEvent e)
    {
        //if(e.getKeyCode()==KeyEvent.VK_SPACE){
            System.out.println(song.getms());
        
    }
    
    public void keyReleased(KeyEvent e)
    {
        
    }
    
    public void keyTyped(KeyEvent e){}
}
