package be.iccbxl.pid.reservationsspringboot.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArtistTest{
	


	@Test 
	void testArtist(){
		
		Artist artist = new Artist("Jean", "Dupont");

		assertEquals("Jean", artist.getFirstname());
		assertEquals("Dupont", artist.getLastname());

		artist.setFirstname("Pierre");

		assertEquals("Pierre", artist.getFirstname());
		assertEquals("Pierre Dupont", artist.toString());

		assertNull(artist.getId());

	}

}
