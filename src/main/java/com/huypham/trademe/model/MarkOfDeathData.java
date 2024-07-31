package com.huypham.trademe.model;

public class MarkOfDeathData {
    private float snapShotHealth;
    private float bonusDamage = 5f; // 5 damage is base

    public MarkOfDeathData() {}

    public float getSnapShotHealth() {
        return snapShotHealth;
    }

    public void setSnapShotHealth(float snapShotHealth) {
        this.snapShotHealth = snapShotHealth;
    }

    public float getBonusDamage() {
        return bonusDamage;
    }

    public void setBonusDamage(float bonusDamage) {
        this.bonusDamage = bonusDamage;
    }

    public void addBonusDamage(float bonusDamage) {
        this.bonusDamage += bonusDamage;
    }
}

