package behaviours;

import java.time.DayOfWeek;

import interfaces.IFreeTicketBehaviour;

public class NonStudentFree implements IFreeTicketBehaviour {
    public boolean IsFree(int index, DayOfWeek day) {
        //check if weekend
        if (day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return false;
        }
        //check if second ticket
        return index % 2 == 1;
    }
}
