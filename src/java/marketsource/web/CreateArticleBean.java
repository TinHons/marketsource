/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.ejb.EJB;
import javax.servlet.http.Part;
import marketsource.ejb.AdminBean;
import marketsource.entity.ExternalArticle;
import marketsource.entity.InternalArticle;
import marketsource.util.ArticleCartegoryEnum;

/**
 *
 * @author dell
 */
@Named(value = "createArticleBean")
@SessionScoped
public class CreateArticleBean implements Serializable {
    
    @EJB
    private AdminBean adminBean;

    private ArticleCartegoryEnum cartegory;
    
    private String title;
    
    private String articleCaption;
    
    private String url;
    
    private String articleText;
    
   private Part image1; 
   
   private Part image2; 
   
   private Part image3; 
   
   private String caption1; 
   
   private String caption2; 
   
   private String caption3; 
    
    
    
    /**
     * Creates a new instance of CreateArticleBean
     */
    public CreateArticleBean() {
    }

    public ArticleCartegoryEnum getCartegory() {
        return cartegory;
    }

    public void setCartegory(ArticleCartegoryEnum cartegory) {
        this.cartegory = cartegory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Part getImage1() {
        return image1;
    }

    public void setImage1(Part image1) {
        this.image1 = image1;
    }

    public Part getImage2() {
        return image2;
    }

    public void setImage2(Part image2) {
        this.image2 = image2;
    }

    public Part getImage3() {
        return image3;
    }

    public void setImage3(Part image3) {
        this.image3 = image3;
    }

    public String getCaption1() {
        return caption1;
    }

    public void setCaption1(String caption1) {
        this.caption1 = caption1;
    }

    public String getCaption2() {
        return caption2;
    }

    public void setCaption2(String caption2) {
        this.caption2 = caption2;
    }

    public String getCaption3() {
        return caption3;
    }

    public void setCaption3(String caption3) {
        this.caption3 = caption3;
    }

    public String getArticleCaption() {
        return articleCaption;
    }

    public void setArticleCaption(String articleCaption) {
        this.articleCaption = articleCaption;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }
    
    
    
    

    
    
    
    
    public ArticleCartegoryEnum[] getCartegoryValues(){
    
    
    return ArticleCartegoryEnum.values();
    
    }
    
    public String createExternalArticle(){
    
        ExternalArticle article = new ExternalArticle();
        article.setTitle(title);
        article.setCartegory(cartegory.toString());
        article.setUrl(url);
        article.setCaption(articleCaption);
        
        Part[] imageArray = {image1, image2, image3};
        
        String[] captionArray = {caption1, caption2, caption3};
        
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp dateTime = Timestamp.valueOf(localDateTime);
        article.setCreationDateTime(dateTime);
        
        adminBean.persistArticle(article, imageArray, captionArray);
    
    return "success";
    }
    
    public String createInternalArticle(){
        
    InternalArticle article = new InternalArticle();
        article.setTitle(title);
        article.setCartegory(cartegory.toString());
        article.setArticleText(articleText);
        article.setCaption(articleCaption);
        
        System.out.println(articleText);
        
        Part[] imageArray = {image1, image2, image3};
        
        String[] captionArray = {caption1, caption2, caption3};
        
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp dateTime = Timestamp.valueOf(localDateTime);
        article.setCreationDateTime(dateTime);
        
        adminBean.persistArticle(article, imageArray, captionArray);
    
    return "success";
    
    
    }
    
}
