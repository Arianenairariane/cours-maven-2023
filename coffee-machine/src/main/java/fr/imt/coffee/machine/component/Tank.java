package fr.imt.coffee.machine.component;

public class Tank {
    private final double maxVolume;
    private final double minVolume;
    private double actualVolume;

    /**
     * Réservoir d'eau de la cafetière.
     * @param initialVolume Volume à mettre dans le réservoir à sa création
     * @param minVolume Volume minimal du réservoir
     * @param maxVolume Volume maximal du réservoir
     */
    public Tank(double initialVolume, double minVolume, double maxVolume){
        this.maxVolume = maxVolume;
        this.minVolume = minVolume;
        this.actualVolume = initialVolume;
    }

    /**
     * Réduit le volume de matière dans le réservoir
     * @param volumeToDecrease Volume de matière à enlever dans le réservoir
     */
    public void decreaseVolumeInTank(double volumeToDecrease){
        if(volumeToDecrease<0){
            throw new IllegalArgumentException("Le volume à réduire ne peut pas être négtif");
        }else if(volumeToDecrease>actualVolume-minVolume){
            throw new IllegalArgumentException("Le volume à réduire ne peut pas être supérieur au volume qui peut l'être");
        }
        this.actualVolume -= volumeToDecrease;
    }

    /**
     * Augmente le volume de matière dans le réservoir
     * @param volumeToIncrease Volume de matière à ajouter dans le réservoir
     */
    public void increaseVolumeInTank(double volumeToIncrease){
        if(volumeToIncrease<0){
            throw new IllegalArgumentException("Le volume à ajouter ne peut pas être négtif");
        }else if(volumeToIncrease>maxVolume-actualVolume){
            throw new IllegalArgumentException("Le volume à ajouter ne peut pas être supérieur au volume qui peut l'être");
        }
        this.actualVolume += volumeToIncrease;
    }

    public double getMaxVolume() {
        return maxVolume;
    }

    public double getMinVolume() {
        return minVolume;
    }

    public double getActualVolume() {
        return actualVolume;
    }
}
