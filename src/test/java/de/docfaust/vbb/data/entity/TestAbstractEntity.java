package de.docfaust.vbb.data.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestAbstractEntity {

	@Test
	public void testNotEquals()
	{
		Season season = new Season();
		assertThat(season).isNotEqualTo("irgendeinString");
	}
	
	@Test
	public void testEquals()
	{
		Season season1 = new Season();
		season1.setId(1);
		Season season2 = new Season();
		season2.setId(1);
		assertThat(season1).isEqualTo(season2);
	}
}
