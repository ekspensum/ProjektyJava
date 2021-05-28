package pl.aticode.bean;

import java.util.List;

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

@ManagedBean
@RequestScoped
@Getter
@Setter
public class AllOrdersBean {

	private ProductRepository productRepository;
	private OrderingRepository orderingRepository;
	private List<ProductOrder> allOrdersList;

	public AllOrdersBean() {
		productRepository = ProductRepositoryImpl.getInstance();
		orderingRepository = OrderingRepositoryImpl.getInstance();
		allOrdersList = orderingRepository.findAll();
		allOrdersList.sort((a, b) -> b.getOrderedDateTime().compareTo(a.getOrderedDateTime()));
	}

}
