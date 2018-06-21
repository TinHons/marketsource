/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.entity;

import java.io.Serializable;
import javax.persistence.Column;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author dell
 */
@Entity
@DiscriminatorValue("INTERNAL")
public class InternalArticle extends Article implements Serializable {

    @Column(columnDefinition = "MEDIUMTEXT")
    private String articleText;

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

    

}