package com.famonely.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Incomes.
 */
@Entity
@Table(name = "incomes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Incomes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "spent_money", precision = 21, scale = 2)
    private BigDecimal spentMoney;

    @ManyToOne
    @JsonIgnoreProperties(value = "incomes", allowSetters = true)
    private IncomeType incomeType;

    @ManyToOne
    @JsonIgnoreProperties(value = "incomes", allowSetters = true)
    private UsersFamonely userSpendMoney;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Incomes date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getSpentMoney() {
        return spentMoney;
    }

    public Incomes spentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
        return this;
    }

    public void setSpentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
    }

    public IncomeType getIncomeType() {
        return incomeType;
    }

    public Incomes incomeType(IncomeType incomeType) {
        this.incomeType = incomeType;
        return this;
    }

    public void setIncomeType(IncomeType incomeType) {
        this.incomeType = incomeType;
    }

    public UsersFamonely getUserSpendMoney() {
        return userSpendMoney;
    }

    public Incomes userSpendMoney(UsersFamonely usersFamonely) {
        this.userSpendMoney = usersFamonely;
        return this;
    }

    public void setUserSpendMoney(UsersFamonely usersFamonely) {
        this.userSpendMoney = usersFamonely;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Incomes)) {
            return false;
        }
        return id != null && id.equals(((Incomes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Incomes{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", spentMoney=" + getSpentMoney() +
            "}";
    }
}
