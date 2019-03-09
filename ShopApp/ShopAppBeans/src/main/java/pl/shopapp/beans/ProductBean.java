package pl.shopapp.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import pl.shopapp.entites.Category;
import pl.shopapp.entites.Operator;
import pl.shopapp.entites.Product;
import pl.shopapp.entites.ProductCategory;
import pl.shopapp.entites.User;

/**
 * Session Bean implementation class ProductOperations
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class ProductBean implements ProductBeanRemote, ProductBeanLocal {

	@PersistenceContext(unitName = "ShopAppEntites")
	private EntityManager em;

	@Resource
	private UserTransaction ut;
	
	private List<Category> listCat;
	private List<Product> listProductByCat;
	private List<Product> listProductByName;
	private List<Product> listProductByQuantity;

	/**
	 * Default constructor.
	 */
	public ProductBean() {
		// TODO Auto-generated constructor stub
	}
//	for tests
	public ProductBean(EntityManager em, UserTransaction ut) {
		super();
		this.em = em;
		this.ut = ut;
	}

	@Override
	public boolean addProduct(String productName, String productDescription, double productPrice, int productUnitsInStock, byte [] buffer, List<Integer> helperListCat, int idUser) {
		try {
			ut.begin();
			User u = em.find(User.class, idUser);
			Operator op = em.createNamedQuery("operatorQuery", Operator.class).setParameter("user", u).getSingleResult();
			Product p = new Product();
			p.setName(productName);
			p.setDescription(productDescription);
			p.setPrice(productPrice);
			p.setUnitsInStock(productUnitsInStock);
			p.setProductImage(buffer);
			p.setDateTime(LocalDateTime.now());
			p.setOp(op);
			em.persist(p);	
			for(int i = 0; i<helperListCat.size(); i++) {
				Category cat = em.find(Category.class, helperListCat.get(i));
				ProductCategory pc = new ProductCategory();
				pc.setCategory(cat);
				pc.setProduct(p);
				em.persist(pc);
			}
			ut.commit();
			return true;
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			try {
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateProduct(String productName, String productDescription, double productPrice, int productUnitsInStock, byte [] buffer, int productIdToEdit, int [] categoryToEdit, int sizeFileImage, int idUser, List<Integer> helperListCat) {
		// TODO Auto-generated method stub
		int rowProduct = 0;
		int rowCategory = 0;
		try {
			ut.begin();
			Product p = em.find(Product.class, productIdToEdit);
			User u = em.find(User.class, idUser);
			Operator op = em.createNamedQuery("operatorQuery", Operator.class).setParameter("user", u).getSingleResult();
			String updateProductWithoutImage = "UPDATE Product SET name = '"+productName+"', description = '"+productDescription+"', price = "+productPrice+", unitsInStock = "+productUnitsInStock+", dateTime = '"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"', op = :op WHERE id = "+productIdToEdit+"";
			String updateProductWithImage = "UPDATE Product SET name = '"+productName+"', description = '"+productDescription+"', price = "+productPrice+", unitsInStock = "+productUnitsInStock+", productImage = :productImage, dateTime = '"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"', op = :op WHERE id = "+productIdToEdit+"";

			if(sizeFileImage == 0)
				rowProduct = em.createQuery(updateProductWithoutImage).setParameter("op", op).executeUpdate();
			else 
				rowProduct = em.createQuery(updateProductWithImage).setParameter("productImage", buffer).setParameter("op", op).executeUpdate();
			
			for(int i = 0; i<helperListCat.size(); i++) {
				Category cat = em.find(Category.class, categoryToEdit[i]);
				ProductCategory pc = em.createNamedQuery("getProductCategoryQuery", ProductCategory.class).setParameter("category", cat).setParameter("product", p).getSingleResult();
				String updateProductCategory = "UPDATE ProductCategory SET category_id = "+helperListCat.get(i)+", product_id = "+p.getId()+" WHERE id = "+pc.getId()+"";
				rowCategory = em.createQuery(updateProductCategory).executeUpdate();
			}
			
			ut.commit();
			if(rowProduct == 1 && rowCategory == 1)
				return true;
			else
				ut.rollback();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Product> findProduct(String name) {
		// TODO Auto-generated method stub
		listProductByName = em.createNamedQuery("productsByName", Product.class).setParameter("name", "%"+name+"%").getResultList();
		return listProductByName;
	}

	@Override
	public List<Product> findProduct(int quantity) {
		// TODO Auto-generated method stub
		listProductByQuantity = em.createNamedQuery("productsByQuantity", Product.class).setParameter("quantity", quantity).getResultList();
		return listProductByQuantity;
	}

	@Override
	public boolean addCategory(String categoryName, byte[] buffer, int idUser) {
		try {
			ut.begin();
			User user = em.find(User.class, idUser);
			Operator op = em.createNamedQuery("operatorQuery", Operator.class).setParameter("user", user).getSingleResult();
			Category cat = new Category();
			cat.setName(categoryName);
			cat.setDateTime(LocalDateTime.now());
			cat.setCategoryImage(buffer);
			cat.setOp(op);
			em.persist(cat);
			ut.commit();
			return true;
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;			
		}
	}

	@Override
	public List<Category> listCategory() {
		listCat = (List<Category>) em.createNamedQuery("listCategory", Category.class).getResultList();
		return listCat;
	}

	@Override
	public List<Product> listProductByCategory(int idCategory) {
		listProductByCat = em.createNamedQuery("productsByCategory", Product.class).setParameter(1, idCategory).getResultList();
		return listProductByCat;
	}

	@Override
	public Product getProduct(int idProduct) {
		return em.find(Product.class, idProduct);
	}

	@Override
	public List<Category> getProductCategories(Product p) {
		// TODO Auto-generated method stub
		List<Category> catList = em.createNamedQuery("productCategoriesQuery", Category.class).setParameter("product", p).getResultList();
		return catList;
	}
	
	@Override
	public List<Product> listProductByIdRange(int idFrom, int idTo) {
		// TODO Auto-generated method stub
		return em.createNamedQuery("productsByIdRange", Product.class).setParameter("idFrom", idFrom).setParameter("idTo", idTo).getResultList();
	}

}
