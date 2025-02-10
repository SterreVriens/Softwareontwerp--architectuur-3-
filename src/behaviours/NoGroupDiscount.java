package behaviours;

import interfaces.IGroupDiscountBehaviour;

public class NoGroupDiscount implements IGroupDiscountBehaviour {
    public double CalculateGroupDiscountMultiplier(int size) {
        return 1;
    }
}
