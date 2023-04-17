package utilities;

import game1.Constants;

// mutable 2D vectors
public final class Vector2D {
    public double x, y;

    // constructor for zero vector ./
    public Vector2D() {
        x = 0; y = 0;
    }

    // constructor for vector with given coordinates ./
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // constructor that copies the argument vector ./
    public Vector2D(Vector2D v) {
        x = v.x;
        y = v.y;
    }

    // set coordinates ./
    public Vector2D set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    // set coordinates based on argument vector ./
    public Vector2D set(Vector2D v) {
        x = v.x;
        y = v.y;
        return this;
    }

    // compare for equality (note Object type argument) ./
    public boolean equals(Object o) {
        if (o instanceof Vector2D){
            Vector2D other = new Vector2D((Vector2D) o);
            return x - other.x < 0.001 && y - other.y < 0.001;
        } else {
            return false;
        }
    }

    // String for displaying vector as text ./
    public String toString() {
        return "x= " + x + " y= " + y;
    }

    // magnitude (= "length") of this vector ./
    public double mag() {
        return Math.sqrt(x*x + y*y);
    }

    // angle between vector and horizontal axis in radians in range [-PI,PI]
// can be calculated using Math.atan2 ./
    public double angle() {
        return Math.atan2(y, x);
    }

    // angle between this vector and another vector in range [-PI,PI] ./
    public double angle(Vector2D other) {
        return Math.atan2(this.x * other.y - this.y * other.x,
                this.x * other.x + this.y * other.y);
    }

    // add argument vector ./
    public Vector2D add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    // add values to coordinates ./
    public Vector2D add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    // weighted add - surprisingly useful ./
    public Vector2D addScaled(Vector2D v, double fac) {
        this.x += v.x * fac;
        this.y += v.y * fac;
        return this;
    }

    // subtract argument vector ./
    public Vector2D subtract(Vector2D v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    // subtract values from coordinates ./
    public Vector2D subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    // multiply with factor ./
    public Vector2D mult(double fac) {
        x *= fac;
        y *= fac;
        return this;
    }

    // rotate by angle given in radians x
    public Vector2D rotate(double angle) {
        double PrevX = x;
        double PrevY = y;
        x = PrevX * Math.cos(angle) - PrevY * Math.sin(angle);
        y = PrevX * Math.sin(angle) + PrevY * Math.cos(angle);
        return this;
    }

    // "dot product" ("scalar product") with argument vector ./
    public double dot(Vector2D v) {
        return (this.x * v.x) + (this.y * v.y);
    }

    // distance to argument vector ./
    public double dist(Vector2D v) {
        return Math.sqrt(Math.pow(this.x - v.x, 2) + Math.pow(this.y - v.y, 2));
    }

    // normalise vector so that magnitude becomes 1 ./
    public Vector2D normalise() {
        double PrevX = x;
        double PrevY = y;
        x = PrevX / Math.sqrt(PrevX*PrevX + PrevY*PrevY);
        y = PrevY / Math.sqrt(PrevX*PrevX + PrevY*PrevY);
        return this;
    }

    // wrap-around operation, assumes w> 0 and h>0
// remember to manage negative values of the coordinates ./
    public Vector2D wrap(double w, double h) {
        x = (x+w) % w;
        y = (y+h) % h;
        if (x > Constants.FRAME_WIDTH) {
            x -= Constants.FRAME_WIDTH;
        }
        if (y > Constants.FRAME_HEIGHT) {
            y -= Constants.FRAME_HEIGHT;
        }
        if (x < 0) {
            x -= Constants.FRAME_WIDTH;
        }
        if (y < 0) {
            y -= Constants.FRAME_HEIGHT;
        }
        return this;
    }

    // construct vector with given polar coordinates x
    public static Vector2D polar(double angle, double mag) {
        return new Vector2D(mag*Math.cos(angle), mag*Math.sin(angle));
    }

}
