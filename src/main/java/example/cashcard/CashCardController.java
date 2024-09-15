package example.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.net.URI;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {
    private final CashCardRepository cashCardRepository;


    //конструктор для обязательных полей или объктов класса(вызывается при создании класса)
    private CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }


    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) {
        Optional<CashCard> cashCardOptional =
                Optional.ofNullable(cashCardRepository.findByIdAndOwner(requestedId, principal.getName()));
        if (cashCardOptional.isPresent()){
            return ResponseEntity.ok(cashCardOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest,
                                                UriComponentsBuilder ucb, Principal principal) {
        CashCard cashCardWithOwner = new CashCard(null,
                newCashCardRequest.getAmount(), principal.getName());
        CashCard savedCashCard = cashCardRepository.save(cashCardWithOwner);
        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}")
                .buildAndExpand(savedCashCard.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping
    private ResponseEntity<List<CashCard>> findAll(Pageable pageable, Sort sort, Principal principal) {//Iterable колекция объектов для простоты перебора for-each, можно и в List конвертировать, если нужны методы
        Page<CashCard> page = cashCardRepository.findByOwner(
                principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))
                )
        );

        return ResponseEntity.ok(page.getContent());
    }
}
