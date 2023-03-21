package gymhum.de;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gymhum.de.model.Film;

@Controller
public class FilmController {
    
    ArrayList<Film> filme;

    // Bei Objekterzeugung erstellen wir eine Standardliste mit vier Personen.
    public FilmController(){
        setFilme(new ArrayList<>());
        getFilme().add(new Film(12, 120, "Star Wars Episode I"));
        getFilme().add(new Film(0, 89, "Frozen II"));
        getFilme().add(new Film(6, 124, "Manta Manta"));
    }

    
    @GetMapping("/filme")
    public String showFilme(@RequestParam(name="activePage", required = false, defaultValue = "filme") String activePage, Model model){
        model.addAttribute("activePage", "filme");
        model.addAttribute("filme", getFilme());
        return "index.html";
    }

    public void setFilme(ArrayList<Film> filme) {
        this.filme = filme;
    }
    public ArrayList<Film> getFilme() {
        return filme;
    }


}
