package fr.imt.coffee.machine.component;

import fr.imt.coffee.cupboard.coffee.type.CoffeeType;
import fr.imt.coffee.machine.exception.BeanTypeDifferentOfCoffeeTypeTankException;

public class BeanTank extends Tank{

    private fr.imt.coffee.cupboard.coffee.type.CoffeeType beanCoffeeType;
    /**
     * Réservoir de graines de café de la cafetière
     *
     * @param initialVolume Volume de graines à mettre dans le réservoir à sa création
     * @param minVolume     Volume de graines minimal du réservoir
     * @param maxVolume     Volume de graines maximal du réservoir
     * @param beanCoffeeType Type de café dans le réservoir
     */
    public BeanTank(double initialVolume, double minVolume, double maxVolume, CoffeeType beanCoffeeType) {
        super(initialVolume, minVolume, maxVolume);
        this.beanCoffeeType = beanCoffeeType;
    }

    public void increaseCoffeeVolumeInTank(double coffeeVolume, fr.imt.coffee.cupboard.coffee.type.CoffeeType coffeeType) throws BeanTypeDifferentOfCoffeeTypeTankException {
        if(this.getActualVolume()>this.getMinVolume() && coffeeType!=this.beanCoffeeType) {
            throw new BeanTypeDifferentOfCoffeeTypeTankException("Type de grain différent de celui dans le réservoir, qui n'est pas vide");
        }
            this.increaseVolumeInTank(coffeeVolume);
            this.beanCoffeeType = coffeeType;
    }
    public CoffeeType getBeanCoffeeType() {
        return beanCoffeeType;
    }

    public void setBeanCoffeeType(CoffeeType beanCoffeeType) {
        this.beanCoffeeType = beanCoffeeType;
    }

    public void emptyTank() {
        decreaseVolumeInTank(getActualVolume()-getMinVolume());
    }
}
