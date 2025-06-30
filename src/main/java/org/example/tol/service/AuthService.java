package org.example.tol.service;

import org.example.tol.bean.Log;
import org.example.tol.bean.Register;
import org.example.tol.database.entity.User;

public interface AuthService {

    User login(Log request);

    User register(Register request);
}
