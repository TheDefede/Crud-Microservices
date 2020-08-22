package ar.com.gl.shop.product.servicesimpl.test;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.ProductRepository;
import ar.com.gl.shop.product.repository.CategoryRepository;
import ar.com.gl.shop.product.repository.StockRepository;
import ar.com.gl.shop.product.service.impl.CategoryServiceImpl;
import ar.com.gl.shop.product.service.impl.ProductServiceImpl;
import ar.com.gl.shop.product.service.impl.StockServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
	
	@InjectMocks
	ProductServiceImpl productService;

	@Mock
	ProductRepository productRepository;

	@Mock
	CategoryRepository categoryRepository;

	@Mock
	StockRepository stockRepository;
		
	Product product1, product2;

	Optional<Product> oProduct1, oProduct2;
	
	@BeforeEach
	void setUp() {
		product1 = new Product("Test product", "Product for testing", 500.0, new Category());
		product1.setStock(new Stock(30, "SJ"));
		product2 = new Product("Test product2", "Second product for testing", 500.0, new Category());
		product2.setStock(new Stock(50, "MDZ"));
		oProduct1 = Optional.of(product1);
		oProduct2 = Optional.of(product2);
		
	}

	@Test
	@DisplayName("test service get By id with id=null")
	void tesCase_1(){

		assertNull(productService.getById(null, true));
		
	}

	@Test
	@DisplayName("test service get By id with, id != null, oProduct.isPresent()=true, searchEnabled = true, product.getEnabled() = true")
	void tesCase_2(){

		product1 = oProduct1.get();

		product1.setId(1l);		

		product1.setEnabled(true);

		when(productRepository.findById(product1.getId())).thenReturn(oProduct1);

		assertEquals(product1, productService.getById(product1.getId(), true));
	}

	@Test
	@DisplayName("test service get By id with, id != null, oProduct.isPresent()=true, searchEnabled = true, product.getEnabled() = false")
	void tesCase_3(){

		product1 = oProduct1.get();

		product1.setId(1l);		

		product1.setEnabled(false);

		when(productRepository.findById(product1.getId())).thenReturn(oProduct1);

		assertNull(productService.getById(product1.getId(), true));
	}

	@Test
	@DisplayName("test service get By id with, id != null, oProduct.isPresent()=true, searchEnabled = false")
	void tesCase_4(){

		product1 = oProduct1.get();

		product1.setId(1l);		

		product1.setEnabled(true);

		when(productRepository.findById(product1.getId())).thenReturn(oProduct1);

		assertEquals(product1, productService.getById(product1.getId(), false));
	}

	@Test
	@DisplayName("test service get By id with, id != null, oProduct.isPresent()=false")
	void tesCase_5(){

		oProduct1.ofNullable(null);

		when(productRepository.findById(1l)).thenReturn(oProduct1);

		assertNull(productService.getById(1l, true));
	}

	@Test
	@DisplayName("test find all")
	void testCase_99() {	

		Product[] theProducts = {product1, product2};

		when(productRepository.findAll()).thenReturn(Arrays.asList(theProducts));
		
		assertArrayEquals(theProducts, productService.findAll().toArray());		
	}

	

	
	/*@Test
	@DisplayName("test DeleteById")
	void testCase_3() throws ItemNotFound
	{
		Product productToDelete = productService.getById(1L, true);
		productService.softDelete(productToDelete.getId());
		
		when(productRepositoryImpl.getById(1L)).thenReturn(null);
		assertNull(productService.getById(1L, true));
		
		when(productRepositoryImpl.getById(1L)).thenReturn(product1);
		assertNotNull(productService.getById(1L, false));
	}

	@Test
	@DisplayName("test FindAllDisabled")
	void testCase_3() throws ItemNotFound {		
		productService.getById(1l, true).setEnabled(false);
		productService.getById(2l, true).setEnabled(false);
		
		product1.setEnabled(false);
		product2.setEnabled(false);
		
		Product[] theProducts = {product1,product2};	
		
		when(productRepositoryImpl.findAll()).thenReturn(Arrays.asList(theProducts));
		
		Boolean sameSize = productService.findAllDisabled().size() == 2;
		assertTrue(sameSize);
	}
	

	
	@Test
	@DisplayName("test UpdateById")
	void testCase_5()
	{
		Product updateProduct = productService.getById(1L, true);
		updateProduct.setName("updated product");
		productService.update(updateProduct);
		assertEquals("updated product",updateProduct.getName());
	}
	
	
	@Test
	@DisplayName("test ForceDelete")
	void testCase_6() throws ItemNotFound
	{
		Product product = productService.getById(2L, true);
		productService.delete(product.getId());
		
		when(productRepositoryImpl.getById(2L)).thenReturn(null);
		assertNull(productService.getById(2L, true));
		assertNull(productService.getById(2L, false));
	}*/
	
}
