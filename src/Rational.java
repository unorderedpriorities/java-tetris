public class Rational {
    private int top;
    private int bottom;

    public Rational(int top, int bottom){
        this.top = top;
        this.bottom = bottom;
        reduce();
    }

    public Rational(int top){
        this.top = top;
        this.bottom = 1;
        reduce();
    }

    private int gcd(int a, int b)
    {
        int n = Math.min(Math.abs(a), Math.abs(b));
        while(!(a % n == 0 && b % n == 0))
            n = n - 1;
        return n;
    }

    public void reduce(){
        if(this.bottom < 0){
            this.bottom *= -1;
            this.top *= -1;
        }
        int gcd = gcd(top, bottom);
        this.top /= gcd;
        this.bottom /= gcd;
    }

    public Rational add(Rational r){
        int top = this.top*r.bottom + this.bottom*r.bottom;
        int bottom = this.bottom * r.bottom;
        return new Rational(top, bottom);
    }

    public Rational subtract(Rational r){
        return add(new Rational(-1*r.top, r.bottom));
    }

    public Rational multiply(Rational r){
        return new Rational(top * r.top, bottom*r.bottom);
    }

    public Rational divide(Rational r){
        return multiply(new Rational(top*r.bottom, bottom*r.top));
    }

    public boolean equals(Rational r){
        return (top*r.bottom == bottom*r.top);
    }
}
