/*
================================================================================

  SquareMatrix

================================================================================
*/

public class SquareMatrix {
    public class MatrixDimensionsException extends IndexOutOfBoundsException {
        public MatrixDimensionsException(String e) {
            super(e);
        }
    }
    private int [][] table;
    private int rows;
    public SquareMatrix(int [][] inputs) throws IndexOutOfBoundsException {
        if (inputs.length == 0)
            throw new MatrixDimensionsException("Matrix must be not be empty!");
        try {
            int std = inputs.length;
            for (int i = 0; i < inputs.length; ++i) {
                if (inputs[i].length != std)
                    throw new MatrixDimensionsException("Matrix must be square!");
            }
        }
        catch(MatrixDimensionsException e) {
            System.out.printf("Matrix Failure -> %s\n", e.toString());
            System.exit(1);
        }

        this.table   = inputs;
        this.rows    = this.table.length;
    }
    /*
    ========================================
    IsMagickSquare

      Determines if Matrix is a valid
      Magick Square.
    ========================================
    */
    public boolean IsMagickSquare() {
        int square = this.rows*this.rows;    // Evaluate square to reduce complexity
        int cube   = this.rows*square;       // Evaluate cube, used to provide an upper threshold for magick membership
        int    b, d, x, g;                   // Declare base and secondary diagonals, exclusivity tracker, and magick tracker
        int [] r, c, xm, gm;                 // Declare row / column sums, and exclusivity / magick membership pools
        x = 1;                               // Initialize exclusivity value to 1
        b = d = g = 0;                       // Initialize base/secondary diagonals and magick tacker
        r = new int [this.rows];             // Initialize pool of row sums
        c = new int [this.rows];             // Initialize pool of column sums
        xm = new int [square];               // Initialize exclusivity membership pool
        gm = new int [cube];                 // Initialize magick membership pool
        int cap = this.rows - 1;             // Precalculate index capacity for negative-indexing
        for (int i = 0; i <= cap; ++i) {     // Begin Loop A
            b += this.table[i][i];           // Calculate base diagonal magick
            d += this.table[i][cap-i];       // Calculate secondary diagonal magick
            for (int j = 0; j <= cap; ++j) { // Begin Loop B
                r[i] += this.table[i][j];    // Calculate current row sum
                c[i] += this.table[j][i];    // Calculate current column sum
                xm[this.table[i][j]-1] = 1;  // Acquire exclusivity/uniqueness of cell
            }                                // End Loop B
            gm[r[i]] = gm[c[i]] = 1;         // Acquire magick membership of sums
        }                                    // End Loop A
        gm[b] = gm[d] = 1;                   // Acquire magick membership of diagonals
        for (int i = 0; i < square; ++i) {   // Begin Loop C
            x *= xm[i];                      // Evaluate exclusivity value
        }                                    // End Loop C
        for (int i = 0; i < cube; ++i) {     // Begin Loop D
            g += gm[i];                      // Evaluate magick value
        }                                    // End Loop D
        if (x == 0 || g != 1)                // Test failure conditions
            return false;                    // Provide failure condition
        return true;                         // We have a magick square
    }
}
