/*
+ Name :: Nicholas Sims
+ ID   :: 80713446

* Function Classes

  a. Ω(1)
  b. Ω(log n)
  c. Ω(log log log n)
  d. Ω((log n)^3)
  e. Ω(n * log n)
  f. Ω(n)
  g. Ω(n^6)
  h. Ω(3^n)
  i. Ω(n!)
  j. Ω(n^n)
  k. Bonues: Not in any Ω(g(n)) where g(n) is an arithmetic expression on n


* Objective

  Devise a series of arbitrary incrementing functions.

  Each should either directly increment in as simple a manner as possible, or
  calculate the limit for which it is incremented.

  Since the assignment requires that stars be printed that match the number of
  elementary operations determined by the input size, the incerment of an
  arbitrary counter is the bounded operation relative to n; this counting token
  is returned to a star printer that is not included in the algorithm
  calculation.

* Log Log Log n

  I never figured this one out


*/

public class Main {

    public static void main(String [] argv) {



        /*

           Special Note on

           c. Ω(log log log n)

           Works best with 16 ^ (2 ^ ( sum(i=0, i^2, k=target-1) ) )

           i.e. 16^2^(0+2+4+8) == 3

           Be warned that these obviously scale insanely fast, such numbers
           cannot be held by integers after a target value of 2. Calculating
           these numbers with calculator, even matlab, causes breaks in the
           calculations due to the shear size if the number.

           The Java VM probably won't handle numbers larger than target 2.


        */


        // Establish an arbitrary set of inputs,
        // play with these at will.

        int [] inputs = {
            /* a. Ω(1)             :: */      1    ,
            /* b. Ω(log n)         :: */      1    ,
            /* c. Ω(log log log n) :: */      1    ,  // Did you read the note above?
            /* d. Ω((log n)^3)     :: */      1    ,
            /* e. Ω(n * log n)     :: */      1    ,
            /* f. Ω(n)             :: */      1    ,
            /* g. Ω(n^6)           :: */      1    ,
            /* h. Ω(3^n)           :: */      1    ,
            /* i. Ω(n!)            :: */      1    ,
            /* j. Ω(n^n)           :: */      1    ,
            /* k. Ω(g(n))          :: */      1
        };


        // Pass along values to the printer
        // Printer takes a header for easier debugging output.

        print("a. Ω(1)",                 inputs[0],  a_constant(inputs[0]));
        print("b. Ω(log n)",             inputs[1],  b_logarithmic(inputs[1]));
        print("c. Ω(log log log n)",     inputs[2],  c_logloglogarithmic(inputs[2]));
        print("d. Ω((log n)^3)",         inputs[3],  d_logarithmiccubed(inputs[3]));
        print("e. Ω(n * log n)",         inputs[4],  e_linearithmic(inputs[4]));
        print("f. Ω(n)",                 inputs[5],  f_linear(inputs[5]));
        print("g. Ω(n^6)",               inputs[6],  g_hexic(inputs[6]));
        print("h. Ω(3^n)",               inputs[7],  h_exponentialbase3(inputs[7]));
        print("i. Ω(n!)",                inputs[8],  i_factorial(inputs[8]));
        print("j. Ω(n^n)",               inputs[9],  j_exponentialbasen(inputs[9]));
        print("k. Ω(non-deterministic)", inputs[10], k_bonus(inputs[10]));


    }


    /*
      Each function here does not itself perform the printing, as each print
      operation is substituted by a single increment to count. A wrapper
      function #print() then prints the number of stars proportional to the
      count returned by each function. This is to avoid writing
      "System.out.printf" over and over again, and makes each function more
      precise to read, if a bit arbitrary and contrived.
    */

    // a. Ω(1) -[x]-
    // Self explanatory
    private static int a_constant(int n) {
        return 1;
    }

    // b. Ω(log n) -[x]-
    // logarithm in base 2
    private static int b_logarithmic(int n) {
        int count = 0;
        for (;n>1;++count)
            n /= 2;
        return count;
    }

    // c. Ω(log log log n) -[x]-
    // Triple composition logarithm in base 2
    private static int c_logloglogarithmic(int n) {
        return b_logarithmic(b_logarithmic(b_logarithmic(n)));
    }

    // d. Ω((log n)^3) -[x]-
    // Cut each increment series by 2,
    // reset child-cycle at each parent-cycle
    // to achieve cubic-logarithm reduction
    private static int d_logarithmiccubed(int n) {
        int count = 0;
        int i, j, k;
        i = j = k = n;
        for (;j>1;) {
            j /= 2;
            k = i;
            for (;k>1;) {
                k /= 2;
                n = i;
                for (;n>1;++count)
                    n /= 2;
            }
        }
        return count;
    }

    // e. Ω(n * log n) -[x]-
    // Similar principle as above, just arbitrarily limits sub-cycles
    // by the initial n
    private static int e_linearithmic(int n) {
        int count = 0;
        int k = n;
        for (int i=0; i<k; ++i) {
            n = k;
            for (;n>1;++count)
                n /= 2;
        }
        return count;
    }

    // f. Ω(n) -[x]-
    private static int f_linear(int n) {
        int count = 0;
        for (int i=0; i<n; ++i)
            ++count;
        return count;
    }

    // g. Ω(n^6) -[x]-
    // Arbitrary cyclical depth of 6
    private static int g_hexic(int n) {
        int count = 0;
        for (int i_1=0; i_1<n; ++i_1)
            for (int i_2=0; i_2<n; ++i_2)
                for (int i_3=0; i_3<n; ++i_3)
                    for (int i_4=0; i_4<n; ++i_4)
                        for (int i_5=0; i_5<n; ++i_5)
                            for (int i_6=0; i_6<n; ++i_6,++count){}
        return count;
    }

    // h. Ω(3^n) -[x]-
    // Initial complexity looks linear, but performs
    // an arbitrary elementary operation (++count)
    // limited by a value that may be calculated
    // in a linear fashion, though the complexity
    // does indeed grow at 3^n, albiet in a silly way.
    private static int h_exponentialbase3(int n) {
        int count = 0;
        int k = 1;
        for (int i=0; i<n; ++i)
            k *= 3;
        for (;count<k;++count){}
        return count;
    }

    // i. Ω(n!) -[x]-
    // Run-of-the-mill factorial
    private static int i_factorial(int n) {
        int count = 0;
        int k = i_factorial_r(n);
        for (;count<k;++count){}
        return count;
    }
    private static int i_factorial_r(int n) {
        if (n == 1)
            return 1;
        return n * i_factorial_r(n-1);
    }

    // j. Ω(n^n) -[x]-
    // Same principle as in base three
    private static int j_exponentialbasen(int n) {
        int count = 0;
        int k = 1;
        for (int i=0; i<n; ++i)
            k *= n;
        for (;count<k;++count){}
        return count;
    }

    // k. Ω(non-deterministic) -[x]-
    // Not sure if this is what you meant, though it doesn't
    // grow based on any arithmetic operation, might as well
    // be at random for its application.
    // Uses a jump table with some random values to bound n by,
    // n limited at a threshold of 1022 since its the largest value
    // used in the table.
    // Could have had more values, but you get the idea.
    private static int k_bonus(int n) {
        int count = 0;
        int k;
        switch(n % 1022) {
        case 0:
            k = 1;
            break;
        case 17:
            k = 66;
            break;
        case 21:
            k = 171;
            break;
        case 66:
            k = 21;
            break;
        case 274:
            k = 777;
            break;
        case 777:
            k = 274;
            break;
        case 5:
            k = 389;
            break;
        case 389:
            k = 5;
            break;
        case 624:
            k = 45;
            break;
        case 1:
            k = 622;
            break;
        default:
            k = 1;
        }
        for (;count<k;++count){}
        return count;
    }

    // Star Printer, provides a header for debugging purposes when playing with
    // different input values.
    private static void print(String header, int n, int t) {
        System.out.printf("%s for n = %d\n+----------+\n| %8d |\n+----------+\n%s\n\n",
                          header,
                          n,
                          t,
                          new String(new char [t]).replace("\0", "*"));
    }
}
