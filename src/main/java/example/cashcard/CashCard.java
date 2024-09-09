package example.cashcard;

public class CashCard {
    private Long id;
    private Double amount;

    public CashCard(Long id, Double amount) {
        this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }
}