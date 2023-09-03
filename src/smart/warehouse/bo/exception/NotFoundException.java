package smart.warehouse.bo.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException() {
    }

    public NotFoundException(String string) {
        super(string);
    }

    public NotFoundException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public NotFoundException(Throwable thrwbl) {
        super(thrwbl);
    }

    public NotFoundException(String string, Throwable thrwbl, boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }
    
    
}
