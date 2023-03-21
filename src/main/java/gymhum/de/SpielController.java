package gymhum.de;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gymhum.de.model.Feld;

@Controller
public class SpielController {
    
    Feld[][] felder;
    boolean activePlayer;

    public SpielController(){
        setFelder(new Feld[6][7]);
        initFeld();
        //loadingDemo();
        //startDemoDataTest();
        setActivePlayer(true);
    }

    private void resetGameFunction(){
        resetFeld();
        setActivePlayer(true);
    }

    private void toggleActivePlayer(){
        if(getActivePlayer()){
            setActivePlayer(false);
        }
        else{
            setActivePlayer(true);
        }
    }

    public void setActivePlayer(boolean activePlayer) {
        this.activePlayer = activePlayer;
    }

    public boolean getActivePlayer(){
        return this.activePlayer;
    }

   

    

    @GetMapping("/spiel")
    public String showSpiel(@RequestParam(name="activePage", required = false, defaultValue = "spiel") String activePage, Model model){
        boolean winTrue = pruefe(true);
        boolean winFalse = pruefe(false);
        
        model.addAttribute("activePage", "spiel");

        model.addAttribute("activePlayer", getActivePlayer());
        model.addAttribute("winTrue", winTrue);
        model.addAttribute("winFalse", winFalse);
        
        // Felder an das Spielfeld übertragen
        model.addAttribute("felder", getFelder());
        return "index.html";
    }

    @GetMapping("/resetgame")
    public String resetGame(@RequestParam(name="activePage", required = false, defaultValue = "spiel") String activePage, Model model){
        resetGameFunction();
        return "redirect:/spiel";
    }

    @GetMapping("/playerinteraction")
    public String playerInteraction(@RequestParam(name="activePage", required = false, defaultValue = "spiel") String activePage, @RequestParam(name="column", required = true, defaultValue = "null") int column, Model model){
        model.addAttribute("activePage", "spiel");
        // Felder an das Spielfeld übertragen
        for(int i = 5; i >= 0; i--){
            if(getFelder()[i][column].getIstFrei()){
                getFelder()[i][column].setZustand(getActivePlayer());
                getFelder()[i][column].setIstFrei(false);
                break;
            }
        }
        toggleActivePlayer();
        return "redirect:/spiel";
    }

    

    private void initFeld(){
        for(int i = 0; i < 6; i++){
            for(int k = 0; k < 7; k++){
                getFelder()[i][k] = new Feld();
            }
        }
        
    }

    public void setDemoData(int f1x, int f1y, int f2x, int f2y, int f3x, int f3y, int f4x, int f4y, boolean value){
        getFelder()[f1x][f1y].setIstFrei(false);
        getFelder()[f1x][f1y].setZustand(value);

        getFelder()[f2x][f2y].setIstFrei(false);
        getFelder()[f2x][f2y].setZustand(value);

        getFelder()[f3x][f3y].setIstFrei(false);
        getFelder()[f3x][f3y].setZustand(value);

        getFelder()[f4x][f4y].setIstFrei(false);
        getFelder()[f4x][f4y].setZustand(value);
        
    }

    public void resetFeld(){
        setFelder(new Feld[6][7]);
        initFeld();
    }

    public boolean pruefe(boolean aufX){
        boolean pruefXoO = aufX;
        boolean horizontal = false;
        boolean senkrecht = false;
        boolean sLR = false;
        boolean sRL = false;
        boolean windetected= false;
        // horizontale Prüfung
        // Obere Reihe inkl. Verschiebung
        for(int i = 0; i < 6; i++){
            for(int k = 0; k < 4; k++){
                if(!getFelder()[i][k].getIstFrei() 
                && !getFelder()[i][k+1].getIstFrei() 
                && !getFelder()[i][k+2].getIstFrei() 
                && !getFelder()[i][k+3].getIstFrei()){
                    if(getFelder()[i][k].getZustand() == pruefXoO 
                        && getFelder()[i][k+1].getZustand() == pruefXoO 
                        && getFelder()[i][k+2].getZustand() == pruefXoO 
                        && getFelder()[i][k+3].getZustand() == pruefXoO){
                        horizontal = true;
                    }
                }
            }
        }
        // Senkrecht
        for(int i = 0; i < 3; i++){
            for(int k = 0; k < 7; k++){
                if(!getFelder()[i][k].getIstFrei() 
                && !getFelder()[i+1][k].getIstFrei() 
                && !getFelder()[i+2][k].getIstFrei() 
                && !getFelder()[i+3][k].getIstFrei()){
                    if(getFelder()[i][k].getZustand() == pruefXoO 
                    && getFelder()[i+1][k].getZustand() == pruefXoO 
                    && getFelder()[i+2][k].getZustand() == pruefXoO 
                    && getFelder()[i+3][k].getZustand() == pruefXoO){
                        senkrecht = true;
                    }
                }
            }
        }

        // Schräg oben links nach rechts unten
        for(int i = 0; i < 3; i++){
            for(int k = 0; k < 7; k++){
                if(!getFelder()[i][k].getIstFrei() 
                && !getFelder()[i+1][k+1].getIstFrei() 
                && !getFelder()[i+2][k+2].getIstFrei() 
                && !getFelder()[i+3][k+3].getIstFrei()){
                    if(getFelder()[i][k].getZustand() == pruefXoO 
                    && getFelder()[i+1][k+1].getZustand() == pruefXoO 
                    && getFelder()[i+2][k+2].getZustand() == pruefXoO 
                    && getFelder()[i+3][k+3].getZustand() == pruefXoO){
                        sRL = true;
                    }
                }
            }
        }

        // Schräg oben rechts nach links unten
        for(int i = 0; i < 4; i++){
            for(int k = 5; k > 2; k--){
                if(!getFelder()[i][k].getIstFrei() 
                && !getFelder()[i+1][k-1].getIstFrei() 
                && !getFelder()[i+2][k-2].getIstFrei() 
                && !getFelder()[i+3][k-3].getIstFrei()){
                    if(getFelder()[i][k].getZustand() == pruefXoO 
                    && getFelder()[i+1][k-1].getZustand() == pruefXoO 
                    && getFelder()[i+2][k-2].getZustand() == pruefXoO 
                    && getFelder()[i+3][k-3].getZustand() == pruefXoO){
                        sLR = true;
                    }
                }
            }
        }
        
        if(horizontal || senkrecht || sRL || sLR){
            windetected = true;
        }
        return windetected;
    }

    public void setFelder(Feld[][] felder) {
        this.felder = felder;
    }

    public Feld[][] getFelder() {
        return felder;
    }

}
