package com.famonely.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A PlannedOutcomes.
 */
@Entity
@Table(name = "planned_outcomes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlannedOutcomes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "planned_date")
    private LocalDate plannedDate;

    @Column(name = "planned_money", precision = 21, scale = 2)
    private BigDecimal plannedMoney;

    @Column(name = "money_to_goal", precision = 21, scale = 2)
    private BigDecimal moneyToGoal;

    @Column(name = "is_already_paid")
    private Boolean isAlreadyPaid;

    @ManyToOne
    @JsonIgnoreProperties(value = "plannedOutcomes", allowSetters = true)
    private OutcomeType outcomeType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPlannedDate() {
        return plannedDate;
    }

    public PlannedOutcomes plannedDate(LocalDate plannedDate) {
        this.plannedDate = plannedDate;
        return this;
    }

    public void setPlannedDate(LocalDate plannedDate) {
        this.plannedDate = plannedDate;
    }

    public BigDecimal getPlannedMoney() {
        return plannedMoney;
    }

    public PlannedOutcomes plannedMoney(BigDecimal plannedMoney) {
        this.plannedMoney = plannedMoney;
        return this;
    }

    public void setPlannedMoney(BigDecimal plannedMoney) {
        this.plannedMoney = plannedMoney;
    }

    public BigDecimal getMoneyToGoal() {
        return moneyToGoal;
    }

    public PlannedOutcomes moneyToGoal(BigDecimal moneyToGoal) {
        this.moneyToGoal = moneyToGoal;
        return this;
    }

    public void setMoneyToGoal(BigDecimal moneyToGoal) {
        this.moneyToGoal = moneyToGoal;
    }

    public Boolean isIsAlreadyPaid() {
        return isAlreadyPaid;
    }

    public PlannedOutcomes isAlreadyPaid(Boolean isAlreadyPaid) {
        this.isAlreadyPaid = isAlreadyPaid;
        return this;
    }

    public void setIsAlreadyPaid(Boolean isAlreadyPaid) {
        this.isAlreadyPaid = isAlreadyPaid;
    }

    public OutcomeType getOutcomeType() {
        return outcomeType;
    }

    public PlannedOutcomes outcomeType(OutcomeType outcomeType) {
        this.outcomeType = outcomeType;
        return this;
    }

    public void setOutcomeType(OutcomeType outcomeType) {
        this.outcomeType = outcomeType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlannedOutcomes)) {
            return false;
        }
        return id != null && id.equals(((PlannedOutcomes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlannedOutcomes{" +
            "id=" + getId() +
            ", plannedDate='" + getPlannedDate() + "'" +
            ", plannedMoney=" + getPlannedMoney() +
            ", moneyToGoal=" + getMoneyToGoal() +
            ", isAlreadyPaid='" + isIsAlreadyPaid() + "'" +
            "}";
    }
}
