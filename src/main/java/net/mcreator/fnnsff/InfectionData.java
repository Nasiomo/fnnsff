package net.mcreator.fnnsff.infection;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

/**
 * Speichert den Infektions-Status eines Spielers
 * Stufen: 0 = nicht infiziert, 1-5 = Infektions-Level
 */
public class InfectionData {
    public static final String TAG_KEY = "BlackVirusInfection";
    
    private int infectionLevel = 0;
    private int infectionProgress = 0; // 0-100 für nächste Stufe
    
    public InfectionData() {}
    
    /**
     * Erhöht die Infektion um 1
     */
    public void addInfection(int amount) {
        this.infectionProgress += amount;
        
        // Wenn Progress >= 100, nächste Stufe freischalten
        while (this.infectionProgress >= 100 && this.infectionLevel < 5) {
            this.infectionLevel++;
            this.infectionProgress -= 100;
        }
        
        // Max Level 5
        if (this.infectionLevel > 5) {
            this.infectionLevel = 5;
        }
    }
    
    public void setLevel(int level) {
        this.infectionLevel = Math.max(0, Math.min(5, level));
    }
    
    public int getLevel() {
        return this.infectionLevel;
    }
    
    public int getProgress() {
        return this.infectionProgress;
    }
    
    public void setProgress(int progress) {
        this.infectionProgress = progress;
    }
    
    public boolean isInfected() {
        return this.infectionLevel > 0;
    }
    
    /**
     * Speichert in NBT für Persistent-Data
     */
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Level", this.infectionLevel);
        tag.putInt("Progress", this.infectionProgress);
        return tag;
    }
    
    /**
     * Lädt von NBT
     */
    public void deserializeNBT(CompoundTag tag) {
        this.infectionLevel = tag.getInt("Level");
        this.infectionProgress = tag.getInt("Progress");
    }
}