package academy.devdojo.domain;


import lombok.Getter;

import java.util.List;

@Getter
public class Anime {

    private Long id;
    private String name;

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Anime> getAnimes(){
        var ninjaKamui = new Anime(1L, "Ninja Kamui");
        var kaijuu = new Anime(2L, "Kaijuu-8gou");
        var kimetsuNoYaba = new Anime(3L, "Kimetsu No Yaba");

        return List.of(ninjaKamui, kaijuu, kimetsuNoYaba);
    }


}
