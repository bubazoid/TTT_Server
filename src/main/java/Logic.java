/**
 * Created by bubnyshev on 21.10.2016.
 */
public class Logic {
    public static final int DRAW = 2;
    public static final int WIN = 1;
    public static final int NEXT = 0;


    public static synchronized int checkDesk(Integer[] desk) {

        // line One
        if (desk[0] == desk[1] && desk[1] == desk[2] && desk[0] != 0) {
            return WIN;
        }
        if (desk[3] == desk[4] && desk[4] == desk[5] && desk[3] != 0) {
            return WIN;
        }
        if (desk[6] == desk[7] && desk[7] == desk[8] && desk[6] != 0) {
            return WIN;
        }
        if (desk[0] == desk[3] && desk[3] == desk[6] && desk[0] != 0) {
            return WIN;
        }
        if (desk[1] == desk[4] && desk[4] == desk[7] && desk[1] != 0) {
            return WIN;
        }
        if (desk[2] == desk[5] && desk[5] == desk[8] && desk[2] != 0) {
            return WIN;
        }
        if (desk[0] == desk[4] && desk[4] == desk[8] && desk[0] != 0) {
            return WIN;
        }
        if (desk[2] == desk[4] && desk[4] == desk[6] && desk[2] != 0) {
            return WIN;
        }
        for (int i = 0; i <= 8; i++) {
            if (desk[i] == 0) {
                return NEXT;
            }
        }
        return DRAW;
    }
}
