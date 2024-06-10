package com.example.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.entity.Category;

public class CategoryResponseDto extends CommonApiResponse{
	private List<Category> cat = new ArrayList<>();

	public List<Category> getCategories() {
	    return cat;
	}

	public void setcategories(List<Category> cat) {
	    this.cat = cat;
	}
}
