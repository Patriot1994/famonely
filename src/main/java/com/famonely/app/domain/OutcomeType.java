package com.famonely.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A OutcomeType.
 */
@Entity
@Table(name = "outcome_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OutcomeType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_of_outcome")
    private String nameOfOutcome;

    @OneToMany(mappedBy = "outcomeType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Outcomes> outcomes = new HashSet<>();

    @OneToMany(mappedBy = "outcomeType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PlannedOutcomes> plannedOutcomes = new HashSet<>();

    @OneToMany(mappedBy = "outcomeType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<UsuallyOutcomes> usuallyOutcomes = new HashSet<>();

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

    public OutcomeType nameOfOutcome(String nameOfOutcome) {
        this.nameOfOutcome = nameOfOutcome;
        return this;
    }

    public void setNameOfOutcome(String nameOfOutcome) {
        this.nameOfOutcome = nameOfOutcome;
    }

    public Set<Outcomes> getOutcomes() {
        return outcomes;
    }

    public OutcomeType outcomes(Set<Outcomes> outcomes) {
        this.outcomes = outcomes;
        return this;
    }

    public OutcomeType addOutcomes(Outcomes outcomes) {
        this.outcomes.add(outcomes);
        outcomes.setOutcomeType(this);
        return this;
    }

    public OutcomeType removeOutcomes(Outcomes outcomes) {
        this.outcomes.remove(outcomes);
        outcomes.setOutcomeType(null);
        return this;
    }

    public void setOutcomes(Set<Outcomes> outcomes) {
        this.outcomes = outcomes;
    }

    public Set<PlannedOutcomes> getPlannedOutcomes() {
        return plannedOutcomes;
    }

    public OutcomeType plannedOutcomes(Set<PlannedOutcomes> plannedOutcomes) {
        this.plannedOutcomes = plannedOutcomes;
        return this;
    }

    public OutcomeType addPlannedOutcomes(PlannedOutcomes plannedOutcomes) {
        this.plannedOutcomes.add(plannedOutcomes);
        plannedOutcomes.setOutcomeType(this);
        return this;
    }

    public OutcomeType removePlannedOutcomes(PlannedOutcomes plannedOutcomes) {
        this.plannedOutcomes.remove(plannedOutcomes);
        plannedOutcomes.setOutcomeType(null);
        return this;
    }

    public void setPlannedOutcomes(Set<PlannedOutcomes> plannedOutcomes) {
        this.plannedOutcomes = plannedOutcomes;
    }

    public Set<UsuallyOutcomes> getUsuallyOutcomes() {
        return usuallyOutcomes;
    }

    public OutcomeType usuallyOutcomes(Set<UsuallyOutcomes> usuallyOutcomes) {
        this.usuallyOutcomes = usuallyOutcomes;
        return this;
    }

    public OutcomeType addUsuallyOutcomes(UsuallyOutcomes usuallyOutcomes) {
        this.usuallyOutcomes.add(usuallyOutcomes);
        usuallyOutcomes.setOutcomeType(this);
        return this;
    }

    public OutcomeType removeUsuallyOutcomes(UsuallyOutcomes usuallyOutcomes) {
        this.usuallyOutcomes.remove(usuallyOutcomes);
        usuallyOutcomes.setOutcomeType(null);
        return this;
    }

    public void setUsuallyOutcomes(Set<UsuallyOutcomes> usuallyOutcomes) {
        this.usuallyOutcomes = usuallyOutcomes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OutcomeType)) {
            return false;
        }
        return id != null && id.equals(((OutcomeType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OutcomeType{" +
            "id=" + getId() +
            ", nameOfOutcome='" + getNameOfOutcome() + "'" +
            "}";
    }
}
