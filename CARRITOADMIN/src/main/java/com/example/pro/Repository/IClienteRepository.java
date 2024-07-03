package com.example.pro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pro.model.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository <Cliente, Integer> {
}
