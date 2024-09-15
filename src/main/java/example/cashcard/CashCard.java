package example.cashcard;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class CashCard {
    private @Id Long id;
    private Double amount;
    private String owner;

    public CashCard(Long id, Double amount, String owner) {
        this.id = id;
        this.amount = amount;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashCard cashCard = (CashCard) o;
        return Objects.equals(id, cashCard.id) &&
                Objects.equals(amount, cashCard.amount) &&
                Objects.equals(owner, cashCard.owner);
    }
}