import javax.swing.*;
import java.awt.event.*;

public class Main {
    static byte GameStatus = 1;
    static JButton CurrentButton1 = null;
    static JButton CurrentButton2 = null;
    public static void TogglePlayerField(JButton[] FieldMassive, Boolean toggle) {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                FieldMassive[i+j*10].setEnabled(toggle);
            }
        }
    }
    public static void CreateShip(JButton[] FieldMassive, JButton Border1, JButton Border2, int[] FieldStatusMassive, int[] Ships, JLabel InfoLabel) {
        int StartX = Integer.valueOf(Border1.getName())%10;
        int StartY = Integer.valueOf(Border1.getName())/10;
        int EndX = Integer.valueOf(Border2.getName())%10;
        int EndY = Integer.valueOf(Border2.getName())/10;
        byte MultiplierX = 0;
        byte MultiplierY = 0;
        int Delta = 0;
        if (StartX > EndX && StartY == EndY) {
            MultiplierX = -1;
            MultiplierY = 1;
            Delta = StartX - EndX;
        } else if (StartY > EndY && StartX == EndX) {
            MultiplierY = -1;
            MultiplierX = 1;
            Delta = StartY - EndY;
        }else if (StartX < EndX && StartY == EndY) {
            MultiplierX = 1;
            MultiplierY = 1;
            Delta = EndX - StartX;
        }else if (StartY < EndY && StartX == EndX) {
            MultiplierY = 1;
            MultiplierX = 1;
            Delta = EndY - StartY;
        }
        if ((MultiplierX != 0) && (MultiplierY != 0) && Delta < 4)  {
            Ships[Delta] += 1;
            for(int i = Integer.valueOf(Border1.getName())%10; i <= Integer.valueOf(Border2.getName())%10; i = i  + MultiplierX) {
                for(int j = Integer.valueOf(Border1.getName())/10; j <= Integer.valueOf(Border2.getName())/10; j = j + MultiplierY) {
                    FieldMassive[i+j*10].setIcon(null);
                    FieldStatusMassive[i+j*10] = Delta + 3;
                }
            }
            System.out.println("Placed a " + Delta+1 + "cells ship.");
            InfoLabel.setText("<html>Ваши корабли<br>Крейсер:" + Ships[3] + "<br>Подводная лодка: " + Ships[2] + "<br>Эсминец: " + Ships[1] + "<br>Фрегат: " + Ships[0] + "</html>");
        }
        else {
            Border1 = null;
            Border2 = null;
            System.out.println("Warning! Trying to spawn a " + (Delta+1) + " cell ship!");
        }
    }

    public static void main(String[] args){
        System.out.println("GUI Test Project");

        int FieldSpacing = 60;
         //        ВАЖНО!!!!!!!!!!! 0 - недействителен, 1 - Главное меню, 2 - расстановка игроком, 3 - расстановка ИИ, 4 - ход игрока, 5 - ход ИИ

        System.out.print("Frame init: ");

        JFrame jframe = new JFrame("SeaBattle");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Закрой окно когда оно закроется
        jframe.setSize(650,650);        //Размер окна
        jframe.setResizable(false);          //Ибо нечего тут менять размер окна и делать его полноэкранным
        jframe.setLayout(null);           //Без этого, изменение размеров, положения кнопок будет невозможным
        jframe.setIconImage(new ImageIcon("icons/AppIcon.png").getImage());     //Задаем иконку, однако в конце необходимо прописать getImage,
                                                                                        // т.к. он просит именно изображение

        System.out.println("done");

        System.out.println("Content in frame init");

        System.out.print("Menu init: ");

        JMenu jFileMenu = new JMenu("File");                                              //Создаем меню, параллельно задавая ему название
        JMenuItem PlayButton = new JMenuItem("Play");                       //Создаем кнопки
        JMenuItem ExitButton = new JMenuItem("Exit");
        ExitButton.addActionListener(new ActionListener() {                       //При нажатии кнопки бла-бла...
            public void actionPerformed(ActionEvent ExitApp) {
                System.exit(0);
            }
        });                      //Создаем "строку", содержащую меню, добавляя кнопки
        jFileMenu.add(PlayButton);
        jFileMenu.add(ExitButton);

        JMenu jGameMenu = new JMenu("Game");
        JMenuItem HelpButton = new JMenuItem("Help");
        JMenuItem SettingsButton = new JMenuItem("Settings");
        HelpButton.addActionListener(new ActionListener() {                       //При нажатии кнопки бла-бла...
            public void actionPerformed(ActionEvent GetHelp) {
                System.exit(0);
            }
        });
        jGameMenu.add(HelpButton);
        jGameMenu.add(SettingsButton);

        JMenuBar jmenubar = new JMenuBar();
        jmenubar.add(jFileMenu);                       //И прикрепляем к ней наше меню
        jmenubar.add(jGameMenu);
        jframe.setJMenuBar(jmenubar);

        System.out.println("done");

        System.out.print("Labels init: ");

        JLabel HeaderLabel = new JLabel("Морской бой");
        HeaderLabel.setHorizontalAlignment(JLabel.CENTER);
        HeaderLabel.setVerticalAlignment(JLabel.NORTH);
        HeaderLabel.setBounds(0,20,jframe.getWidth()-20,jframe.getHeight());
        jframe.getContentPane().add(HeaderLabel);

        JLabel ActionLabel = new JLabel("");
        ActionLabel.setHorizontalAlignment(JLabel.CENTER);
        ActionLabel.setVerticalAlignment(JLabel.NORTH);
        ActionLabel.setBounds(0,50,jframe.getWidth()-20,jframe.getHeight());
        jframe.getContentPane().add(ActionLabel);

        JLabel ShipsInfoLabel = new JLabel("asd");
        ShipsInfoLabel.setHorizontalAlignment(JLabel.LEFT);
        ShipsInfoLabel.setVerticalAlignment(JLabel.NORTH);
        ShipsInfoLabel.setBounds(0,340,jframe.getWidth()-20,jframe.getHeight());
        jframe.getContentPane().add(ShipsInfoLabel);

        JLabel ShipsEnemyInfoLabel = new JLabel("asd");
        ShipsEnemyInfoLabel.setHorizontalAlignment(JLabel.RIGHT);
        ShipsEnemyInfoLabel.setVerticalAlignment(JLabel.NORTH);
        ShipsEnemyInfoLabel.setBounds(0,340,jframe.getWidth()-20,jframe.getHeight());
        jframe.getContentPane().add(ShipsEnemyInfoLabel);

        System.out.println("done");

        System.out.print("Control buttons init: ");



        System.out.println("done");

        System.out.print("Field init: ");

        JButton[] FieldMassive;                       //Будем работать с двумя массивами-полями боя
        JButton[] FieldEnemyMassive;
        int[] Ships;
        int[] FieldMassiveStatus;
        int[] FieldEnemyMassiveStatus;   //        ВАЖНО!!!!!!!!!!! 0 - недействителен, 1 - вода, 2 - вода смежна кораблю,
                                          // 3 - одноклетник(фрегат), 4 - двуклетник(эсминец), 5 - трехклетник(подлодка), 6 - четырехклетник(крейсер)
        Ships = new int[4];
        FieldMassiveStatus = new int[100];
        FieldEnemyMassiveStatus = new int[100];             //FIXME: Вообще изначально я хотел структурой, но пока так
        FieldMassive = new JButton[100];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                FieldMassive[i+j*10] = new JButton();        //Объявление кнопки, размер, местоположение
                FieldMassive[i+j*10].setName(Integer.toString(i+j*10));
                FieldMassive[i+j*10].setSize(20,20);                       //Такая большая формула нужна для того, что бы я мог легко менять разрешение окна, отступ между полями и размер кнопок
                FieldMassive[i+j*10].setLocation(((jframe.getWidth()/2)-(FieldMassive[i+j*10].getWidth()*10)-(FieldMassive[i+j*10].getWidth()/2)+j*FieldMassive[i+j*10].getWidth())-FieldSpacing/2,140+i*20);
                FieldMassive[i+j*10].setIcon(new ImageIcon("icons/water.png"));   //создание иконочек для кнопок, а так-же предварительно ее вырубаем
                FieldMassive[i+j*10].setEnabled(false);
                jframe.getContentPane().add(FieldMassive[i+j*10]);   //Добавляем кнопку на фрейм
                FieldMassive[i+j*10].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent FieldAction) {
                        if (GameStatus == 2) {
                            JButton Cell = (JButton) FieldAction.getSource();   //Действие при нажатии на кнопку=
                            if (CurrentButton1 == null) {
                                CurrentButton1 = Cell;
                            } else if (CurrentButton1 != null && CurrentButton2 == null) {
                                CurrentButton2 = Cell;
                                CreateShip(FieldMassive, CurrentButton1, CurrentButton2, FieldMassiveStatus, Ships, ShipsInfoLabel);
                                CurrentButton1 = null;
                                CurrentButton2 = null;
                            }
                        }
                        else {
                            System.exit(0);
                        }
                    }
                });
                FieldMassiveStatus[i+j*10] = 1;
            }
        }
        FieldEnemyMassive = new JButton[100];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                FieldEnemyMassive[i+j*10] = new JButton();
                FieldEnemyMassive[i+j*10].setSize(20,20);
                FieldEnemyMassive[i+j*10].setLocation(((jframe.getWidth()/2)-(FieldMassive[i+j*10].getWidth()/2)+j*FieldMassive[i+j*10].getWidth())+FieldSpacing/2,140+i*20);
                FieldEnemyMassive[i+j*10].setIcon(new ImageIcon("icons/water.png"));
                FieldEnemyMassive[i+j*10].setEnabled(false);
                jframe.getContentPane().add(FieldEnemyMassive[i+j*10]);
                FieldEnemyMassive[i+j*10].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent FieldEnemyAction) {
                        System.exit(0);
                    }
                });
                FieldEnemyMassiveStatus[i+j*10] = 1;
            }
        }

        PlayButton.addActionListener(new ActionListener() {                       //При нажатии кнопки бла-бла...
            public void actionPerformed(ActionEvent ExitApp) {
                if (GameStatus == 1) {
                    TogglePlayerField(FieldMassive,true);
                    GameStatus = 2;
                    PlayButton.setEnabled(false);
                    ActionLabel.setText("Расстанавливайте корабли");
                    ShipsInfoLabel.setText("<html>Ваши корабли<br>Крейсер:" + Ships[3] + "<br>Подводная лодка: " + Ships[2] + "<br>Эсминец: " + Ships[1] + "<br>Фрегат: " + Ships[0] + "</html>");
                }
            }
        });

        System.out.println("Done");

        jframe.setVisible(true);    //Делаем фрейм видимым

        System.out.println("Ready.");
    }
}