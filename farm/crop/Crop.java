package farm.crop;
import module java.base;

public class Crop {
    private final CropType type;
    private int growthLevel;
    private int waterLevel;
    private boolean isCropDead;
    private int dryDays;

    public CropType getType(){
        return type;
    }

    public int getGrowthLevel(){
        return growthLevel;
    }

    public int getWaterLevel(){
        return waterLevel;
    }

    public boolean getIsCropDead(){
        return isCropDead;
    }

    public int getDryDays(){
        return dryDays;
    }

    public Crop(CropType type){
        if (type == null){
            throw new IllegalArgumentException("`type` cannot be `null`.");
        }

        this.type = type;
        this.growthLevel = 0;
        this.waterLevel = 2;
        this.isCropDead = false;
        this.dryDays = 0;
    }

    public void water(){
        if (waterLevel < 10){
            waterLevel += 2;
        }
        if (waterLevel > 10){
            waterLevel = 10;
        }
        dryDays = 0;
    }

    public void simulateDay(){
        waterLevel--;

        if (waterLevel > 0){
            growthLevel += type.getGrowthRate();
            if (growthLevel > 100){
                growthLevel = 100;
            }
        } else {
            dryDays++;
        }
        if(dryDays > type.getMaxDryDays()){
            isCropDead = true;
        }        
    }

    public boolean isMature(){
        if (!isCropDead && growthLevel >= type.getPossibleMaturity()){
            return true;
        }
        return false;
    }

    public boolean harvest(){
        if (growthLevel >= type.getPossibleMaturity() && !isCropDead){
            growthLevel = 0;    
            waterLevel = 2;    
            dryDays = 0; 
            return true;
        }
        return false;
    }

@Override
    public String toString(){
        if (isCropDead){
            return "D";
        }
        return switch (type){
            case LETTUCE -> "L";
            case CORN -> "C";
            case STRAWBERRY -> "S";
        };
    }
}


// ./check.cmd CropStructureTest.java CropStructureTest
