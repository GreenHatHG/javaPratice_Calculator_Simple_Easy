import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.math.*;

public class Calculator extends JFrame implements ActionListener
{
    JPanel jp1, jp2;
    JButton[] buttons;
    JButton CE;
    JTextField text;
    String[] name = {"1", "2", "3", "+", "-", "(", "4", "5", "6",
            "x", "/", ")", "7", "8", "9", "0", "Back", "="};
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
        jp2.setLayout(new GridLayout(3, 6));
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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

   /* public double calculate(String s) //没有加括号功能时计算中缀表达式的办法
    {
        Stack<Double> st1 = new Stack<Double>();
        Stack<Character> st2 = new Stack<Character>();
        int i = s.length() - 1;
        while(i >= 0)
        {
            char c = s.charAt(i);
            int indexNum = 0;
            double num = 0;
            if(c >= '0' && c <= '9')
            {
                int j;
                for(j = i; j >= 0; j--)
                    {
                        char c1 = s.charAt(j);
                        if(!(c1 >= '0' && c1 <= '9'))
                            break;
                        double temp= (double) (c1 - '0');
                        num += Math.pow(10, indexNum++) * temp;
                    }
                i = j;
                st1.push(num);
            }
            else
            {
                    if(i == 0) //处理-1+3，+1+2这种等式
                    {
                        double temp = st1.pop();
                        if(c == '-')
                          temp *= (-1);
                        st1.push(temp);
                    }
                    else
                        st2.push(c);
                    i--;
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
                        st2.push(st2op1);
                    }
                    else
                    {
                        double d1 = st1.pop();
                        double d2 = st1.pop();
                        if(st2op1 == '+' )
                            st1.push(d1 + d2);
                        else if(st2op1 == '-' )
                            st1.push(d1 - d2 );
                        st2.push(st2op2);
                    }
                }
            }
        }
        return st1.pop();
    }*/

    public double convert(String s) //将字符串转化为数字
    {
        double ans = 0;
        for(int i = 0; i < s.length(); i++)
            ans = ans * 10 + s.charAt(i) - '0';
        return ans;
    }

    public String suffix(String s)
    {
        Stack<Character>op = new Stack<Character>();
        String ss = "";
        int pos = 0, len = 0; boolean first = true; //为了分割等式中的数字
        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9')
            {
                if (first)
                {
                    pos = i;
                    first = false;
                }
                len++;
                if(i == s.length() - 1)
                {
                    String temp = s.substring(pos, pos+ len);
                    ss += temp + ".";
                }
            }
            else
            {
                String temp = s.substring(pos, pos+ len);
                if(len != 0)
                    ss += temp + ".";
                first = true;
                len = 0;

                if(op.empty())
                    op.push(c);
                else
                {
                    switch(c)
                    {
                        case '+':
                        case '-':
                            while(!op.empty() && op.peek() != '(')
                                ss += op.pop();
                            op.push(c);
                            break;
                        case 'x':
                        case '/':
                            while(!op.empty() && op.peek() != '+' && op.peek() != '-' && op.peek() != '(')
                                ss += op.pop();
                            op.push(c);
                            break;
                        case '(':
                            op.push(c);
                            break;
                        case ')':
                            while(!op.empty() && op.peek() != '(')
                                ss += op.pop();
                            op.pop();
                            break;
                    }
                }
            }
        }
        while(!op.empty())
            ss += op.pop();
        return ss;
    }

    public double calculate(String s)
    {
        s = suffix(s);
        System.out.print(s);
        Stack<Double>sta = new Stack<Double>();
        int len = 0, pos = 0; boolean first = true;
        for(int i = 0; i < s.length() ; i++)
        {
            char c = s.charAt(i);
            if(c>= '0' && c <= '9')
            {
                if(first)
                {
                    first = false;
                    pos = i;
                }
                len++;
            }
            else if(c == '.')
            {
                sta.push(convert(s.substring(pos, pos + len)));
                first = true;
                len = 0;
            }
            else
            {
                double n1 = sta.pop();
                double n2 = sta.pop();
                switch(c)
                {
                    case '-': {sta.push(n2 - n1); break;}
                    case '+': {sta.push(n2 + n1); break;}
                    case 'x': {sta.push(n2 * n1); break;}
                    case '/': {sta.push(n2 / n1); break;}
                }
            }
        }
        return sta.pop();
    }

    public static void main(String[] args)
    {
        Calculator cal = new Calculator();
    }
}
//2x2+2x2/2+3 = 9
//2*3/2+3/6
//3/6*6-1/2-1
//1-2x2-3/6
//1+2+3+2*2+1/2
