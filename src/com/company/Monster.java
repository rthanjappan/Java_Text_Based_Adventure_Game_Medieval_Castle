package com.company;

import java.util.ArrayList;
import java.util.Objects;

public class Monster extends GameCharacter {

    private String monsterID;
    private String monsterName;
    private ArrayList<String> monsterDescription;
    private ArrayList<String> defeatedDescription;
    private int fixNumber;
    private boolean defeated;
    private int armor;
    private String itemDropped;



    public Monster(){
        super.setHP(0);
        this.monsterID = "0";
        this.monsterName = "";
        this.monsterDescription =new ArrayList<>();
        this.defeatedDescription =new ArrayList<>();
        this.fixNumber = 0;
        this.defeated = true;
        this.armor = 0;
        this.itemDropped="0";
    }

    public String getMonsterID() {
        return monsterID;
    }

    public void setMonsterID(String monsterID) {
        this.monsterID = monsterID;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }

    public ArrayList<String> getMonsterDescription() {
        return monsterDescription;
    }

    public void setMonsterDescription(ArrayList<String> monsterDescription) {
        this.monsterDescription = monsterDescription;
    }

    public ArrayList<String> getDefeatedDescription() {
        return defeatedDescription;
    }

    public void setDefeatedDescription(ArrayList<String> defeatedDescription) {
        this.defeatedDescription = defeatedDescription;
    }

    public int getFixNumber() {
        return fixNumber;
    }

    public void setFixNumber(int fixNumber) {
        this.fixNumber = fixNumber;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    public String getItemDropped() {
        return itemDropped;
    }

    public void setItemDropped(String itemDropped) {
        this.itemDropped = itemDropped;
    }

    @Override
    public String toString() {
        return "Monster{" +

                ", monsterID=" + monsterID +
                ", monsterName='" + monsterName + '\'' +
                ", \nmonsterDescription=" + monsterDescription +
                ", \ndefeatedDescription=" + defeatedDescription +
                ", \nHP=" + super.getHP() +
                ", Deal Damage =" + super.getDealDamage() +
                ", Fix number =" + fixNumber +
                ", defeated=" + defeated +
                ", armor=" + armor +
                ", itemDropped=" + itemDropped +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Monster monster = (Monster) o;
        return fixNumber == monster.fixNumber && defeated == monster.defeated && armor == monster.armor && Objects.equals(monsterID, monster.monsterID) && Objects.equals(monsterName, monster.monsterName) && Objects.equals(monsterDescription, monster.monsterDescription) && Objects.equals(defeatedDescription, monster.defeatedDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), monsterID, monsterName, monsterDescription, defeatedDescription, fixNumber, defeated, armor);
    }
}
