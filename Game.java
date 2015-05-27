import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements ActionListener
{
    private JFrame f;
    private AudioPlayer aplayer;

    public static void main(String[] args)
    {
        Game g = new Game();
        Timer t = new Timer(1000, g);
        t.start();
    }

    public Game()
    {
        super();

        JButton b;
        b = new JButton("Play");
        b.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent a){play();}});
        add(b);

        b = new JButton("Pause");
        b.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){if(aplayer!=null)aplayer.pause();}});
        add(b);

        b = new JButton("Stop");
        b.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){if(aplayer!=null)aplayer.stop();}});
        add(b);

        b = new JButton("Close");
        b.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));}});
        add(b);

        f=new JFrame("1kmania");
        f.setContentPane(this);
        f.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    aplayer.end();
                    notifyAll();
                }
            });
        f.setSize(800,600);
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void play()
    {
        aplayer=AudioPlayer.newPlayer("f.wav");
        try{
            aplayer.start();
        }catch(Exception e){}
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Color c = new Color((float)Math.random(),0,0);
        g.setColor(c);
        g.fillRect(100,100,600,400);
    }

    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }
}
