// Unit Test for OpenLibraryAPI, Database, and Models

package com.pustakadigital.test;

import com.pustakadigital.api.OpenLibraryAPI;
import com.pustakadigital.model.Buku;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTest {

    @Test
    public void testOpenLibraryAPIWithInvalidISBN() {
        OpenLibraryAPI api = new OpenLibraryAPI();
        JSONObject response = api.getBookInfoByISBN("INVALID_ISBN");
        assertNotNull("Response should not be null even for invalid ISBN", response);
        assertEquals("Response should be empty JSON for invalid ISBN", "{}", response.toString());
    }


    @Test
    public void testModelBukuInitialization() {
        Buku buku = new Buku("Test Judul", "Test Penulis", "Test Genre", "Test Cover", "1234567890");

        assertEquals("Judul should be 'Test Judul'", "Test Judul", buku.getJudul());
        assertEquals("Penulis should be 'Test Penulis'", "Test Penulis", buku.getPenulis());
        assertEquals("Genre should be 'Test Genre'", "Test Genre", buku.getGenre());
        assertEquals("Gambar Sampul should be 'Test Cover'", "Test Cover", buku.getGambarSampul());
        assertEquals("ISBN should be '1234567890'", "1234567890", buku.getIsbn());
    }

    @Test
    public void testModelBukuInvalidData() {
        Buku buku = new Buku("", "", "", "", "");

        assertTrue("Judul should be empty", buku.getJudul().isEmpty());
        assertTrue("Penulis should be empty", buku.getPenulis().isEmpty());
        assertTrue("Genre should be empty", buku.getGenre().isEmpty());
        assertTrue("Gambar Sampul should be empty", buku.getGambarSampul().isEmpty());
        assertTrue("ISBN should be empty", buku.getIsbn().isEmpty());
    }
}
