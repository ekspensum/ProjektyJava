package pl.aticode.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;

import org.jboss.classfilewriter.util.Boxing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;
import pl.aticode.dao.OrderingRepository;
import pl.aticode.dao.OrderingRepositoryImpl;
import pl.aticode.dao.ProductRepository;
import pl.aticode.dao.ProductRepositoryImpl;
import pl.aticode.entity.Product;
import pl.aticode.entity.ProductOrder;
import pl.aticode.entity.ProductOrderItem;
import pl.aticode.entity.User;


@ManagedBean
@SessionScoped
@Getter @Setter
public class BasketBean {

	private final static Logger logger = LoggerFactory.getLogger(BasketBean.class);
	
	private ProductOrder productOrder;
	private List<ProductOrderItem> productOrderItemList;
	private ProductRepository productRepository;
	private OrderingRepository orderingRepository;
	private BigDecimal totalPrice;

	public BasketBean() {
		setNewBasket();
		productRepository = ProductRepositoryImpl.getInstance();
		orderingRepository = OrderingRepositoryImpl.getInstance();
	}
	
	public String addProduct(Long productId) {
		boolean isProductInBasket = false;
		final Product product = productRepository.findById(productId);
		for (ProductOrderItem productOrderItem : productOrderItemList) {
			if(productOrderItem.getProduct().getId() == product.getId()) {
				int productQuantity = productOrderItem.getProductQuantity();
				productQuantity++;
				productOrderItem.setProductQuantity(productQuantity);
				isProductInBasket = true;
				break;
			}
		}
		if(!isProductInBasket) {
			ProductOrderItem productOrderItem = new ProductOrderItem();
			productOrderItem.setProduct(product);
			productOrderItem.setProductQuantity(1);
			productOrderItem.setProductOrder(productOrder);			
			productOrderItemList.add(productOrderItem);
		}
		productOrder.setProductOrderItemList(productOrderItemList);
		calculateTotalPrice();
		return "";
	}
	
	public String removeProduct(Long productId) {
		Iterator<ProductOrderItem> iterator = productOrderItemList.iterator();
		while(iterator.hasNext()) {
			ProductOrderItem productOrderItem = iterator.next();
			if(productOrderItem.getProduct().getId() == productId) {
				productOrderItemList.remove(productOrderItem);
				break;
			}
		}
		productOrder.setProductOrderItemList(productOrderItemList);
		return "";
	}
	
	public String saveOrder() {
		productOrder.setOrderedDateTime(LocalDateTime.now());
		try {
			orderingRepository.saveOrUpdate(productOrder);
			setNewBasket();
			return "allOrders";
		} catch (Exception e) {
			logger.error("ERROR save product order ", e);		
			FacesContext.getCurrentInstance().addMessage("messages", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL, 
							"Błąd działania programu!", "Nie udało się zapisać zmówienia do bazy danych. Proszę o kontakt z administratorem lub sprzedawcą."));
			return "";
		}
	}
	
	private void setNewBasket() {
		productOrder = new ProductOrder();
		productOrderItemList = new ArrayList<>();
		productOrder.setUser(new User());
	}
	
	public void calculateTotalPrice() {
		BigDecimal itemCost = BigDecimal.ZERO;
		totalPrice = new BigDecimal(0);
		for (ProductOrderItem productOrderItem : productOrderItemList) {
			itemCost = productOrderItem.getProduct().getPrice().multiply(new BigDecimal(productOrderItem.getProductQuantity()));
			totalPrice = totalPrice.add(itemCost);
		}
	}
}
