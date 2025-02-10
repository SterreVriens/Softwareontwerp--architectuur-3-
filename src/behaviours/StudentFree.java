package behaviours;

import java.time.DayOfWeek;

import interfaces.IFreeTicketBehaviour;

public class StudentFree implements IFreeTicketBehaviour {
    public boolean IsFree(int index, DayOfWeek day) {
        return index % 2 == 0;
    }
}
