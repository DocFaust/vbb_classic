package de.docfaust.vbb.data.entity;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestAbstractEntity {

	@Test
	public void testNotEquals()
	{
		Season season = new Season();
		assertThat(season, not(equalTo("irgendeinString")));
	}
	
	@Test
	public void testEquals()
	{
		Season season1 = new Season();
		season1.setId(1);
		Season season2 = new Season();
		season2.setId(1);
		assertThat(season1, equalTo(season2));
		
	}
}
