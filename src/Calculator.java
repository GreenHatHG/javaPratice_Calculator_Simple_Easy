import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Calculator extends JFrame implements ActionListener
{
    JPanel jp1, jp2;
    JButton[] buttons;
    JButton CE;
    JTextField text;
    String[] name = {"1", "2", "3", "+", "4", "5", "6", "-", "7",
            "8", "9", "x", "0", "/", "=", "Back"};
    Calculator()
    {
        super();
        text = new JTextField();
        jp1 = new JPanel();
        jp1.setLayout(new BorderLayout());
        CE = new JButton("CE");
        CE.addActionListener(this);
        jp1.add(text, BorderLayout.CENTER);
        jp1.add(CE, BorderLayout.EAST);
        jp1.setPreferredSize(new Dimension(0, 30));

        jp2 = new JPanel();
        jp2.setLayout(new GridLayout(4, 4));
        buttons = new JButton[name.length];
        for(int i = 0; i < name.length; i++)
        {
            buttons[i] = new JButton(name[i]);
            buttons[i].addActionListener(this);
            jp2.add(buttons[i]);
        }

        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        content.add(jp1, BorderLayout.NORTH);
        content.add(jp2, BorderLayout.CENTER);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        String v = text.getText();
        if(s.equals("CE"))
            text.setText("");
        else if(s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") ||s.equals("5") || s.equals("6") ||
                s.equals("7") || s.equals("8") ||  s.equals("9") || s.equals("0"))
        {
            if(v == null || v.equals(""))
                text.setText(s);
            else
            {
                v += s;
                text.setText(v);
            }
        }

        else if(s.equals("."))
        {
            if(v == null || v.equals(""))
                text.setText("0");
            else
            {
                v += s;
                text.setText(v);
            }
        }

        else if(s.equals("="))
        {
            double temp = calculate(v);
            text.setText("" + temp);
        }
        else if(s.equals("Back"))
        {
            if(!v.isEmpty())
            {
                v = v.substring(0, v.length() - 1);
                text.setText(v);
            }
            else
                text.setText("");
        }
        else
        {
            v += s;
            text.setText(v);
        }
    }

    public double calculate(String s)
    {
        Stack<Double> st1 = new Stack<Double>();
        Stack<Character> st2 = new Stack<Character>();
        for (int i = s.length() - 1; i >= 0; i--)
        {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9')
            {
                    st1.push((double) (c - '0'));
            }
            else
            {
                    if(i == 0) //处理-1+3这种等式
                    {
                        double temp = st1.pop() * (-1);
                        st1.push(temp);
                    }
                    else
                        st2.push(c);
            }
        }
        while(!st2.empty())
        {
            char st2op1 = st2.pop();
            if(st2op1 == 'x')
            {
                double d1 = st1.pop();
                double d2 = st1.pop();
                st1.push(d1 * d2);
            }
            else if(st2op1 == '/')
            {
                double d1 = st1.pop();
                double d2 = st1.pop();
                st1.push(d1 / d2);
            }
            else if(st2op1 == '+' || st2op1 == '-')
            {
                if(st2.empty())
                {
                    if(st2op1 == '+')
                    {
                        double d1 = st1.pop();
                        double d2 = st1.pop();
                        st1.push(d1 + d2);
                    }
                    else
                    {
                        double d1 = st1.pop();
                        double d2 = st1.pop();
                        st1.push(d1 - d2);
                    }
                }
                else
                {
                    char st2op2 = st2.pop();
                    if(st2op2 == 'x' || st2op2 == '/')
                    {
                        double temp = st1.pop();
                        double d1 = st1.pop();
                        double d2 = st1.pop();
                        if( st2op2 == 'x')
                            st1.push(d1 * d2);
                        else
                            st1.push(d1 / d2);
                        st1.push(temp);
                    }
                    else
                    {
                        double d1 = st1.pop();
                        double d2 = st1.pop();
                        if(st2op1 == '+' )
                            st1.push(d1 + d2);
                        else if(st2op1 == '-' )
                            st1.push(d1 - d2 );
                    }
                    st2.push(st2op1);
                }
            }
        }
        return st1.pop();
    }
    public static void main(String[] args)
    {
        Calculator cal = new Calculator();
    }
}
//2x2+2x2/2+3
//2*3/2+3/6
//3/6*6-1/2-1
//1-2x2-3/6
//1+2+3+2*2+1/2