package triangle;

public class Debug {
    static boolean PRINT_TRACE = true;
    public static void print(String message){
        if (PRINT_TRACE) {
            System.out.println(message);
        }
    }
}
