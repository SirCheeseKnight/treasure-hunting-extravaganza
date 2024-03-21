package com.cheeseknight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

    public class Main extends JFrame implements KeyListener {
    private static boolean[][] spatiuExcavat;
    private static int[][] hartaNumere;
    private static char[][] hartaCaractere;
    private static int x=8;
    private static int y=8;
    private static int xo=0;
    private static int yo=0;
    private static boolean busola = false;
    private static int bombe = 0;
    static JFrame f;
    JLabel l;
    JLabel k;
    TextArea area;
    Main(){

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            l=new JLabel();
            l.setBounds(50,50,1200,700);
            area = new TextArea();
            area.setBounds(1,1,1, 100);
            area.addKeyListener(this);
            add(l);
            add(area);
            setSize(1280,720);
            setLayout(null);
            setVisible(true);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        public static void bamb(int x, int y)
    {
        int i;
        int j;
        int a=-1;
        int b=1;
        if (x==4) b=0;
        else if (x==5) a=0;
        for (i=a;i<=b;++i) for (j=-1;j<=1;++j) if (!spatiuExcavat[x + i][y + j])
        {
            spatiuExcavat[x+i][y+j]=true;
            if (hartaNumere[x+i][y+j]==1 && (i!=0 && j!=0)) bamb(x+i,y+j);
        }
    }

    public static void main(String[] args) {
        new Main();
        spatiuExcavat = new boolean[10][10];
        hartaCaractere = new char[10][10];
        hartaNumere = new int[10][10];
        int i;
        int j;
        int[] obiecteGenerate = new int[8];
        obiecteGenerate[0] = 33; // spatii goale
        obiecteGenerate[1] = 8; // bombe
        obiecteGenerate[2] = 16; // capcane
        obiecteGenerate[3] = 1; // tunel catre 1,1
        obiecteGenerate[4] = 1; // jurnal
        obiecteGenerate[5] = 1; // busola
        obiecteGenerate[6] = 3; // cartita
        obiecteGenerate[7] = 1; // comoara
        for (i=1;i<=8;++i) for (j=1;j<=8;++j) spatiuExcavat[i][j] = false;
        int rand;
        for (i=0; i<=9;++i)
        {
            hartaNumere[i][0]=0;
            hartaNumere[0][i]=0;
            hartaNumere[9][i]=0;
            hartaNumere[i][9]=0;
            hartaCaractere[i][0]='0';
            hartaCaractere[0][i]='0';
            hartaCaractere[9][i]='0';
            hartaCaractere[i][9]='0';
        }
        Random rnd = new Random();
        hartaNumere[1][1]=3;
        for (i = 1; i <= 8; ++i)
            for (j = 1; j <= 8; ++j) {
                if (i!=1 || j!=1) {
                    outer:
                    {
                        rand = rnd.nextInt(8);
                        if (rand == 3) {
                            if (obiecteGenerate[3] > 0 && i > 4) {
                                hartaNumere[i][j] = 3;
                                obiecteGenerate[3] = 0;
                            }
                        }
                        else if (obiecteGenerate[rand] > 0) {
                            hartaNumere[i][j] = rand;
                            obiecteGenerate[rand] = obiecteGenerate[rand] - 1;
                            break outer;
                        }
                    }
                }
            }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Random rnd = new Random();
        int rand;
        int i,j;
        //if (e.getKeyCode() == KeyEvent.VK_H) JOptionPane.showMessageDialog(null, "Ati gasit comoara! Felicitari! Ati Castigat jocul!");
        if (e.getKeyCode() == KeyEvent.VK_A && y>1) y--;
        if (e.getKeyCode() == KeyEvent.VK_S && x<8 && x!=4) x++;
        if (e.getKeyCode() == KeyEvent.VK_W && x>1 && x!=5) x--;
        if (e.getKeyCode() == KeyEvent.VK_D && y<8) y++;
        if (e.getKeyCode() == KeyEvent.VK_X)
        {
            if(!spatiuExcavat[x][y]) {
                spatiuExcavat[x][y] = true;
                if (hartaNumere[x][y] == 0) System.out.println("Ati gasit o cizma! Traiti inca o zi!");
                else if (hartaNumere[x][y] == 1)
                {
                    int a = rnd.nextInt(80)+1;
                    int b = rnd.nextInt(13)+1;
                    String ecq = new String();
                    JOptionPane.showMessageDialog(null, "Ati gasit o bomba! Rezolvati urmatoarea problema pentru a dezamorsa-o:");
                    ecq = JOptionPane.showInputDialog(a+"*"+b+"=");
                    if (ecq.equals(String.valueOf(a*b))) rand=1;
                    else rand=0;
                    if ( rand == 0 )
                    {
                        JOptionPane.showMessageDialog(null, "Bomba a explodat :(");
                        return;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Bomba nu a explodat :) \n Acum o puteti folosi cu tasta B");
                        bombe++;
                    }
                }
                else if (hartaNumere[x][y] == 2)
                {
                    int a = rnd.nextInt(8980)+1000;
                    int b = rnd.nextInt(8913)+1000;
                    String ecq = new String();
                    JOptionPane.showMessageDialog(null, "Ati gasit o capcana! Rezolvati adunarea ca sa scapati:");
                    ecq = JOptionPane.showInputDialog(a+"+"+b+"=");
                    if (ecq.equals(String.valueOf(a+b))) rand=1;
                    else rand=0;
                    if ( rand == 0 )
                    {
                        JOptionPane.showMessageDialog(null, "Nu ati scapat :(");
                        return;
                    }
                    else JOptionPane.showMessageDialog(null, "Ati Scapat :)");
                }
                else if (hartaNumere[x][y] == 4) JOptionPane.showMessageDialog(null, "Ati gasit un jurnal! Scrie ca prin tunel ( O ) ajungeti pe celalta parte a zidului.");
                else if (hartaNumere[x][y] == 5)
                {
                    JOptionPane.showMessageDialog(null, "Ati gasit o busola! Aceasta va va avertiza cand va gasiti in apropierea comorii!");
                    busola=true;
                }
                else if (hartaNumere[x][y] == 6)
                {
                    if (!busola) JOptionPane.showMessageDialog(null, "O cartita iese din pamant, destul de deranjata, dar va lasa in pace vazand ca nu detineti nimic de valoare");
                    else
                    {
                        busola = false;
                        JOptionPane.showMessageDialog(null, "O cartita iese din pamant, suparata. Va ocheste busola si, fara sa va dati seama, v-o smulge din maini");
                    }
                }
                else if (hartaNumere[x][y]==7)
                {

                    JOptionPane.showMessageDialog(null, "Ati gasit comoara! Felicitari! Ati Castigat jocul!");
                    return;
                }
            }
            else if (hartaNumere[x][y] == 3) {
                if (x!=1 && y!=1)
                {
                    xo=x;
                    yo=y;
                    x = 1;
                    y = 1;
                }
                else
                {
                    x=xo;
                    y=yo;
                }
            }
            else if (hartaNumere[x][y] == 7)
            {
                JOptionPane.showMessageDialog(null, "Ati gasit comoara! Felicitari! Ati Castigat jocul!");
                return;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_B && bombe>0)
        {

            /*if (x==4) for (i=-1;i<=0;++i) for (j=-1;j<=1;++j) spatiuExcavat[x+i][y+j]=true;
                else if (x==5) for (i=0;i<=1;++i) for (j=-1;j<=1;++j) spatiuExcavat[x+i][y+j]=true;
                else for (i=-1;i<=1;++i) for (j=-1;j<=1;++j) spatiuExcavat[x+i][y+j]=true;*/
            bamb(x,y);
            bombe--;
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) return;
        if (busola) {
            if (hartaNumere[x - 1][y - 1] == 7 || hartaNumere[x][y - 1] == 7 || hartaNumere[x - 1][y] == 7 || hartaNumere[x + 1][y - 1] == 7 || hartaNumere[x + 1][y] == 7 || hartaNumere[x][y + 1] == 7 || hartaNumere[x + 1][y + 1] == 7 || hartaNumere[x - 1][y + 1] == 7 || hartaNumere[x][y] == 7)
                System.out.println("Busola Calda");
            else System.out.println("Busola rece");
        }
        l.setText ("Actiuni posibile: \n");
        l.setText (l.getText() + "w = mutare sus ; a = mutare stanga ; s = mutare jos ; d = mutare dreapta ; x = sapare ; q = iesire");
        for (i = 1; i <= 8; ++i) {
            for (j = 1; j <= 8; ++j) {
                if (x == i && y == j)
                {
                    int a=-1;
                    int b=1;
                    int ii;
                    int cc=0;
                    int jj;
                    if (x==4) b=0;
                    else if (x==5) a=0;
                    for (ii=a;ii<=b;++ii) for (jj=-1;jj<=1;++jj) if (hartaNumere[x+ii][y+jj]==1) cc++;
                    hartaCaractere[i][j] = (char) (cc + '0');
                }
                else if (!spatiuExcavat[i][j]) hartaCaractere[i][j] = '~';
                else if (hartaNumere[i][j] == 0) hartaCaractere[i][j] = 'C';
                else if (hartaNumere[i][j] == 1) hartaCaractere[i][j] = 'B';
                else if (hartaNumere[i][j] == 2) hartaCaractere[i][j] = '#';
                else if (hartaNumere[i][j] == 3) hartaCaractere[i][j] = 'O';
                else if (hartaNumere[i][j] == 4) hartaCaractere[i][j] = 'J';
                else if (hartaNumere[i][j] == 5) hartaCaractere[i][j] = '%';
                else if (hartaNumere[i][j] == 6) hartaCaractere[i][j] = '*';
                else if (hartaNumere[i][j] == 7) hartaCaractere[i][j] = '$';
            }
        }
        for (i = 1; i <= 8; ++i) {
            for (j = 1; j <= 8; ++j) {
                System.out.print(hartaCaractere[i][j]);
                System.out.print(' ');
            }
            if (i == 4) {
                System.out.println();
                System.out.print("- - - - - - - -");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
