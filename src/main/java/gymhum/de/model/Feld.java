package gymhum.de.model;

public class Feld {
    
    boolean istFrei;
    boolean zustand;

    public Feld(){
        setIstFrei(true);
    }

    public void setIstFrei(boolean istFrei) {
        this.istFrei = istFrei;
    }
    public void setZustand(boolean zustand) {
        this.zustand = zustand;
    }
    public boolean getZustand(){
        return this.zustand;
    }
    public boolean getIstFrei(){
        return this.istFrei;
    }

}
