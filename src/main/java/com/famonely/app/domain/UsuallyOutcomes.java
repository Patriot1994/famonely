package com.famonely.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A UsuallyOutcomes.
 */
@Entity
@Table(name = "usually_outcomes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UsuallyOutcomes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pay_day")
    private LocalDate payDay;

    @Column(name = "money", precision = 21, scale = 2)
    private BigDecimal money;

    @Column(name = "is_already_paid")
    private Boolean isAlreadyPaid;

    @ManyToOne
    @JsonIgnoreProperties(value = "usuallyOutcomes", allowSetters = true)
    private OutcomeType outcomeType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPayDay() {
        return payDay;
    }

    public UsuallyOutcomes payDay(LocalDate payDay) {
        this.payDay = payDay;
        return this;
    }

    public void setPayDay(LocalDate payDay) {
        this.payDay = payDay;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public UsuallyOutcomes money(BigDecimal money) {
        this.money = money;
        return this;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Boolean isIsAlreadyPaid() {
        return isAlreadyPaid;
    }

    public UsuallyOutcomes isAlreadyPaid(Boolean isAlreadyPaid) {
        this.isAlreadyPaid = isAlreadyPaid;
        return this;
    }

    public void setIsAlreadyPaid(Boolean isAlreadyPaid) {
        this.isAlreadyPaid = isAlreadyPaid;
    }

    public OutcomeType getOutcomeType() {
        return outcomeType;
    }

    public UsuallyOutcomes outcomeType(OutcomeType outcomeType) {
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
        if (!(o instanceof UsuallyOutcomes)) {
            return false;
        }
        return id != null && id.equals(((UsuallyOutcomes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuallyOutcomes{" +
            "id=" + getId() +
            ", payDay='" + getPayDay() + "'" +
            ", money=" + getMoney() +
            ", isAlreadyPaid='" + isIsAlreadyPaid() + "'" +
            "}";
    }
}
