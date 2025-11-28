package farm.field;
import module java.base;

public class Farm{
    private final Plot[][] field;

    public Plot[][] getField(){
        return field;
    }

    public Farm(int rows, int cols){
        if (rows < 1 || cols < 1){
            throw new IllegalArgumentException();
        }
        this.field = new Plot[rows][cols];

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                this.field[i][j] = new Plot();
            }
        }
    }

    public void simulateDay(){
        for (int i = 0; i < field.length; i++){
            for (int j = 0; j < field[i].length; j++){
                farm.crop.Crop crop = field[i][j].getCrop();
                if (crop != null){
                    crop.simulateDay();
                }
            }
        }
    }

    public farm.crop.Crop findMostThirstyCrop() {
        farm.crop.Crop mostThirsty = null;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                farm.crop.Crop current = field[i][j].getCrop();
                if (current != null && !current.getIsCropDead()) {
                    if (mostThirsty == null || current.getWaterLevel() < mostThirsty.getWaterLevel()) {
                        mostThirsty = current;
                    }
                }
            }
        }
        return mostThirsty;
        }   

@Override
    public String toString() {
        StringBuilder txt = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                txt.append(field[i][j].toString());
                if (j < field[i].length - 1) {
                    txt.append(" ");
                }
            }
            if (i < field.length - 1) {
                txt.append("\n");
            }
        }
        return txt.toString();
    }
}