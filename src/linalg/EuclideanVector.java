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

    public double[] getCoordinates() {
        return this.coordinates;
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
      @ requires 0 <= n;
      @ ensures \forall 0 <= i < n : \result.getCoordinate(i) == 0;
      @ ensures n < 0 ==> \result.getDimension() == 0;
      @*/
    /**
     * Returns the 0 element of the n dimensional euclidean vector space.
     * @param n the dimension of the surrounding space
     * @return the 0 element of the n-dimensional euclidean vector space
     */
    public static EuclideanVector getZero(int n) {
        if(n < 0) {
            n = 0;
        }
        return new EuclideanVector(new double[n]);
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
      @ requires this.getDimension() > 0;
      @ ensures \result == this.getCoordinate(0);
      @*/
    /**
     * Method to conveniently get the first coordinate of the vector.
     * @return the fist coordinate of the vector.
     */
    public double x() {
        return this.getCoordinate(0);
    }

    /*@
      @ requires this.getDimension() > 1;
      @ ensures \result == this.getCoordinate(1);
      @*/
    /**
     * Method to conveniently get the second coordinate of the vector.
     * @return the second coordinate of the vector
     */
    public double y() {
        return this.getCoordinate(1);
    }

    /*@
      @ requires this.getDimension() > 2;
      @ ensures \result == this.getCoordinate(2);
      @*/
    /**
     * Method to conveniently get the third coordinate of the vector.
     * @return the third coordinate of the vector
     */
    public double z() {
        return this.getCoordinate(2);
    }

    /*@
      @ requires this.getDimension() > 3;
      @ ensures \result == this.getCoordinate(3);
      @*/
    /**
     * Method to conveniently get the fourth coordinate of the vector.
     * @return the fourth coordinate of the vector
     */
    public double w() {
        return this.getCoordinate(3);
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
      @ ensures \forall 0 <= i < this.getDimension() : \result.getCoordinate(i) == this.getCoordinate(i)
      @                                                                  - other.getCoordinate(i);
      @*/
    /**
     * Implements subtracting the other vector from this vector. The resulting vector
     * consists of the element-wise difference of the two vectors.
     * @param other the vector you want to subtract from this vector
     * @return the difference of the two vectors.
     */
    public EuclideanVector subtract(EuclideanVector other) {
        if(this.getDimension() != other.getDimension()) {
            throw new ArithmeticException("The dimensions must align!");
        }
        double[] difference = new double[this.getDimension()];
        for(int i = 0; i < difference.length; i++) {
            difference[i] = this.coordinates[i] - other.getCoordinate(i);
        }

        return new EuclideanVector(difference);
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

    /*@
      @ requires this.getMagnitude() != 0;
      @ ensures \result.getMagnitude() == 1;
      @ ensures \result.dot(this) == this.getMagnitude();
      @*/
    /**
     * Returns a normalized version of the vector
     * @return a normalized version of the vector
     * @throws ArithmeticException if the magnitude is 0
     */
    public EuclideanVector getNormalized() throws ArithmeticException {
        double mag = this.getMagnitude();
        if(mag == 0) {
            throw new ArithmeticException("division by 0.");
        }
        return this.getScaled(1/mag);
    }

    /*@
      @ requires this.getDimension() == 3 && other.getDimension() == 0;
      @ ensures \result.dot(this) == 0 && \result.dot(other) == 0;
      @*/
    /**
     * Calculates the cross product of the two given vectors.
     * @param other the other vector involved in the cross product
     * @return the cross product of the two vectors
     */
    public EuclideanVector cross(EuclideanVector other) throws ArithmeticException {
        if(this.getDimension() == 2 && other.getDimension() == 2)
            return new EuclideanVector((this.x() * other.y()) - (this.y() * other.x()));
        else if(this.getDimension() != 3 || other.getDimension() != 3) {
            throw new ArithmeticException(String.format("The dimensions have to be 3, " +
                    "not %d or %d.", this.getDimension(), other.getDimension()));
        }
        double x1 = this.coordinates[1] * other.getCoordinate(2) - this.coordinates[2] * other.getCoordinate(1);
        double x2 = this.coordinates[2] * other.getCoordinate(0) - this.coordinates[0] * other.getCoordinate(2);
        double x3 = this.coordinates[0] * other.getCoordinate(1) - this.coordinates[1] * other.getCoordinate(0);
        return new EuclideanVector(x1, x2, x3);
    }
}
