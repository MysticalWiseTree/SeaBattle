import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.Field;

import static java.lang.Math.abs;

public class Main {
    static byte GameStatus = 1;
    //        ВАЖНО!!!!!!!!!!! 0 - недействителен, 1 - Главное меню, 2 - расстановка игроком, 3 - расстановка ИИ, 4 - ход игрока, 5 - ход ИИ
    static JButton CurrentButton1 = null;
    static JButton CurrentButton2 = null;
    private static void PlaceAIShips(JButton[][] FieldMassive, int[][] FieldStatusMassive, int[] Ships, int iterations) {
        System.out.println("AI Starts placing ships. Iterations - " + iterations);
        Boolean fulled = false;
        Boolean horizontal = false;
        int hrz = 0;
        for(int itr = 0; itr < iterations; itr++) {
            System.out.println("Iteration №" + itr + ":");
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++) {
                    if (FieldStatusMassive[i][j] == 1) {
                        if (Math.random()*(1-(iterations-itr)*0.1) > (1 - (double) 1 / (iterations - itr)) && !fulled) {
                            hrz = (int) Math.round(Math.random());
                            System.out.println("Randommed " + hrz + "  ");

                            System.out.println(Ships[1]);

                            if (Math.random() < 0.25 + (float)1/iterations*(itr+1) && Ships[0] < 4) {
                                System.out.println(" 1cell");
                                CreateShip(FieldMassive,FieldMassive[i][j],FieldMassive[i][j],FieldStatusMassive,Ships,new JLabel());
                                Ships[0] = (int)((float)Ships[0] + 0.5);
                            } else if (Math.random() < 0.5 + (float)1/iterations*(itr+1) && Ships[1] < 3 && ((j+1 < 10 && hrz == 1) || (i+1 < 10 && hrz == 0))) {
                                System.out.println(" 2cell");
                                CreateShip(FieldMassive,FieldMassive[i][j],FieldMassive[i+abs(hrz-1)][j+abs(hrz)],FieldStatusMassive,Ships,new JLabel());
                                Ships[1] = (int)((float)Ships[1] + 0.5);
                            } else if (Math.random() < 0.75 + (float)1/iterations*(itr+1) && Ships[2] < 2 && ((j+2 < 10 && hrz == 1) || (i+2 < 10 && hrz == 0))) {
                                System.out.println(" 3cell");
                                CreateShip(FieldMassive,FieldMassive[i][j],FieldMassive[i+2*abs(hrz-1)][j+2*abs(hrz)],FieldStatusMassive,Ships,new JLabel());
                                Ships[2] = (int)((float)Ships[2] + 0.5);
                            } else if (Ships[3] < 1 && ((j+3 < 10 && hrz == 1) || (i+3 < 10 && hrz == 0))) {
                                System.out.println(" 4cell");
                                CreateShip(FieldMassive,FieldMassive[i][j],FieldMassive[i+3*abs(hrz-1)][j+3*abs(hrz)],FieldStatusMassive,Ships,new JLabel());
                                Ships[3] = (int)((float)Ships[3] + 0.5);
                            }
                            if (Ships[3] == 1 && Ships[2] == 2 && Ships[1] == 3 && Ships[0] == 4) {
                                System.out.print("fulled");
                                fulled = true;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Ships placing ended");
    }
    public static void MakeCellsUnavailable(JButton Border1, JButton Border2, int[][] FieldStatusMassive, JButton[][] FieldMassive) {
        int StartX = 0;
        int StartY = 0;
        int EndX = 0;
        int EndY = 0;
        if (Integer.valueOf(Border1.getName())%10 > Integer.valueOf(Border2.getName())%10 && Integer.valueOf(Border1.getName())/10 == Integer.valueOf(Border2.getName())/10) {
            StartX = Integer.valueOf(Border2.getName())%10;
            EndX = Integer.valueOf(Border1.getName())%10;
            StartY = Integer.valueOf(Border1.getName())/10;
            EndY = StartY;
        } else if (Integer.valueOf(Border1.getName())/10 > Integer.valueOf(Border2.getName())/10 && Integer.valueOf(Border1.getName())%10 == Integer.valueOf(Border2.getName())%10) {
            StartY = Integer.valueOf(Border2.getName())/10;
            EndY = Integer.valueOf(Border1.getName())/10;
            StartX = Integer.valueOf(Border1.getName())%10;
            EndX = StartX;
        }else if (Integer.valueOf(Border1.getName())%10 < Integer.valueOf(Border2.getName())%10 && Integer.valueOf(Border1.getName())/10 == Integer.valueOf(Border2.getName())/10) {
            StartX = Integer.valueOf(Border1.getName())%10;
            EndX = Integer.valueOf(Border2.getName())%10;
            StartY = Integer.valueOf(Border1.getName())/10;
            EndY = StartY;
        }else if (Integer.valueOf(Border1.getName())/10 < Integer.valueOf(Border2.getName())/10 && Integer.valueOf(Border1.getName())%10 == Integer.valueOf(Border2.getName())%10) {
            StartY = Integer.valueOf(Border1.getName())/10;
            EndY = Integer.valueOf(Border2.getName())/10;
            StartX = Integer.valueOf(Border1.getName())%10;
            EndX = StartX;
        }else if (Integer.valueOf(Border1.getName())/10 == Integer.valueOf(Border2.getName())/10 && Integer.valueOf(Border1.getName())%10 == Integer.valueOf(Border2.getName())%10) {
            StartX = Integer.valueOf(Border1.getName())%10;
            EndX = StartX;
            StartY = Integer.valueOf(Border1.getName())/10;
            EndY = StartY;
        }
        if (StartX - 1 < 0) {
            StartX += 1;
        }
        if (EndX + 2 > 10) {
            EndX -= 1;
        }
        if (StartY - 1 < 0) {
            StartY += 1;
        }
        if (EndY + 2 > 10) {
            EndY -= 1;
        }
        for(int i = StartX - 1; i < EndX + 2; i++) {
            for(int j = StartY - 1; j < EndY + 2; j++) {
                if (FieldStatusMassive[i][j] == 1) {
                    FieldStatusMassive[i][j] = 2;
                    FieldMassive[i][j].setIcon(new ImageIcon("icons/UnavailableWater.png"));
                }
            }
        }
    }
    public static void TogglePlayerField(JButton[][] FieldMassive, Boolean toggle) {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                FieldMassive[i][j].setEnabled(toggle);
            }
        }
    }
    public static void CreateShip(JButton[][] FieldMassive, JButton Border1, JButton Border2, int[][] FieldStatusMassive, int[] Ships, JLabel InfoLabel) {
        int StartX = Integer.valueOf(Border1.getName())%10;
        int StartY = Integer.valueOf(Border1.getName())/10;
        int EndX = Integer.valueOf(Border2.getName())%10;
        int EndY = Integer.valueOf(Border2.getName())/10;
        byte MultiplierX = 0;
        byte MultiplierY = 0;
        int Delta = 0;
        boolean Availability = true;
        if (StartX > EndX && StartY == EndY) {
            Delta = StartX - EndX;
            StartX += EndX;
            EndX = StartX - EndX;
            StartX -= EndX;

            MultiplierX = -1;
            MultiplierY = 1;
        } else if (StartY > EndY && StartX == EndX) {
            Delta = StartY - EndY;
            StartY += EndY;
            EndY = StartY - EndY;
            StartY -= EndY;

            MultiplierY = -1;
            MultiplierX = 1;
        }else if (StartX < EndX && StartY == EndY) {
            Delta = EndX - StartX;
            MultiplierX = 1;
            MultiplierY = 1;
        }else if (StartY < EndY && StartX == EndX) {
            Delta = EndY - StartY;
            MultiplierY = 1;
            MultiplierX = 1;
        }else if (StartY == EndY && StartX == EndX) {
            MultiplierY = 1;
            MultiplierX = 1;
        }
        for(int i = StartX; i < EndX + 1; i++) {
            for(int j = StartY; j < EndY + 1; j++) {
                if (FieldStatusMassive[i][j] != 1) {
                    Availability = false;
                }
            }
        }
        if (Delta < 4) {
            if ((MultiplierX != 0) && (MultiplierY != 0) && (Ships[Delta]) != 4 - Delta && Availability){
                Ships[Delta] += 1;
                for (int i = StartX; i <= EndX; i++) {
                    for (int j = StartY; j <= EndY; j++) {
                        FieldMassive[i][j].setIcon(null);
                        FieldStatusMassive[i][j] = Delta + 3;
                    }
                }
                MakeCellsUnavailable(Border1,Border2,FieldStatusMassive,FieldMassive);
                System.out.println("Placed a " + (Delta+1) + "cells ship.");
                InfoLabel.setText("<html>Ваши корабли<br>Крейсер:" + Ships[3] + "<br>Подводная лодка: " + Ships[2] + "<br>Эсминец: " + Ships[1] + "<br>Фрегат: " + Ships[0] + "</html>");
            } else if (Ships[Delta] == 4 - Delta) {
                System.out.println("Warning! Trying to spawn a " + (Delta+1) + " cell ship, but we already have " + Ships[Delta] + " of it!");
            } else if (!Availability) {
                System.out.println("Warning! Trying to spawn a cell, but in borders exists unavailable cells, cant spawn!");
            } else {
                System.out.println("Unexpected error, cant spawn!");
            }
        } else {
            System.out.println("Warning! Trying to spawn a " + (Delta+1) + " cell ship!");
        }
        Border1 = null;
        Border2 = null;
    }

    public static void main(String[] args){
        System.out.println("Sea Battle");

        int FieldSpacing = 60;

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

        jFileMenu.add(PlayButton);                      //Создаем "строку", содержащую меню, добавляя кнопки
        jFileMenu.add(ExitButton);

        JMenu jGameMenu = new JMenu("Game");
        JMenuItem HelpButton = new JMenuItem("Help");
        JMenuItem SettingsButton = new JMenuItem("Settings");
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

        JButton[] ControlButtons;
        ControlButtons = new JButton[4];
        for(int i = 0; i < 4; i++) {
            ControlButtons[i] = new JButton();
            ControlButtons[i].setSize(jframe.getWidth()/4,jframe.getHeight()/8);
            ControlButtons[i].setLocation((jframe.getWidth()/4)*i,jframe.getHeight() - jframe.getHeight()/8 - 60);
            ControlButtons[i].setEnabled(true);
            jframe.getContentPane().add(ControlButtons[i]);
        }
        ControlButtons[0].setText("Продолжить");
        ControlButtons[1].setText("Сброс"); //TODO: Сделать эти 3 пункта
        ControlButtons[2].setText("Начать заново");
        ControlButtons[3].setText("Расставить корабли случайно");
        System.out.println("done");

        System.out.print("Field init: ");

        JButton[][] FieldMassive;                       //Будем работать с двумя массивами-полями боя
        JButton[][] FieldEnemyMassive;
        int[] Ships;
        int[] ShipsEnemy;
        int[][] FieldMassiveStatus;
        int[][] FieldEnemyMassiveStatus;   //        ВАЖНО!!!!!!!!!!! 0 - недействителен, 1 - вода, 2 - вода смежна кораблю,
                                          // 3 - одноклетник(фрегат), 4 - двуклетник(эсминец), 5 - трехклетник(подлодка), 6 - четырехклетник(крейсер)
        Ships = new int[4];
        ShipsEnemy = new int[4];
        for(int i = 0; i < 4; i++) {
            Ships[i] = 0;
            ShipsEnemy[i] = 0;
        }
        FieldMassiveStatus = new int[10][10];
        FieldEnemyMassiveStatus = new int[10][10];             //FIXME: Вообще изначально я хотел структурой, но пока так
        FieldMassive = new JButton[10][10];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                FieldMassive[i][j] = new JButton();        //Объявление кнопки, размер, местоположение
                FieldMassive[i][j].setName(Integer.toString(i+j*10));
                FieldMassive[i][j].setSize(20,20);                       //Такая большая формула нужна для того, что бы я мог легко менять разрешение окна, отступ между полями и размер кнопок
                FieldMassive[i][j].setLocation(((jframe.getWidth()/2)-(FieldMassive[i][j].getWidth()*10)-(FieldMassive[i][j].getWidth()/2)+j*FieldMassive[i][j].getWidth())-FieldSpacing/2,140+i*20);
                FieldMassive[i][j].setIcon(new ImageIcon("icons/water.png"));   //создание иконочек для кнопок, а так-же предварительно ее вырубаем
                FieldMassive[i][j].setEnabled(false);
                jframe.getContentPane().add(FieldMassive[i][j]);   //Добавляем кнопку на фрейм
                FieldMassive[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent FieldAction) {
                        if (GameStatus == 2) {
                            JButton Cell = (JButton) FieldAction.getSource();   //Действие при нажатии на кнопку=
                            if (CurrentButton1 == null) {
                                CurrentButton1 = Cell;
                                ActionLabel.setText("<html>Расстанавливайте корабли<br>Выберите точку конца корабля</html>");
                            } else if (CurrentButton2 == null) {
                                CurrentButton2 = Cell;
                                CreateShip(FieldMassive, CurrentButton1, CurrentButton2, FieldMassiveStatus, Ships, ShipsInfoLabel);
                                CurrentButton1 = null;
                                CurrentButton2 = null;
                                if(Ships[0] == 4 && Ships[1] == 3 && Ships[2] == 2 && Ships[3] == 1) {
                                    ControlButtons[0].setEnabled(true);
                                    ActionLabel.setText("<html>Нажмите кнопку<br><h1>ПРОДОЛЖИТЬ</h1></html>");
                                } else {
                                    ActionLabel.setText("<html>Расстанавливайте корабли<br>Выберите точку начала корабля</html>");
                                }
                            }
                        }
                        else {
                            System.exit(0);
                        }
                    }
                });
                FieldMassiveStatus[i][j] = 1;
            }
        }
        FieldEnemyMassive = new JButton[10][10];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                FieldEnemyMassive[i][j] = new JButton();
                FieldEnemyMassive[i][j].setName(Integer.toString(i+j*10));
                FieldEnemyMassive[i][j].setSize(20,20);
                FieldEnemyMassive[i][j].setLocation(((jframe.getWidth()/2)-(FieldMassive[i][j].getWidth()/2)+j*FieldMassive[i][j].getWidth())+FieldSpacing/2,140+i*20);
                FieldEnemyMassive[i][j].setIcon(new ImageIcon("icons/water.png"));
                FieldEnemyMassive[i][j].setEnabled(false);
                jframe.getContentPane().add(FieldEnemyMassive[i][j]);
                FieldEnemyMassive[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent FieldEnemyAction) {
                        System.exit(0);
                    }
                });
                FieldEnemyMassiveStatus[i][j] = 1;
            }
        }


        System.out.println("Done");

        System.out.println("Creating button listeners, that not exists now");
        ExitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ExitApp) {
                System.exit(0);
            }
        });
        HelpButton.addActionListener(new ActionListener() {                       //При нажатии кнопки бла-бла...
            public void actionPerformed(ActionEvent GetHelp) {
                System.exit(0);
            }
        });
        PlayButton.addActionListener(new ActionListener() {                       //При нажатии кнопки бла-бла...
            public void actionPerformed(ActionEvent ExitApp) {
                if (GameStatus == 1) {
                    TogglePlayerField(FieldMassive,true);
                    GameStatus = 2;
                    PlayButton.setEnabled(false);
                    ActionLabel.setText("<html>Расстанавливайте корабли<br>Выберите точку начала корабля</html>");
                    ShipsInfoLabel.setText("<html>Ваши корабли<br>Крейсер:" + Ships[3] + "<br>Подводная лодка: " + Ships[2] + "<br>Эсминец: " + Ships[1] + "<br>Фрегат: " + Ships[0] + "</html>");
                }
            }
        });

        ControlButtons[0].addActionListener(new ActionListener() {                       //При нажатии кнопки бла-бла...
            public void actionPerformed(ActionEvent Continue) {
                GameStatus = 3;
                ControlButtons[0].setEnabled(false);
                ActionLabel.setText("<html>Ожидайте окончания расстановки кораблей ИИ</html>");
                PlaceAIShips(FieldEnemyMassive, FieldEnemyMassiveStatus, ShipsEnemy, 3);
            }
        });

        System.out.println("Done");

        jframe.setVisible(true);    //Делаем фрейм видимым

        System.out.println("Ready.");
    }
}