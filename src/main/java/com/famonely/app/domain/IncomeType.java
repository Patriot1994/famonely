package com.famonely.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A IncomeType.
 */
@Entity
@Table(name = "income_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IncomeType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_of_outcome")
    private String nameOfOutcome;

    @OneToMany(mappedBy = "incomeType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Incomes> incomes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfOutcome() {
        return nameOfOutcome;
    }

    public IncomeType nameOfOutcome(String nameOfOutcome) {
        this.nameOfOutcome = nameOfOutcome;
        return this;
    }

    public void setNameOfOutcome(String nameOfOutcome) {
        this.nameOfOutcome = nameOfOutcome;
    }

    public Set<Incomes> getIncomes() {
        return incomes;
    }

    public IncomeType incomes(Set<Incomes> incomes) {
        this.incomes = incomes;
        return this;
    }

    public IncomeType addIncomes(Incomes incomes) {
        this.incomes.add(incomes);
        incomes.setIncomeType(this);
        return this;
    }

    public IncomeType removeIncomes(Incomes incomes) {
        this.incomes.remove(incomes);
        incomes.setIncomeType(null);
        return this;
    }

    public void setIncomes(Set<Incomes> incomes) {
        this.incomes = incomes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncomeType)) {
            return false;
        }
        return id != null && id.equals(((IncomeType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IncomeType{" +
            "id=" + getId() +
            ", nameOfOutcome='" + getNameOfOutcome() + "'" +
            "}";
    }
}
