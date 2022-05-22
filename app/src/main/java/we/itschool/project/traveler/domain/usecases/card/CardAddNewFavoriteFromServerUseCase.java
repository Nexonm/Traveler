package we.itschool.project.traveler.domain.usecases.card;

import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardAddNewFavoriteFromServerUseCase {

    CardDomainRepository repository;

    public CardAddNewFavoriteFromServerUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public void cardAddNewFavsFromServ(long id){
        repository.cardAddNewFavsFromServ(id);
    }
}
