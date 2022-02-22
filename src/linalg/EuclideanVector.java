package linalg;

/**
 * A class to model a vector in the n dimensional euclidean vector space.
 */
public class EuclideanVector {
    //@ private instance invariant this.getDimension() >= 0;
    private double[] coordinates;

    /*@
      @ ensures \forall 0 <= i < coordinates.length : this.coordinates[i] == coordinates[i];
      @*/
    /**
     * Constructor. We take n numbers (double) as the
     * coordinates for the vector.
     * @param coordinates the n coordinates for the
     */
    public EuclideanVector(double ... coordinates) {
        this.coordinates = new double[coordinates.length];
        System.arraycopy(coordinates, 0, this.coordinates, 0, coordinates.length);
    }

    /*@
      @ ensures \result == this.coordinates.length;
      @*/
    /**
     * Returns the dimension of the euclidean space the vector is an element of.
     * @return the dimension of the surrounding space
     */
    public int getDimension() {
        return this.coordinates.length;
    }

    /*@
      @ requires i >= 0 && i < this.coordinates.length;
      @ ensures \result == this.coordinates[i];
      @*/
    /**
     * Returns the i-th entry of the vector.
     * @param i the entry to retrieve
     * @return the i-th entry of the vector
     * @throws ArrayIndexOutOfBoundsException if i < 0 or i >= this.coordinates.length
     */
    public double getCoordinate(int i) throws ArrayIndexOutOfBoundsException {
        if(i < 0 || i >= this.coordinates.length) {
            throw new ArrayIndexOutOfBoundsException("The Vector does not have enough coordinates stored!");
        }

        return this.coordinates[i];
    }

    /*@
      @ requires this.getDimension() == other.getDimension();
      @ ensures \forall 0 <= i < this.getDimension() : \result.getCoordinate(i) == this.getCoordinate(i)
      @                                                                  + other.getCoordinate(i);
      @*/
    /**
     * Implements the adding of two vectors. The resulting vector just consists of
     * the element-wise sum of the two vectors.
     * @param other the vector you want to add to this vector
     * @return the sum of the two vectors
     * @throws ArithmeticException if the dimensions do not align
     */
    public EuclideanVector add(EuclideanVector other) throws ArithmeticException {
        if(this.getDimension() != other.getDimension()) {
            throw new ArithmeticException("The dimensions must align!");
        }
        double[] sum = new double[this.getDimension()];
        for(int i = 0; i < sum.length; i++) {
            sum[i] = this.coordinates[i] + other.getCoordinate(i);
        }

        return new EuclideanVector(sum);
    }

    /*@
      @ requires this.getDimension() == other.getDimension();
      @*/
    /**
     * Implements the standard euclidean dot product for two vectors.
     * @param other the other vector involved in the dot product
     * @return the standard dot product of the two vectors
     * @throws ArithmeticException if the dimensions of the vectors do not align
     */
    public float dot(EuclideanVector other) throws ArithmeticException {
        if(this.getDimension() != other.getDimension()) {
            throw new ArithmeticException("The dimensions must align!");
        }
        float product = 0;
        for(int i = 0; i < this.getDimension(); i++) {
            product += this.coordinates[i] * other.getCoordinate(i);
        }
        return product;
    }

    /*@
      @ ensures \forall 0 <= i < this.getDimension() : \result.getCoordinate(i) == alpha * this.getCoordinate(i);
      @*/
    /**
     * Returns the scaled a version of the vector by the factor alpha
     * @param alpha the factor you want to scale the vector with
     * @return a scaled version of the vector by the factor alpha
     */
    public EuclideanVector getScaled(double alpha) {
        double[] scaled = new double[this.getDimension()];
        for(int i = 0; i < scaled.length; i++) {
            scaled[i] = alpha * this.coordinates[i];
        }
        return new EuclideanVector(scaled);
    }

    /*@
      @ ensures \result == Math.sqrt(this.dot(this));
      @*/
    /**
     * Calculates the standard 2-norm for the vector
     * @return the 2-norm (or magnitude) of the vector
     */
    public double getMagnitude() {
        return Math.sqrt(this.dot(this));
    }
}
