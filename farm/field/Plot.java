package farm.field;
import module java.base;

public class Plot{
    private farm.crop.Crop crop;

    public farm.crop.Crop getCrop(){
        return crop;
    }

    public Plot(){
        this.crop = null;
    }

    public boolean isEmpty(){
        return crop == null;
    }

    public void plant(farm.crop.Crop crop){
        if (crop == null){
            throw new IllegalArgumentException();
        }
        if (this.crop != null){
            throw new IllegalStateException();
        }
        this.crop = crop;
    }

    public void removeCrop(){
        if (crop != null){
            crop = null;
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean hasDeadCrop(){
        return crop != null && crop.getIsCropDead();
    }

    @Override
    public String toString(){
        if (isEmpty()){
            return "E";
        }
        return crop.toString();
    }
}