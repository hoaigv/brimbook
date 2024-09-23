package com.example.bookshop.utils.componentUtils.spec;


import com.example.bookshop.books.models.BookEntity;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
@Join(path = "authors",alias = "auths")
@And({
        @Spec(path = "auths.name" , params = "authorName" , spec = Like.class),
        @Spec(path = "title", params = "bookTitle", spec = Like.class),
})
public interface BookSpecification extends Specification<BookEntity> {

}
