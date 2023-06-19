package CSC8012;
import java.util.*;

public class SortedArrayList<E extends Comparable> extends ArrayList<E>
{
    /**
     * Extended ArrayList, and adds a lexicographic sorting algorithm to the ".add" method.
     * @param data element whose presence in this collection is to be ensured
     * @return
     */
    public boolean add(E data)
    {
        super.add(data);
        int thelastIndex = 0;
        for(thelastIndex = this.size() -1 ; thelastIndex > 0 && this.get(thelastIndex-1).compareTo(data) > 0 ; thelastIndex--)
        {
            this.set(thelastIndex, this.get(thelastIndex-1));
        }
        this.set(thelastIndex,data);
        return true;
    }


}



