package pl.aticode.bean;

import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import lombok.Setter;
import pl.aticode.dao.OrderingRepository;
import pl.aticode.dao.OrderingRepositoryImpl;
import pl.aticode.dao.ProductRepository;
import pl.aticode.dao.ProductRepositoryImpl;
import pl.aticode.entity.ProductOrder;
import pl.aticode.entity.ProductOrderItem;

@ManagedBean
@RequestScoped
@Getter @Setter
public class AllOrdersBean {

	private ProductRepository productRepository;
	private OrderingRepository orderingRepository;
	private List<ProductOrder> allOrdersList;
	
	public AllOrdersBean() {
		productRepository = ProductRepositoryImpl.getInstance();
		orderingRepository = OrderingRepositoryImpl.getInstance();
		allOrdersList = orderingRepository.findAll();
		allOrdersList.sort(new Comparator<ProductOrder>() {
			@Override
			public int compare(ProductOrder o1, ProductOrder o2) {
				return o2.getOrderedDateTime().compareTo(o1.getOrderedDateTime());
			}
		});
	}

}
