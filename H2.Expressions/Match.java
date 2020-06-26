import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
public class Match {
    public static void main(String [] argv) throws Exception {
        Scanner inStream = new Scanner(new File(argv[0])).useDelimiter("\\Z");
        String body = inStream.next();
        inStream.close();
        String syntax = body.replaceAll("[^\\(\\[\\{\\)\\]\\}]+","");
        System.out.println(syntax);
        System.out.println(match(syntax));
    }
    private static boolean match(String s) {
        try {
            switch(s.charAt(0)) {
            case '(':
                match(s,1,')');
                break;
            case '[':
                match(s,1,']');
                break;
            case '{':
                match(s,1,'}');
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static int match(String s, int i, char t) throws Exception { // --- ( [ ] { } )
        System.out.printf("Have: -%c- # Need: -%c- # Index: %d\n", s.charAt(i), t, i);
        while (s.charAt(i) != t) {
            switch(s.charAt(i)) {
            case '(':
                i = match(s,i+1,')');
                continue;
            case '[':
                i = match(s,i+1,']');
                continue;
            case '{':
                i = match(s,i+1,'}');
                continue;
            default:
                throw new Exception();
            }
        }
        System.out.printf("Match!\n");
        return i+1;
    }

}
