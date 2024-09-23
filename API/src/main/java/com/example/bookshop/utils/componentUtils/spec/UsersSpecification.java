package com.example.bookshop.utils.componentUtils.spec;


import com.example.bookshop.users.models.UserEntity;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "username" , params = "username" , spec = Like.class),
        @Spec(path = "email", params = "email", spec = Like.class),
        @Spec(path = "role", params = "role", spec = Equal.class),
})
public interface UsersSpecification extends Specification<UserEntity> {

}
