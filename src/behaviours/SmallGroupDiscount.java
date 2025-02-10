package behaviours;

import interfaces.IGroupDiscountBehaviour;

public class SmallGroupDiscount implements IGroupDiscountBehaviour {
    public double CalculateGroupDiscountMultiplier(int size) {
        return size >= 6 ? 0.9 : 1;
    }
}
