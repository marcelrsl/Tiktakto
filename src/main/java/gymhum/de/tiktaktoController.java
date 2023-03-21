package gymhum.de;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gymhum.de.model.Feld;

@Controller
public class tiktaktoController{


    Feld[][] felder ;
    boolean activePlayer;

    public tiktaktoController() {
        setFelder(new Feld[3][3]);
        initFeld();
        setActivePlayer(true);
    }


    public void setActivePlayer(boolean activePlayer) {
        this.activePlayer = activePlayer;
    }

    public boolean getActivePlayer(){
        return this.activePlayer;
    }
    
    private void resetSpielFunction(){
        resetFeld();
        setActivePlayer(true);
    }

    private void toggleActivePlayer(){
        if(getActivePlayer()){
            setActivePlayer(false);
        }   else{
            setActivePlayer(true);
        }
    }

    @GetMapping("/tiktakto")
    public String ShowTiktakto(@RequestParam(name= "activePage", required = false, defaultValue = "tiktakto") String activePage, Model model) {
        model.addAttribute("felder", getFelder());
        model.addAttribute("activePage", activePage);
        return "index.html";
    } 

    @GetMapping("/resetspiel")
    public String resetspiel(@RequestParam(name= "activePage", required = false, defaultValue = "tiktakto") String activePage, Model model){
        resetSpielFunction();
        return "redirect:/tiktakto";
    }

    @GetMapping("/tikplayerinteraction")
    public String playerInteraction(@RequestParam(name= "activePage", required = false, defaultValue = "tiktakto")String activePage, @RequestParam(name ="column", required = true, defaultValue = "null")int column, Model model)
    {
        model.addAttribute("activePage", "tiktakto");
        for(int b = 2; b >= 0; b--){
            if(getFelder()[b][column].getIstFrei()){
                getFelder()[b][column].setZustand(getActivePlayer());
                getFelder()[b][column].setIstFrei(false);
                break;
            }
        }
        toggleActivePlayer();
        return "redirekt:/tiktakto";
    }

    private void initFeld(){
        for(int b = 0; b < 3; b++){
            for(int h = 0; h < 3; h++){
                getFelder()[b][h] = new Feld();
            }
        }
    }

    public void setDemodaten(int f1x, int f1y, int f2x, int f2y, int f3x, int f3y, boolean value){
        getFelder()[f1x][f1y].setIstFrei(false);
        getFelder()[f1x][f1y].setZustand(value);
        
        getFelder()[f2x][f2y].setZustand(false);
        getFelder()[f2x][f2y].setZustand(value);

        getFelder()[f3x][f3y].setZustand(false);
        getFelder()[f3x][f3y].setZustand(value);
    }

    public void resetFeld() {
        setFelder(new Feld[3][3]);
    }

    public boolean pruefe(boolean aufX) 
    {
        boolean pruefXoO = aufX;
        boolean horizontal = false;
        boolean senkrecht = false;
        boolean sRL = false;
        boolean sLR = false;
        boolean windetected = false;
        //horizontale Prüfung
        for(int b = 0; b <= 2; b++){
            for(int h = 0; h <= 2; h++){
                if(!getFelder()[b][h].getIstFrei()
                && !getFelder()[b][h+1].getIstFrei()
                && !getFelder()[b][h+2].getIstFrei()){
                    if(getFelder()[b][h].getZustand() == pruefXoO
                    && getFelder()[b][h+1].getZustand() == pruefXoO
                    && getFelder()[b][h+2].getZustand() == pruefXoO
                    ){
                        horizontal = true;
                    }
                }

            }
        }
        //senkrecht
        for(int b = 0; b <= 2; b++){
            for(int h = 0; h <= 2; h++){
                if(!getFelder()[b][h].getIstFrei()
                && !getFelder()[b+1][h].getIstFrei()
                && !getFelder()[b+2][h].getIstFrei())
                {
                    if(getFelder()[b][h].getZustand() == pruefXoO
                    && getFelder()[b+1][h].getZustand() == pruefXoO
                    && getFelder()[b+2][h].getZustand() == pruefXoO) 
                    {
                        senkrecht = true;
                    }              
                }
            }
        }

        //schräg oben rechts nach unten links 
           for(int b = 0; b <= 2; b++){
            for(int h = 2; h <= 0; h--){
                if(getFelder()[b][h].getIstFrei()
                && getFelder()[b+1][h-1].getIstFrei()
                &&getFelder()[b+2][h-2].getIstFrei())
                {
                    if(getFelder()[b][h].getIstFrei() == pruefXoO
                    && getFelder()[b+1][h-1].getIstFrei() == pruefXoO
                    &&getFelder()[b+2][h-2].getIstFrei() == pruefXoO)
                    {
                        sRL = true;
                    }
                }
                
            }
        }
        //Schräg oben links nach unten rechts
        for(int b = 0; b <= 2; b++){
            for(int h = 2; h >= 0; h--){
                if(getFelder()[b][h].getIstFrei()
                && getFelder()[b+1][h+1].getIstFrei()
                &&getFelder()[b+2][h+2].getIstFrei())
                {
                    if(getFelder()[b][h].getIstFrei() == pruefXoO
                    && getFelder()[b+1][h+1].getIstFrei() == pruefXoO
                    && getFelder()[b+2][h+2].getIstFrei() == pruefXoO)
                    {
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
    
