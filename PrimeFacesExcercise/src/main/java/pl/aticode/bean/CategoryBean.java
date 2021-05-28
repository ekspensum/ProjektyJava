package pl.aticode.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;

import lombok.Getter;
import lombok.Setter;
import pl.aticode.dao.CategoryRepository;
import pl.aticode.dao.CategoryRepositoryImpl;
import pl.aticode.entity.Category;

@ManagedBean
@RequestScoped
@Getter @Setter
public class CategoryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Category> categoryList;

	public CategoryBean() {
		categoryList = new ArrayList<>();
		CategoryRepository categoryRepository = CategoryRepositoryImpl.getInstance();
		categoryList = categoryRepository.findAll();
	}
	
	
}
