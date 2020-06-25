/*

  + Storage ::

  integers for the [b]ase diagonal, and secondary [d]iagonal

  membership bits for e[x]clusivity of integers and ma[g]ick number sums

  arrays to maintain the sums of each [r]ow and [c]olumn

  arrays to test exclusivity and magick membership [xm] and [gm]

  + Algorithm ::

  Iterate over the matrix capturing the an iterative sum each diagonal, row, and
  column in a single pass.

  Use the values of each square cell as an index to track exclusivity
  membership, setting each value at such index to 1.

  Use the values of diagonal, row, and column sums from each iterative pass as
  an index to track magick membership, setting each value at such index to 1.

  Sequentially multiply each value in the exclusivity tracker, if any value
  was repeated in the square, an index would have been skipped leaving a 0
  value; this multiplication sequence will thus result in 1 if every value is
  reached exactly once, and 0 if any value is skipped (meaning numerical
  repetition).

  Sum over each value in the magick tracker; the repetition of any magick
  number would result in a sum greater than 1, meaning that more than one
  magick number was derived and therefore the square is not a magick square.

  Thus, if the exclusivity value is 0, or the magick value is not exactly 1, then
  the square is not a magick square.

  Otherwise, it is a magick square.

*/
public class Main {
    public static void main(String [] argv) {
        int root = (int)Math.sqrt(argv.length);
        if (root*root != argv.length) {
            System.out.printf("%s\n", "Incorrect number of arguments!");
            System.exit(1);
        }
        int [][] candidate = new int [root][root];
        for (int i = 0, k = 0; i < root; ++i)
            for (int j = 0; j < root; ++j, ++k)
                candidate[i][j] = Integer.parseInt(argv[k]);
        /*
        int [][] candidate1 = {{2, 7, 6},
                               {9, 5, 1}
                               {4, 3, 8}};
        */
        boolean t = new SquareMatrix(candidate).IsMagickSquare();
        System.out.println(t);
    }
}
