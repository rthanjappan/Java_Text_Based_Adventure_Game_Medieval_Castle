package com.company;

import java.util.Objects;

public class Usable extends Item {

    private int healthEffect;
    private boolean isUsed;
    public Usable(){};
    public Usable(String  id, String name, String Description, String type, int healthEffect){
        super(id,name,Description,type,false);
        this.healthEffect=healthEffect;
        this.isUsed=false;
    }

    public int getHealthEffect() {
        return healthEffect;
    }

    public void setHealthEffect(int healthEffect) {
        this.healthEffect = healthEffect;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public String toString() {
        return "Usable{" + super.toString()+
                " healthEffect=" + healthEffect +
                ", isUsed=" + isUsed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Usable usable = (Usable) o;
        return healthEffect == usable.healthEffect && isUsed == usable.isUsed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), healthEffect, isUsed);
    }
}
