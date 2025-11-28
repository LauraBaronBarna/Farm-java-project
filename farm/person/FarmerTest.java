package farm.person;
import module java.base;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FarmerTest{
    @Test
     public void testPlantCropAndWater() {
        farm.field.Farm myFarm = new farm.field.Farm(5, 5);
        Farmer farmer = new Farmer("Laura", myFarm);
        
        farmer.plantCrop(0, 0, farm.crop.CropType.CORN);
        
        assertNotNull(myFarm.getField()[0][0].getCrop());
        
        int initialWater = myFarm.getField()[0][0].getCrop().getWaterLevel();
        farmer.waterCrop(0, 0);
        
        assertEquals(initialWater + 2, myFarm.getField()[0][0].getCrop().getWaterLevel());
    }
    @Test
    public void testHarvestRemovesMatureCrop() {
        farm.field.Farm myFarm = new farm.field.Farm(5, 5);
        Farmer farmer = new Farmer("Laura", myFarm);
        
        farmer.plantCrop(0, 0, farm.crop.CropType.STRAWBERRY);
        farm.crop.Crop crop = myFarm.getField()[0][0].getCrop();
        
        for(int i = 0; i < 12; i++) { 
            crop.water(); 
            crop.simulateDay(); 
        }
        
        farmer.harvestCrop(0, 0);
        
        assertTrue(myFarm.getField()[0][0].isEmpty());
    }

    @Test
    public void testCleanDeadCrop() {
        farm.field.Farm myFarm = new farm.field.Farm(5, 5);
        Farmer farmer = new Farmer("Laura", myFarm);
        
        farmer.plantCrop(0, 0, farm.crop.CropType.LETTUCE);
        farm.crop.Crop crop = myFarm.getField()[0][0].getCrop();
        
        for(int i = 0; i < 10; i++) { 
            crop.simulateDay(); 
        } 
        
        farmer.cleanPlot(0, 0);
        
        assertTrue(myFarm.getField()[0][0].isEmpty());
    }

        @Test
    public void testStory() {
        runSimulation(farm.crop.CropType.LETTUCE, farm.crop.CropType.CORN, 2, 14, 1);
        runSimulation(farm.crop.CropType.LETTUCE, farm.crop.CropType.CORN, 2, 15, 2);
        runSimulation(farm.crop.CropType.STRAWBERRY, farm.crop.CropType.CORN, 4, 27, 0);
        runSimulation(farm.crop.CropType.STRAWBERRY, farm.crop.CropType.CORN, 4, 28, 1);
        runSimulation(farm.crop.CropType.STRAWBERRY, farm.crop.CropType.LETTUCE, 5, 8, 0);
    }

    private void runSimulation(farm.crop.CropType type1, farm.crop.CropType type2, int interval, int days, int expectedHarvest) {
        farm.field.Farm myFarm = new farm.field.Farm(5, 5);
        Farmer farmer = new Farmer("SimulationFarmer", myFarm);

        farmer.plantCrop(0, 0, type1);
        farmer.plantCrop(0, 1, type2);

        for (int day = 1; day <= days; day++) {
            farmer.simulateDay();
            if (day % interval == 0) {
                farmer.waterCrop(0, 0);
                farmer.waterCrop(0, 1);
            }
        }

        farmer.harvestCrops(type1);
        farmer.harvestCrops(type2);

        assertEquals(expectedHarvest, farmer.getHarvestedCrops().size());
    }
    
    @Test
    public void testWaterMostThirstyCrop() {
        farm.field.Farm myFarm = new farm.field.Farm(5, 5);
        Farmer farmer = new Farmer("Laura", myFarm);
        
        farmer.plantCrop(0, 0, farm.crop.CropType.CORN);
        farmer.plantCrop(0, 1, farm.crop.CropType.CORN);
        
        myFarm.getField()[0][0].getCrop().simulateDay(); 
        
        int water00 = myFarm.getField()[0][0].getCrop().getWaterLevel();
        int water01 = myFarm.getField()[0][1].getCrop().getWaterLevel();
        
        assertTrue(water00 < water01);
        
        farmer.waterMostThirstyCrop();
        
        assertEquals(water00 + 2, myFarm.getField()[0][0].getCrop().getWaterLevel());
    }
    
}