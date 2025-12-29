package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MedievalGame {
    private static Input input;
    //private static HashMap<Integer,Room> rooms;//stores the room data
    private static Room currentRoom;//current room in the game loop
    private static Scanner keyboard;//reads the player input

    private static boolean endFlag=false;//indicates whether the game is ending or playing
    private static int commandType=1;//-2:no further action,-1:invalid command,1:valid command,
    // 2:help,3:Quit,4:restart,5:Menu,6:pickup Item,7:inspect item, 8 drop item,
    // 9: display inventory,10:explore current room
    private static String nextRoomID="TLR1";//next room to display
    private static ArrayList<Item> items;

    private static Item currentItem;
    private static String itemName;
    private static Player player;
    private static Puzzle currentPuzzle;
    private static String currentCommand="";
    private static int currentMode=1;//1:game mode,2:battle mode
    private static HashMap<String,Integer> commandsTable;
    /**
     * initialization of the game
     * Data is read from the file and data is processed into an internal data structure.
     * THe starting room is displayed.
     */

    public static void initialization(){

        input=new Input("Player.txt","ItemsTable.txt","PuzzlesTable.txt",
                "MonstersTable.txt","RoomsTable.txt");//reads and creates the game data from file

        keyboard=new Scanner(System.in);
        commandsTable=input.getCommandsTable();
        //player= Player.getPlayer();
        player= Player.getPlayer();
        System.out.println(player.map);
        player.map.getRoom("TLR1");



        displayLogo();

        mainMenu();

        //display the first room
        displayCurrentRoom();
        displayCurrentPuzzle();
        //processPuzzle();
        //puzzleRoutine();

    }

    /**
     * Displays welcome screen
     */

    private static void displayLogo() {
        System.out.println("+----------------------------------------------+");
        System.out.println("| Welcome to Medieval Game !!! |");
        System.out.println("+----------------------------------------------+");
    }

    /**
     * Main program execution.
     * The command type is validated and according to the command type
     * each process is executed.
     * If the command entered is invalid , it is notified to the player.
     */
    public static void process() {

        String command=getCommand();//reads the command from user
        getRoomID(command);//figure outs the next room to move and processes other commands

        switch(commandType){
            case -2://No further processing

                break;
            case -1:
                System.out.print("Invalid command.\n");
                break;
            case 1:

                displayCurrentRoom();//displays current room
                //processPuzzle();//puzzle logic
                displayCurrentPuzzle();
                break;
            case 2 :
                displayHelp();//displays help
                break;
            case 3:
                quitRoutine();//quit command logic
                break;
            case 4:
                restartRoutine();//restart command logic
                break;
            case 5:
                mainMenu();//displays main menu
                break;
            case 6:
                player.displayInventory();
                break;
            case 7:
                currentCommand = "explore";
                player.exploreCurrentRoom(currentCommand);
                currentCommand = "";
                break;
            case 8:
                currentMode = player.attack();
                if (currentMode == -1) {
                    gameOverRoutine();

                }
                break;
            case 9://the player leaves the combat.
                currentMode = 1;
                player.ignore(itemName);
                break;
            case 10:
                player.pickupItem(itemName);
                break;
            case 11:
                player.inspectItem(itemName);
                break;
            case 12:
                player.dropItem(itemName);
                break;
            case 13:
                player.equipItem(itemName);
                break;
            case 14:
                player.unEquipItem(itemName);
                break;
            case 15:
                player.consume(itemName);
                break;
            case 16:
                player.examineMonster(itemName);
                break;
            case 17:
                player.displayHP(itemName);
                break;
            case 18:
                puzzleRoutine();

                break;
            case 19:
                saveGameRoutine();
                break;
            case 20:
                reloadGameRoutine();
                break;
            default:
                break;
        }


    }




    /**
     * At any point The player can quit the game.
     * This quitRoutine make sure the player wants to quit and
     * indicates the game is ending with an endFlag.
     */
    private static void quitRoutine() {

        System.out.print("Are you sure you want to Quit? (Y/N) : ");
        String choice = keyboard.nextLine();
        choice = choice.toUpperCase();
        if(choice.compareTo("Y")==0 || choice.compareTo("YES")==0){

            endFlag=true;

        }
    }

    /**
     * reads the command from the user
     * @return the commandString
     */

    private static String getCommand() {
        if(currentMode==1) {
            System.out.print(currentRoom.getRoomName()+": Which direction do you want to go? (N,S,E,W)\n>");
        }else if(currentMode==2){
            System.out.print("Attack or Heal <item name>  or Equip/unequip <itemName>: ");
        }
        String command=keyboard.nextLine();


        return command;
    }

    /**
     * figure outs the next room to move and processes other commands
     * @param command
     */
    private static void getRoomID(String command) {


        commandType = 0;
        command = command.toLowerCase();
        StringTokenizer st = new StringTokenizer(command, " ");

        if (st.countTokens() == 1) {
            processSingleWordCommand(command);
        } else if (st.countTokens() > 1) {
            processMultiWordCommand(st);

        }
    }
    private static void processSingleWordCommand(String command){
        HashMap<String, String> navTable = currentRoom.getNavTable();
        Integer commandtype = commandsTable.get(command);
        if (commandtype == null) {
            commandType = -1;
            return;
        } else if (commandtype == 1) {
            if (currentMode == 2) {
                System.out.println("You have to leave the combat before navigating to another room.");
                commandType = -2;
                return;
            }
            nextRoomID = navTable.get(command);
            player.map.getRoom(nextRoomID);
            commandType = (nextRoomID.compareTo("0") != 0) ? 1 : 0;
            return;
        } else if (commandtype != 1) {
            commandType = commandtype;
            return;
        }
    }
    private static void processMultiWordCommand(StringTokenizer st){
        commandType = 0;

        //command = command.toLowerCase();
        String command = st.nextToken();
        itemName = st.nextToken();

        while (st.hasMoreTokens()) {
            itemName += " " + st.nextToken();
        }
        //itemName = itemName.toUpperCase();
        Integer commandtype = commandsTable.get(command);
        if (commandtype == null) {
            commandType = -1;
            return;
        } else if (commandtype >8) {
            commandType = commandtype;
            return;
        }else  {
            commandType = -1;
            return;
        }
    }
    private static void gameOverRoutine() {
        System.out.println("The game is over.");
        System.out.print("You can (E)xit or (R)estart the game : ");
        String command=keyboard.nextLine().toLowerCase();
        if(command.compareTo("exit")==0|| command.compareTo("e")==0){
            exitRoutine();
        }else {
            restartRoutine();
            currentMode=1;
        }

    }

    private static void exitRoutine() {
        System.out.println("You are exiting the game...");
        System.exit(0);
    }

    /**
     * displays the name of the current room
     */
    private static void displayCurrentRoom() {

        currentRoom=player.map.getRooms().get(player.getPlayerLocation());

        System.out.println("You are at the "+currentRoom.getRoomName());



    }
    private static void displayCurrentPuzzle(){
        Puzzle currentPuzzle = currentRoom.getPuzzle();


        if(!currentPuzzle.isSolved()) {
            System.out.println("\nYou have a puzzle to solve : "+currentPuzzle.getPuzzleName());
            for(String s : currentPuzzle.getPuzzleDescription()){
                System.out.println(s);
            }
        }
    }
    private static void solvePuzzle(){
        if (itemName.toLowerCase().compareTo("puzzle") == 0) {
            if (currentRoom.getPuzzle().isSolved()) {
                System.out.println("There are no puzzles to solve in this room.");
            } else {
                //processPuzzle();
                if(!currentRoom.getPuzzle().isSolved()) {
                    currentPuzzle = currentRoom.getPuzzle();
                    ArrayList<String> requiredItems = currentPuzzle.getRequiredItems();
                    boolean foundArray[] = new boolean[requiredItems.size()];
                    if (!requiredItems.isEmpty()) {
                        ArrayList<Item> playerInventory = player.getPlayerInventory();
                        for (int i = 0; i < requiredItems.size(); i++) {
                            for (int j = 0; j < playerInventory.size(); j++) {
                                if (requiredItems.get(i).compareTo(playerInventory.get(j).getItemID()) == 0) {
                                    foundArray[i] = true;
                                }
                            }
                        }
                        for (int i = 0; i < foundArray.length; i++) {
                            if (!foundArray[i]) {

                                System.out.println("Your inventory does not have required items.");
                                return; //-3;
                            }
                        }
                        ArrayList<String> solvedDescription = currentPuzzle.getSolvedDescription();
                        for (String s : solvedDescription) {
                            System.out.println(s);
                        }
                        currentRoom.getPuzzle().setSolved(true);
                        //ArrayList<Item> playerInventory = player.getPlayerInventory();
                        for (int i = 0; i < requiredItems.size(); i++) {
                            for (int j = 0; j < playerInventory.size(); j++) {
                                if (requiredItems.get(i).compareTo(playerInventory.get(j).getItemID()) == 0) {
                                    playerInventory.remove(playerInventory.get(j));
                                }
                            }
                        }
                    }
                }
            }

        } else {
            System.out.println("Invalid command.");
        }

    }
    /**
     * executes the puzzle routine
     */
    private static void puzzleRoutine() {
        if (itemName.toLowerCase().compareTo("puzzle") == 0) {
            if (currentRoom.getPuzzle().isSolved()) {
                System.out.println("There are no puzzles to solve in this room.");
            } else {
                //processPuzzle();
                currentPuzzle= currentRoom.getPuzzle();
                if(currentPuzzle.getRequiredString().compareTo("0")==0){
                    solvePuzzle();
                }else {
                    processPuzzle();
                }

            }

        } else {
            System.out.println("Invalid command.");
        }
    }
    /**
     * the puzzle logic
     */
    private static void processPuzzle() {

        if(!currentRoom.getPuzzle().isSolved()) {
            currentPuzzle = currentRoom.getPuzzle();

            int availableAttempts = currentPuzzle.getNoOfAttempts();
//            System.out.println("\nNumber of attempts available are " +
//                    availableAttempts +".");
            System.out.println("\nYou have a puzzle to solve : (number of attempts available are " +
                    availableAttempts + ".)");
            ArrayList<String> puzzleDescription= currentPuzzle.getPuzzleDescription();
            for(String s : puzzleDescription){
                System.out.println(s);
            }

            do {
                System.out.print(">");
                String ans = keyboard.nextLine();
                //Possible commands :  Examine,solve,ignore and the answer
                int status=processPuzzleCommand(ans);
                if(status==-1){
                    return;
                }else if(status==0){
                    continue;
                }else {
                    ans.toLowerCase();

                    //if (ans.compareTo(currentPuzzle.getAnswer()) == 0) {
                    if (ans.compareTo(currentPuzzle.getRequiredString().toLowerCase()) == 0) {
                        System.out.println("you solved the puzzle correctly!");

                        currentPuzzle.setSolved(true);
                        return;
                    } else {
                        if (availableAttempts == 1) {
                            break;
                        }
                        System.out.println("Incorrect ." +
                                " You still have " + --availableAttempts + " attempt(s) left." +
                                " Try one more time.");
                    }
                }

            } while (availableAttempts > 0);

            System.out.println("You failed to solve the puzzle. You have no more attempts left.");
        }
    }

    /**
     * processes the commands entered by the user during the puzzle processing
     * @param ans
     * @return status
     */
    private static int processPuzzleCommand(String ans) {
        ans=ans.toLowerCase();
        StringTokenizer st = new StringTokenizer(ans," ");
        if(st.countTokens()>1){
            String command = st.nextToken();
            if(command.compareTo("examine")==0 ||command.compareTo("x")==0){
                if(ans.compareTo("examine puzzle")==0|| ans.compareTo("x puzzle")==0){
                    System.out.println(currentPuzzle.getPuzzleDescription());
                    return 0;
                }

            }

            else if(command.compareTo("ignore")==0 ||command.compareTo("ig")==0) {
                if (ans.compareTo("ignore puzzle") == 0 || ans.compareTo("ig puzzle") == 0) {
                    System.out.println("You are leaving the " + currentPuzzle.getPuzzleName() + ".");
                    return -1;
                }
            }
        }
        return 1;//this is an answer

    }

    /**
     * displays the help
     */
    private static void displayHelp() {
        System.out.println("You are at the "+currentRoom.getRoomName());
        System.out.println("+------------------------------------------------------------------------+");
        System.out.println("| 			                Help   			                             |");
        System.out.println("+------------------------------------------------------------------------+");
        System.out.println("| Player directions are North,South,East, and West	                     |");
        System.out.println("| You may pick up items by entering \"pickup <item name>\"	             |");
        System.out.println("| You may drop items by entering \"drop <item name>\"	                     |");
        System.out.println("| You may get description of the item by entering \"inspect <item name>\"  |");
        System.out.println("| You may check your inventory by entering \"inventory\"	                 |");
        System.out.println("| You may get description of the puzzle by entering \"examine <puzzle>\"   |");
        System.out.println("| You may leave the puzzle by entering \"ignore <puzzle name>\"            |");
        System.out.println("| Other valid commands are [Quit],[Restart],[Help],[Menu]                |");
        System.out.println("+------------------------------------------------------------------------+");

        HashMap<String,String> navTable=currentRoom.getNavTable();
        String str="The valid directions from this room are :";
        if(navTable.get("north").compareTo("0")!=0){
            str+=" [North]";
        }
        if(navTable.get("east").compareTo("0")!=0){
            str+=" [East]";
        }
        if(navTable.get("south").compareTo("0")!=0){
            str+=" [South]";
        }
        if(navTable.get("west").compareTo("0")!=0){
            str+=" [West]";
        }
        System.out.println(str);
    }

    /**
     * restarts the game resetting the rooms, items, puzzles and monsters
     */
    private static void restartRoutine() {

        initialization();

        currentMode=1;//navigation mode
//        displayLogo();
//        mainMenu();
//        player.getPlayerInventory().clear();
//
//        player.map.getRooms().clear();
//
//        input.processRawItemData();
//        input.processRawPuzzleData();
//        input.processRawMonsterData();
//        input.processRawRoomData();
//
//        //display the first room
//        player= input.getPlayer();
//        player.map.getRoom("TLR1");//setPlayerLocation(1);
//        nextRoomID="TLR1";
//        displayCurrentRoom();
//        processPuzzle();
    }

    /**
     * displays the main menu
     */
    private static void mainMenu() {
        System.out.println("+----------------------------------------------+");
        System.out.println("|           Main Menu                          |");
        System.out.println("+----------------------------------------------+");
        System.out.println("|           Restart                            |");
        System.out.println("|           Directions(North,East,South,West)  |");
        System.out.println("|           Help                               |");
        System.out.println("|           Quit                               |");
        System.out.println("+----------------------------------------------+");
    }

    private static void reloadGameRoutine() {
        //load currentRoom
        input=new Input("OldPlayer.txt","OldItems.txt","OldPuzzles.txt",
                "OldMonsters.txt","OldRoom.txt");//reads and creates the game data from file

        //keyboard=new Scanner(System.in);
        commandsTable=input.getCommandsTable();
        player= Player.getPlayer();
        System.out.println(player.map);
        //player.map.getRoom("TLR1");
        player.map.getRoom(player.getRoomID());


        displayLogo();

        mainMenu();

        //display the first room
        displayCurrentRoom();
        processPuzzle();

    }

    private static void saveGameRoutine() {

        //have to save current room data
        //have to save each room data
        Output output=new Output();
        output.writeRooms(player);
        output.writeItems(player);
        output.writePuzzles(player);
        output.writeMonsters(player);
        output.writePlayer(player);
//        try {
//
//            PrintWriter fileWriter = new PrintWriter("OldRoom.txt");
//            fileWriter.println("File format :");
//            HashMap<String,Room> rooms=player.map.getRooms();
//            for(Room room : rooms.values()){
//                fileWriter.println();
//                fileWriter.println(room.getRoomID());
//                fileWriter.println(room.getRoomName());
//                ArrayList<String> description = room.getRoomDescription();
//                for(String str : description){
//                    fileWriter.println(str);
//                }
//                fileWriter.println("----");
//                fileWriter.println(room.isVisited());
//                HashMap<String,String> navTable= room.getNavTable();
//                String str=navTable.get("north");
//                str+=","+navTable.get("east");
//                str+=","+navTable.get("south");
//                str+=","+navTable.get("west");
//
//                fileWriter.println(str);//writes the navigation : north,east,south,west
//
//                ArrayList<Item> items= room.getRoomInventory();
//                if(items.isEmpty()){
//                    fileWriter.println("0");
//                }else{
//                    str= items.get(0).getItemID();
//
//                    for(int i=1;i<items.size();i++){
//                        str+=","+items.get(i).getItemID();
//                    }
//                    fileWriter.println(str);
//                }
//                fileWriter.println(room.getPuzzle().getPuzzleID());
//                fileWriter.println(room.getMonster().getMonsterID());
//            }
//
//            fileWriter.close();
//
//        }catch (FileNotFoundException e){
//            System.out.println("OldRoom.txt" + "not found");
//            e.printStackTrace();
//        }

        //have to save items
        //have to save puzzles
        //have to save monsters


    }
    /**
     * displays the ending message
     */
    public static void end() {

        System.out.println("Thanks for playing....");
    }

    public static boolean isEndFlag() {
        return endFlag;
    }
}
