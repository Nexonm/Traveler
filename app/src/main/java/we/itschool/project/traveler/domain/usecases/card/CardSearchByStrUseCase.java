package we.itschool.project.traveler.domain.usecases.card;

import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardSearchByStrUseCase {

    CardDomainRepository repository;

    public CardSearchByStrUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public void cardSearchByStr(String str){
        repository.cardSearchByStr(str);
    }
}
