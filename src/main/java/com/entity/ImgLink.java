/**
 * 
 */
package com.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.constant.Constant;

/**
 * @author 14520278
 *
 */

@Entity
@Table(name="Img_Link")
public class ImgLink implements Serializable {
	private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="name", length = Constant.COLUMN_LENGTH_20_LIMIT)
    private String name;
    
    @Column(name="link", length = Constant.COLUMN_LENGTH_200_LIMIT)
    private String link;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "saleID", referencedColumnName = "id")
    private Sale sale;
    
    public ImgLink(){}
    
	public ImgLink(String name, String link, Sale sale) {
		super();
		this.name = name;
		this.link = link;
		this.sale = sale;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}
    
    
}
