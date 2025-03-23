package com.marketplace.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {

    private Integer id;

    private String email;

    private String name;

    private String contactNumber;

    private String status;

    public UserWrapper(Integer id, String email, String name, String contactNumber, String status) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.contactNumber = contactNumber;
        this.status = status;
    }
}
