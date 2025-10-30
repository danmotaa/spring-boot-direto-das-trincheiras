package academy.devdojo.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Anime {

    private Long id;
    private String name;
    private static List<Anime> animes = new ArrayList<>();

    static {
        var ninjaKamui = new Anime(1L, "Ninja Kamui");
        var kaijuu = new Anime(2L, "Kaijuu-8gou");
        var kimetsuNoYaba = new Anime(3L, "Kimetsu No Yaba");

        animes.addAll(List.of(ninjaKamui, kaijuu, kimetsuNoYaba));
    }

    public static List<Anime> getAnimes() {
        return animes;
    }
}

