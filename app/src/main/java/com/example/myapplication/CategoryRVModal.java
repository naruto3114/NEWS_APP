package com.example.myapplication;

public class CategoryRVModal {

    private String  Category;
    private String  CategoryImageUrl;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCategoryImageUrl() {
        return CategoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        CategoryImageUrl = categoryImageUrl;
    }

    public CategoryRVModal(String category, String categoryImageUrl) {
        Category = category;
        CategoryImageUrl = categoryImageUrl;
    }
}
