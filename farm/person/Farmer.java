package farm.person;
import module java.base;
import java.util.ArrayList;

public class Farmer {
    private final String name;
    private final farm.field.Farm farm;
    private final ArrayList<farm.crop.Crop> harvestedCrops;

    public String getName() {
        return name;
    }

    public ArrayList<farm.crop.Crop> getHarvestedCrops() {
        return harvestedCrops;
    }

    public Farmer(String name, farm.field.Farm farm) {
        if (name == null || name.isBlank() || farm == null) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.farm = farm;
        this.harvestedCrops = new ArrayList<>();
    }

    public void plantCrop(int row, int col, farm.crop.CropType type) {
        if (farm.getField()[row][col].getCrop() != null) {
            throw new IllegalStateException();
        }
        farm.crop.Crop newCrop = new farm.crop.Crop(type);
        farm.getField()[row][col].plant(newCrop);
    }

    public void waterCrop(int row, int col) {
        farm.field.Plot plot = farm.getField()[row][col];
        farm.crop.Crop crop = plot.getCrop();

        if (crop != null && !crop.getIsCropDead()) {
            crop.water();
        }
    }

    public void harvestCrop(int row, int col) {
        farm.field.Plot plot = farm.getField()[row][col];
        farm.crop.Crop crop = plot.getCrop();

        if (crop != null && crop.isMature()) {
            crop.harvest();
            harvestedCrops.add(crop);
            plot.removeCrop();
        }
    }

    public void waterCrops(farm.crop.CropType type){
        farm.field.Plot[][] field = farm.getField();
        for(int i = 0; i < field.length; i++){
            for(int j = 0; j< field[i].length; j++){
                farm.crop.Crop crop = field[i][j].getCrop();
                if (crop != null && crop.getType() == type && !crop.getIsCropDead()) {
                    crop.water();
                }
            }
        }
    }

    public void harvestCrops(farm.crop.CropType type) {
        farm.field.Plot[][] field = farm.getField();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                farm.crop.Crop crop = field[i][j].getCrop();
                if (crop != null && crop.getType() == type) {
                    if (crop.harvest()) {
                        harvestedCrops.add(crop);
                    }
                }
            }
        }
    }

    public void cleanPlot(int row, int col) {
        farm.field.Plot plot = farm.getField()[row][col];

        if (plot.hasDeadCrop()) {
            plot.removeCrop();
        }
    }

    public void simulateDay() {
        farm.simulateDay();
    }

    public void waterMostThirstyCrop() {
        farm.crop.Crop thirsty = farm.findMostThirstyCrop();
        if (thirsty != null) {
            thirsty.water();
        }
    }
    

}