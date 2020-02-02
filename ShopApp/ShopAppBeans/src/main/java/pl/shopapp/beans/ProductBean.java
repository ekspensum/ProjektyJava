package pl.shopapp.beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import pl.shopapp.entites.User;

/**
 * Session Bean implementation class ProductOperations
 * This class provide 
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
	}
//	for tests
	public ProductBean(EntityManager em, UserTransaction ut) {
		super();
		this.em = em;
		this.ut = ut;
	}

	@Override
	public boolean addProduct(String productName, String productDescription, double productPrice, int productUnitsInStock, byte [] buffer, List<Integer> helperListCat, int idUser) throws IllegalStateException, SecurityException, SystemException {
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
			List<Category> categories = new ArrayList<>();	
			for(int i = 0; i<helperListCat.size(); i++) {
				Category cat = em.find(Category.class, helperListCat.get(i));
				categories.add(cat);
			}
			p.setCategories(categories);
			em.persist(p);
			ut.commit();
			return true;
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			ut.rollback();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateProduct(String productName, String productDescription, double productPrice, int productUnitsInStock, byte [] buffer, int productIdToEdit, int sizeFileImage, int idUser, List<Integer> helperListCat) throws IllegalStateException, SecurityException, SystemException {
		try {
			ut.begin();
			
			Product p = em.find(Product.class, productIdToEdit);
			User u = em.find(User.class, idUser);
			Operator op = em.createNamedQuery("operatorQuery", Operator.class).setParameter("user", u).getSingleResult();
			p.setOp(op);
			p.setName(productName);
			p.setDescription(productDescription);
			p.setPrice(productPrice);
			p.setUnitsInStock(productUnitsInStock);
			p.setDateTime(LocalDateTime.now());
			
			if(sizeFileImage > 0) {
				p.setProductImage(buffer);				
			}
			
			List<Category> categories = null;
			for(int i = 0; i<helperListCat.size(); i++) {
				Category cat = em.find(Category.class, helperListCat.get(i));
				categories = p.getCategories();
				categories.set(i, cat);
			}
			p.setCategories(categories);
			em.persist(p);
			ut.commit();

			return true;
			
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			ut.rollback();
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Product> findProduct(String name) {
		listProductByName = em.createNamedQuery("productsByName", Product.class).setParameter("name", "%"+name+"%").getResultList();
		return listProductByName;
	}

	@Override
	public List<Product> findProduct(int quantity) {
		listProductByQuantity = em.createNamedQuery("productsByQuantity", Product.class).setParameter("quantity", quantity).getResultList();
		return listProductByQuantity;
	}

	@Override
	public boolean addCategory(String categoryName, byte[] buffer, int idUser) throws IllegalStateException, SecurityException, SystemException {
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
			ut.rollback();
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
		Category findCategory = em.find(Category.class, idCategory);
		listProductByCat = findCategory.getProduct();
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
	@Override
	public int findIdCategory(String categoryName) {
		// TODO Auto-generated method stub
		return (int) em.createNamedQuery("findIdCategory").setParameter("name", categoryName).getSingleResult();
	}
	

}
