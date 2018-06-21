/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "articleType", discriminatorType = DiscriminatorType.STRING, length = 12)
@DiscriminatorValue("BASE")
@Table(name = "article")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a"),
    @NamedQuery(name = "Article.findByPriority", query = "SELECT a FROM Article a WHERE a.priority = :priority"),
    @NamedQuery(name = "Article.findByRecordId", query = "SELECT a FROM Article a WHERE a.recordId = :recordId"),
    @NamedQuery(name = "Article.findByTitle", query = "SELECT a FROM Article a WHERE a.title = :title"),
    @NamedQuery(name = "Article.findByCartegory", query = "SELECT a FROM Article a WHERE a.cartegory = :cartegory"),
    @NamedQuery(name = "Article.findByPublishStatus", query = "SELECT a FROM Article a WHERE a.publishStatus = :publishStatus")})
public class Article implements Serializable, Comparable<Article> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "recordId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer recordId;
    @Column(name = "creationDateTime")
    private Timestamp creationDateTime;
    @Column(name = "publishDateTime")
    private Timestamp publishDateTime;
    @Size(max = 255)
    @Column(name = "priority")
    private String priority;
    @Size(max = 12)
    @Column(name = "articleType")
    private String articleType;
    @Size(max = 255)
    @Column(name = "title")
    private String title;

    @Size(max = 800)
    @Column(name = "caption")
    private String caption;

    @Size(max = 255)
    @Column(name = "cartegory")
    private String cartegory;
    @Size(max = 255)
    @Column(name = "publishStatus")
    private String publishStatus;

    public Article() {
    }

    public Article(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Timestamp getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Timestamp creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Timestamp getPublishDateTime() {
        return publishDateTime;
    }

    public void setPublishDateTime(Timestamp publishDateTime) {
        this.publishDateTime = publishDateTime;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCartegory() {
        return cartegory;
    }

    public void setCartegory(String cartegory) {
        this.cartegory = cartegory;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordId != null ? recordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.recordId == null && other.recordId != null) || (this.recordId != null && !this.recordId.equals(other.recordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "marketsource.entity.Article[ recordId=" + recordId + " ]";
    }

    @Override
    public int compareTo(Article other) {

        // return this.name.compareTo(other.name);
        return this.publishDateTime.compareTo(other.publishDateTime);
        
    }

}
