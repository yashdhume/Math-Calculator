
/**
 *  @author Yash Dhume
 * @author Mahip Singh
 **/
import java.util.Arrays;

public class Matrix implements Cloneable {
    private int rows;
    private int columns;
    private double[][] m;

    /**
     * Creates a new matrix with:
     * @param rows
     * @param columns
     * and an empty double[rows][columns]
     */
    Matrix(int rows, int columns) {
        this(rows, columns, new double[rows][columns]);
    }

    /**
     * Creates a new Matrix with the parameters
     * @param rows
     * @param columns
     * @param m
     */
    Matrix(int rows, int columns, double[][] m) {
        this.rows = rows;
        this.columns = columns;
        this.m = m;
    }

    /**
     * Default Constructor
     */
    Matrix() {
        this(0, 0);
    }

    /**
     * @return number of rows
     */

    public int getRows() {
        return rows;
    }

    /**
     * @param rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * @return number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * @param columns
     */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     * @return m
     */
    public double[][] getM() {
        return m;
    }

    /**
     * @param m
     */
    public void setM(double[][] m) {
        this.m = m;
    }

    /**
     *
     * @param r
     * @param c
     * @return m[r][c]
     */
    public double getMAt(int r, int c) {
        return this.m[r][c];
    }

    /**
     *
     * @param r
     * @param c
     * @param val
     */
    public void setMAt(int r, int c, double val) {
        this.m[r][c] = val;
    }

    /**
     * @param r
     * @param temp
     */
    public void setMAt(int r, double[] temp) {
        this.m[r] = temp;
    }

    /**
     * @param m2
     * @return this + m2
     * @throws Exception
     */
    public Matrix add(Matrix m2) throws Exception {
        if (this.rows != m2.rows || this.columns != m2.columns)
            throw new IllegalArgumentException();
        Matrix result = new Matrix(this.rows, m2.columns);
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < m2.columns; j++)
                result.m[i][j] = this.m[i][j] + m2.m[i][j];
        return result;
    }

    /**
     * @param m2
     * @return this - m2
     * @throws Exception
     */
    public Matrix subtract(Matrix m2) throws Exception {
        if (this.rows != m2.rows || this.columns != m2.columns)
            throw new IllegalArgumentException();
        Matrix result = new Matrix(this.rows, m2.columns);
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < m2.columns; j++)
                result.m[i][j] = this.m[i][j] - m2.m[i][j];
        return result;
    }

    /**
     * @param m2
     * @return result of multiplication = this * m2
     * @throws Exception
     */
    public Matrix multiply(Matrix m2) throws Exception {
        if (this.columns != m2.rows)
            throw new IllegalArgumentException();
        Matrix result = new Matrix(this.rows, m2.columns);
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < m2.columns; j++)
                for (int k = 0; k < this.columns; k++)
                    result.m[i][j] += this.m[i][k] * m2.m[k][j];
        return result;
    }

    /**
     * @param o
     * @return Is this equal to Object o?
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrix)) return false;
        Matrix matrix = (Matrix) o;
        if (getRows() != matrix.getRows()) return false;
        if (getColumns() != matrix.getColumns()) return false;
        return Arrays.deepEquals(getM(), matrix.getM());
    }

    /**
     * Reduces a given M by N matrix where
     * M = #equations = rows
     * N = #variables + 1 = columns + 1
     *
     * the last column contains the column vector B
     *
     * @return null if there are an infinite number of solutions or if there are no solutions. Else: the one solution
     */
    public double[] solve() {
        int rows = this.rows;
        int columns = this.columns - 1;

        if (columns > rows) return null;

        for (int i = 0; i < columns; i++) {//forward

            //swapping rows to make computation easier
            int maxIndex = i;
            for (int j = i + 1; j < rows; j++) {
                if (Math.abs(this.getMAt(j, i)) > Math.abs(this.getMAt(maxIndex, i)))
                    maxIndex = j;
            }
            if (Math.abs(this.getMAt(maxIndex, i)) < GeneralMath.EPS)
                return null;
            double[] temp = this.getM()[i];
            this.setMAt(i, this.getM()[maxIndex]);
            this.setMAt(maxIndex, temp);

            if (this.getMAt(i, i) < 0) {//making coefficients positive
                for (int j = i; j <= columns; j++) {
                    this.setMAt(i, j, -this.getMAt(i, j));
                }
            }

            for (int j = i + 1; j < columns; j++) {
                double factor = this.getMAt(j, i) / this.getMAt(i, i);
                for (int k = i; k <= columns; k++) {
                    this.setMAt(j, k, this.getMAt(j, k) - (this.getMAt(i, k) * factor));
                }
            }
        }

        for (int i = columns; i < rows; i++)
            if (Math.abs(this.getMAt(i, columns)) > GeneralMath.EPS)
                return null;


        for (int i = columns - 1; i >= 0; i--) {//backward
            if (Math.abs(this.getMAt(i, i)) < GeneralMath.EPS)
                return null;

            for (int j = i - 1; j >= 0; j--) {
                double factor = this.getMAt(j, i) / this.getMAt(i, i);
                for (int k = i; k <= columns; k++)
                    this.setMAt(j, k, this.getMAt(j, k) - (this.getMAt(i, k) * factor));
            }
        }

        for (int i = 0; i < columns; i++) {//creating leading ones
            this.setMAt(i, columns, this.getMAt(i, columns) / this.getMAt(i, i));
            this.setMAt(i, i, this.getMAt(i, i) / this.getMAt(i, i));
        }

        double[] solved = new double[rows];

        for (int i = 0; i < rows; i++)
            solved[i] = this.getMAt(i, columns);

        return solved;
    }


    @Override
    public String toString() {
        return "Matrix{" + "rows=" + rows + ", columns=" + columns + ", m=" + Arrays.toString(m) + '}';
    }

    @Override
    public Matrix clone() {
        return new Matrix(this.rows, this.columns, this.m);
    }
}
