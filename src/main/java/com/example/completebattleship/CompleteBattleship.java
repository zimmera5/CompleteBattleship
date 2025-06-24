package com.example.completebattleship;

import java.util.*;

public class CompleteBattleship {

    //constants - how much extra space is needed to place the boat
    private static final int PATROL = 1;
    private static final int SUB = 2;
    private static final int GUNNER = 3;
    private static final int CARRIER = 4;
    private static final String [] [] board = new String[10][10];

    public static void main(String[] args) {

        Scanner in = new Scanner (System.in);
        SafeInput.prettyHeader("Welcome to Battleship!");
        System.out.println();
        //start of game loop
        int missCounter = 0;
        int strike = 0;
        clearBoard();
        placeShips();
        System.out.println();

        System.out.println();
        //start of move loop
        displayBoard();
        //collect the row/column of move
        do {
            System.out.println("What is your move?");
            int row = SafeInput.getRangedInt(in, "Row: ", 1, 10);
            row --;
            String alphaCol = SafeInput.getRegExString(in, "Column:", "[AaBbCcDdEeFfGgHhIiJj]");
            alphaCol = alphaCol.toUpperCase();
            int col = 0;
            //remember to make a = 0 and so on...
            switch (alphaCol){
                case "A": col = 0;
                    break;
                case "B": col = 1;
                    break;
                case "C": col = 2;
                    break;
                case "D": col = 3;
                    break;
                case "E": col = 4;
                    break;
                case "F": col = 5;
                    break;
                case "G": col = 6;
                    break;
                case "H": col = 7;
                    break;
                case "I": col = 8;
                    break;
                case "J": col = 9;
                    break;
            }
            if (board[row][col].equals("-")){
                board[row][col] = "miss";
                missCounter ++;
                System.out.println("That's a miss!");
                //if they miss 5 times in a row, they get a strike
                if (missCounter==5){
                    strike ++;
                    if (strike==3){

                        //lose game
                        System.out.println("3 strikes, they sunk your battleship!");
                    }
                }
            }
            else if (board[row][col].equals("ship")){
                board[row][col] = "hit";
                System.out.println("That's a hit!!!");
                missCounter = 0;
            }
            else{
                System.out.println("You have already moved in that spot, try again");
            }
            displayBoard();
        }while(true);
        //if it row/col = ship, it is 'hit', replace String in array with 'hit'
        //if it is = - , then it is a 'miss'
        //else you've already moved there, have them move again
        //5 misses in a row? YOU LOSE
        //All ships destroyed?  YOU WIN

        //want to play again?



    }

    public static void displayBoard() {
        String wave = "\uD83C\uDF0A";
        String boat = "\u26F5";
        String hit = "\uD83D\uDCA5";
        String miss = "\uD83D\uDCA6";

        System.out.print("   ");

        System.out.print("\uFF21"+" "); //A

        System.out.print("\uFF22"+" "); //B

        System.out.print("\uFF23"+" ");  //C

        System.out.print("\uFF24"+" ");  //D

        System.out.print("\uFF25"+" "); //E

        System.out.print("\uFF26"+" "); //F

        System.out.print("\uFF27"+" ");  //G

        System.out.print("\uFF28"+" ");  //H

        System.out.print("\uFF29"+" ");  //I

        System.out.print("\uFF2A");  //J

        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print(i+1+" ");
            if (i!=9){
                System.out.print(" ");
            }
            for (int c = 0; c < 10; c++) {
                //if '-' is in array spot
                if (board[i][c].equals("-")) {
                    System.out.print(wave + " ");
                }
                //testing to see if ships are placed - - remove for final game
                else if (board[i][c].equals("ship")){
                    System.out.print(boat+" ");
                }
                else if (board[i][c].equals("hit")){
                    System.out.print(hit+" ");
                }
                else{
                    System.out.print(miss+" ");
                }

                //if 'hit' then draw x

                //if 'miss' then draw dash
            }
            System.out.println();
        }
    }

    public static void clearBoard(){

        for(int j = 0; j <10; j ++){
            for(int i = 0; i <10; i ++) {
                board[i][j] = "-";
            }
        }
    }
    public static void placeShips(){

        placeShip(CARRIER);
        placeShip(GUNNER);
        placeShip(SUB);
        placeShip(PATROL);
    }

    //draw the patrolBoat - only need 2 spaces - legacy code, removed from final game
    public static void patrolBoat(){
        Random rnd = new Random();
        //vertOrHorz needs to be bound to 2 (0,1)
        int vertOrHorz = rnd.nextInt(2);
        int row = rnd.nextInt(10-PATROL); //need to account for space needed to place the rest of the boat
        int col = rnd.nextInt(10-PATROL); //need to account for space needed to place the rest of the boat

        //place ship vertically
        if (vertOrHorz == 0){
            //rely on column - check all positions to see if we can go there
            do{
                if(board[row][col].equals("-")){
                    //only checking for next space, patrol boat is only 2 spaces
                    if (board[row+1][col].equals("-")){
                        board[row][col] = "ship";
                        board[row+1][col] = "ship";
                        break;
                    }
                }
            }while(true);

        }
        //place ship horizontally
        else{
            //rely on row - check all positions to see if we can go there
            do{
                if(board[row][col].equals("-")){
                    //only checking for next space, patrol boat is only 2 spaces
                    if (board[row][col+1].equals("-")){
                        board[row][col] = "ship";
                        board[row][col+1] = "ship";
                        break;
                    }
                }
            }while(true);
        }
    }

    public static void placeShip(int ship){
        Random rnd = new Random();
        //vertOrHorz needs to be bound to 2 (0,1)
        int vertOrHorz = rnd.nextInt(2);
        //need to count valid spaces
        int validCounter = 0;
        int row = 0;
        int col = 0;
        //place ship vertically
        if (vertOrHorz == 0){
            //rely on column - check all positions to see if we can go there
            do{
                //reset the validCounter each time, if enough spaces are valid we can exit and place our boats
                validCounter = 0;
                row = rnd.nextInt(10 - ship); //need to account for space needed to place the rest of the boat
                col = rnd.nextInt(10 - ship + 1); //need to account for space needed to place the rest of the boat
                System.out.println("vertically placing "+ship+" in position: "+row +" "+col);
                for(int i = 0; i <= ship; i ++){
                    if(board[row+i][col].equals("-")){
                        //sub needs 2 more spaces, gunner needs 3, carrier needs 4
                        validCounter ++;
                    }
                }
            }while(validCounter!=(ship+1));
            //we have a valid row area, place the ship
            for(int i = 0; i <= ship; i ++){
                board[row+i][col] = "ship";
            }
        }
        //place ship horizontally
        else{
            //rely on column - check all positions to see if we can go there
            do{
                validCounter = 0;
                row = rnd.nextInt(10 - ship+1); //need to account for space needed to place the rest of the boat
                col = rnd.nextInt(10 - ship); //need to account for space needed to place the rest of the boat
                System.out.println("horizontally placing "+ship+" in position: "+row +" "+col);
                for(int i = 0; i <= ship; i ++){
                    if(board[row][col+i].equals("-")) {
                        //sub needs 2 more spaces
                        validCounter++;
                    }
                }

            }while(validCounter!=ship+1);
            //we have a valid row area, place the ship
            for(int i = 0; i <= ship; i ++){
                board[row][col+i] = "ship";
            }

        }
    }

}