package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {


    /**
     * main game loop
     * first initialization is done.
     * then starts the game loop
     * The game loop is executed while the endFlag is true.
     * If the endFlag is false the game ends.
     * @param args
     */
    public static void main(String[] args) {


        MedievalGame medievalGame=new MedievalGame();
        //initializing the game
        medievalGame.initialization();

        //main game loop
        do{
            try {
                medievalGame.process();
            }catch(Exception e){
                e.printStackTrace();
            }
        }while(!medievalGame.isEndFlag());

        //Game is ending
        medievalGame.end();

        System.exit(0);
    }

}
