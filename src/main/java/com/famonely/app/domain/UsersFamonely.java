package com.famonely.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UsersFamonely.
 */
@Entity
@Table(name = "users_famonely")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UsersFamonely implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "sweet_shortname")
    private String sweetShortname;

    @OneToMany(mappedBy = "userSpendMoney")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Outcomes> outcomes = new HashSet<>();

    @OneToMany(mappedBy = "userSpendMoney")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Incomes> incomes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UsersFamonely name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public UsersFamonely surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSweetShortname() {
        return sweetShortname;
    }

    public UsersFamonely sweetShortname(String sweetShortname) {
        this.sweetShortname = sweetShortname;
        return this;
    }

    public void setSweetShortname(String sweetShortname) {
        this.sweetShortname = sweetShortname;
    }

    public Set<Outcomes> getOutcomes() {
        return outcomes;
    }

    public UsersFamonely outcomes(Set<Outcomes> outcomes) {
        this.outcomes = outcomes;
        return this;
    }

    public UsersFamonely addOutcomes(Outcomes outcomes) {
        this.outcomes.add(outcomes);
        outcomes.setUserSpendMoney(this);
        return this;
    }

    public UsersFamonely removeOutcomes(Outcomes outcomes) {
        this.outcomes.remove(outcomes);
        outcomes.setUserSpendMoney(null);
        return this;
    }

    public void setOutcomes(Set<Outcomes> outcomes) {
        this.outcomes = outcomes;
    }

    public Set<Incomes> getIncomes() {
        return incomes;
    }

    public UsersFamonely incomes(Set<Incomes> incomes) {
        this.incomes = incomes;
        return this;
    }

    public UsersFamonely addIncomes(Incomes incomes) {
        this.incomes.add(incomes);
        incomes.setUserSpendMoney(this);
        return this;
    }

    public UsersFamonely removeIncomes(Incomes incomes) {
        this.incomes.remove(incomes);
        incomes.setUserSpendMoney(null);
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
        if (!(o instanceof UsersFamonely)) {
            return false;
        }
        return id != null && id.equals(((UsersFamonely) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsersFamonely{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", sweetShortname='" + getSweetShortname() + "'" +
            "}";
    }
}
