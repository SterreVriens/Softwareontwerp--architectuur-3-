package behaviours;

import interfaces.IPremiumBehaviour;

public class StudentPremium implements IPremiumBehaviour {
    public double CalculateExtraPrice(boolean isPremium) {
        return isPremium ? 2 : 0;
    }
}
