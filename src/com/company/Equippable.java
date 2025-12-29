package com.company;

import java.util.Objects;

public class Equippable extends Item {

    private int damageEffect;
    private boolean isEquipped;
    public Equippable(){};
    public Equippable(String  id, String name, String Description, String type, int damageEffect){
        super(id,name,Description,type,false);
        this.damageEffect=damageEffect;
        this.isEquipped=false;
    }

    public int getDamageEffect() {
        return damageEffect;
    }

    public void setDamageEffect(int damageEffect) {
        this.damageEffect = damageEffect;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    @Override
    public String toString() {
        return "Equippable{" +super.toString()+
                "damageEffect=" + damageEffect +
                ", isEquipped=" + isEquipped +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Equippable that = (Equippable) o;
        return damageEffect == that.damageEffect && isEquipped == that.isEquipped;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), damageEffect, isEquipped);
    }
}
