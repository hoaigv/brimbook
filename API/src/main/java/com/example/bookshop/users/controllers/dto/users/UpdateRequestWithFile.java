package com.example.bookshop.users.controllers.dto.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRequestWithFile {
    @JsonProperty("user")
     UserUpdateRequest user;

    @JsonProperty("file")
     String file;

}
