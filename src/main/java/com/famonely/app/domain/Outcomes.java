package com.famonely.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Outcomes.
 */
@Entity
@Table(name = "outcomes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Outcomes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "spent_money", precision = 21, scale = 2)
    private BigDecimal spentMoney;

    @Column(name = "is_monthly_out_come")
    private Boolean isMonthlyOutCome;

    @Column(name = "is_planned_out_come")
    private Boolean isPlannedOutCome;

    @ManyToOne
    @JsonIgnoreProperties(value = "outcomes", allowSetters = true)
    private OutcomeType outcomeType;

    @ManyToOne
    @JsonIgnoreProperties(value = "outcomes", allowSetters = true)
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

    public Outcomes date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getSpentMoney() {
        return spentMoney;
    }

    public Outcomes spentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
        return this;
    }

    public void setSpentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
    }

    public Boolean isIsMonthlyOutCome() {
        return isMonthlyOutCome;
    }

    public Outcomes isMonthlyOutCome(Boolean isMonthlyOutCome) {
        this.isMonthlyOutCome = isMonthlyOutCome;
        return this;
    }

    public void setIsMonthlyOutCome(Boolean isMonthlyOutCome) {
        this.isMonthlyOutCome = isMonthlyOutCome;
    }

    public Boolean isIsPlannedOutCome() {
        return isPlannedOutCome;
    }

    public Outcomes isPlannedOutCome(Boolean isPlannedOutCome) {
        this.isPlannedOutCome = isPlannedOutCome;
        return this;
    }

    public void setIsPlannedOutCome(Boolean isPlannedOutCome) {
        this.isPlannedOutCome = isPlannedOutCome;
    }

    public OutcomeType getOutcomeType() {
        return outcomeType;
    }

    public Outcomes outcomeType(OutcomeType outcomeType) {
        this.outcomeType = outcomeType;
        return this;
    }

    public void setOutcomeType(OutcomeType outcomeType) {
        this.outcomeType = outcomeType;
    }

    public UsersFamonely getUserSpendMoney() {
        return userSpendMoney;
    }

    public Outcomes userSpendMoney(UsersFamonely usersFamonely) {
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
        if (!(o instanceof Outcomes)) {
            return false;
        }
        return id != null && id.equals(((Outcomes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Outcomes{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", spentMoney=" + getSpentMoney() +
            ", isMonthlyOutCome='" + isIsMonthlyOutCome() + "'" +
            ", isPlannedOutCome='" + isIsPlannedOutCome() + "'" +
            "}";
    }
}
