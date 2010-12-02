package voltest;
/**
 * Repräsentiert eine nxm-Matrix
 *
 * @author Nicolas Neubauer
 *
 */
public class Matrix {
	private double[][] elements;
	
	public final static double EPSILON = 0.0000000001;

	/**
	 * Übernimmt die Referenz auf das 2-Dimensionale Array in Form von
	 * array[zeilen][spalten].
	 * 
	 * @param elements
	 *            die Elemente der Matrix
	 */
	public Matrix(double[][] elements) {
		this.elements = elements;
	}

	/**
	 * Erzeugt eine Einheitsmatrix mit rows Zeilen und Spalten
	 * 
	 * @param rows
	 *            Anzahl der Zeilen/Spalten
	 * @return eine neue Einheitsmatrix
	 */
	public static Matrix createIdentity(int rows) {
		double[][] identity = new double[rows][rows];

		for (int i = 0; i < rows; i++)
			identity[i][i] = 1;

		return new Matrix(identity);
	}

	/**
	 * Gibt die Matrix als String zurück.
	 * 
	 * @return String
	 */
	public String toString() {
		String r = "{";
		// Durch alle Textfelder gehen und Doubles rausziehen
		for (int row = 0; row < elements.length; row++) {
			r += "{";
			for (int col = 0; col < elements[row].length; col++) {
				r += (col + 1 == elements[row].length) ? elements[row][col]
						: elements[row][col] + ", ";
			}
			r += (row + 1) == elements.length ? "}" : "}, \n";
		}

		return r + "}";
	}

	/**
	 * @return Anzahl der Zeilen
	 */
	public int getRows() {
		return elements.length;
	}

	/**
	 * @return Anzahl der Spalten
	 */
	public int getCols() {
		return elements[0].length;
	}

	/**
	 * Der Wert der Matrix an der Stelle (row,col)
	 * 
	 * @param row
	 *            Zeile
	 * @param col
	 *            Spalte
	 * @return Wert in der Matrix
	 */
	public double getValue(int row, int col) {
		return elements[row][col];
	}

	/**
	 * Setzt den Wert der Matrix an der Stelle (row,col) auf val
	 * 
	 * @param row
	 *            Zeile
	 * @param col
	 *            Spalte
	 * @param val
	 *            Wert
	 */
	public void setValue(int row, int col, double val) {
		elements[row][col] = val;
	}

	/**
	 * Multipliziert diese Matrix von links mit der übergebenen Matrix m und
	 * gibt das Ergebnis als neue Matrix zurück. (seiteneffektfrei)
	 * 
	 * this * m = ergebnismatrix
	 * 
	 * @param m
	 *            die Matrix mit der diese multipliziert werden soll.
	 * @return die Ergebnismatrix
	 */
	public Matrix multiply(Matrix m) {

		if (this.getCols() != m.getRows())
			throw new ArithmeticException(
					"Eine a x b-Matrix kann nur mit einer b x c-Matrix multipliziert werden!");

		double[][] result = new double[this.getRows()][m.getCols()];

		for (int curRow = 0; curRow < this.getRows(); curRow++) {
			for (int mCol = 0; mCol < m.getCols(); mCol++) {
				for (int curCol = 0; curCol < this.getCols(); curCol++) {
					result[curRow][mCol] += this.getValue(curRow, curCol)
							* m.getValue(curCol, mCol);
				}
			}
		}

		return new Matrix(result);

	}

	/**
	 * Multipliziert diese Matrix von links mit dem übergebenen Vector
	 * 
	 * @param p
	 *            der Vector
	 * @return das Ergebnis dieser Matrix * den Vector
	 */
	public Vector multiply(Vector p) {
		// Betrachte Punkt als 3x1 Matrix
		double[][] vector = new double[4][1];
		vector[0][0] = p.x;
		vector[1][0] = p.y;
		vector[2][0] = p.z;
		vector[3][0] = p.w; // homogene Koordinate

		Matrix r = this.multiply(new Matrix(vector));
		return new Vector(r.getValue(0, 0), r.getValue(1, 0), r.getValue(2, 0),
				r.getValue(3, 0));
	}

	/**
	 * Liefert diese Matrix invertiert, falls möglich. (seiteneffektfrei)
	 * 
	 * @return Matrix
	 */
	public Matrix invert() {

		int zeile, spalte; // Indizes fuer Adjunkte
		int z1, z2, z3; // Zeilen der akt. Unterdet
		int s1, s2, s3; // Spalten der akt. Unterdet

		boolean negflag = false;
		double det, eins_det;

		double[][] A = new double[4][4]; // Ausgangsmatrix
		double[][] A_I = new double[4][4]; // ZielMatrix

		for (zeile = 0; zeile < 4; zeile++) {
			for (spalte = 0; spalte < 4; spalte++) {
				A[zeile][spalte] = this.getValue(zeile, spalte);
			}
		}

		for (zeile = 0; zeile < 4; zeile++) {
			// Indizes fuer die Zeilen ermitteln, nach denen jetzt entwickelt
			// wird. &3 fungiert dabei als Modulo-Operator
			z1 = (zeile + 1) & 3;
			z2 = (zeile + 2) & 3;
			z3 = (zeile + 3) & 3;
			for (spalte = 0; spalte < 4; spalte++) {
				// Indizes fuer die Spalten ermitteln, nach denen jetzt
				// entwickelt
				// wird. &3 fungiert dabei als Modulo-Operator
				s1 = (spalte + 1) & 3;
				s2 = (spalte + 2) & 3;
				s3 = (spalte + 3) & 3;
				A_I[spalte][zeile] = A[z1][s1]
						* (A[z2][s2] * A[z3][s3] - A[z2][s3] * A[z3][s2])
						+ A[z1][s2]
						* (A[z2][s3] * A[z3][s1] - A[z2][s1] * A[z3][s3])
						+ A[z1][s3]
						* (A[z2][s1] * A[z3][s2] - A[z2][s2] * A[z3][s1]);
				if (negflag) {
					A_I[spalte][zeile] = -A_I[spalte][zeile];
				}
				negflag = !negflag;
			}
			negflag = !negflag;
		}
		det = A[0][0] * A_I[0][0];
		for (zeile = 1; zeile < 4; zeile++) {
			det += A[0][zeile] * A_I[zeile][0];
		}

		if (Math.abs(det) < EPSILON) {
			throw new RuntimeException("Matrix not invertible.");
		}

		Matrix dest = Matrix.createIdentity(4);
		eins_det = 1 / det; // verwandelt teure Divisionen
		// in Multiplikationen
		for (zeile = 0; zeile < 4; zeile++) {
			for (spalte = 0; spalte < 4; spalte++) {
				dest.setValue(zeile, spalte, A_I[zeile][spalte] * eins_det);
			}
		}
		return dest;
	}

	/**
	 * Sets a new Value
	 * 
	 * @param column
	 * @param vert
	 */
	public void setColumn(int column, Vector vert) {
		if ((getCols() == 4) && (column <= 3) && (column >= 0)) {
			this.setValue(0, column, vert.x);
			this.setValue(1, column, vert.y);
			this.setValue(2, column, vert.z);
			this.setValue(3, column, vert.w);
		}
	}

	/**
	 * Gibt die Matrix formatiert aus
	 */
	public void printFormatted() {
		for (int i = 0; i < elements.length; i++) {
			System.out.print("| ");
			for (int j = 0; j < elements[0].length; j++) {
				String c = "";
				if (getValue(i, j) >= 0) {
					c = " ";
				}
				System.out.printf(" %s%5.5f \t", c, getValue(i, j));
			}
			System.out.print(" |");
			System.out.println();
		}
	}
}
