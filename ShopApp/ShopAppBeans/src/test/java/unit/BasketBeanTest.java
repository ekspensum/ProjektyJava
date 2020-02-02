package unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.shopapp.beans.BasketBean;
import pl.shopapp.beans.BasketData;

class BasketBeanTest {
	
	BasketBean bb;
	BasketData bd;
	List<BasketData> bdl;

	@BeforeEach
	void setUp() throws Exception {
		bb = new BasketBean();
		bd = new BasketData();
		bd.setPrice(33.44);
		bdl = bb.getBasketData();
		bdl.add(bd);
	}

	@AfterEach
	void tearDown() throws Exception {
		bb = null;
		bd = null;
		bdl.clear();
	}

	@Test
	void testGetBasketData() {
		BasketData bdExpected = new BasketData();
		bdExpected.setPrice(33.44);
		List<BasketData> bdlExpected = new ArrayList<>();
		bdlExpected.add(bdExpected);
		List<BasketData> bdlActual = bb.getBasketData();
		assertEquals(bdlExpected.get(0).getPrice(), bdlActual.get(0).getPrice());
		assertNotEquals(55.66, bdlActual.get(0).getPrice());
	}

	@Test
	void testAddBasketRow() {
		bb.addBasketRow(1, 10, "productName", 11.11, bdl);
		List<BasketData> bdlActual = bb.getBasketData();
		assertEquals(11.11, bdlActual.get(1).getPrice());
	}

}
