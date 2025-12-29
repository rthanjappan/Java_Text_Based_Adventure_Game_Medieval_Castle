package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Output {
    public void writeRooms(Player player){

    try{

        PrintWriter fileWriter = new PrintWriter("OldRoom.txt");
        fileWriter.println("File format :RoomID,RoomName,Description,Separator,isVisited," +
                "Navigation,ItemID,PuzzleID,MonsterID");
        HashMap<String, Room> rooms = player.map.getRooms();
        for (Room room : rooms.values()) {
            fileWriter.println();
            fileWriter.println(room.getRoomID());
            fileWriter.println(room.getRoomName());
            ArrayList<String> description = room.getRoomDescription();
            for (String str : description) {
                fileWriter.println(str);
            }
            fileWriter.println("----");
            fileWriter.println(room.isVisited());
            HashMap<String, String> navTable = room.getNavTable();
            String str = navTable.get("north");
            str += "," + navTable.get("east");
            str += "," + navTable.get("south");
            str += "," + navTable.get("west");

            fileWriter.println(str);//writes the navigation : north,east,south,west

            ArrayList<Item> items = room.getRoomInventory();
            if (items.isEmpty()) {
                fileWriter.println("0");
            } else {
                str = items.get(0).getItemID();

                for (int i = 1; i < items.size(); i++) {
                    str += "," + items.get(i).getItemID();
                }
                fileWriter.println(str);
            }
            if(!room.getPuzzle().isSolved()) {
                fileWriter.println(room.getPuzzle().getPuzzleID());
            }else{
                fileWriter.println("0");

            }
            if(!room.getMonster().isDefeated()) {
                fileWriter.println(room.getMonster().getMonsterID());
            }else{
                fileWriter.println("0");

            }
        }

        fileWriter.close();

    }catch(
    FileNotFoundException e)

    {
        System.out.println("OldRoom.txt" + "not found");
        e.printStackTrace();
    }
}
    public void writeItems(Player player){

        try{

            PrintWriter fileWriter = new PrintWriter("OldItems.txt");
            fileWriter.println("File format :ItemType,ItemID,ItemName,Description," +
                    "damage/healthEffect,isUsed,isAvailableToPickup");
            HashMap<String, Room> rooms = player.map.getRooms();
            for (Room room : rooms.values()) {

                ArrayList<Item> items = room.getRoomInventory();
                if (!items.isEmpty()) {


                    //str = items.get(0).getItemID();

                    for (Item item : items) {
                        fileWriter.println();
                        fileWriter.println(item.getItemType());
                        fileWriter.println(item.getItemID());
                        fileWriter.println(item.getItemName());
                        fileWriter.println(item.getItemDescription());
                        if(item.getItemType().compareTo("Equippable")==0){
                            fileWriter.println(((Equippable)item).getDamageEffect());
                            fileWriter.println(((Equippable)item).isEquipped());
                        }else if(item.getItemType().compareTo("Usable")==0) {
                            fileWriter.println(((Usable)item).getHealthEffect());
                            fileWriter.println(((Usable)item).isUsed());
                        }

                        fileWriter.println(item.isAvailable());
                    }

                }

            }
            ArrayList<Item> playerInventoryItems = player.getPlayerInventory();
            if (!playerInventoryItems.isEmpty()) {


                //str = items.get(0).getItemID();

                for (Item item : playerInventoryItems) {
                    fileWriter.println();
                    fileWriter.println(item.getItemType());
                    fileWriter.println(item.getItemID());
                    fileWriter.println(item.getItemName());
                    fileWriter.println(item.getItemDescription());
                    if(item.getItemType().compareTo("Equippable")==0){
                        fileWriter.println(((Equippable)item).getDamageEffect());
                        fileWriter.println(((Equippable)item).isEquipped());
                    }else if(item.getItemType().compareTo("Usable")==0) {
                        fileWriter.println(((Usable)item).getHealthEffect());
                        fileWriter.println(((Usable)item).isUsed());
                    }

                    fileWriter.println(item.isAvailable());
                }

            }


            fileWriter.close();

        }catch(FileNotFoundException e){
            System.out.println("OldItems.txt" + "not found");
            e.printStackTrace();
        }
    }
    public void writePuzzles(Player player){

        try{

            PrintWriter fileWriter = new PrintWriter("OldPuzzles.txt");
            fileWriter.println("File format :PuzzleID, PuzzleName, Description,----,solvedDescription,****,requiredItem, " +
                    "requiredString,NoOfAttempts,isSolved");
            HashMap<String, Room> rooms = player.map.getRooms();
            for (Room room : rooms.values()) {

                Puzzle puzzle= room.getPuzzle();
                if(!puzzle.isSolved()){
                    fileWriter.println();
                    fileWriter.println(puzzle.getPuzzleID());
                    fileWriter.println(puzzle.getPuzzleName());
                    ArrayList<String> description = puzzle.getPuzzleDescription();
                    for(String str : description){
                        fileWriter.println(str);
                    }
                    fileWriter.println("----");
                    description = puzzle.getSolvedDescription();
                    for(String str : description){
                        fileWriter.println(str);
                    }
                    fileWriter.println("****");
                    ArrayList<String> requiredItems = puzzle.getRequiredItems();
                    if(!requiredItems.isEmpty()) {
                        String str=requiredItems.get(0);
                        for (int i=1;i<requiredItems.size();i++) {
                            str+=","+requiredItems.get(i);
                        }
                        fileWriter.println(str);
                    }else{
                        fileWriter.println("0");
                    }
                    fileWriter.println(puzzle.getRequiredString());
                    fileWriter.println(puzzle.getNoOfAttempts());
                    fileWriter.println(puzzle.isSolved());

                }

            }

            fileWriter.close();

        }catch(FileNotFoundException e){
            System.out.println("OldPuzzles.txt" + "not found");
            e.printStackTrace();
        }
    }
    public void writeMonsters(Player player){

        try{

            PrintWriter fileWriter = new PrintWriter("OldMonsters.txt");
            fileWriter.println("File format :MonsterID,MonsterName,Description,Separator," +
                    "DefeatedDescription,Separator,HP,DealDamage,FixNumber,isDefeated,Armor,MonsterDrops");
            HashMap<String, Room> rooms = player.map.getRooms();
            for (Room room : rooms.values()) {

                Monster monster= room.getMonster();
                if(!monster.isDefeated()){
                    fileWriter.println();
                    fileWriter.println(monster.getMonsterID());
                    fileWriter.println(monster.getMonsterName());
                    ArrayList<String> description = monster.getMonsterDescription();
                    for(String str : description){
                        fileWriter.println(str);
                    }
                    fileWriter.println("----");

                    description = monster.getDefeatedDescription();
                    for(String str : description){
                        fileWriter.println(str);
                    }
                    fileWriter.println("****");
                    fileWriter.println(monster.getHP());
                    fileWriter.println(monster.getDealDamage());
                    fileWriter.println(monster.getFixNumber());
                    fileWriter.println(monster.isDefeated());
                    fileWriter.println(monster.getArmor());
                    fileWriter.println(monster.getItemDropped());

                }

            }

            fileWriter.close();

        }catch(FileNotFoundException e){
            System.out.println("OldMonsters.txt" + "not found");
            e.printStackTrace();
        }
    }
    public void writePlayer(Player player){
        try{

            PrintWriter fileWriter = new PrintWriter("OldPlayer.txt");
            fileWriter.println("File format :PlayerID,PlayerHP,PlayerDealDamage,PLayerInventory," +
                    "RoomID,RoundCount,equippedItems");
            //PlayerID,PlayerHP,PlayerDealDamage,PLayerInventory,RoomID,RoundCount,equippedItems
            fileWriter.println(player.getPlayerID());
            fileWriter.println(player.getHP());
            fileWriter.println(player.getDealDamage());
            if(!player.getPlayerInventory().isEmpty()){
                fileWriter.print( player.getPlayerInventory().get(0).getItemID());
                for(int i=1;i<player.getPlayerInventory().size();i++){

                    fileWriter.print( ","+player.getPlayerInventory().get(i).getItemID());
                }
            }else{
                fileWriter.print("0");
            }
            fileWriter.println();
            fileWriter.println(player.getRoomID());
            fileWriter.println(player.getRoundCount());
            //writing the equipped items
            if(!player.getEquippedItems().isEmpty()){
                fileWriter.print( player.getEquippedItems().get(0).getItemID());
                for(int i=1;i<player.getEquippedItems().size();i++){

                    fileWriter.print( ","+player.getEquippedItems().get(i).getItemID());
                }
            }else{
                fileWriter.print("0");

            }
            fileWriter.println();
            fileWriter.close();


    }catch(FileNotFoundException e){
        System.out.println("OldPlayer.txt" + "not found");
        e.printStackTrace();
    }

    }

    public void writeCurrentRoom(Room currentRoom){

    }
}
