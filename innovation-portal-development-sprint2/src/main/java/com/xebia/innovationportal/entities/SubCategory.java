package com.xebia.innovationportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SubCategories")
@Setter
@Getter
@NoArgsConstructor
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String subCategoryName;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    private boolean isActive = false;

    private SubCategory(Integer id, String subCategoryName, Category category, boolean isActive) {
        this.id = id;
        this.subCategoryName = subCategoryName;
        this.category = category;
        this.isActive = isActive;
    }

    public static SubCategory of(final Integer id, final String subCategoryName, final Category category) {
        return new SubCategory(id, subCategoryName, category, true);

    }

    public static SubCategory of(final String subCategoryName, final Category category) {
        return new SubCategory(null, subCategoryName, category, true);

    }

}
