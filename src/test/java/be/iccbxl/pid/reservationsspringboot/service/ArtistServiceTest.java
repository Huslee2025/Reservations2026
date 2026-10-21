package be.iccbxl.pid.reservationsspringboot.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.iccbxl.pid.reservationsspringboot.model.Artist;
import be.iccbxl.pid.reservationsspringboot.repository.ArtistRepository;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

  @Mock
  private ArtistRepository artistRepository;

  @InjectMocks
  private ArtistService artistService;

  @Test
  void getAllArtists_shouldReturnAllArtists() {

    // Preparer les données
    Artist artist1 = new Artist("Daniel", "Marcelin");
    Artist artist2 = new Artist("Philippe", "Laurent");

    List<Artist> expectedArtists = List.of(artist1, artist2);

    // Definir comportement du faux repository
    when(artistRepository.findAll()).thenReturn(expectedArtists);

    // Appeler la methode du service
    List<Artist> actualArtists = artistService.getAllArtists();

    // Verifier le resultat
    assertEquals(2, actualArtists.size());

  }

  @Test
  void getArtist_ShouldReturnArtistById() {

    // Preparer un artiste
    Artist expectedArtist = new Artist("Daniel", "Marcelin");

    // Simuler le comportement du repository
    when(artistRepository.findById(1L)).thenReturn(expectedArtist);

    // Appeler la methode du service
    Artist actualArtist = artistService.getArtist(1L);

    // Verifier le resultat
    assertEquals(expectedArtist, actualArtist);

  }

  @Test
  void addArtist_ShouldSaveArtist() {

    // Preparer un artiste
    Artist artist = new Artist("Daniel", "Marcelin");

    // Appeler la methode du service
    artistService.addArtist(artist);

    // Verifier que Repository a appele save()
    verify(artistRepository).save(artist);

  }

  @Test
  void updateArtist_ShouldSaveArtist() {

    // Preparer un artiste
    Artist artist = new Artist("Daniel", "Marcelin");

    // Appeler la methode de service
    artistService.updateArtist(1L, artist);

    // Verifier l'appel au repository
    verify(artistRepository).save(artist);

  }

  @Test
  void deleteArtist_ShouldDeleteArtistById() {

    // Preparer identifiant
    long id = 1L;

    // Appeler methode du service
    artistService.deleteArtist(id);

    // Verifier l'appel au repository
    verify(artistRepository).deleteById(id);

  }

}
