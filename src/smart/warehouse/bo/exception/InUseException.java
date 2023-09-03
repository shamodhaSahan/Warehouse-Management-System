package smart.warehouse.bo.exception;

public class InUseException extends RuntimeException{

    public InUseException() {
    }

    public InUseException(String string) {
        super(string);
    }

    public InUseException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public InUseException(Throwable thrwbl) {
        super(thrwbl);
    }

    public InUseException(String string, Throwable thrwbl, boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }
    
    
}
