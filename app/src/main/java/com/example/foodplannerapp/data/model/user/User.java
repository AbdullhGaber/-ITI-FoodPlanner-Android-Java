package com.example.foodplannerapp.data.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    String uid;
    String name;
    String email;
}
