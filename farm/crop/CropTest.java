package farm.crop;
import module java.base;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class CropTest {
    @Test
    public void testIsMature() {
        Crop crop = new Crop(CropType.LETTUCE);
    
        assertFalse(crop.isMature());

        for (int i = 0; i < 12; i++) {
            crop.water();  
            crop.simulateDay(); 
        }
        assertTrue(crop.isMature());
    }
    @Test
    public void testHarvestSuccess() {
        Crop crop = new Crop(CropType.LETTUCE);
        for (int i = 0; i < 12; i++) {
            crop.water();
            crop.simulateDay();
        }
        
        assertTrue(crop.harvest());
        assertEquals(0, crop.getGrowthLevel());
        assertEquals(2, crop.getWaterLevel());
        assertEquals(0, crop.getDryDays());
    }

    @Test
    public void testHarvestFailsWhenNotMatureOrDead() {
        Crop immature = new Crop(CropType.CORN);
        assertFalse(immature.harvest());

        Crop dead = new Crop(CropType.LETTUCE);
        for (int i = 0; i < 10; i++) {
            dead.simulateDay();
        }
        assertFalse(dead.harvest());
    }

    @Test
    public void testWaterDecayAfterSimulateDay() {
        Crop crop = new Crop(CropType.CORN);
        int initialWater = crop.getWaterLevel();
        crop.simulateDay();
        assertEquals(initialWater - 1, crop.getWaterLevel());
    }

    @Test
    public void testGrowthOccursWhenWatered() {
        Crop crop = new Crop(CropType.LETTUCE);
        int initialGrowth = crop.getGrowthLevel();
        crop.water();
        crop.simulateDay();
        assertEquals(initialGrowth + 3, crop.getGrowthLevel());
    }

    @Test
    public void testDifferentDeathThresholds() {
        Crop lettuce = new Crop(CropType.LETTUCE);
        
        lettuce.simulateDay();
        lettuce.simulateDay();
        
        lettuce.simulateDay();
        lettuce.simulateDay();
        assertFalse(lettuce.getIsCropDead());
        
        lettuce.simulateDay();
        assertTrue(lettuce.getIsCropDead());
    }
}
// javac -cp .:junit6all.jar farm/crop/CropTest.java  