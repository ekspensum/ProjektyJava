package pl.shopapp.beans;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

	@Override
	public boolean addProduct(Product p, List<Integer> helperListCat, int idUser) {
		try {
			ut.begin();
			User u = em.find(User.class, idUser);
			Operator op = em.createNamedQuery("operatorQuery", Operator.class).setParameter("user", u).getSingleResult();
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
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateProduct(Product p, int sizeFileImage) {
		// TODO Auto-generated method stub
		int row = 0;
		String updateQueryWithoutImage = "UPDATE Product SET name = '"+p.getName()+"', description = '"+p.getDescription()+"', price = "+p.getPrice()+", unitsInStock = "+p.getUnitsInStock()+", dateTime = '"+p.getDateTime()+"' WHERE id = "+p.getId()+"";
		String updateQueryWithImage = "UPDATE Product SET name = '"+p.getName()+"', description = '"+p.getDescription()+"', price = "+p.getPrice()+", unitsInStock = "+p.getUnitsInStock()+", productImage = :productImage, dateTime = '"+p.getDateTime()+"' WHERE id = "+p.getId()+"";
		try {
			ut.begin();
			if(sizeFileImage == 0)
				row = em.createQuery(updateQueryWithoutImage).executeUpdate();
			else {
				Query query = em.createQuery(updateQueryWithImage);	
				query.setParameter("productImage", p.getProductImage());
				row = query.executeUpdate();
			}
			ut.commit();
			if(row == 1)
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
	public void deleteProduct(Product p, int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addCategory(Category cat, int idUser) {
		try {
			ut.begin();
			User user = em.find(User.class, idUser);
			Operator op = em.createNamedQuery("operatorQuery", Operator.class).setParameter("user", user).getSingleResult();
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


}
