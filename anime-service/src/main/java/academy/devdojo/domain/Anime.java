package academy.devdojo.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Anime {

    private Long id;
    private String name;

    public static List<Anime> getAnimes(){
        var ninjaKamui = new Anime(1L, "Ninja Kamui");
        var kaijuu = new Anime(2L, "Kaijuu-8gou");
        var kimetsuNoYaba = new Anime(3L, "Kimetsu No Yaba");

        return List.of(ninjaKamui, kaijuu, kimetsuNoYaba);
    }


}
