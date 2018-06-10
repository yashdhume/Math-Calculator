

/**
 *  @author Yash Dhume
 * @author Mahip Singh
 * A Square Matrix should have all functionality of a Matrix
 * If and only if a matrix is a square matrix, can it's determinant be found
 */

public class SquareMatrix extends Matrix {
    /**
     * constructor with 
     * @param rows
     * @param columns
     */
    SquareMatrix(int rows, int columns) {
        super(rows, columns, new double[rows][columns]);
    }

    SquareMatrix(int rows, int columns, double[][] m) {
        super(rows, columns, m);
    }

    public double determinant() throws CloneNotSupportedException {
        int N = this.getColumns();
        if (N == 1) return this.getMAt(0, 0);
        else if (N == 2) return ((this.getMAt(0, 0) * this.getMAt(1, 1)) - (this.getMAt(0, 1) * this.getMAt(1, 0)));
        else {
            double ans = 0;
            for (int i = 0; i < this.getColumns(); i++) {
                SquareMatrix val = (SquareMatrix) this.clone();

                for (int j = 1; j < this.getRows(); j++) {
                    for (int k = 0; k < this.getColumns(); k++) {
                        if (k < i) val.setMAt(j - 1, k, this.getMAt(j, k));
                        else if (k > i)
                            val.setMAt(j - 1, k - 1, this.getMAt(j, k));
                    }
                }
                ans += this.getMAt(0, i) * GeneralMath.power(-1, i) * val.determinant();
            }
            return ans;
        }
    }
}
