package behaviours;

import interfaces.IPremiumBehaviour;

public class NonStudentPremium implements IPremiumBehaviour {
    public double CalculateExtraPrice(boolean isPremium) {
        return isPremium ? 3 : 0;
    }
}
