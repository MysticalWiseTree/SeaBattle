import javax.swing.*;
import static java.lang.Math.abs;

public class Main {
    static byte GameStatus = 1;
    //        ВАЖНО!!!!!!!!!!! 0 - недействителен, 1 - Главное меню, 2 - расстановка игроком, 3 - расстановка ИИ, 4 - ход игрока, 5 - ход ИИ, 6 - игра окончена
    static JButton CurrentButton1 = null;
    static JButton CurrentButton2 = null;
    public static boolean CheckForRemainingShips(int[][] FieldStatusMassive) {
        boolean Remain = false;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (FieldStatusMassive[i][j] > 2 && FieldStatusMassive[i][j] < 7) {
                    Remain = true;
                }
            }
        }
        System.out.println(Remain);
        return Remain;
    }
    public static void SearchShootCell(JButton[][] FieldMassive, int[][] FieldStatusMassive, int[] WreckedShips) {
        int correctorX = 0;
        int correctorXE = 0;
        int correctorY = 0;
        int correctorYE = 0;
        boolean Shooted = false;
        if (WreckedShips[0] == 0) {
            int x = (int)(Math.random()*10);
            int y = (int)(Math.random()*10);
            System.out.println(x + "x" + y + "   " + (FieldStatusMassive[x][y]>0 && FieldStatusMassive[x][y]<7));
            if(FieldStatusMassive[x][y]>0 && FieldStatusMassive[x][y]<7) {
                ShootAI(FieldMassive,FieldStatusMassive,x,y,WreckedShips,true);
                System.out.println("Default shoot");
            } else {
                for (int i = 0; i < 10; i++) {
                    for(int j = 0; j < 10; j++) {
                        if (Math.random() < 0.2 && FieldStatusMassive[i][j]>0 && FieldStatusMassive[i][j]<7) {
                            ShootAI(FieldMassive,FieldStatusMassive,i,j,WreckedShips,true);
                            System.out.println("Shoot with a ''probable shot''");
                            j += 10;
                            i += 10;
                            Shooted = true;
                        }
                    }
                }
                if (!Shooted) {
                    for (int i = 0; i < 10; i++) {
                        for(int j = 0; j < 10; j++) {
                            if (FieldStatusMassive[i][j]>0 && FieldStatusMassive[i][j]<7) {
                                ShootAI(FieldMassive,FieldStatusMassive,i,j,WreckedShips,true);
                                System.out.println("Shoot with a 100% shot");
                                j += 10;
                                i += 10;
                            }
                        }
                    }
                }
            }
        } else {
            if (WreckedShips[4] == 2) {
                if(WreckedShips[1] == 0) {
                    correctorX = 2;
                    System.out.println("correctorX");
                } else if (WreckedShips[1] == 9) {
                    correctorXE = 2;
                    System.out.println("correctorXe");
                }
                if(WreckedShips[2] == 0) {
                    correctorY = 1;
                    System.out.println("correctorY");
                } else if (WreckedShips[2] == 9) {
                    correctorYE = 1;
                    System.out.println("correctorYe");
                }
                System.out.println(WreckedShips[1] + "x" + WreckedShips[2]);
                for (int j = WreckedShips[2] - 1 + correctorY; j < WreckedShips[2] + 2 - correctorYE; j++) {
                    if (j == WreckedShips[2]){
                        for (int i = WreckedShips[1] - 1 + correctorX; i < WreckedShips[1] + 2 - correctorXE; i = i + 2) {
                            if (!(FieldStatusMassive[i][j] == 0) && FieldStatusMassive[i][j] < 7 && !Shooted) {
                                if(IsCellHaveShip(FieldStatusMassive,i,j)) {
                                    System.out.println("1= "+WreckedShips[1]);
                                    System.out.println("2= "+WreckedShips[2]);
                                    System.out.println("i= "+i);
                                    System.out.println("j= "+j);
                                    if(WreckedShips[1] < i) {
                                        WreckedShips[5] = WreckedShips[1];
                                        WreckedShips[6] = i;
                                    } else {
                                        WreckedShips[5] = i;
                                        WreckedShips[6] = WreckedShips[1];
                                    }
                                    if(FieldStatusMassive[i][j] == 4) {
                                        WreckedShips[4] = 0;
                                    }
                                    System.out.println("5= "+WreckedShips[5]);
                                    System.out.println("6= "+WreckedShips[6]);
                                }
                                System.out.println("Try in " + i + " " + j);
                                if (ShootAI(FieldMassive,FieldStatusMassive,i,j,WreckedShips,false)) {
                                    WreckedShips[4] = 0;
                                    System.out.println("POPAL! Vertical " + WreckedShips[4]);
                                }
                                Shooted = true;
                                i = 10;
                                j = 10;
                            }
                        }
                    } else {
                        for (int i = WreckedShips[1]; i < WreckedShips[1]+2; i = i + 4) {
                            if (!(FieldStatusMassive[i][j] == 0) && FieldStatusMassive[i][j] < 7 && !Shooted) {
                                if(IsCellHaveShip(FieldStatusMassive,i,j)) {
                                    System.out.println("1= "+WreckedShips[1]);
                                    System.out.println("2= "+WreckedShips[2]);
                                    System.out.println("i= "+i);
                                    System.out.println("j= "+j);
                                    if(WreckedShips[2] < j) {
                                        WreckedShips[5] = WreckedShips[2];
                                        WreckedShips[6] = j;
                                    } else {
                                        WreckedShips[5] = j;
                                        WreckedShips[6] = WreckedShips[2];
                                    }
                                    if(FieldStatusMassive[i][j] == 4) {
                                        WreckedShips[4] = 1;
                                    }
                                    System.out.println("5= "+WreckedShips[5]);
                                    System.out.println("6= "+WreckedShips[6]);
                                }
                                System.out.println("Try in " + i + " " + j);
                                if (ShootAI(FieldMassive,FieldStatusMassive,i,j,WreckedShips,false)) {
                                    WreckedShips[4] = 1;
                                    System.out.println("POPAL! Horizontal " + WreckedShips[4]);
                                }
                                Shooted = true;
                                i = 10;
                                j = 10;
                            }
                        }
                    }
                }
            } else {
                int xg = WreckedShips[1];
                int yg = WreckedShips[2];
                boolean end = false;
                System.out.println("Trying to contiune destroying");
                if (WreckedShips[4] == 0) {
                    if ((WreckedShips[5] == 10) && (WreckedShips[6] == 10)) {
                        WreckedShips[5] = xg;
                        WreckedShips[6] = xg;
                    }
                    while (!(FieldStatusMassive[xg][yg] ==0) && !Shooted) {
                        System.out.println("    " + xg + "x" + yg);
                        if (!(FieldStatusMassive[xg][yg]==1) && !(FieldStatusMassive[xg][yg]>6)) {
                            if(IsCellHaveShip(FieldStatusMassive,xg,yg)) {
                                WreckedShips[6] = xg;
                            }
                            if (ShootAI(FieldMassive,FieldStatusMassive,xg,yg,WreckedShips,false)) {
                                WreckedShips[6] = xg;
                            } else {
                                System.out.println("miss!");
                            }
                            Shooted = true;
                        } else {
                            xg +=1;
                        }
                        System.out.println("XG=" + xg);
                        if (xg > 9 || FieldStatusMassive[xg][yg] == 0) {end = true;}
                    }
                    if (end) {
                        xg = WreckedShips[1];
                        while (!(FieldStatusMassive[xg][yg] ==0) && !Shooted) {
                            System.out.println("    " + xg + "x" + yg);
                            if (!(FieldStatusMassive[xg][yg]==1) && !(FieldStatusMassive[xg][yg]>6)) {
                                if(IsCellHaveShip(FieldStatusMassive,xg,yg)) {
                                    WreckedShips[5] = xg;
                                }
                                if (ShootAI(FieldMassive,FieldStatusMassive,xg,yg,WreckedShips,false)) {
                                    WreckedShips[5] = xg;
                                } else {
                                    System.out.println("miss!");
                                }
                                Shooted = true;
                            } else {
                                xg -=1;
                            }
                            System.out.println("XG=" + xg);
                        }
                    }
                } else {
                    if ((WreckedShips[5] == 10) && (WreckedShips[6] == 10)) {
                        WreckedShips[5] = yg;
                        WreckedShips[6] = yg;
                    }
                    while (!(FieldStatusMassive[xg][yg] ==0) && !Shooted) {
                        System.out.println("    " + xg + "x" + yg);
                        if (!(FieldStatusMassive[xg][yg]==1) && !(FieldStatusMassive[xg][yg]>6)) {
                            if(IsCellHaveShip(FieldStatusMassive,xg,yg)) {
                                WreckedShips[6] = yg;
                            }
                            if (ShootAI(FieldMassive,FieldStatusMassive,xg,yg,WreckedShips,false)) {
                                WreckedShips[6] = yg;
                            } else {
                                System.out.println("miss!");
                            }
                            Shooted = true;
                        } else {
                            yg +=1;
                        }
                        System.out.println("YG=" + yg);
                        if (yg > 9 || FieldStatusMassive[xg][yg] ==0) {end = true;}
                    }
                    if (end) {
                        yg = WreckedShips[2];
                        while (!(FieldStatusMassive[xg][yg] ==0) && !Shooted) {
                            System.out.println("    " + xg + "x" + yg);
                            if (!(FieldStatusMassive[xg][yg]==1) && !(FieldStatusMassive[xg][yg]>6)) {
                                if(IsCellHaveShip(FieldStatusMassive,xg,yg)) {
                                    WreckedShips[5] = yg;
                                }
                                if (ShootAI(FieldMassive,FieldStatusMassive,xg,yg,WreckedShips,false)) {
                                    WreckedShips[5] = yg;
                                } else {
                                    System.out.println("miss!");
                                }
                                Shooted = true;
                            } else {
                                yg -=1;
                            }
                            System.out.println("YG=" + yg);
                        }
                    }
                }
            }
        }
        GameStatus = 4;
    }
    public static boolean ShootAI(JButton[][] FieldMassive, int[][] FieldStatusMassive, int x, int y, int[] WreckedShips, boolean isFirst) {
        boolean isShip = false;
        if (FieldStatusMassive[x][y] == 3 || FieldStatusMassive[x][y] == 4 || FieldStatusMassive[x][y] == 5 || FieldStatusMassive[x][y] == 6) {
            FieldMassive[x][y].setIcon(new ImageIcon("icons/UnavailableWater.png"));
            WreckedShips[3] += 1;
            FieldStatusMassive[x][y] += 4;
            if(isFirst) {
                WreckedShips[0] = FieldStatusMassive[x][y];
                WreckedShips[1] = x;
                WreckedShips[2] = y;
                WreckedShips[3] = 1;
                WreckedShips[4] = 2;
                WreckedShips[5] = x;
                WreckedShips[6] = y;
            }
            if (WreckedShips[0] == 7 || WreckedShips[0] == 8 && WreckedShips[3] == 2 || WreckedShips[0] == 9 && WreckedShips[3] == 3 || WreckedShips[0] == 10 && WreckedShips[3] == 4) {
                System.out.println("DestroyedVert " + WreckedShips[5] + "x" + WreckedShips[2] + "    " + WreckedShips[6] + "x" + WreckedShips[2]);
                System.out.println("DestroyedHoriz " + WreckedShips[1] + "x" + WreckedShips[5] + "    " + WreckedShips[1] + "x" + WreckedShips[6]);
                System.out.println(WreckedShips[4]);
                if (FieldStatusMassive[x][y] == 7) {
                    MakeCellsUnavailableOrNeighbor(FieldMassive[x][y],FieldMassive[x][y],FieldStatusMassive, false);
                } else {
                    if (WreckedShips[4] == 0) {
                        MakeCellsUnavailableOrNeighbor(FieldMassive[WreckedShips[5]][WreckedShips[2]],FieldMassive[WreckedShips[6]][WreckedShips[2]],FieldStatusMassive, false);
                    } else if (WreckedShips[4] == 1) {
                        MakeCellsUnavailableOrNeighbor(FieldMassive[WreckedShips[1]][WreckedShips[5]],FieldMassive[WreckedShips[1]][WreckedShips[6]],FieldStatusMassive, false);
                    }
                }
                WreckedShips[0] = 0;
                WreckedShips[1] = 0;
                WreckedShips[2] = 0;
                WreckedShips[3] = 0;
                WreckedShips[4] = 2;
                WreckedShips[5] = 10;
                WreckedShips[6] = 10;
            } else {
                System.out.println("POPAL!");
            }
            isShip = true;
        } else {
            FieldMassive[x][y].setIcon(new ImageIcon("icons/emptywater.png"));
            FieldStatusMassive[x][y] = 0;
        }
        return isShip;
    }
    public static void PlaceAIShips(JButton[][] FieldMassive, int[][] FieldStatusMassive, int[] Ships, int iterations, boolean IsCellsVisible) {
        System.out.println("AI Starts placing ships. Iterations - " + iterations);
        boolean fulled = false;
        int hrz;
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
                                CreateShip(FieldMassive,FieldMassive[i][j],FieldMassive[i][j],FieldStatusMassive,Ships,new JLabel(), IsCellsVisible);
                                Ships[0] = (int)((float)Ships[0] + 0.5);
                            } else if (Math.random() < 0.5 + (float)1/iterations*(itr+1) && Ships[1] < 3 && ((j+1 < 10 && hrz == 1) || (i+1 < 10 && hrz == 0))) {
                                System.out.println(" 2cell");
                                CreateShip(FieldMassive,FieldMassive[i][j],FieldMassive[i+abs(hrz-1)][j+abs(hrz)],FieldStatusMassive,Ships,new JLabel(), IsCellsVisible);
                                Ships[1] = (int)((float)Ships[1] + 0.5);
                            } else if (Math.random() < 0.75 + (float)1/iterations*(itr+1) && Ships[2] < 2 && ((j+2 < 10 && hrz == 1) || (i+2 < 10 && hrz == 0))) {
                                System.out.println(" 3cell");
                                CreateShip(FieldMassive,FieldMassive[i][j],FieldMassive[i+2*abs(hrz-1)][j+2*abs(hrz)],FieldStatusMassive,Ships,new JLabel(), IsCellsVisible);
                                Ships[2] = (int)((float)Ships[2] + 0.5);
                            } else if (Ships[3] < 1 && ((j+3 < 10 && hrz == 1) || (i+3 < 10 && hrz == 0))) {
                                System.out.println(" 4cell");
                                CreateShip(FieldMassive,FieldMassive[i][j],FieldMassive[i+3*abs(hrz-1)][j+3*abs(hrz)],FieldStatusMassive,Ships,new JLabel(), IsCellsVisible);
                                Ships[3] = (int)((float)Ships[3] + 0.5);
                            }
                            if (Ships[3] == 1 && Ships[2] == 2 && Ships[1] == 3 && Ships[0] == 4) {
                                System.out.println("Field filled!");
                                itr = iterations;
                                fulled = true;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Ships placing ended");
    }
    public static boolean IsCellHaveShip(int[][] FieldStatusMassive, int x, int y) {
        return FieldStatusMassive[x][y] == 3 || FieldStatusMassive[x][y] == 4 || FieldStatusMassive[x][y] == 5 || FieldStatusMassive[x][y] == 6;
    }
    public static void MakeCellsUnavailableOrNeighbor(JButton Border1, JButton Border2, int[][] FieldStatusMassive, boolean CellsType) {
        int Border1x = Integer.parseInt(Border1.getName())%10;
        int Border1y = Integer.parseInt(Border1.getName())/10;
        int Border2x = Integer.parseInt(Border2.getName())%10;
        int Border2y = Integer.parseInt(Border2.getName())/10;
        int StartX = 0;
        int StartY = 0;
        int EndX = 0;
        int EndY = 0;
        if (Border1x > Border2x && Border1y == Border2y) {
            StartX = Border2x;
            EndX = Border1x;
            StartY = Border1y;
            EndY = StartY;
        } else if (Border1y > Border2y && Border1x == Border2x) {
            StartY = Border2y;
            EndY = Border1y;
            StartX = Border1x;
            EndX = StartX;
        }else if (Border1x < Border2x && Border1y == Border2y) {
            StartX = Border1x;
            EndX = Border2x;
            StartY = Border1y;
            EndY = StartY;
        }else if (Border1y < Border2y && Border1x == Border2x) {
            StartY = Border1y;
            EndY = Border2y;
            StartX = Border1x;
            EndX = StartX;
        }else if (Border1y == Border2y) {
            StartX = Border1x;
            EndX = StartX;
            StartY = Border1y;
            EndY = StartY;
        }
        if (StartX == 0) {
            StartX += 1;
        }
        if (EndX == 9) {
            EndX -= 1;
        }
        if (StartY == 0) {
            StartY += 1;
        }
        if (EndY == 9) {
            EndY -= 1;
        }
        System.out.println(StartX + " " + EndX + "x" + StartY + " " + EndY);
        for(int i = StartX - 1; i < EndX + 2; i++) {
            for(int j = StartY - 1; j < EndY + 2; j++) {
                if(CellsType) {
                    if (FieldStatusMassive[i][j] == 1) {
                        FieldStatusMassive[i][j] = 2;
                    }
                } else {
                    FieldStatusMassive[i][j] = 11;
                    //FieldMassive[i][j].setIcon(new ImageIcon("icons/AppIcon.png"));
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
    public static void CreateShip(JButton[][] FieldMassive, JButton Border1, JButton Border2, int[][] FieldStatusMassive, int[] Ships, JLabel InfoLabel, boolean IsCellsVisible) {
        int StartX = Integer.parseInt(Border1.getName())%10;
        int StartY = Integer.parseInt(Border1.getName())/10;
        int EndX = Integer.parseInt(Border2.getName())%10;
        int EndY = Integer.parseInt(Border2.getName())/10;
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
        }else if (StartY == EndY) {
            MultiplierY = 1;
            MultiplierX = 1;
        }
        for(int i = StartX; i < EndX + 1; i++) {
            for(int j = StartY; j < EndY + 1; j++) {
                if (FieldStatusMassive[i][j] != 1) {
                    Availability = false;
                    j = EndY;
                    i = EndX;
                }
            }
        }
        if (Delta < 4) {
            if ((MultiplierX != 0) && (MultiplierY != 0) && (Ships[Delta]) != 4 - Delta && Availability){
                Ships[Delta] += 1;
                for (int i = StartX; i <= EndX; i++) {
                    for (int j = StartY; j <= EndY; j++) {
                        if (IsCellsVisible) {
                            FieldMassive[i][j].setIcon(null);
                        }
                        FieldStatusMassive[i][j] = Delta + 3;
                    }
                }
                MakeCellsUnavailableOrNeighbor(Border1,Border2,FieldStatusMassive, true);
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
    }

    public static void main(String[] args){
        System.out.println("Sea Battle");

        int FieldSpacing = 60;
        int[] AIHasWreckedShips; // Эта переменная - мозг алгоритма-противника, наличие большого числа переменных необходимо для облегчения алгоритма
        AIHasWreckedShips = new int[7]; // 0 - тип корабля, 1 и 2 - координата "центра", 3 сколько его клеток уничтожено, 4 вертикален или горизонтален(0-вертикаль, 1-горизонталь,2-неизвестно), 5 координата начала, 6 координата конца

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

        JMenu jGameMenu = new JMenu("Help");
        JMenuItem HelpButton = new JMenuItem("Help");
        jGameMenu.add(HelpButton);

        JMenuBar jmenubar = new JMenuBar();
        jmenubar.add(jFileMenu);                       //И прикрепляем к ней наше меню
        jmenubar.add(jGameMenu);
        jframe.setJMenuBar(jmenubar);

        System.out.println("done");

        System.out.print("HelpDialog init: ");

        JDialog helpmepleeeaseeee = new JDialog(jframe, "Помогите мне, я не могу выиграть");
        helpmepleeeaseeee.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        helpmepleeeaseeee.setSize(750, 440);
        helpmepleeeaseeee.setResizable(false);
        JLabel HelpmeLabel = new JLabel("<html>Добро пожаловать в симулятор проиг...<br><h1>Добро пожаловать в хардкорный морской бой!</h1><br>" +
                "Особенность этого морского боя - его сложность(нет). Вы узнаете какой корабль потопили только тогда, когда промажете.<br>" +
                "Правила этого морского боя идентичны правилам стандартного, разве что я 1-но, 2-х, 3-х, 4-х клеточные корабли назвал так:<br>" +
                "1 клетка - Фрегат,<br>" +
                "2 клетки - Эсминец,<br>" +
                "3 клетки - Подводная лодка,<br>" +
                "4 клетки - Крейсер.<br>" +
                "Для начала игры необходимо нажать в верхнем меню File->Play. Далее рекомендуется следовать текстовым подсказкам вверху экрана.<br>" +
                "Вы можете в любой момент игры после расстановки кораблей вернуться в главное меню и начать игру заново, нажав 'Начать заново'<br>" +
                "Если вы устроили на своем поле плохую на ваш взгляд расстановку(шитрасстановку), нажмите 'Сбросить'<br>" +
                "Вы можете расставить корабли случайно, для этого нажмите 'Расставить корабли случайно'<br><br>" +
                "Для повышения ВАШИХ шансов на победу, в некоторых моментах алгоритм был упрощен.<br>" +
                "<h3>Приятной игры :)</h3></html>");
        helpmepleeeaseeee.getContentPane().add(HelpmeLabel);

        System.out.println("done");

        System.out.println("done");

        System.out.print("Labels init: ");

        JLabel HeaderLabel = new JLabel("<html><h2>Морской бой</h2></html>");
        HeaderLabel.setHorizontalAlignment(JLabel.CENTER);
        HeaderLabel.setVerticalAlignment(JLabel.NORTH);
        HeaderLabel.setBounds(0,20,jframe.getWidth()-20,jframe.getHeight());
        jframe.getContentPane().add(HeaderLabel);

        JLabel ActionLabel = new JLabel("");
        ActionLabel.setHorizontalAlignment(JLabel.CENTER);
        ActionLabel.setVerticalAlignment(JLabel.NORTH);
        ActionLabel.setBounds(0,60,jframe.getWidth()-20,jframe.getHeight());
        jframe.getContentPane().add(ActionLabel);

        JLabel ShipsInfoLabel = new JLabel("");
        ShipsInfoLabel.setHorizontalAlignment(JLabel.LEFT);
        ShipsInfoLabel.setVerticalAlignment(JLabel.NORTH);
        ShipsInfoLabel.setBounds(0,340,jframe.getWidth()-20,jframe.getHeight());
        jframe.getContentPane().add(ShipsInfoLabel);

        System.out.println("done");

        System.out.print("Control buttons init: ");

        JButton[] ControlButtons;
        ControlButtons = new JButton[4];
        for(int i = 0; i < 4; i++) {
            ControlButtons[i] = new JButton();
            ControlButtons[i].setSize(jframe.getWidth()/4,jframe.getHeight()/8);
            ControlButtons[i].setLocation((jframe.getWidth()/4)*i,jframe.getHeight() - jframe.getHeight()/8 - 60);
            ControlButtons[i].setEnabled(false);
            jframe.getContentPane().add(ControlButtons[i]);
        }
        ControlButtons[0].setText("Продолжить");
        ControlButtons[1].setText("Сброс");                //TODO: Сделать эти 3 пункта
        ControlButtons[2].setText("Начать заново");
        ControlButtons[3].setText("Расставить корабли случайно");
        System.out.println("done");

        System.out.print("Field init: ");

        JButton[][] FieldMassive;                       //Будем работать с двумя массивами-полями боя
        JButton[][] FieldEnemyMassive;
        int[] Ships;
        int[] ShipsEnemy;
        int[][] FieldMassiveStatus;
        int[][] FieldEnemyMassiveStatus;   //        ВАЖНО!!!!!!!!!!! 0 - известная вода, 1 - вода, 2 - вода смежна кораблю,
                                          // 3 - одноклетник(фрегат)(7 - уничтоженный), 4 - двуклетник(эсминец)(8 - уничтоженный),
                                        //5 - трехклетник(подлодка)(9 - уничтоженный), 6 - четырехклетник(крейсер)(10 - уничтоженный)
                                        //11 - Известная смежная клетка
        Ships = new int[4];
        ShipsEnemy = new int[4];
        for(int i = 0; i < 4; i++) {
            Ships[i] = 0;
            ShipsEnemy[i] = 0;
        }
        FieldMassiveStatus = new int[10][10];
        FieldEnemyMassiveStatus = new int[10][10];
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
                FieldMassive[i][j].addActionListener(FieldAction -> {
                    if (GameStatus == 2) {
                        JButton Cell = (JButton) FieldAction.getSource();
                        if (CurrentButton1 == null) {
                            CurrentButton1 = Cell;
                            CurrentButton1.setIcon(new ImageIcon("icons/emptywater.png"));
                            ActionLabel.setText("<html>Расстанавливайте корабли<br>Выберите точку конца корабля</html>");
                        } else if (CurrentButton2 == null) {
                            CurrentButton2 = Cell;
                            CurrentButton1.setIcon(new ImageIcon("icons/water.png"));
                            CreateShip(FieldMassive, CurrentButton1, CurrentButton2, FieldMassiveStatus, Ships, ShipsInfoLabel, true);
                            CurrentButton1 = null;
                            CurrentButton2 = null;
                            if(Ships[0] == 4 && Ships[1] == 3 && Ships[2] == 2 && Ships[3] == 1) {
                                ControlButtons[0].setEnabled(true);
                                ActionLabel.setText("<html>Нажмите кнопку<br><h4>ПРОДОЛЖИТЬ</h4></html>");
                            } else {
                                ActionLabel.setText("<html>Расстанавливайте корабли<br>Выберите точку начала корабля</html>");
                            }
                        }
                    } else if (GameStatus == 4) {
                        JButton cell = (JButton) FieldAction.getSource();
                        int x = Integer.parseInt(cell.getName())%10;
                        int y = Integer.parseInt(cell.getName())/10;
                        System.out.println(FieldMassiveStatus[x][y]);
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
                FieldEnemyMassive[i][j].addActionListener(FieldEnemyAction -> {
                    if(GameStatus == 4) {
                        JButton cell = (JButton) FieldEnemyAction.getSource();
                        int x = Integer.parseInt(cell.getName())%10;
                        int y = Integer.parseInt(cell.getName())/10;
                        int status = FieldEnemyMassiveStatus[x][y];
                        if (status == 2 || status == 1) {
                            FieldEnemyMassive[x][y].setIcon(new ImageIcon("icons/emptywater.png"));
                            FieldEnemyMassiveStatus[x][y] = 0;
                            GameStatus = 5;
                            ActionLabel.setText("<html>Ожидайте<br>алгоритм стреляет</html>");
                            SearchShootCell(FieldMassive,FieldMassiveStatus, AIHasWreckedShips);
                            if(!CheckForRemainingShips(FieldMassiveStatus)) {
                                ActionLabel.setText("<html>Игра окончена!<br> Вы проиграли.</html>");
                                GameStatus = 6;
                            } else {
                                ActionLabel.setText("<html>Игра продолжается, стреляйте!</html>");
                            }
                        } else if (status > 2 && status < 7) {
                            FieldEnemyMassive[x][y].setIcon(new ImageIcon("icons/UnavailableWater.png"));
                            FieldEnemyMassiveStatus[x][y] += 4;
                            GameStatus = 5;
                            ActionLabel.setText("<html>Ожидайте<br>алгоритм стреляет</html>");
                            SearchShootCell(FieldMassive,FieldMassiveStatus, AIHasWreckedShips);
                            if (!CheckForRemainingShips(FieldEnemyMassiveStatus)) {
                                ActionLabel.setText("<html>Игра окончена!<br> Вы выиграли.</html>");
                                GameStatus = 6;
                            } else if(!CheckForRemainingShips(FieldMassiveStatus)) {
                                ActionLabel.setText("<html>Игра окончена!<br> Вы проиграли.</html>");
                                GameStatus = 6;
                            } else {
                                ActionLabel.setText("<html>Игра продолжается, стреляйте!</html>");
                            }
                        } else if (status > 6 || status == 0) {
                            ActionLabel.setText("<html>Вы стреляете в уже отстрелянную клетку<br>Выберите иную клетку</html>");
                        }
                        if(GameStatus == 6) {
                            TogglePlayerField(FieldMassive, false);
                            TogglePlayerField(FieldEnemyMassive, false);
                        }
                    }
                });
                FieldEnemyMassiveStatus[i][j] = 1;
            }
        }


        System.out.println("Done");

        System.out.println("Creating button listeners, that not exists now");

        ExitButton.addActionListener(ExitApp -> System.exit(0));
        HelpButton.addActionListener(GetHelp -> helpmepleeeaseeee.setVisible(true));
        PlayButton.addActionListener(ExitApp -> {
            if (GameStatus == 1) {
                TogglePlayerField(FieldMassive,true);
                GameStatus = 2;
                PlayButton.setEnabled(false);
                ActionLabel.setText("<html>Расстанавливайте корабли<br>Выберите точку начала корабля</html>");
                ShipsInfoLabel.setText("<html>Ваши корабли<br>Крейсер:" + Ships[3] + "<br>Подводная лодка: " + Ships[2] + "<br>Эсминец: " + Ships[1] + "<br>Фрегат: " + Ships[0] + "</html>");
                ControlButtons[1].setEnabled(true);
                ControlButtons[3].setEnabled(true);
            }
        });

        ControlButtons[0].addActionListener(Continue -> {
            if(GameStatus==2) {
                GameStatus = 3;
                TogglePlayerField(FieldMassive,false);
                ControlButtons[0].setEnabled(false);
                ActionLabel.setText("<html>Ожидайте окончания расстановки кораблей ИИ</html>");
                PlaceAIShips(FieldEnemyMassive, FieldEnemyMassiveStatus, ShipsEnemy, 3,false);
                GameStatus = 4;
                ShipsInfoLabel.setText("");
                ControlButtons[1].setEnabled(false);
                ControlButtons[2].setEnabled(true);
                ActionLabel.setText("<html>Игра началась!<br>Делайте выстрел</html>");
                TogglePlayerField(FieldMassive,true);
                TogglePlayerField(FieldEnemyMassive,true);
            } else if (GameStatus==6) {
                GameStatus = 1;
                HeaderLabel.setText("<html><h2>Морской бой</h2></html>");
                ActionLabel.setText("");
                PlayButton.setEnabled(true);
                for(int i = 0; i < 4; i++) {
                    ControlButtons[i].setEnabled(false);
                    Ships[i] = 0;
                    ShipsEnemy[i] = 0;
                }
                for(int i = 0; i < 10; i++) {
                    for(int j = 0; j < 10; j++) {
                        FieldMassive[i][j].setIcon(new ImageIcon("icons/water.png"));
                        FieldMassive[i][j].setEnabled(false);
                        FieldEnemyMassive[i][j].setIcon(new ImageIcon("icons/water.png"));
                        FieldEnemyMassive[i][j].setEnabled(false);
                        FieldMassiveStatus[i][j] = 1;
                        FieldEnemyMassiveStatus[i][j] = 1;
                    }
                }
                CurrentButton1 = null;
                CurrentButton2 = null;
                ShipsInfoLabel.setText("");
            }
        });

        ControlButtons[1].addActionListener(Continue -> {
            if(GameStatus==2) {
                for(int i = 0; i < 10; i++) {
                    for(int j = 0; j < 10; j++) {
                        FieldMassive[i][j].setIcon(new ImageIcon("icons/water.png"));
                        FieldMassiveStatus[i][j] = 1;
                    }
                }
                for(int i = 0; i < 4; i++) {
                    Ships[i] = 0;
                }
                ActionLabel.setText("<html>Расстанавливайте корабли<br>Выберите точку начала корабля</html>");
                ShipsInfoLabel.setText("<html>Ваши корабли<br>Крейсер:" + Ships[3] + "<br>Подводная лодка: " + Ships[2] + "<br>Эсминец: " + Ships[1] + "<br>Фрегат: " + Ships[0] + "</html>");
                CurrentButton1 = null;
                CurrentButton2 = null;
            }
        });

        ControlButtons[2].addActionListener(Continue -> {
            if(GameStatus!=5) {
                GameStatus = 1;
                HeaderLabel.setText("<html><h2>Морской бой</h2></html>");
                ActionLabel.setText("");
                PlayButton.setEnabled(true);
                for(int i = 0; i < 4; i++) {
                    ControlButtons[i].setEnabled(false);
                    Ships[i] = 0;
                    ShipsEnemy[i] = 0;
                }
                for(int i = 0; i < 10; i++) {
                    for(int j = 0; j < 10; j++) {
                        FieldMassive[i][j].setIcon(new ImageIcon("icons/water.png"));
                        FieldMassive[i][j].setEnabled(false);
                        FieldEnemyMassive[i][j].setIcon(new ImageIcon("icons/water.png"));
                        FieldEnemyMassive[i][j].setEnabled(false);
                        FieldMassiveStatus[i][j] = 1;
                        FieldEnemyMassiveStatus[i][j] = 1;
                    }
                }
                CurrentButton1 = null;
                CurrentButton2 = null;
                ShipsInfoLabel.setText("");
            }
        });

        ControlButtons[3].addActionListener(Continue -> {
            if(GameStatus==2) {
                for(int i = 0; i < 10; i++) {
                    for(int j = 0; j < 10; j++) {
                        FieldMassive[i][j].setIcon(new ImageIcon("icons/water.png"));
                        FieldMassiveStatus[i][j] = 1;
                    }
                }
                for(int i = 0; i < 4; i++) {
                    Ships[i] = 0;
                }
                PlaceAIShips(FieldMassive,FieldMassiveStatus,Ships,3,true);
                ControlButtons[0].setEnabled(true);
                ActionLabel.setText("<html>Нажмите кнопку<br><h4>ПРОДОЛЖИТЬ</h4></html>");
                ShipsInfoLabel.setText("<html>Ваши корабли<br>Крейсер:" + Ships[3] + "<br>Подводная лодка: " + Ships[2] + "<br>Эсминец: " + Ships[1] + "<br>Фрегат: " + Ships[0] + "</html>");
            }
        });

        System.out.println("Done");

        jframe.setVisible(true);    //Делаем фрейм видимым

        System.out.println("Ready.");
    }
}