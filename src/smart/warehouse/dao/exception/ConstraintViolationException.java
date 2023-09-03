package smart.warehouse.dao.exception;

public class ConstraintViolationException extends RuntimeException{

    public ConstraintViolationException() {
    }

    public ConstraintViolationException(String string) {
        super(string);
    }

    public ConstraintViolationException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public ConstraintViolationException(Throwable thrwbl) {
        super(thrwbl);
    }

    public ConstraintViolationException(String string, Throwable thrwbl, boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }
    
}
