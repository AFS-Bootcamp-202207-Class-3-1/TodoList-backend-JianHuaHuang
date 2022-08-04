package com.rest.springbootemployee.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String companyName;



    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "companyId")
    @ToString.Exclude
    //PERSIST:持久保存拥有方实体时，也会持久保存该实体的所有相关数据。
    //NERGE:将分离的实体重新合并到活动的持久性上下文时，也会合并该实体的所有相关数据。//RENOVE:删除一个实体时，也会删除该实体的所有相关数据◇
    //AL宁:以上都适用。
    private List<Employee> employees;

    public Company(int id, String companyName, Employee employee) {
        this.id = id;
        this.companyName = companyName;
        if (this.getEmployees()!=null){
            this.getEmployees().add(employee);
            return;
        }
        this.setEmployees(new ArrayList<>());
        this.getEmployees().add(employee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Company company = (Company) o;
        return id != null && Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
