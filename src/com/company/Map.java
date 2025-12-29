package com.company;

import java.util.HashMap;

public class Map {

    public static HashMap<String, Room> rooms = new HashMap<>();//HashMap holds the room information including
    //monsters,puzzles, and items

    /**
     * adds the room to rooms HashMap
     * @param room - the room to add
     */
    public static void addRoom(Room room){
        rooms.put(room.getRoomID(),room);

    }
    public static HashMap<String,Room> getRooms(){
        return rooms;
    }

    /**
     * Moves the player to the room in the specified direction if possible
     * @param direction -North,East,South,West
     */
    public static void getRoom(String direction){
        if(direction.compareTo("0")==0){
            System.out.println("You can't go in this direction.");
        }
        else if(direction.compareTo("0")!=0) {
            String playerLocation= direction;
            if(rooms.get(playerLocation).isVisited()){
                System.out.println("This looks familiar!");
            }else{
                rooms.get(playerLocation).setVisited(true);
            }

            Player.setPlayerLocation(playerLocation);

        }
    }


    @Override
    public String toString() {
        return "The map of the rooms : \n"+rooms+"\n";
    }
}
