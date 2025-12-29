package com.company;

import java.util.*;

public class Player extends GameCharacter {
    private int playerID;
    private ArrayList<Item> playerInventory;
    private Room currentRoom;
    private ArrayList<Item> equippedItems;
    private static String roomID;
    Map map= new Map();//The map object containing all room objects with puzzles,items and monsters.
    //int oldHP;
    int roundCount=1;
    private static Player player=null;
    public Player() {
        //super.setHP( 500);
        this.playerInventory = new ArrayList<>();
        //this.equippedItems = new ArrayList<>();
    }

    public static Player getPlayer(){
        if(player==null){
            player=Input.getPlayer();
        }
        return player;
    }

    public static void setPlayer(Player player) {
        Player.player = player;
    }

    public static String getRoomID() {
        return roomID;
    }

    public static void setRoomID(String roomID) {
        Player.roomID = roomID;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public void equipItem(String itemName){
        boolean found=false;
        for (int i = 0; i < playerInventory.size(); i++) {
            if (playerInventory.get(i).getItemName().toLowerCase().compareTo(itemName.toLowerCase())== 0) {
                Item item = playerInventory.get(i);
                if(playerInventory.contains(item)) {
                    if (item.getItemType().compareTo("Equippable") == 0) {
                        Equippable currentItem = ((Equippable) item);
                        if (!currentItem.isEquipped()) {
                            //this.equippedItems.add(item);
                            currentItem.setEquipped(true);
                            System.out.println("You are successfully equipped with " + item.getItemName() + ".");
                            //this.HP += currentItem.getDamageEffect();
                            //System.out.println("Your health is now " + this.HP + ".");
                        } else{
                            System.out.println("The item is already equipped.");
                        }
                    } else
                    {
                        System.out.println("This item is not equippable.");
                    }

                }
                else{
                    System.out.println("You are not picked up the item yet.");
                }

                found=true;
            }
        }
        if(!found) {
            System.out.println("The " + itemName + " is/are not picked up yet.");
        }



    }
    public void unEquipItem(String itemName){
        boolean found=false;
        for (int i = 0; i < playerInventory.size(); i++) {
            if (playerInventory.get(i).getItemName().toLowerCase().compareTo(itemName.toLowerCase()) == 0) {
                Item item = playerInventory.get(i);
                if(item.getItemType().compareTo("Equippable") == 0) {
                    Equippable currentItem = ((Equippable) item);
                    if (currentItem.isEquipped()) {
                        currentItem.setEquipped(false);
                        // this.HP -= currentItem.getDamageEffect();
                        System.out.println("The "+currentItem.getItemName()+" is/are successfully unequipped.");
                        //System.out.println("Your health is now "+ this.HP+".");
                    }else{
                        System.out.println("The item is not equipped yet.");
                    }

                }else{
                    System.out.println("This item is not equippable.");
                }
                found=true;
            }
        }
        if(!found) {
            System.out.println("The " + itemName + " is/are not picked up yet.");
        }

    }
    public void consume(String itemName){
        //System.out.println("inside consume item fn.");
        boolean found=false;
        Item currentItem=null;
        for (int i = 0; i < playerInventory.size(); i++) {
            if (playerInventory.get(i).getItemName().toLowerCase().compareTo(itemName.toLowerCase()) == 0) {
                currentItem = playerInventory.get(i);
                if(playerInventory.contains(currentItem)) {
                    if (currentItem.getItemType().compareTo("Usable") == 0) {

                        ((Usable) currentItem).setUsed(true);
                        System.out.println("You are successfully used the "+ currentItem.getItemName()+".");
                        this.setHP(this.getHP()+ ((Usable) currentItem).getHealthEffect());
                        System.out.println("Your health is now "+ this.getHP()+".");
                        playerInventory.remove(currentItem);
                    }else{
                        System.out.println("This item is not usable.");
                    }
                }
                else{
                    System.out.println("You are not picked up the item yet.");
                }
                found=true;
            }
        }
        if(!found) {
            System.out.println("The " + itemName + " is/are not picked up yet.");
        }


    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public static String getPlayerLocation(){
        return roomID;
    }
    public static void setPlayerLocation(String roomid){
        roomID=roomid;
    }

    public ArrayList<Item> getPlayerInventory() {
        return playerInventory;
    }

    public void setPlayerInventory(ArrayList<Item> playerInventory) {
        this.playerInventory = playerInventory;
    }

    public ArrayList<Equippable> getEquippedItems(){
        ArrayList<Equippable> equippedItems= new ArrayList();
        for(int i=0; i<playerInventory.size();i++){
            if(playerInventory.get(i).getItemType().compareTo("Equippable")==0){
                if(((Equippable)(playerInventory.get(i))).isEquipped()){
                    equippedItems.add(((Equippable)(playerInventory.get(i))));
                }

            }
        }
        return equippedItems;
    }
    public int attack(){
        currentRoom=map.getRooms().get(getPlayerLocation());
        if(currentRoom.getMonster().isDefeated()){
            System.out.println("This room has no monsters.");
            return 1;
        }
        int oldHP=currentRoom.getMonster().getHP();
        Scanner keyboard= new Scanner(System.in);
        Monster monster= currentRoom.getMonster();
        Random rand=new Random(oldHP);
        //int randRange= oldHP/2;
        System.out.println("================= Round "+ player.roundCount++ +" =================");
        int playerDamage = player.getDealDamage();
        int totalDamage= 0;
        for(Equippable equippedItem:getEquippedItems()){
            totalDamage+=equippedItem.getDamageEffect();
        }
        playerDamage=playerDamage+totalDamage;
        System.out.println("The player attack for : "+ playerDamage+".");
        for(Equippable equippedItem:getEquippedItems()){
            playerInventory.remove(equippedItem);
        }
        monster.setHP(monster.getHP()-playerDamage);
        if (monster.getHP()<=0){
            System.out.println("Congratulations! You defeated the monster.");
            for(String s : monster.getDefeatedDescription()){
                System.out.println(s);
            }
            currentRoom.getMonster().setDefeated(true);
            String itemDropped = monster.getItemDropped();
            Item droppedItem=new Item();
            boolean found=false;
            ArrayList<Item> roomInventory= currentRoom.getRoomInventory();
            for(int i=0;i<roomInventory.size();i++){
                if(roomInventory.get(i).getItemID().compareTo(itemDropped)==0){
                    droppedItem=roomInventory.get(i);
                    droppedItem.setAvailable(true);

                }
            }


            return 1;
        }

        System.out.println("The monster has "+ monster.getHP() + " HP left.");
        int monsterDamage = currentRoom.getMonster().getDealDamage();
        int randValue=rand.nextInt(50);
        if(monster.getFixNumber()>randValue){
            monsterDamage*=2;
        }
        System.out.println("Monster attacks you for "+monsterDamage+".");
        this.setHP(this.getHP()-monsterDamage);

        if(this.getHP() <=0 ){
            System.out.println("You got killed by the "+ monster.getMonsterName());

            return -1;
        }
        System.out.println("You have "+this.getHP() + " HP left.");
        return 2;

    }



    /**
     * Displays the description and deal damage of the monster.
     */
    public void examineMonster(String itemName){

        if (itemName.toLowerCase().compareTo("monster") == 0) {
            currentRoom=map.getRooms().get(getPlayerLocation());
            Monster monster = currentRoom.getMonster();
            if(monster.isDefeated()){
                System.out.println("This room has no monsters.");
            }else{
                for(String s : monster.getMonsterDescription()){
                    System.out.println(s);
                }
                System.out.println("The HP of the monster is "+monster.getHP()+
                        " and the monster has "+ monster.getDealDamage()+" deal damage.");
            }

        }else if (itemName.toLowerCase().compareTo("puzzle") == 0) {
            if(!currentRoom.getPuzzle().isSolved()) {
                for(String s : currentRoom.getPuzzle().getPuzzleDescription()){
                    System.out.println(s);
                }
            }else{
                System.out.println("There are no puzzles to solve in this room.");
            }



        }else{
            System.out.println("Invalid command.");
        }

    }

    /**
     * displays the health points of the player
     */
    public void displayHP(String itemName) {
        if (itemName.toLowerCase().compareTo("hp") == 0) {
            System.out.println("The player has " + player.getHP() + " HP.");
        } else {
            System.out.println("Invalid command.");
        }
    }
    /**
     * Fleeing from the combat so that the monster will not be available in the room.
     */
    public void ignore(String itemName){
        currentRoom=map.getRooms().get(getPlayerLocation());
        if (itemName.toLowerCase().compareTo("monster") == 0) {
            if (currentRoom.getMonster().isDefeated()) {
                System.out.println("You are not in a combat.");
            } else {
                System.out.println("You are leaving the combat.");
                currentRoom.getMonster().setDefeated(true);
            }
        }else if (itemName.compareTo("puzzle") == 0) {
            if(!currentRoom.getPuzzle().isSolved()) {
                System.out.println("You are leaving the " + currentRoom.getPuzzle().getPuzzleName() + ".");
            }else{
                System.out.println("There are no puzzles to solve in this room.");
            }

        }else{
            System.out.println("Invalid command");
        }


    }

    /**
     * displays the description, items, puzzles and monsters in the room
     */
    public void exploreCurrentRoom(String currentCommand ) {
        currentRoom=map.getRooms().get(getPlayerLocation());
        System.out.println("You are at the "+currentRoom.getRoomName());
        ArrayList<String> descriptions = currentRoom.getRoomDescription();
        for(String s : descriptions){
            System.out.println(s);
        }
        System.out.println();
        ArrayList<Item> inventory = currentRoom.getRoomInventory();

        if(inventory.isEmpty()){
            if(currentCommand.compareTo("explore")==0){
               // System.out.println("There is no items available in this room.");
            }


        }else{
            int count=0;

            for(Item item : inventory){
                if(item.isAvailable()) {
                    count++;
                    if(count==1){
                        System.out.println("Items available in this room are : ");
                    }

                    System.out.println(" - " + item.getItemName() + ": " +
                            item.getItemDescription());
                }
            }
        }
        Monster monster=currentRoom.getMonster();
        if(!monster.isDefeated()){
            System.out.println("\nThere is a monster in this room: \n"+monster.getMonsterName());
            for(String s : monster.getMonsterDescription()){

                System.out.println(s);
            }

        }
        if(!currentRoom.getPuzzle().isSolved()) {
            Puzzle currentPuzzle = currentRoom.getPuzzle();

            System.out.println("\nYou have a puzzle to solve : "+currentPuzzle.getPuzzleName());
        }

        currentCommand="";
    }

    /**
     * Displays the description of the item
     */
    public  void inspectItem(String itemName ) {
        for (int i = 0; i < playerInventory.size(); i++) {
            if (playerInventory.get(i).getItemName().toLowerCase().compareTo(itemName.toLowerCase()) == 0) {
                Item currentItem = playerInventory.get(i);
                System.out.println(currentItem.getItemDescription());
                if(currentItem.getItemType().compareTo("Equippable")==0){
                    System.out.println("This has "+ ((Equippable)currentItem).getDamageEffect() +
                            " deal damage.");
                }
                if(currentItem.getItemType().compareTo("Usable")==0){
                    System.out.println("This has "+ ((Usable)currentItem).getHealthEffect() +
                            " health point.");
                }
                return;
            }
        }
        System.out.println("The " + itemName + " is/are not picked up yet.");

    }
    /**
     * displays the items in the inventory
     */
    public void displayInventory() {


        if(playerInventory.size() ==0 ){
            System.out.println("You didn’t pickup any items yet.");
            return;
        }else {
            System.out.println("Your inventory has the following items: ");
            //ArrayList<Item> playerInventory= player.getPlayerInventory();
            for(int i=0;i<playerInventory.size();i++){

                System.out.println(" - "+playerInventory.get(i).getItemName());


            }

        }

    }

    public void dropItem(String itemName) {
        Room currentRoom= map.getRooms().get(getPlayerLocation());
        for (int i = 0; i < playerInventory.size(); i++) {
            if (playerInventory.get(i).getItemName().toLowerCase().compareTo(itemName.toLowerCase()) == 0) {
                Item currentItem = playerInventory.get(i);


                if (currentItem.getItemType().compareTo("equippable") == 0) {
                    if (((Equippable) currentItem).isEquipped()) {
                        System.out.println("Before dropping the " + itemName +
                                " you have to unequip it.");
                        return;
                    }

                }
                currentRoom.getRoomInventory().add(currentItem);
                playerInventory.remove(currentItem);
                System.out.println(currentItem.getItemName() + " has been dropped successfully from " +
                        "the player inventory and placed in the " + currentRoom.getRoomName());
                return;
            }
        }
        System.out.println("The " + itemName + " is/are not picked up yet.");

        return;
    }
    /**
     * lets the player to pickup the item specified
     * The item is removed from room inventory and
     * added to Player Inventory
     */
    public void pickupItem(String itemName) {
        Room currentRoom= map.getRooms().get(getPlayerLocation());
        ArrayList<Item> roomInventory=currentRoom.getRoomInventory();
        for(int i=0;i<roomInventory.size();i++){
            if(roomInventory.get(i).getItemName().toLowerCase().compareTo(itemName.toLowerCase())==0){
                Item currentItem=roomInventory.get(i);
                if(currentItem.isAvailable()) {
                    playerInventory.add(currentItem);
                    currentRoom.getRoomInventory().remove(currentItem);
                    System.out.println(currentItem.getItemName() + " has been picked up from the " + currentRoom.getRoomName() +
                            " and successfully added to the player inventory.");
                }

//                else{
//                    System.out.println(itemName + " is not available for pickup.");
//                }

                return;
            }
        }
        System.out.println("The item "+ itemName +" does not exist in this room.");

    }

    @Override
    public String toString() {
        return "Player{" +
                "playerID=" + playerID +
                ",\n playerInventory=" + playerInventory +
                ",\n currentRoom=" + currentRoom +
                ", \nroundCount=" + roundCount +
                '}';
    }
}
