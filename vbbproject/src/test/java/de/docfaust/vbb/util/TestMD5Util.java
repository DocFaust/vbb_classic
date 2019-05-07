package de.docfaust.vbb.util;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class TestMD5Util {

	@Test
	public void testCreateHash() {
		assertThat(MD5Util.createHash("12345678"), equalTo("25d55ad283aa400af464c76d713c07ad".getBytes()));
	}

	@Test
	public void testMd5() {
		assertThat(MD5Util.md5("12345678"), equalTo("25d55ad283aa400af464c76d713c07ad"));
	}

	@Test
	public void testMd5Null() {
		assertThat(MD5Util.md5(null), nullValue());
	}
	@Test
	public void testCreateHashNull() {
		assertThat(MD5Util.createHash(null), nullValue());
	}
}
