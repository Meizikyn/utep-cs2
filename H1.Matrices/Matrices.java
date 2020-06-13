import java.util.Scanner;
public class Matrices {
    public static void main(String [] argv) {
        new Matrices().run();
    }

    private void run() {
        double [][] athetos1 = {
            {1, 2, 3},
            {4, 5, 6}
        };
        double [][] athetos2 = {
            {7,  8},
            {9, 10},
            {11,12},
        };
        Matrix mA1 = new Matrix(athetos1);
        Matrix mA2 = new Matrix(athetos2);
        Matrix mA3 = new Matrix(mA1.MultiplyMatrix(mA2));

        // Input is not validated
        // Input is in column-major order
        System.out.printf("%s",
                          "Provide 16 integers in column-major order\nto create matrix: ");
        double [][] athetos3 = Matrices.ParseAthetos(
            new Scanner(System.in).nextLine().split(" "), 4);
        System.out.println();

        System.out.printf("%s",
                          "Provide 9 integers in column-major order\nto create matrix: ");
        double [][] athetos4 = Matrices.ParseAthetos(
            new Scanner(System.in).nextLine().split(" "), 3);
        System.out.println();

        System.out.printf("%s",
                          "Provide 9 integers in column-major order\nto create matrix: ");
        double [][] athetos5 = Matrices.ParseAthetos(
            new Scanner(System.in).nextLine().split(" "), 3);
        System.out.println();

        System.out.printf("%s",
                         "Positive Integer: ");
        int squareLimit =
            new Scanner(System.in).nextInt();

        Matrix mI1 = new Matrix(athetos3);
        Matrix mI2 = new Matrix(athetos4);
        Matrix mI3 = new Matrix(athetos5);
        Matrix mI4 = new Matrix(mI2.MultiplyMatrix(mI3));

        int [] largePair = mI1.LocateLargest();

        System.out.printf("Squares: %s\n\n", Squares(12).toString());
        System.out.printf("Matrix M-A1:\n%s\n", mA1.toString());
        System.out.printf("Matrix M-A2:\n%s\n", mA2.toString());
        System.out.printf("Matrix M-A1 by M-A2:\n%s\n", mA3.toString());
        System.out.printf("Matrix M-I1 Diagonal Sum:\n%f\n\n", mI1.SumMajorDiagonal());
        System.out.printf("Matrix M-I2 by Matrix M-I3:\n%s\n", mI4.toString());
        System.out.printf("Matrix M-I1 Largest Value at:\n(%d, %d)\n\n",
                          largePair[0], largePair[1]);
        System.out.printf("User Generated Squares: %s\n\n", Squares(squareLimit).toString());
        return;
    }
    private static double [][] ParseAthetos(String [] stub, int axiom) {
        double [][] athetos = new double [axiom][axiom];
        for (int i=0,k=0;i<axiom;++i)
            for (int j=0;j<axiom;++j,++k)
                athetos[i][j] = Double.parseDouble(stub[k]);
        return athetos;
    }
    private Node Squares(int n) {
        Node s = new Node(1);
        for (int i=2;i<=n;++i)
            s.add(i*i);
        return s;
    }
    private class Node {
        Node next;
        int val;

        public Node(int val) {
            this.next = null;
            this.val = val;
        }
        public void add(int val) {
            Node walk = this;
            for(;walk.next != null;)
                walk = walk.next;
            walk.next = new Node(val);
            return;
        }
        public String toString() {
            String s = "";
            Node walk = this;
            for(;walk != null;) {
                s = s.concat(String.valueOf(walk.val)).concat(" ");
                walk = walk.next;
            }
            return s;
        }
    }
    private class Matrix {
        double [][] trace;
        int [] axiom = new int [2];
        int breach;

        private class MatrixDimensionsException extends IndexOutOfBoundsException {
            public MatrixDimensionsException(String e) {super(e);}
        }

        public Matrix(double [][] athetos) {
            try {
                int base = athetos[0].length;
                for (int i=1;i<athetos.length;++i)
                    if (athetos[i].length != base)
                        throw new MatrixDimensionsException("Axiom Geometry must be Euclidean!");
            } catch(IndexOutOfBoundsException e) {
                e.printStackTrace();
                System.exit(1);
            }
            this.trace = athetos;
            this.axiom[0] = athetos.length;
            this.axiom[1] = athetos[0].length;
            this.breach = Math.min(axiom[0],axiom[1]);
        }

        public double SumMajorDiagonal() {
            double sum = 0;
            for (int i=0;i<this.breach;++i)
                sum += this.trace[i][i];
            return sum;
        }

        public double [][] MultiplyMatrix(Matrix divergence) {
            if (this.axiom[1] != divergence.axiom[0]) {
                System.out.printf("Modus Assertion Failure!");
                return null;
            }
            double [][] projection = new double [this.axiom[0]][divergence.axiom[1]];
            for (int i=0;i<this.axiom[0];++i)
                for (int j=0;j<divergence.axiom[1];++j)
                    for (int k=0;k<this.axiom[1];++k)
                        projection[i][j] += this.trace[i][k] * divergence.trace[k][j];
            return projection;
        }

        public int [] LocateLargest() {
            int [] tuple = {0,0};
            for (int i=0;i<this.axiom[0];++i)
                for (int j=0;j<this.axiom[1];++j)
                    if (this.trace[i][j] > this.trace[tuple[0]][tuple[1]])
                        tuple = new int [] {i,j};
            return tuple;
        }

        public String toString() {
            String s = "";
            for (int i=0;i<this.axiom[0];++i) {
                for (int j=0;j<this.axiom[1];++j)
                    s = s.concat(String.valueOf(this.trace[i][j])).concat(" ");
                s = s.concat("\n");
            }
            return s;
        }
    }

}
