package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeHardCodedRepositoryTest {

    @InjectMocks
    private AnimeHardCodedRepository repository;

    @Mock
    private AnimeData animeData;

    private List<Anime> animesList;

    @BeforeEach
    void init() {

        var shingeki = Anime.builder().id(1L).name("Attack On Titan").build();
        var blackCover = Anime.builder().id(2L).name("Black Cover").build();
        var gachiakuta = Anime.builder().id(3L).name("Gachiakuta").build();

        animesList = new ArrayList<>(List.of(shingeki, blackCover, gachiakuta));

    }

    @Test
    @DisplayName("findAll returns a list with all animes")
    @Order(1)
    void findAll_ReturnAllAnimes_WhenSuccess() {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var animes = repository.findAll();
        Assertions.assertThat(animes).isNotNull().hasSameElementsAs(animesList);
    }

    @Test
    @DisplayName("findById returns an anime with given id")
    @Order(2)
    void findById_ReturnAnimeById_WhenSuccess() {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var expectedAnime = animesList.getFirst();
        var animes = repository.findById(expectedAnime.getId());
        Assertions.assertThat(animes).isPresent().contains(expectedAnime);
    }

    @Test
    @DisplayName("findByName returns empty list when name is null")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNull() {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var animes = repository.findByName(null);
        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findByName returns list with found object when name exists")
    @Order(4)
    void findByName_ReturnsFoundAnimesInList_WhenNameIsFound() {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var expectedAnime = animesList.getFirst();
        var animes = repository.findByName(expectedAnime.getName());
        Assertions.assertThat(animes).hasSize(1).contains(expectedAnime);
    }

    @Test
    @DisplayName("save creates an anime")
    @Order(5)
    void save_CreatesAnimes_WhenSuccessful() {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var animeToSave = Anime.builder().id(99L).name("Pokemon").build();
        var anime = repository.save(animeToSave);

        Assertions.assertThat(anime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();

        var animeSavedOptional = repository.findById(animeToSave.getId());
        Assertions.assertThat(animeSavedOptional).isPresent().contains(animeToSave);

    }

    @Test
    @DisplayName("delete removes an anime")
    @Order(6)
    void delete_RemovesAnimes_WhenSuccessful() {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var animeToDelete = animesList.getFirst();
        repository.delete(animeToDelete);

        var animes = repository.findAll();
        Assertions.assertThat(animes).doesNotContain(animeToDelete);
    }

    @Test
    @DisplayName("update update a anime")
    @Order(7)
    void update_UpdatesAnimes_WhenSuccessful() {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var animeToUpdate = this.animesList.getFirst();

        animeToUpdate.setName("Hellsing");

        repository.update(animeToUpdate);

        Assertions.assertThat(this.animesList).contains(animeToUpdate);

        var animeUpdatedOptional = repository.findById(animeToUpdate.getId());

        Assertions.assertThat(animeUpdatedOptional).isPresent();
        Assertions.assertThat(animeUpdatedOptional.get().getName()).isEqualTo(animeToUpdate.getName());

    }

}