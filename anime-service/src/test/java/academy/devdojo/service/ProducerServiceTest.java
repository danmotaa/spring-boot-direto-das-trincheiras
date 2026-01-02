package academy.devdojo.service;

import academy.devdojo.domain.Producer;
import academy.devdojo.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;

    @Mock
    private ProducerHardCodedRepository repository;

    private List<Producer> producersList;

    @BeforeEach
    void init() {

        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().id(2L).name("Wit Studios").createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().id(3L).name("Studios Ghibli").createdAt(LocalDateTime.now()).build();

        producersList = new ArrayList<>(List.of(ufotable, witStudio, studioGhibli));
    }

    @Test
    @DisplayName("findAll returns a list with all producers when name is null")
    @Order(1)
    void findAll_ReturnAllProducers_WhenArgumentIsNull() {

        BDDMockito.when(repository.findAll()).thenReturn(producersList);

        var producers = service.findAll(null);
        Assertions.assertThat(producers).isNotNull().hasSameElementsAs(producersList);
    }
    
    @Test
    @DisplayName("findAll returns list with found object when name exists")
    @Order(2)
    void findByName_ReturnsFoundProducersInList_WhenNameIsFound() {
        var producer = producersList.getFirst();
        var expectedProducersFound = singletonList(producer);

        BDDMockito.when(repository.findByName(producer.getName())).thenReturn(expectedProducersFound);

        var producersFound = service.findAll(producer.getName());
        Assertions.assertThat(producersFound).containsAll(expectedProducersFound);
    }

    @Test
    @DisplayName("findAll returns empty list when name is not found")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotFound() {

        var name = "not-found";

        BDDMockito.when(repository.findByName(name)).thenReturn(emptyList());

        var producers = service.findAll(name);
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById returns a producer with given id")
    @Order(4)
    void findById_ReturnProducerById_WhenSuccess() {

        var expectedProducer = producersList.getFirst();
        BDDMockito.when(repository.findById(expectedProducer.getId())).thenReturn(Optional.of(expectedProducer));

        var producers = service.findByIdOrThrowNotFound(expectedProducer.getId());

        Assertions.assertThat(producers).isEqualTo(expectedProducer);
    }

    @Test
    @DisplayName("findById throws ResponseStatusException when producer is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenProducerIsNotFound() {

        var expectedProducer = producersList.getFirst();
        BDDMockito.when(repository.findById(expectedProducer.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedProducer.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save creates a producer")
    @Order(6)
    void save_CreatesProducers_WhenSuccessful() {
        var producerToSave = Producer.builder().id(99L).name("Mappa").createdAt(LocalDateTime.now()).build();

        BDDMockito.when(repository.save(producerToSave)).thenReturn(producerToSave);

        var savedProducer = service.save(producerToSave);

        Assertions.assertThat(savedProducer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes a producer")
    @Order(7)
    void delete_RemovesProducers_WhenSuccessful() {

        var producerToDelete = producersList.getFirst();
        BDDMockito.when(repository.findById(producerToDelete.getId())).thenReturn(Optional.of(producerToDelete));

        BDDMockito.doNothing().when(repository).delete(producerToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(producerToDelete.getId()));
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when producer is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenProducerIsNotFound() {

        var producerToDelete = producersList.getFirst();
        BDDMockito.when(repository.findById(producerToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(producerToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("update update a producer")
    @Order(9)
    void update_UpdatesProducers_WhenSuccessful() {

        var producerToUpdate = producersList.getFirst();
        producerToUpdate.setName("Aniplex");
        BDDMockito.when(repository.findById(producerToUpdate.getId())).thenReturn(Optional.of(producerToUpdate));
        BDDMockito.doNothing().when(repository).update(producerToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(producerToUpdate));
    }

    @Test
    @DisplayName("update throws ResponseStatusException when producer is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var producerToUpdate = producersList.getFirst();

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(producerToUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }

}