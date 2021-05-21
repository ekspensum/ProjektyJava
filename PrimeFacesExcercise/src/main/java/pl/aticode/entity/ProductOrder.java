package pl.aticode.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_order")
@Getter @Setter
public class ProductOrder implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	private LocalDateTime orderedDateTime;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "productOrder")
	private List<ProductOrderItem> productOrderItem;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User user;
}
