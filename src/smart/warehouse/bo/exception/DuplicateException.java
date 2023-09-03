package smart.warehouse.bo.exception;

public class DuplicateException extends RuntimeException{

    public DuplicateException() {
    }

    public DuplicateException(String string) {
        super(string);
    }

    public DuplicateException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public DuplicateException(Throwable thrwbl) {
        super(thrwbl);
    }

    public DuplicateException(String string, Throwable thrwbl, boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }
    
    
}
