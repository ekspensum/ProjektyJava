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
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

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
	
    @Size(min = 0, max = 2048)
    @Type(type = "text")
	private String description;
    
	private LocalDateTime orderedDateTime;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "productOrder", orphanRemoval = true)
	private List<ProductOrderItem> productOrderItemList;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User user;
}
